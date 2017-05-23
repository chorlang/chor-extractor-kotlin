package sat;

import ast.cc.interfaces.CCNode;
import ast.cc.nodes.BadTermination;
import ast.cc.nodes.Condition;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.ExtractionLabel;
import ast.sp.interfaces.SPNode;
import ast.sp.labels.*;
import ast.sp.nodes.Network;
import com.google.appengine.labs.repackaged.com.google.common.collect.HashMultimap;
import com.google.appengine.labs.repackaged.com.google.common.collect.Multimap;
import com.microsoft.z3.Context;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import extraction.ChoreographyExtraction;
import extraction.NetworkExtraction;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GraphReduction {
    private ChoreographyExtraction np;
    CCNode chor;
    ListenableDirectedGraph<Network, ExtractionLabel> graph;
    //DirectedGraph<Network, ExtractionLabel> graph;


    public GraphReduction(String grammar) throws Exception {
        np = new ChoreographyExtraction(grammar);
        chor = np.extract();
        System.out.println(chor.toString());

        SPNode network = np.getNetwork();
        NetworkExtraction ne = new NetworkExtraction(network);
        graph = ne.getGraph();

        GraphVisualization gv = new GraphVisualization();
        gv.init(graph);

        Multimap<String, Boolean> varset = HashMultimap.create();
        System.out.println(parseGraph(ne.getRoot(), varset));

    }

    private CCNode parseGraph(Network leaf, Multimap<String, Boolean> varset) throws Exception {
        CCNode retval = null;
        Set<ExtractionLabel> edges = graph.outgoingEdgesOf(leaf);
        switch (edges.size()) {
            case 0:
                //assert ( checkSat(varset) );
                if (leaf.isProjectable()) {
                    retval = new Termination();
                }
                break;
            case 1:
                if ( checkSat(varset) ) {
                    ExtractionLabel edge = edges.iterator().next();
                    Network target = graph.getEdgeTarget(edge);

                    if (edge instanceof Communication) {
                        Communication e = (Communication) edge;
                        varset.put(e.getExpression(), true);
                        CCNode continuation = parseGraph(target, varset);
                        retval = new ast.cc.nodes.Communication(e.getSender(), e.getReceiver(), e.getExpression(), continuation);
                    } else if (edge instanceof Selection) {
                        Selection e = (Selection) edge;
                        retval = new ast.cc.nodes.Selection(e.getSender(), e.getReceiver(), e.getLabel(), parseGraph(target, varset));
                    } else if (edge instanceof Recursion) {
                        retval = new Termination();
                    }
                } else {
                    retval = new BadTermination();
                }
                break;
            case 2:
                if ( checkSat(varset) ) {

                    ExtractionLabel[] labels = edges.toArray(new ExtractionLabel[2]);
                    Then thenLabel = ( labels[0] instanceof Then ) ? (Then) labels[0] : (Then) labels[1];
                    Else elseLabel = ( labels[0] instanceof Then ) ? (Else) labels[1] : (Else) labels[0];

                    // throw exception is processName or expression is different in the two nodes

                    HashMultimap<String, Boolean> elseset = HashMultimap.create(varset);
                    String expr = thenLabel.getExpression();
                    Optional<Boolean> first = varset.get(expr).stream().findFirst();
                    if (!first.isPresent()) {
                        varset.put(expr, true);
                        elseset.put(expr, false);

                    } else {
                        elseset.put(expr, !first.get());
                    }

                    retval = new Condition(
                            thenLabel.getProcess(),
                            expr,
                            parseGraph(graph.getEdgeTarget(thenLabel), varset),
                            parseGraph(graph.getEdgeTarget(elseLabel), elseset)
                    );
                } else {
                    retval = new BadTermination();
                }
                break;
            default:
                break;
            //throw new RuntimeException("AAAAH!");
        }
        return retval;
    }

    private boolean checkSat(Multimap<String, Boolean> varset) {

        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);
        Solver solver = ctx.mkSolver();

        if (varset.isEmpty()) {
            return true;
        } else {
            for (Map.Entry<String, Boolean> entry : varset.entries()) {
                String key = entry.getKey();
                if (entry.getValue().equals(true)) {
                    solver.add(ctx.mkBoolConst(key));
                } else {
                    solver.add(ctx.mkNot(ctx.mkBoolConst(key)));
                }
            }
            Status check = solver.check();
            System.out.println("Result:" + check.toString());
            if (check.equals(Status.SATISFIABLE)){
                Model model = solver.getModel();
                System.out.println("Model:" + model.toString());
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    String s = "p {def X {q!<e1>; q&{L: q!<e2>;X, R: stop}} main {q!<e3>; X}} | q {def Y {p?;p?; if q.e1 then p+L;Y else p+R;stop} main {Y}}";
                    GraphReduction gr = new GraphReduction(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
