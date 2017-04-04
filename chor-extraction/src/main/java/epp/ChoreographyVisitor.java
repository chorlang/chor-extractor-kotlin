package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import ast.cc.CCNode;
import ast.cc.nodes.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<CCNode> {
    public CCNode getCCAST(ParseTree parseTree){
        return this.visit(parseTree);
    }

    @Override
    public CCNode visitInteraction(InteractionContext ctx) {

        CCNode communication = visit(ctx.communication());
        CCNode continuation = visit(ctx.choreography());

        return new Interaction(communication, continuation);
    }

    @Override
    public CCNode visitCommunication(CommunicationContext ctx) {
        String sender = ctx.process().get(0).getText();
        String receiver = ctx.process().get(1).getText();
        String expression = ctx.expression().getText();

        return new Communication(sender,receiver,expression);
    }

    @Override
    public CCNode visitSelection(SelectionContext ctx) {
        String sender = ctx.process().get(0).getText();
        String receiver = ctx.process().get(1).getText();
        String expression = ctx.label().getText();

        return new Selection(sender,receiver,expression);
    }

    @Override
    public CCNode visitCondition(ConditionContext ctx) {
        String process = ctx.process().getText();
        String expression = ctx.expression().getText();
        CCNode thenChoreography = visit(ctx.choreography().get(0));
        CCNode elseChoreography = visit(ctx.choreography().get(1));

        return new Condition(process, expression, thenChoreography, elseChoreography);
    }

    @Override
    public CCNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        String procedurename = ctx.procedure().getText();
        CCNode choreography = visit(ctx.choreography(0));
        CCNode inchoreography = visit(ctx.choreography(1));

        return new ProcedureDefinition(procedurename, choreography, inchoreography);
    }

    @Override public CCNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
        String procedureName = ctx.procedure().getText();

        return new ProcedureInvocation(procedureName);
    }

    @Override
    public CCNode visitTerminal(TerminalNode node) {
        return new Termination();
    }
}
