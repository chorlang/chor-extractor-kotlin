package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.CommunicationContext;
import antlr4.ChoreographyParser.SelectionContext;
import antlr4.ChoreographyParser.ConditionContext;
import antlr4.ChoreographyParser.ProcedureDefinitionContext;
import antlr4.ChoreographyParser.ProcedureInvocationContext;
import ast.cc.interfaces.CCNode;
import ast.cc.nodes.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<CCNode> {

    private HashSet<String> processes;

    public HashSet<String> getProcesses() {
        return processes;
    }

    public ChoreographyVisitor() {
        processes = new HashSet<>();
    }

    public CCNode getCCAST(ParseTree parseTree){
        return this.visit(parseTree);
    }

    @Override
    public CCNode visitCommunication(CommunicationContext ctx) {
        String sender = ctx.process().get(0).getText();
        String receiver = ctx.process().get(1).getText();
        String expression = ctx.expression().getText();

        CCNode continuation = visit(ctx.choreography());

        processes.add(sender);
        processes.add(receiver);

        return new Communication(sender,receiver,expression, continuation);
    }

    @Override
    public CCNode visitSelection(SelectionContext ctx) {

        String sender = ctx.process().get(0).getText();
        String receiver = ctx.process().get(1).getText();
        String expression = ctx.label().getText();

        CCNode continuation = visit(ctx.choreography());

        processes.add(sender);
        processes.add(receiver);

        return new Selection(sender,receiver,expression, continuation);
    }

    @Override
    public CCNode visitCondition(ConditionContext ctx) {
        String process = ctx.process().getText();
        String expression = ctx.expression().getText();
        CCNode thenChoreography = visit(ctx.choreography(0));
        CCNode elseChoreography = visit(ctx.choreography(1));

        processes.add(process);

        return new Condition(process, expression, thenChoreography, elseChoreography);
    }

    @Override
    public CCNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        String procedurename = ctx.procedure().getText();
        CCNode choreography = visit(ctx.choreography(0));
        CCNode inchoreography = visit(ctx.choreography(1));

        return new ProcedureDefinition(procedurename, choreography, inchoreography, processes);
    }

    @Override public CCNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
        String procedureName = ctx.procedure().getText();

        return new ProcedureInvocation(procedureName, processes);
    }

    @Override
    public CCNode visitTerminal(TerminalNode node) {
        return new Termination();
    }
}
