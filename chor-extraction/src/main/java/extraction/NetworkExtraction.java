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

public class NetworkExtraction {
    DirectedGraph<Network, ExtractionLabel> graph;

    public NetworkExtraction(SPNode network) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        graph = new DefaultDirectedGraph<>(ExtractionLabel.class);
        graph.addVertex((Network) network);
        Deque<Network> deque = new ArrayDeque<>();
        deque.add((Network) network);
        extract(deque);
    }

    public DirectedGraph<Network, ExtractionLabel> getGraph() {
        return graph;
    }

    public CCNode graphToChoreograpy(){

        if (findroot().isPresent()) {
            Network node = findroot().get();
             return extractNodeForward(node);
        }
        else {
            return null;
            //asyclic graph
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

    private boolean checkLeaveTerminate(Network leaf){
        for (ProcessBehaviour process: leaf.getNetwork()) {
            if (!(process.getMain() instanceof TerminationSP))
                return false;
        }
        return true;
    }

    private CCNode extractNodeBackward(Network leaf){
        return null;
    }

    private Optional<Network> findroot(){
        for (Network entry : graph.vertexSet()) {
            if (graph.inDegreeOf(entry) == 0){
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    /*private Network findleaves(){
        HashMap<String, SPNode> leaves = new HashMap<>();
        for (Network entry : graph.vertexSet()) {
            if (graph.outDegreeOf(entry) == 0){
                leaves.putAll(entry);
            }
        }
        return leaves;
    }*/


    private void extract(Deque<Network> networks)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        while( !networks.isEmpty() ) {
            Network network = networks.removeFirst();
            List<ProcessBehaviour> processBehaviours = network.getNetwork();
            Boolean ifProcessed = false;

            for (ProcessBehaviour processBehaviour: processBehaviours) {
                Behaviour process = processBehaviour.getMain();

                if (!ifProcessed) {

                    if (process instanceof Receiving) {

                        Receiving receiving = (Receiving) process;
                        String sendingProcess = receiving.getProcess();

                        ProcessBehaviour node = null;
                        for (ProcessBehaviour entry : processBehaviours) {
                            if (entry.getProcess().equals(sendingProcess)) {
                                node = entry;
                                break;
                            }
                        }

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
                            nextNodeBehaviours.add(new ProcessBehaviour
                                    (processBehaviour.getProcess(), processBehaviour.getProcedures(), receivingBehaviour));
                            nextNodeBehaviours.add(new ProcessBehaviour
                                    (node.getProcess(), node.getProcedures(), sendingBehaviour));
                            Network nextNode = new Network(nextNodeBehaviours);

                            graph.addVertex(nextNode);
                            ifProcessed = graph.addEdge(network, nextNode, label);

                            networks.addLast(nextNode);
                        }
                    } else if (process instanceof Sending) {

                        Sending sending = (Sending) process;
                        String receivingProcess = sending.getProcess();

                        ProcessBehaviour node = null;
                        for (ProcessBehaviour entry : processBehaviours) {
                            if (entry.getProcess().equals(receivingProcess)) {
                                node = entry;
                                break;
                            }
                        }

                        if (( node != null )
                                && ( node.getMain() instanceof Receiving )
                                && ( processBehaviour.getProcess().equals(( (Receiving) node.getMain() ).getProcess()) )) {
                            Receiving receiving = (Receiving) node.getMain();
                            String sendingProcess = receiving.getProcess();

                            ExtractionLabel label = new Communication(sendingProcess, receivingProcess, sending.getExpression());
                            Behaviour receivingBehaviour = receiving.getContinuation();
                            Behaviour sendingBehaviour = sending.getContinuation();
                            List<ProcessBehaviour> nextNodeBehaviours = new ArrayList<>(processBehaviours);
                            nextNodeBehaviours.remove(processBehaviour);
                            nextNodeBehaviours.remove(node);
                            nextNodeBehaviours.add(new ProcessBehaviour
                                    (processBehaviour.getProcess(), processBehaviour.getProcedures(), sendingBehaviour));
                            nextNodeBehaviours.add(new ProcessBehaviour
                                    (node.getProcess(), node.getProcedures(), receivingBehaviour));
                            Network nextNode = new Network(nextNodeBehaviours);

                            graph.addVertex(nextNode);
                            ifProcessed = graph.addEdge(network, nextNode, label);

                            networks.addLast(nextNode);
                        }
                    } else if (process instanceof Offering) {

                        Offering offering = (Offering) process;
                        String selectionProcess = offering.getProcess();

                        ProcessBehaviour node = null;
                        for (ProcessBehaviour entry : processBehaviours) {
                            if (entry.getProcess().equals(selectionProcess)) {
                                node = entry;
                                break;
                            }
                        }

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
                            ifProcessed = graph.addEdge(network, nextNode, label);

                            networks.addLast(nextNode);
                        }
                    } else if (process instanceof SelectionSP) {

                        SelectionSP selection = (SelectionSP) process;
                        String offeringProcess = selection.getProcess();

                        ProcessBehaviour node = null;
                        for (ProcessBehaviour entry : processBehaviours) {
                            if (entry.getProcess().equals(offeringProcess)) {
                                node = entry;
                                break;
                            }
                        }

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

                            graph.addVertex(nextNode);
                            ifProcessed = graph.addEdge(network, nextNode, label);

                            networks.addLast(nextNode);
                        }
                    } else if (process instanceof ConditionSP) {
                        ConditionSP condition = (ConditionSP) process;

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
                        graph.addEdge(network, networkThen, labelThen);
                        networks.addLast(networkThen);

                        graph.addVertex(networkElse);
                        ifProcessed = graph.addEdge(network, networkElse, labelElse);

                        networks.addLast(networkElse);
                    }
                }
            }
        }
    }
}