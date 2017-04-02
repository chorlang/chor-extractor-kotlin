package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<String> {

    private HashMap<String, String> processes = new HashMap<>();
    private HashMap<String, String> ends = new HashMap<>();
    private HashMap<String, List<String>> procedures = new HashMap<>();

    public ChoreographyVisitor(Set<String> processes, HashMap<String, List<String>> procedures){
        for (String s : processes) {
            this.processes.put(s, s + " has ");
            ends.put(s, ""); //create the copy of processes to store endings of operations, e.g "}" in p&{l: C} when C is not calculated yet
            this.procedures = procedures;
        }
    }

    @Override
    public String visitExpr(ExprContext ctx) {
    //    System.out.println("Expression");


        visitChildren(ctx);
        return null;
    }

    @Override
    public String visit(ParseTree tree) {

        return super.visit(tree);
    }

    @Override
    public String visitTerminal(TerminalNode node ) {
        /*if (node.getText().equals("stop")){
            for(Map.Entry<String,String> entry: processes.entrySet()) {
                String key = entry.getKey();
                if (ends.containsKey(key)){
                    processes.put(key, entry.getValue() + "stop" + ends.get(key));
                } else {
                    processes.put(key, entry.getValue() + "stop");
                }
            }
        }*/
        //System.out.println( this.toString() + " | Visiting terminal " + node.getText() );
        return null;
    }

    @Override
    public String visitCondition(ConditionContext ctx) {

        //boolean evaluationResult = evalute(ctx.firstExpression(), ctx.secondExpression());

        PreprocessingVisitor preprocessingVisitor = new PreprocessingVisitor();
        preprocessingVisitor.visit(ctx);
        Set<String> conditionProcesses = preprocessingVisitor.getProcesses();

        String activeProcess = ctx.firstExpression().getText();
        String comparingProcess = ctx.secondExpression().getText();

        String thenBehaviour = new StringBuilder().append("if * = ").append(comparingProcess).append(" then ").toString();
        processes.put(activeProcess, processes.get(activeProcess) + thenBehaviour);
        processes.put(comparingProcess, processes.get(comparingProcess) + activeProcess + "!<*>; ");

        visit(ctx.internal_choreography());
        processes.put(activeProcess, processes.get(activeProcess) + " else ");
        visit(ctx.external_choreography());

        ChoreographyContextComparator comparator = new ChoreographyContextComparator();

        Thread p1 = new Thread(new MergingVisitor(ctx.internal_choreography(), comparator));
        Thread p2 = new Thread(new MergingVisitor(ctx.external_choreography(), comparator));
        p1.start();
        p2.start();



        return null;
    }

    @Override
    public String visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        String procedureName = ctx.procedure().getText();
        HashSet<String> procedureProcesses = (HashSet<String>) procedures.get(procedureName);
        if (!procedureProcesses.isEmpty()){
            for (String process: procedureProcesses) {
                String behaviour = new StringBuilder().append("def ").append(procedureName).append(" = ").toString();
                processes.put(process, processes.get(process) + behaviour);
            }
                visit(ctx.internal_choreography());
            for (String process: procedureProcesses) {
                processes.put(process, processes.get(process) + " in ");
            }
                visit(ctx.external_choreography());
        }

        return null;
    }

    @Override public String visitProcedureInvocation(ProcedureInvocationContext ctx) {
        String procedureName = ctx.procedure().getText();
        HashSet<String> procedureProcesses = (HashSet<String>) procedures.get(procedureName);
        if (!procedureProcesses.isEmpty()) {
            for (String process : procedureProcesses) {
                processes.put(process, processes.get(process) + procedureName);
            }
        }
        //visitChildren(ctx);
        return null;
    }

    @Override
    public String visitSend(SendContext ctx) {

        //HashMap<String, String> result = visitChildren(ctx);

        String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String expression = ctx.expression().getText();

        String s = new StringBuilder().append(receivingProcess).append("!<").append(expression).append(">;").toString();
        processes.put(sendingProcess, processes.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("?;").toString();
        processes.put(receivingProcess, processes.get(receivingProcess) + r);

        return null;
    }

    @Override
    public String visitChoose(ChooseContext ctx) {
        String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String label = ctx.label().getText();

        String s = new StringBuilder().append(receivingProcess).append("+").append(label).append(";").toString();
        processes.put(sendingProcess, processes.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("&{").append(label).append(": ").toString();
        processes.put(receivingProcess, processes.get(receivingProcess) + r);

        putToEnds(receivingProcess, "}");

        visitChildren(ctx);
        return null;
    }

    private void putToEnds(String processName, String thingToPut){
        ends.put(processName, thingToPut + ends.get(processName));
    }

    public HashMap<String, String> getProcesses() {
        return processes;
    }

    public HashMap<String, String> getEnds() {
        return ends;
    }

    public HashMap<String, List<String>> getProcedures() {
        return procedures;
    }

    /*private boolean evalute(FirstExpressionContext firstExpressionContext, SecondExpressionContext secondExpressionContext){
        return false;
    }*/

    private String mergeChoreographies(Internal_choreographyContext ctx1, External_choreographyContext ctx2){
        return "";
    }
}
