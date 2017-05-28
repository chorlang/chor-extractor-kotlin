package sat;

import ast.cc.interfaces.CCNode;
import ast.cc.nodes.Condition;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.ExtractionLabel;
import ast.sp.interfaces.SPNode;
import ast.sp.labels.*;
import ast.sp.nodes.Network;
import ast.sp.nodes.expr.*;
import com.microsoft.z3.*;
import extraction.ChoreographyExtraction;
import extraction.NetworkExtraction;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.swing.*;
import java.util.*;

public class GraphReduction {
    private ChoreographyExtraction np;
    private CCNode chor;
    private ListenableDirectedGraph<Network, ExtractionLabel> graph;
    private HashMap<String, List<BooleanExpression>> var;

    public CCNode getReductedGraph() {
        return reductedGraph;
    }

    //DirectedGraph<Network, ExtractionLabel> graph;
    private CCNode reductedGraph;


    public GraphReduction(String grammar) throws Exception {
        np = new ChoreographyExtraction(grammar);
        chor = np.extract();
        System.out.println(chor.toString());

        SPNode network = np.getNetwork();
        NetworkExtraction ne = new NetworkExtraction(network);
        graph = ne.getGraph();

        var = new HashMap<>();
        reductedGraph = parseGraph(ne.getRoot());
        //System.out.println(reductedGraph);
        //GraphVisualization gv = new GraphVisualization();
        //gv.init(graph);
    }

    private CCNode parseGraph(Network leaf) throws Exception {
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
                    updateVarSet(e.getReceiver(), e.getExpression(), var);
                    CCNode continuation = parseGraph(target);
                    retval = new ast.cc.nodes.Communication(e.getSender(), e.getReceiver(), e.getExpression(), continuation);
                } else if (edge instanceof Selection) {
                    Selection e = (Selection) edge;
                    retval = new ast.cc.nodes.Selection(e.getSender(), e.getReceiver(), e.getLabel(), parseGraph(target));
                } else if (edge instanceof Recursion) {
                    retval = new Termination();
                }
                break;
            case 2: //condition
                ExtractionLabel[] labels = edges.toArray(new ExtractionLabel[2]);
                Then thenLabel = ( labels[0] instanceof Then ) ? (Then) labels[0] : (Then) labels[1];
                Else elseLabel = ( labels[0] instanceof Then ) ? (Else) labels[1] : (Else) labels[0];

                // throw exception is processName or expression is different in the two nodes

                boolean thenBranch = checkBranch(thenLabel.getProcess(), thenLabel.getExpression(), var);
                boolean elseBranch = checkBranch(elseLabel.getProcess(), new NegatedBooleanExpression(thenLabel.getExpression()), var);

                CCNode thenChor = thenBranch ? parseGraph(graph.getEdgeTarget(thenLabel)) : new Termination();
                CCNode elseChor = elseBranch ? parseGraph(graph.getEdgeTarget(elseLabel)) : new Termination();

                retval = new Condition(
                        thenLabel.getProcess(),
                        thenLabel.getExpression(),
                        thenChor,
                        elseChor
                );
                break;
            default:
                break;
            //throw new RuntimeException("AAAAH!");
        }
        return retval;
    }

    private HashMap<String, List<BooleanExpression>> updateVarSet(String process, BooleanExpression expression, HashMap<String, List<BooleanExpression>> var){
        if (var.containsKey(process)){
            List<BooleanExpression> bes = var.get(process);
            Optional<BooleanExpression> first = bes.stream().filter(i -> i.getName().equals(expression.getName())).findFirst();
            if (first.isPresent()){
                bes.remove(first.get());
            }
            bes.add(expression);
        } else {
            var.put(process, new ArrayList<BooleanExpression>(){{add(expression);}});
        }

        return var;
    }

    private boolean checkBranch(String process, BooleanExpression expression, HashMap<String, List<BooleanExpression>> var) throws Exception {
        //set up z3
        HashMap<String, String> cfg = new HashMap<String, String>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);
        Solver solver = ctx.mkSolver();;

        if (var.containsKey(process)){
            List<BooleanExpression> bes = var.get(process);
            solver.add(process(expression, ctx, bes));
        } else throw new Exception("No such process");

        //check sat
        Status check = solver.check();

        if (check.equals(Status.SATISFIABLE)) {
            System.out.println("Expression " + expression + " is SAT");
            Model model = solver.getModel();
            //System.out.println(model);

            return true;
        } else {
            System.out.println("Expression " + expression + " is UNSAT");
            return false;
        }
    }

    private BoolExpr process(BooleanExpression be, Context ctx, List<BooleanExpression> bes) throws Exception {
        BoolExpr expr = null;

        if (be instanceof AtomBooleanExpression){
            AtomBooleanExpression b = (AtomBooleanExpression) be;
            Boolean e = b.getExpression();
            expr = ctx.mkBool(e);
        } else if (be instanceof BinaryBooleanExpression){
            Operand operand = ( (BinaryBooleanExpression) be ).getOperand();
            switch (operand) {
                case AND:
                    expr =  ctx.mkAnd(
                            process(( (BinaryBooleanExpression) be ).getLeft(), ctx, bes),
                            process(( (BinaryBooleanExpression) be ).getRight(), ctx, bes) );
                    break;
                case OR:
                    expr = ctx.mkOr(
                            process(( (BinaryBooleanExpression) be ).getLeft(), ctx, bes),
                            process(( (BinaryBooleanExpression) be ).getRight(), ctx, bes) );
                    break;
                case EQUAL:
                    expr = ctx.mkEq(
                            process(( (BinaryBooleanExpression) be ).getLeft(), ctx, bes),
                            process(( (BinaryBooleanExpression) be ).getRight(), ctx, bes) );
                    break;
                case NOTEQUAL:
                    expr = ctx.mkNot(ctx.mkEq(
                            process(( (BinaryBooleanExpression) be ).getLeft(), ctx, bes),
                            process(( (BinaryBooleanExpression) be ).getRight(), ctx, bes) ));
                    break;
            }
        } else if (be instanceof NegatedBooleanExpression){
            expr = ctx.mkNot(process(( (NegatedBooleanExpression) be ).getExpr(), ctx, bes));
        } else {
            String name = be.getName();
            Optional<BooleanExpression> first = bes.stream().filter(i -> i.getName().equals(name)).findFirst();
            if (first.isPresent()){
                expr = process(first.get(), ctx, bes);
            } else throw new Exception("process reference not found");
        }
        return expr;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String s =
                        "p { main {q!<e1 = true>; q!<e2=false && ( false || e1 )>; q?; stop}} | " +
                        "q { main {p?; p?; if q.e1!=e2 then p!<e3=true>; stop else p!<e3=false>; stop}} | " +
                        "r { main {stop}}";
                GraphReduction gr = new GraphReduction(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
