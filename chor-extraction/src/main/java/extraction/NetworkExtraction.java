package extraction;

import ast.cc.interfaces.CCNode;
import ast.cc.nodes.Condition;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.ExtractionLabel;
import ast.sp.interfaces.SPNode;
import ast.sp.labels.Communication;
import ast.sp.labels.Else;
import ast.sp.labels.Selection;
import ast.sp.labels.Then;
import ast.sp.nodes.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class NetworkExtraction {
    private DirectedGraph<Network, ExtractionLabel> graph;
    private Deque<Network> networks;
    private Network root;

    public NetworkExtraction(SPNode network) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        graph = new DefaultDirectedGraph<>(ExtractionLabel.class);
        graph.addVertex((Network) network);
        root = (Network) network;
        networks = new ArrayDeque<>();
        networks.add((Network) network);
        extract();
    }

    public CCNode graphToChoreograpy(){

        if (graph.incomingEdgesOf(root).size() == 0) {
             return extractNodeForward(root);
        }
        else {
            return null;
            //cyclic graph
        }

    }

    private CCNode extractNodeForward(Network leaf){
        CCNode retval = null;
        Set<ExtractionLabel> edges = graph.outgoingEdgesOf(leaf);
        switch( edges.size() ) {
            case 0:
                for (ProcessBehaviour processbehavior: leaf.getNetwork()) {
                    if (!(processbehavior.getMain() instanceof TerminationSP))
                        return retval;
                }
                retval = new Termination();
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
                }
                break;
            case 2:
                ExtractionLabel[] labels = edges.toArray(new ExtractionLabel[2]);
                Then thenLabel = (labels[0] instanceof Then) ? (Then) labels[0] : (Then) labels[1];
                Else elseLabel = (labels[0] instanceof Then) ? (Else) labels[1] : (Else) labels[0];

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

    private void extract() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        while( !networks.isEmpty() ) {
            Network network = networks.removeFirst();
            List<ProcessBehaviour> processBehaviours = network.getNetwork();
            Boolean ifProcessed = false;

            for (ProcessBehaviour processBehaviour: processBehaviours) {
                if (!ifProcessed) {
                    Behaviour process = processBehaviour.getMain();
                    ifProcessed = process(process, processBehaviour, processBehaviours,  network);
                }
            }
        }
    }

    private boolean process(Behaviour behaviour, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        return  Match(behaviour).of(
                Case(instanceOf(Receiving.class), i -> process(i, processBehaviour, processBehaviours,  network)),
                Case(instanceOf(Sending.class), i -> process(i, processBehaviour, processBehaviours, network)),
                Case(instanceOf(Offering.class), i -> process(i, processBehaviour, processBehaviours, network)),
                Case(instanceOf(SelectionSP.class), i -> process(i, processBehaviour, processBehaviours, network)),
                Case(instanceOf(ConditionSP.class), i -> process(i, processBehaviour, processBehaviours, network)),
                Case(instanceOf(ProcedureInvocationSP.class), i -> process(i, processBehaviour, processBehaviours, network)),
                Case(instanceOf(TerminationSP.class), i -> false)
                //Case($(), o -> { throw new Exception(); })
        );
    }

    private boolean process(Receiving receiving, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        String sendingProcess = receiving.getProcess();
        ProcessBehaviour node =  processBehaviours.stream().filter(entry->entry.getProcess().equals(sendingProcess)).findFirst().get();

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
        nextNodeBehaviours.add(new ProcessBehaviour (processBehaviour.getProcess(), processBehaviour.getProcedures(), receivingBehaviour));
        nextNodeBehaviours.add(new ProcessBehaviour (node.getProcess(), node.getProcedures(), sendingBehaviour));
        Network nextNode = new Network(nextNodeBehaviours);

        networks.addLast(nextNode);
        graph.addVertex(nextNode);
        return graph.addEdge(network, nextNode, label);
        } else return false;

    }

    private boolean process(Sending sending, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        String receivingProcess = sending.getProcess();
        ProcessBehaviour node =  processBehaviours.stream().filter(entry->entry.getProcess().equals(receivingProcess)).findFirst().get();

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
        nextNodeBehaviours.add(new ProcessBehaviour(processBehaviour.getProcess(), processBehaviour.getProcedures(), sendingBehaviour));
        nextNodeBehaviours.add(new ProcessBehaviour(node.getProcess(), node.getProcedures(), receivingBehaviour));
        Network nextNode = new Network(nextNodeBehaviours);

        networks.addLast(nextNode);
        graph.addVertex(nextNode);
        return graph.addEdge(network, nextNode, label);
        } else return false;
    }

    private boolean process(Offering offering, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        String selectionProcess = offering.getProcess();
        ProcessBehaviour node =  processBehaviours.stream().filter(entry->entry.getProcess().equals(selectionProcess)).findFirst().get();

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
            nextNodeBehaviours.add(new ProcessBehaviour
                    (processBehaviour.getProcess(), processBehaviour.getProcedures(), offeringBehaviour));
            nextNodeBehaviours.add(new ProcessBehaviour
                    (node.getProcess(), node.getProcedures(), selectionBehaviour));
            Network nextNode = new Network(nextNodeBehaviours);

            graph.addVertex(nextNode);
            networks.addLast(nextNode);
            return graph.addEdge(network, nextNode, label);
        } else return false;

    }

    private boolean process(SelectionSP selection, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        String offeringProcess = selection.getProcess();
        ProcessBehaviour node =  processBehaviours.stream().filter(entry->entry.getProcess().equals(offeringProcess)).findFirst().get();

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
            nextNodeBehaviours.add(new ProcessBehaviour
                    (processBehaviour.getProcess(), processBehaviour.getProcedures(), selectionBehaviour));
            nextNodeBehaviours.add(new ProcessBehaviour
                    (node.getProcess(), node.getProcedures(), offeringBehaviour));
            Network nextNode = new Network(nextNodeBehaviours);

            networks.addLast(nextNode);
            graph.addVertex(nextNode);
            return graph.addEdge(network, nextNode, label);
        } else return false;
    }

    private boolean process(ConditionSP condition, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){

        ExtractionLabel labelThen = new Then(condition.getProcess(), condition.getExpression());
        ExtractionLabel labelElse = new Else(condition.getProcess(), condition.getExpression());

        Behaviour thenBehaviour = condition.getThenBehaviour();
        Behaviour elseBehaviour = condition.getElseBehaviour();

        List<ProcessBehaviour> thenNodeBehaviours = new ArrayList<>(processBehaviours);
        List<ProcessBehaviour> elseNodeBehaviours = new ArrayList<>(processBehaviours);
        thenNodeBehaviours.remove(processBehaviour);
        elseNodeBehaviours.remove(processBehaviour);

        thenNodeBehaviours.add(new ProcessBehaviour
                (processBehaviour.getProcess(), processBehaviour.getProcedures(), thenBehaviour));
        elseNodeBehaviours.add(new ProcessBehaviour
                (processBehaviour.getProcess(), processBehaviour.getProcedures(), elseBehaviour));

        Network networkThen = new Network(thenNodeBehaviours);
        Network networkElse = new Network(elseNodeBehaviours);

        graph.addVertex(networkThen);
        graph.addVertex(networkElse);

        networks.addLast(networkThen);
        networks.addLast(networkElse);

        graph.addEdge(network, networkThen, labelThen);
        return graph.addEdge(network, networkElse, labelElse);
    }

    private boolean process(ProcedureInvocationSP process, ProcessBehaviour processBehaviour, List<ProcessBehaviour> processBehaviours,  Network network){
        Optional<ProcedureDefinitionSP> def = processBehaviour.getProcedures().stream().filter(i -> i.getProcedure().equals(process.getProcedure())).findFirst();
        return def.isPresent() && process(def.get().getBehaviour(), processBehaviour, processBehaviours, network);
    }
}