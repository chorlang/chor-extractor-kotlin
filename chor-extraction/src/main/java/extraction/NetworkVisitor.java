package extraction;

/**
 * Created by lara on 28/03/17.
 */

import antlr4.NetworkBaseVisitor;
import antlr4.NetworkParser.*;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;


public class NetworkVisitor extends NetworkBaseVisitor<SPNode>{
    private Network network;

    @Override
    public SPNode visitNetwork(NetworkContext ctx) {
        network = new Network(new HashMap<>());
        List<ProcessBehaviourContext> pb =  ctx.processBehaviour();
        for (ProcessBehaviourContext pbc: pb) {
            Network sp = (Network) visit(pbc);
            network.addProcess(sp);
        }

        return network;
    }

    @Override
    public SPNode visitProcessBehaviour(ProcessBehaviourContext ctx) {
        return new Network(new HashMap<String, SPNode>(){{put((ctx.process().getText()), visit(ctx.behaviour()));}});
    }

    @Override
    public SPNode visitSending(SendingContext ctx) {
        return new Sending((Behaviour) visit(ctx.behaviour()), ctx.process().getText(), ctx.expression().getText());
    }

    @Override
    public SPNode visitReceiving(ReceivingContext ctx) {
        return new Receiving((Behaviour) visit(ctx.behaviour()), ctx.process().getText());
    }

    @Override
    public SPNode visitSelection(SelectionContext ctx) {
        return new Selection((Behaviour) visit(ctx.behaviour()), ctx.process().getText(), ctx.label().getText());
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
        return new Condition(ctx.process().getText(), ctx.expression().getText(), (Behaviour) visit(ctx.behaviour(0)), (Behaviour) visit(ctx.behaviour(1)));
    }

    @Override
    public SPNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        return new ProcedureDefinition(ctx.procedure().getText(), (Behaviour) visit(ctx.behaviour(0)), (Behaviour) ctx.behaviour(1));
    }

    @Override
    public SPNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
        return new ProcedureInvocation(ctx.procedure().getText());
    }

    @Override
    public SPNode visitTerminal(TerminalNode node) {
        return new Termination();
    }
}
