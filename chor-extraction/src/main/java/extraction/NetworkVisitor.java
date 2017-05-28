package extraction;

import antlr4.NetworkBaseVisitor;
import antlr4.NetworkParser.*;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;
import ast.sp.nodes.expr.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


public class NetworkVisitor extends NetworkBaseVisitor<SPNode>{
    @Override
    public SPNode visitNetwork(NetworkContext ctx) {
        ArrayList<ProcessBehaviour> network = ctx.processBehaviour().stream().map(pbc -> (ProcessBehaviour) visit(pbc)).collect(Collectors.toCollection(ArrayList::new));
        return new Network(network);
    }

    @Override
    public SPNode visitProcessBehaviour(ProcessBehaviourContext ctx) {
        ArrayList<ProcedureDefinitionSP> procedures = ctx.procedureDefinition().
                stream().
                map(procedure -> (ProcedureDefinitionSP) visit(procedure)).
                collect(Collectors.toCollection(ArrayList::new));

        return new ProcessBehaviour(ctx.process().getText(), procedures, (Behaviour) visit(ctx.behaviour()));
    }

    @Override
    public SPNode visitSending(SendingContext ctx) {
        BooleanExpression expr = (BooleanExpression) visit(ctx.expr().expression());
        expr.setName(ctx.expr().Identifier().getText());
        return new Sending((Behaviour) visit(ctx.behaviour()), ctx.process().getText(), expr);
    }

    @Override
    public SPNode visitReceiving(ReceivingContext ctx) {
        return new Receiving((Behaviour) visit(ctx.behaviour()), ctx.process().getText());
    }

    @Override
    public SPNode visitSelection(SelectionContext ctx) {
        return new SelectionSP((Behaviour) visit(ctx.behaviour()), ctx.process().getText(), ctx.label().getText());
    }

    @Override
    public SPNode visitOffering(OfferingContext ctx) {
        HashMap<String, Behaviour> labeledBehaviour = new HashMap<>();
        for (LabeledBehaviourContext lb: ctx.labeledBehaviour()) {
            labeledBehaviour.put(lb.label().getText(), (Behaviour) visit(lb.behaviour()));
        }

        return new Offering(ctx.process().getText(), labeledBehaviour);
    }

    @Override
    public SPNode visitCondition(ConditionContext ctx) {
        BooleanExpression expr = (BooleanExpression) visit(ctx.expression());
        return new ConditionSP(ctx.process().getText(), expr, (Behaviour) visit(ctx.behaviour(0)), (Behaviour) visit(ctx.behaviour(1)));
    }

    @Override
    public SPNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        return new ProcedureDefinitionSP(ctx.procedure().getText(), (Behaviour) visit(ctx.behaviour()));
    }

    @Override
    public SPNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
        return new ProcedureInvocationSP(ctx.procedure().getText());
    }

    @Override
    public SPNode visitExpr(ExprContext ctx) {
        BooleanExpression expression = (BooleanExpression) visit(ctx.expression());
        expression.setName(ctx.Identifier().getText());

        return expression;
    }

    @Override
    public SPNode visitParExpr(ParExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public SPNode visitNotExpr(NotExprContext ctx) {
        return new NegatedBooleanExpression((BooleanExpression) visit(ctx.expression()));
    }

    @Override
    public SPNode visitOpExpr(OpExprContext ctx) {
        return new BinaryBooleanExpression(
                (BooleanExpression) visit(ctx.left),
                (BooleanExpression) visit(ctx.right),
                Operand.fromString(ctx.operand().getText()));
    }

    @Override
    public SPNode visitRefExpr(RefExprContext ctx) {
        BooleanExpression be = new BooleanExpression() {};
        be.setName(ctx.Identifier().getText());
        return be;
    }

    @Override
    public SPNode visitAtomExpr(AtomExprContext ctx) {
        return new AtomBooleanExpression(Boolean.valueOf(ctx.BooleanLiteral().getText()));
    }

    @Override
    public SPNode visitTerminal(TerminalNode node) {
        return new TerminationSP();
    }
}