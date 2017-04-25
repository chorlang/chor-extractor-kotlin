package extraction;

import ast.sp.nodes.Condition;
import ast.sp.nodes.Selection;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.Network;
import ast.sp.nodes.Offering;
import ast.sp.nodes.Receiving;
import ast.sp.nodes.Sending;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class NetworkExtraction {
    DirectedGraph<HashMap<String,SPNode>, String> graph;

    public DirectedGraph<HashMap<String,SPNode>, String> getGraph() {
        return graph;
    }

    public NetworkExtraction(SPNode sp) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        graph = new DefaultDirectedGraph<>(String.class);
        HashMap<String, SPNode> network = ((Network) sp).getNetwork();

        graph.addVertex(network);
        Deque<HashMap<String, SPNode>> deque = new ArrayDeque<>();
        deque.add( network );
        extract(deque);
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

                        String label = receivingProcess + "." + sending.getExpression() + "->" + sendingProcess;
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

                        String label = receivingProcess + "." + sending.getExpression() + "->" + sendingProcess;
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
                    if ((node != null) && (node instanceof Selection) && (((Selection) node).getProcess().equals(process.getKey()))) {
                        Selection selection = (Selection) node;
                        String offeringProcess = selection.getProcess();

                        String label = offeringProcess + "->" + selectionProcess + "[" + selection.getLabel() + "]";
                        HashMap<String, SPNode> nextNode = (HashMap<String, SPNode>) network.clone();
                        nextNode.put(selectionProcess, selection.getContinuation());
                        nextNode.put(offeringProcess, offering.getLabels().get(selection.getLabel()));

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof Selection) {

                    Selection selection = (Selection) processBehaviour;
                    String offeringProcess = selection.getProcess();

                    SPNode node = network.get(selection.getProcess());
                    if ((node != null) && (node instanceof Offering) && (((Offering) node).getProcess().equals(process.getKey()))) {
                        Offering offering = (Offering) node;
                        String selectionProcess = offering.getProcess();

                        String label = offeringProcess + "->" + selectionProcess + "[" + selection.getLabel() + "]";
                        HashMap<String, SPNode> nextNode = (HashMap<String, SPNode>) network.clone();
                        nextNode.put(selectionProcess, selection.getContinuation());
                        nextNode.put(offeringProcess, offering.getLabels().get(selection.getLabel()));

                        graph.addVertex(nextNode);
                        graph.addEdge(network, nextNode, label);

                        networks.addLast(nextNode);
                    }
                } else if (processBehaviour instanceof Condition) {
                    Condition condition = (Condition) processBehaviour;

                    String label = "if " + condition.getProcess() + "." + condition.getExpression();

                    SPNode thennode = condition.getThenBehaviour();
                    SPNode elsenode = condition.getElseBehaviour();

                    HashMap<String, SPNode> networkThen = (HashMap<String, SPNode>) network.clone();
                    networkThen.put(process.getKey(), thennode);
                    graph.addVertex(networkThen);
                    graph.addEdge(network, networkThen, label + "then");
                    networks.addLast(networkThen);

                    HashMap<String, SPNode> networkElse = (HashMap<String, SPNode>) network.clone();
                    networkElse.put(process.getKey(), elsenode);
                    graph.addVertex(networkElse);
                    graph.addEdge(network, networkElse, label + "else");

                    networks.addLast(networkElse);
                }
            }
        }
    }
}
