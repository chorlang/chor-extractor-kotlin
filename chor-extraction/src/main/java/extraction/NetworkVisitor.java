package extraction;

import antlr4.NetworkBaseVisitor;
import antlr4.NetworkParser.*;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


public class NetworkVisitor extends NetworkBaseVisitor<SPNode>{
    @Override
    public SPNode visitNetwork(NetworkContext ctx) {
        ArrayList<ProcessBehaviour> network = new ArrayList<>();
        for (ProcessBehaviourContext pbc: ctx.processBehaviour()) {
            network.add((ProcessBehaviour) visit(pbc));
        }
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
        return new Sending((Behaviour) visit(ctx.behaviour()), ctx.process().getText(), ctx.expression().getText());
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
        return new ConditionSP(ctx.process().getText(), ctx.expression().getText(), (Behaviour) visit(ctx.behaviour(0)), (Behaviour) visit(ctx.behaviour(1)));
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
    public SPNode visitTerminal(TerminalNode node) {
        return new TerminationSP();
    }
}