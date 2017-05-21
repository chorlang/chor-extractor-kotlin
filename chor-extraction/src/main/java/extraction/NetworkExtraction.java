package extraction;

import ast.cc.interfaces.CCNode;
import ast.cc.nodes.Condition;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.ExtractionLabel;
import ast.sp.interfaces.SPNode;
import ast.sp.labels.*;
import ast.sp.nodes.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.*;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class NetworkExtraction {
    private DirectedGraph<Network, ExtractionLabel> graph;
    private Deque<Network> networks;
    private Network root;
    private Network current;

    public NetworkExtraction(SPNode network) throws Exception {
        graph = new DefaultDirectedGraph<>(ExtractionLabel.class);
        graph.addVertex((Network) network);

        root = (Network) network;

        networks = new ArrayDeque<>();
        networks.add((Network) network);

        createGraph();
    }

    private void createGraph() throws Exception {
        while (!networks.isEmpty()) {
            current = networks.removeFirst();

            Pair<Deque<ProcessBehaviour>, Deque<ProcessBehaviour>> pair = findProcedureInvocation(current);
            processNetworkNode(current, pair.getFirst(), pair.getSecond());

        }
    }

    private void processMain(Network network, Deque<ProcessBehaviour> withProcInvoc, Deque<ProcessBehaviour> noProcInvoc) throws Exception {
        ProcessBehaviour pb = noProcInvoc.removeFirst();
        if (process(pb, network)) {
            createGraph();
        } else {
            processNetworkNode(network, withProcInvoc, noProcInvoc);
        }
    }

    private void processNetworkNode(Network network, Deque<ProcessBehaviour> mainWithProcInvoc, Deque<ProcessBehaviour> mainWithoutProcInvoc) throws Exception {
        if (!mainWithoutProcInvoc.isEmpty()) {
            //process nodes in deque of processes without Procedure invocation. If processing completes without issues go to next network processng via create graph fun
            // else take the next node from processes without Procedure invocation and try to process it
            processMain(network, mainWithProcInvoc, mainWithoutProcInvoc);
        } else if (!mainWithProcInvoc.isEmpty()) {
            //find all unvisited procedures
            Deque<ProcessBehaviour> unvisitedproc = new ArrayDeque<>();
            mainWithProcInvoc.stream().filter(i -> !i.getVisitedProcedures().contains(( (ProcedureInvocationSP) i.getMain() ).getProcedure())).forEach(unvisitedproc::add);

            // if there is only visited procedures
            if (unvisitedproc.isEmpty()) {
                //compare current network node with every else in the graph.

                Network n = new Network(network.getNetwork());
                n.getNetwork().stream().forEach(i -> i.clearVisitedProcedures());
                // if it equals to one of them - we are done. make the edge to this node

                networks.add(current);
                Optional<Network> first = networks.stream().filter(i -> i.toString().equals(network.toString())).findFirst();
                networks.remove(current);
                if (first.isPresent()) {
                    graph.addEdge(network, first.get(), new Recursion());

                } else throw new Exception("somethig bad happen"); // if it is not throw exception*/

            } else {//if there is some unvisited procedures
                //unfold one of the procedure and add it to the list of the procedures with or without process invocation
                ProcessBehaviour pb = unvisitedproc.getFirst();

                //create temporal network
                List<ProcessBehaviour> newproclist = new ArrayList<>();
                network.getNetwork().forEach(i -> {
                    if (!( i.getProcess().equals(pb.getProcess()) )) newproclist.add(i);
                });
                //find the invoked procedure name in main
                String procname = ( (ProcedureInvocationSP) pb.getMain() ).getProcedure();

                ProcessBehaviour unfold = unfold(pb, procname);
                unfold.setVisitedProcedures(procname);
                newproclist.add(unfold);

                Network tempnetwork = new Network(newproclist);

                //process the process behaviours again to find out new distribution of processe with or without process invocation
                Pair<Deque<ProcessBehaviour>, Deque<ProcessBehaviour>> pair = findProcedureInvocation(tempnetwork);

                //recursivly run the function on the new tempnetwork
                processNetworkNode(tempnetwork, pair.getFirst(), pair.getSecond());
            }
        } else if (!network.getNetwork().stream().anyMatch(i -> !( i.getMain() instanceof TerminationSP ))) {
        } else throw new Exception("No possible moves, but not all nodes terminated");
    }

    /**
     * @param pb - process behaviour with the Procedure Invocation as the main entry node
     * @return process behaviour of the same process with unfolded procedure
     * @throws Exception if there is no corresponding procedure definition in procedures definitons for the one invoked in main
     */
    private ProcessBehaviour unfold(ProcessBehaviour pb, String procname) throws Exception {
        //find the corresponding procedure in the list of procedure definitions
        Optional<ProcedureDefinitionSP> sp = pb.getProcedures().stream().filter(i -> i.getProcedure().equals(procname)).findAny();
        if (sp.isPresent()) { //if such procedure exist
            //create the new process behaviour node with the procedure definition behaviour instead of main behaviour
            ProcessBehaviour newpb = new ProcessBehaviour(pb.getProcess(), pb.getProcedures(), sp.get().getBehaviour());
            if (sp.get().findRecProcCall(sp.get().getProcedure())) {
                //if the procedure is recursive, add it to the list of visited procedures in process behaviour
                newpb.setVisitedProcedures(procname);
            }
            return newpb;
        } else //ther is no corresponding procedure definition in the process
            throw new Exception();
    }

    private Pair<Deque<ProcessBehaviour>, Deque<ProcessBehaviour>> findProcedureInvocation(Network network) {
        Deque<ProcessBehaviour> withProcInvoc = new ArrayDeque<>();
        Deque<ProcessBehaviour> noProcInvoc = new ArrayDeque<>();
        network.getNetwork().stream().forEach(i -> {
            if (i.getMain() instanceof ProcedureInvocationSP) withProcInvoc.add(i);
            else noProcInvoc.add(i);
        });
        return new Pair<>(withProcInvoc, noProcInvoc);
    }

    private boolean process(ProcessBehaviour processBehaviour, Network network) {
        Behaviour bh = processBehaviour.getMain();

        return Match(bh).of(
                Case(instanceOf(Receiving.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(Sending.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(Offering.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(SelectionSP.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(ConditionSP.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(ProcedureInvocationSP.class), i -> process(i, processBehaviour, network)),
                Case(instanceOf(TerminationSP.class), i -> false)
        );
    }

    private boolean process(Receiving receiving, ProcessBehaviour processBehaviour, Network network) {
        List<ProcessBehaviour> processBehaviours = network.getNetwork();

        String sendingProcess = receiving.getProcess();
        ProcessBehaviour node = processBehaviours.stream().filter(entry -> entry.getProcess().equals(sendingProcess)).findFirst().get();

        if (( node != null )
                && ( node.getMain() instanceof Sending )
                && ( ( (Sending) node.getMain() ).getProcess().equals(processBehaviour.getProcess()) )) {

            Sending sending = (Sending) node.getMain();
            String receivingProcess = sending.getProcess();

            ExtractionLabel label = new Communication(sendingProcess, receivingProcess, sending.getExpression());

            Behaviour receivingBehaviour = receiving.getContinuation();
            Behaviour sendingBehaviour = sending.getContinuation();

            List<ProcessBehaviour> nextNodeBehaviours = new ArrayList<>(processBehaviours);
            nextNodeBehaviours.remove(processBehaviour);
            nextNodeBehaviours.remove(node);

            ProcessBehaviour pbr = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), receivingBehaviour);
            ProcessBehaviour pbs = new ProcessBehaviour(node.getProcess(), node.getProcedures(), sendingBehaviour);
            processBehaviour.getVisitedProcedures().stream().forEach(i -> pbr.setVisitedProcedures(i));
            node.getVisitedProcedures().stream().forEach(i -> pbs.setVisitedProcedures(i));

            nextNodeBehaviours.add(pbr);
            nextNodeBehaviours.add(pbs);
            Network nextNode = new Network(nextNodeBehaviours);

            networks.addLast(nextNode);
            graph.addVertex(nextNode);
            return graph.addEdge(current, nextNode, label);
        } else return false;

    }

    private boolean process(Sending sending, ProcessBehaviour processBehaviour, Network network) {
        List<ProcessBehaviour> processBehaviours = network.getNetwork();
        String receivingProcess = sending.getProcess();
        ProcessBehaviour node = processBehaviours.stream().filter(entry -> entry.getProcess().equals(receivingProcess)).findFirst().get();

        if (( node != null )
                && ( node.getMain() instanceof Receiving )
                && ( ( (Receiving) node.getMain() ).getProcess().equals(processBehaviour.getProcess()) )) {

            Receiving receiving = (Receiving) node.getMain();
            String sendingProcess = receiving.getProcess();
            ExtractionLabel label = new Communication(sendingProcess, receivingProcess, sending.getExpression());
            Behaviour receivingBehaviour = receiving.getContinuation();
            Behaviour sendingBehaviour = sending.getContinuation();
            List<ProcessBehaviour> nextNodeBehaviours = new ArrayList<>(processBehaviours);
            nextNodeBehaviours.remove(processBehaviour);
            nextNodeBehaviours.remove(node);
            ProcessBehaviour pbs = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), sendingBehaviour);
            ProcessBehaviour pbr = new ProcessBehaviour(node.getProcess(), node.getProcedures(), receivingBehaviour);

            //copy visited procedures
            processBehaviour.getVisitedProcedures().stream().forEach(i -> pbs.setVisitedProcedures(i));
            node.getVisitedProcedures().stream().forEach(i -> pbr.setVisitedProcedures(i));

            nextNodeBehaviours.add(pbs);
            nextNodeBehaviours.add(pbr);
            Network nextNode = new Network(nextNodeBehaviours);

            networks.addLast(nextNode);
            graph.addVertex(nextNode);
            return graph.addEdge(current, nextNode, label);
        } else return false;
    }

    private boolean process(Offering offering, ProcessBehaviour processBehaviour, Network network) {
        List<ProcessBehaviour> processBehaviours = network.getNetwork();
        String selectionProcess = offering.getProcess();
        ProcessBehaviour node = processBehaviours.stream().filter(entry -> entry.getProcess().equals(selectionProcess)).findFirst().get();

        if (( node != null )
                && ( node.getMain() instanceof SelectionSP )
                && ( ( (SelectionSP) node.getMain() ).getProcess().equals(processBehaviour.getProcess()) )) {

            SelectionSP selection = (SelectionSP) node.getMain();
            String offeringProcess = selection.getProcess();

            ExtractionLabel label = new ast.sp.labels.Selection(selectionProcess, offeringProcess, selection.getLabel());

            Behaviour selectionBehaviour = selection.getContinuation();
            Behaviour offeringBehaviour = offering.getLabels().get(selection.getLabel());
            List<ProcessBehaviour> nextNodeBehaviours = new ArrayList<>(processBehaviours);
            nextNodeBehaviours.remove(processBehaviour);
            nextNodeBehaviours.remove(node);

            ProcessBehaviour pbo = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), offeringBehaviour);
            ProcessBehaviour pbc = new ProcessBehaviour(node.getProcess(), node.getProcedures(), selectionBehaviour);

            processBehaviour.getVisitedProcedures().stream().forEach(i -> pbo.setVisitedProcedures(i));
            node.getVisitedProcedures().stream().forEach(i -> pbc.setVisitedProcedures(i));

            nextNodeBehaviours.add(pbo);
            nextNodeBehaviours.add(pbc);
            Network nextNode = new Network(nextNodeBehaviours);

            graph.addVertex(nextNode);
            networks.addLast(nextNode);
            return graph.addEdge(current, nextNode, label);
        } else return false;

    }

    private boolean process(SelectionSP selection, ProcessBehaviour processBehaviour, Network network) {
        List<ProcessBehaviour> processBehaviours = network.getNetwork();
        String offeringProcess = selection.getProcess();
        ProcessBehaviour node = processBehaviours.stream().filter(entry -> entry.getProcess().equals(offeringProcess)).findFirst().get();

        if (( node != null )
                && ( node.getMain() instanceof Offering )
                && ( ( (Offering) node.getMain() ).getProcess().equals(processBehaviour.getProcess()) )) {
            Offering offering = (Offering) node.getMain();
            String selectionProcess = offering.getProcess();

            ExtractionLabel label = new ast.sp.labels.Selection(selectionProcess, offeringProcess, selection.getLabel());

            Behaviour selectionBehaviour = selection.getContinuation();
            Behaviour offeringBehaviour = offering.getLabels().get(selection.getLabel());
            List<ProcessBehaviour> nextNodeBehaviours = new ArrayList<>(processBehaviours);
            nextNodeBehaviours.remove(processBehaviour);
            nextNodeBehaviours.remove(node);

            ProcessBehaviour pbc = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), selectionBehaviour);
            ProcessBehaviour pbo = new ProcessBehaviour(node.getProcess(), node.getProcedures(), offeringBehaviour);
            processBehaviour.getVisitedProcedures().stream().forEach(i -> pbc.setVisitedProcedures(i));
            node.getVisitedProcedures().stream().forEach(i -> pbo.setVisitedProcedures(i));

            nextNodeBehaviours.add(pbc);
            nextNodeBehaviours.add(pbo);
            Network nextNode = new Network(nextNodeBehaviours);

            networks.addLast(nextNode);
            graph.addVertex(nextNode);
            return graph.addEdge(current, nextNode, label);
        } else return false;
    }

    private boolean process(ConditionSP condition, ProcessBehaviour processBehaviour, Network network) {
        List<ProcessBehaviour> processBehaviours = network.getNetwork();
        ExtractionLabel labelThen = new Then(condition.getProcess(), condition.getExpression());
        ExtractionLabel labelElse = new Else(condition.getProcess(), condition.getExpression());

        Behaviour thenBehaviour = condition.getThenBehaviour();
        Behaviour elseBehaviour = condition.getElseBehaviour();

        List<ProcessBehaviour> thenNodeBehaviours = new ArrayList<>(processBehaviours);
        List<ProcessBehaviour> elseNodeBehaviours = new ArrayList<>(processBehaviours);
        thenNodeBehaviours.remove(processBehaviour);
        elseNodeBehaviours.remove(processBehaviour);

        ProcessBehaviour pbt = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), thenBehaviour);
        ProcessBehaviour pbe = new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), elseBehaviour);
        processBehaviour.getVisitedProcedures().stream().forEach(i -> {pbt.setVisitedProcedures(i); pbe.setVisitedProcedures(i);});

        thenNodeBehaviours.add(pbt);
        elseNodeBehaviours.add(pbe);

        Network networkThen = new Network(thenNodeBehaviours);
        Network networkElse = new Network(elseNodeBehaviours);

        graph.addVertex(networkThen);
        graph.addVertex(networkElse);

        networks.addLast(networkThen);
        networks.addLast(networkElse);

        graph.addEdge(current, networkThen, labelThen);
        return graph.addEdge(current, networkElse, labelElse);
    }

    private boolean process(ProcedureInvocationSP process, ProcessBehaviour processBehaviour, Network network) {
        Optional<ProcedureDefinitionSP> def = processBehaviour.getProcedures().stream().filter(i -> i.getProcedure().equals(process.getProcedure())).findFirst();
        return def.isPresent() && process((ProcessBehaviour) def.get().getBehaviour(), network);
    }

    public CCNode graphToChoreograpy() {

        if (graph.incomingEdgesOf(root).size() == 0) {
            return extractNodeForward(root);
        } else {
            return null;
            //cyclic graph
        }

    }

    private CCNode extractNodeForward(Network leaf) {
        CCNode retval = null;
        Set<ExtractionLabel> edges = graph.outgoingEdgesOf(leaf);
        switch (edges.size()) {
            case 0:
                if (leaf.isProjectable()) {
                    retval = new Termination();
                }
                break;
            case 1:
                ExtractionLabel edge = edges.iterator().next();
                Network target = graph.getEdgeTarget(edge);

                if (edge instanceof Communication) {
                    Communication e = (Communication) edge;
                    CCNode continuation = extractNodeForward(target);
                    retval = new ast.cc.nodes.Communication(e.getSender(), e.getReceiver(), e.getExpression(), continuation);
                } else if (edge instanceof Selection) {
                    Selection e = (Selection) edge;
                    retval = new ast.cc.nodes.Selection(e.getSender(), e.getReceiver(), e.getLabel(), extractNodeForward(target));
                } else if (edge instanceof Recursion) {
                    retval = new Termination();
                }
                break;
            case 2:
                ExtractionLabel[] labels = edges.toArray(new ExtractionLabel[2]);
                Then thenLabel = ( labels[0] instanceof Then ) ? (Then) labels[0] : (Then) labels[1];
                Else elseLabel = ( labels[0] instanceof Then ) ? (Else) labels[1] : (Else) labels[0];

                // throw exception is processName or expression is different in the two nodes

                retval = new Condition(
                        thenLabel.getProcess(),
                        thenLabel.getExpression(),
                        extractNodeForward(graph.getEdgeTarget(thenLabel)),
                        extractNodeForward(graph.getEdgeTarget(elseLabel))
                );
                break;
            default:
                break;
            //throw new RuntimeException("AAAAH!");
        }
        return retval;
    }
}
