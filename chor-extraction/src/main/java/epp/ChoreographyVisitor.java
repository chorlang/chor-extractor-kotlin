package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<ArrayList<Pair>> {

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
    public ArrayList<Pair> visitExpr(ExprContext ctx) {
    //    System.out.println("Expression");


        visitChildren(ctx);
        return null;
    }

    @Override
    public ArrayList<Pair> visitProcedure(ProcedureContext ctx) {
        return super.visitProcedure(ctx);
    }

    @Override
    public ArrayList<Pair> visitProcess(ProcessContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ArrayList<Pair> visitLabel(LabelContext ctx) {
        return super.visitLabel(ctx);
    }

    @Override
    public ArrayList<Pair> visitValue(ValueContext ctx) {
        return super.visitValue(ctx);
    }

    @Override
    public ArrayList<Pair> visit(ParseTree tree) {

        return super.visit(tree);
    }

    @Override
    public ArrayList<Pair> visitChildren(RuleNode node) {
        ArrayList<ArrayList<Pair>> result = new ArrayList<>();
        int n = node.getChildCount();

        for(int i = 0; i < n; ++i) {
            ParseTree c = node.getChild(i);
            ArrayList<Pair> childResult = c.accept(this);
            result.add(childResult);
        }

        return result;
    }

    @Override
    public ArrayList<Pair> visitTerminal(TerminalNode node ) {
        return new ArrayList<Pair>(){{add(new Pair(ParseNodesEnum.TERMINALNODE.toString(), node.getText()));}};
    }

    @Override
    public ArrayList<Pair> visitChoreography(ChoreographyContext ctx) {
        return super.visitChoreography(ctx);
    }

    @Override
    public ArrayList<Pair> visitCondition(ConditionContext ctx) {

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

        return null;
    }

    @Override
    public ArrayList<Pair> visitProcedureDefinition(ProcedureDefinitionContext ctx) {
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

    @Override public ArrayList<Pair> visitProcedureInvocation(ProcedureInvocationContext ctx) {
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
    public ArrayList<Pair> visitInternal_choreography(Internal_choreographyContext ctx) {
        return super.visitInternal_choreography(ctx);
    }

    @Override
    public ArrayList<Pair> visitExternal_choreography(External_choreographyContext ctx) {
        return super.visitExternal_choreography(ctx);
    }

    @Override
    public ArrayList<Pair> visitCommunication(CommunicationContext ctx) {
        return super.visitCommunication(ctx);
    }

    @Override
    public ArrayList<Pair> visitSend(SendContext ctx) {

        ArrayList<Pair> result = visitChildren(ctx);

        if (result!=null){
            /* String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String expression = ctx.expression().getText();

        String s = new StringBuilder().append(receivingProcess).append("!<").append(expression).append(">;").toString();
        processes.put(sendingProcess, processes.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("?;").toString();
        processes.put(receivingProcess, processes.get(receivingProcess) + r);
        */

        }

        return result;
    }


    @Override
    public ArrayList<Pair> visitChoose(ChooseContext ctx) {
        String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String label = ctx.label().getText();

        String s = new StringBuilder().append(receivingProcess).append("+").append(label).append(";").toString();
        processes.put(sendingProcess, processes.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("&{").append(label).append(": ").toString();
        processes.put(receivingProcess, processes.get(receivingProcess) + r);

        putToEnds(receivingProcess, "}");

        visitChildren(ctx); */
        return null;
    }

    @Override
    public ArrayList<Pair> visitFirstExpression(FirstExpressionContext ctx) {
        return super.visitFirstExpression(ctx);
    }

    @Override
    public ArrayList<Pair> visitSecondExpression(SecondExpressionContext ctx) {
        return super.visitSecondExpression(ctx);
    }

    @Override
    public ArrayList<Pair> visitExpression(ExpressionContext ctx) {
        return super.visitExpression(ctx);
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
