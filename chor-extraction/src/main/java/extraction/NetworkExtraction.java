package extraction;

import ast.cc.interfaces.CCNode;
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
    DirectedGraph<HashMap<String,SPNode>, ExtractionLabel> graph;

    public NetworkExtraction(SPNode sp) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        graph = new DefaultDirectedGraph<>(ExtractionLabel.class);
        HashMap<String, SPNode> network = ((Network) sp).getNetwork();

        graph.addVertex(network);
        Deque<HashMap<String, SPNode>> deque = new ArrayDeque<>();
        deque.add( network );
        extract(deque);
    }

    public DirectedGraph<HashMap<String,SPNode>, ExtractionLabel> getGraph() {
        return graph;
    }

    public void graphToChoreograpy(){

        if (findroot().isPresent()) {
            HashMap<String, SPNode> node = findroot().get();
            extractNodeForward(node);
            extractNodeBackward(node);
        }
        else {
            //asyclic graph
        }

    }

    private CCNode extractNodeForward(HashMap<String, SPNode> leaf){
        Set<ExtractionLabel> edges = graph.outgoingEdgesOf(leaf);
        if (edges.size()==1){
            ExtractionLabel edge = edges.iterator().next();
            HashMap<String, SPNode> target =  graph.getEdgeTarget(edge);
            if (edge instanceof Communication){
                Communication e = (Communication) edge;
                new ast.cc.nodes.Communication(e.getSender(), e.getReceiver(), e.getExpression(), extractNodeForward(target));
            } else if (edge instanceof Selection) {
                Selection e = (Selection) edge;
                new ast.cc.nodes.Selection(e.getSender(), e.getReceiver(), e.getLabel(), extractNodeForward(target));
            }
        } else {
            ArrayList<ExtractionLabel> labels = new ArrayList<>();
            labels.addAll(edges);
            String processName;
            String expression;
            SPNode elseBehavior;
            SPNode thenBehavior;
            for (ExtractionLabel label: labels) {
                if (label instanceof Else){
                    processName =  ( (Else) label ).getProcess();
                    expression = ( (Else) label ).getExpression();
                    elseBehavior = (SPNode) graph.getEdgeTarget(label);

                } else if (label instanceof Then){
                    processName =  ( (Then) label ).getProcess();
                    expression = ( (Then) label ).getExpression();
                    graph.getEdgeTarget(label);
                }
                
            }
            

            
            //new Communication();
        }
        return null;
    }

    private boolean checkLeaveTerminate(HashMap<String,SPNode> leaf){
        for (Map.Entry<String, SPNode> process: leaf.entrySet()) {
            if (!(process.getValue() instanceof TerminationSP))
                return false;
        }
        return true;
    }

    private CCNode extractNodeBackward(HashMap<String,SPNode> leaf){
        return null;
    }

    private Optional<HashMap<String,SPNode>> findroot(){
        for (HashMap<String, SPNode> entry : graph.vertexSet()) {
            if (graph.inDegreeOf(entry) == 0){
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    private HashMap<String,SPNode> findleaves(){
        HashMap<String, SPNode> leaves = new HashMap<>();
        for (HashMap<String, SPNode> entry : graph.vertexSet()) {
            if (graph.outDegreeOf(entry) == 0){
                leaves.putAll(entry);
            }
        }
        return leaves;
    }


    private void extract(Deque< HashMap<String, SPNode> > networks)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        while( !networks.isEmpty() ) {
            HashMap<String, SPNode> network = networks.removeFirst();

            for (Map.Entry<String, SPNode> process : network.entrySet()) {
                SPNode processBehaviour = process.getValue();

                if (processBehaviour instanceof Receiving) {

                    Receiving receiving = (Receiving) processBehaviour;
                    String sendingProcess = receiving.getProcess();

                    SPNode node = network.get(sendingProcess);
                    if ((node != null) && (node instanceof Sending) && (((Sending) node).getProcess().equals(process.getKey()))) {
                        Sending sending = (Sending) node;
                        String receivingProcess = sending.getProcess();

                        ExtractionLabel label = new Communication(sendingProcess, receivingProcess, sending.getExpression());
                        HashMap<String, SPNode> nextNode = new HashMap<>(network);

                        nextNode.put(receivingProcess, receiving.getContinuation());
                        nextNode.put(sendingProcess, sending.getContinuation());

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof Sending) {

                    Sending sending = (Sending) processBehaviour;
                    String receivingProcess = sending.getProcess();

                    SPNode node = network.get(receivingProcess);
                    if ((node != null) && (node instanceof Receiving) && (((Receiving) node).getProcess().equals(process.getKey()))) {
                        Receiving receiving = (Receiving) node;
                        String sendingProcess = receiving.getProcess();

                        ExtractionLabel label = new Communication(sendingProcess, receivingProcess, sending.getExpression());
                        HashMap<String, SPNode> nextNode = (HashMap<String, SPNode>) network.clone();

                        nextNode.put(receivingProcess, receiving.getContinuation());
                        nextNode.put(sendingProcess, sending.getContinuation());

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof Offering) {

                    Offering offering = (Offering) processBehaviour;
                    String selectionProcess = offering.getProcess();

                    SPNode node = network.get(offering.getProcess());
                    if ((node != null) && (node instanceof SelectionSP) && (((SelectionSP) node).getProcess().equals(process.getKey()))) {
                        SelectionSP selection = (SelectionSP) node;
                        String offeringProcess = selection.getProcess();

                        ExtractionLabel label = new ast.sp.labels.Selection(selectionProcess, offeringProcess, selection.getLabel());

                        HashMap<String, SPNode> nextNode = (HashMap<String, SPNode>) network.clone();
                        nextNode.put(selectionProcess, selection.getContinuation());
                        nextNode.put(offeringProcess, offering.getLabels().get(selection.getLabel()));

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof SelectionSP) {

                    SelectionSP selection = (SelectionSP) processBehaviour;
                    String offeringProcess = selection.getProcess();

                    SPNode node = network.get(selection.getProcess());
                    if ((node != null) && (node instanceof Offering) && (((Offering) node).getProcess().equals(process.getKey()))) {
                        Offering offering = (Offering) node;
                        String selectionProcess = offering.getProcess();

                        ExtractionLabel label = new ast.sp.labels.Selection(selectionProcess, offeringProcess, selection.getLabel());
                        HashMap<String, SPNode> nextNode = (HashMap<String, SPNode>) network.clone();
                        nextNode.put(selectionProcess, selection.getContinuation());
                        nextNode.put(offeringProcess, offering.getLabels().get(selection.getLabel()));

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof ConditionSP) {
                    ConditionSP condition = (ConditionSP) processBehaviour;

                    ExtractionLabel label = new Then(condition.getProcess(), condition.getExpression());

                    SPNode thennode = condition.getThenBehaviour();
                    HashMap<String, SPNode> networkThen = (HashMap<String, SPNode>) network.clone();
                    networkThen.put(process.getKey(), thennode);
                    graph.addVertex(networkThen);
                    graph.addEdge(network, networkThen, label);
                    networks.addLast(networkThen);

                    SPNode elsenode = condition.getElseBehaviour();
                    HashMap<String, SPNode> networkElse = (HashMap<String, SPNode>) network.clone();
                    networkElse.put(process.getKey(), elsenode);
                    graph.addVertex(networkElse);
                    graph.addEdge(network, networkElse, label);

                    networks.addLast(networkElse);
                }
            }
        }
    }
}