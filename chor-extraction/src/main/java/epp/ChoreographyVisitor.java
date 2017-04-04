package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import ast.cc.CCNode;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<CCNode> {

    private HashSet<String> processes = new HashSet<>();

    public ChoreographyVisitor(ParseTree parseTree){
        ProcessesCollectingVisitor visitor = new ProcessesCollectingVisitor();
        visitor.visit(parseTree);
        this.processes = visitor.getProcesses();
    }

    @Override
    public CCNode visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public CCNode visitChoreography(ChoreographyContext ctx) {
        return super.visitChoreography(ctx);
    }

    @Override
    public CCNode visitCondition(ConditionContext ctx) {
        return null;
    }

    @Override
    public CCNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        /*String procedureName = ctx.procedure().getText();
        HashSet<String> procedureProcesses = (HashSet<String>) procedures.get(procedureName);
        if (!procedureProcesses.isEmpty()){
            for (String process: procedureProcesses) {
                String behaviour = new StringBuilder().append("def ").append(procedureName).append(" = ").toString();
                processes.put(process, processes.get(process) + behaviour);
            }

            for (String process: procedureProcesses) {
                processes.put(process, processes.get(process) + " in ");
            }

        }*/

        return null;
    }

    @Override public CCNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
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
    public CCNode visitInteraction(InteractionContext ctx) {
        return super.visitInteraction(ctx);
    }

    @Override
    public CCNode visitCommunication(CommunicationContext ctx) {
        return super.visitCommunication(ctx);
    }

    @Override
    public CCNode visitSelection(SelectionContext ctx) {
        return super.visitSelection(ctx);
    }

    @Override
    public CCNode visitExpression(ExpressionContext ctx) {
        return super.visitExpression(ctx);
    }

       /* public CCNode visitSend(SendContext ctx) {

        CCNode result = visitChildren(ctx);

        if (result!=null){
            /* String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String expression = ctx.expression().getText();

        String s = new StringBuilder().append(receivingProcess).append("!<").append(expression).append(">;").toString();
        processes.put(sendingProcess, processes.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("?;").toString();
        processes.put(receivingProcess, processes.get(receivingProcess) + r);

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

        visitChildren(ctx);
        return null;
    }*/

    @Override
    public CCNode visitProcedure(ProcedureContext ctx) {
        return super.visitProcedure(ctx);
    }

    @Override
    public CCNode visitProcess(ProcessContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public CCNode visitLabel(LabelContext ctx) {
        return super.visitLabel(ctx);
    }

    @Override
    public CCNode visitValue(ValueContext ctx) {
        return super.visitValue(ctx);
    }

}
