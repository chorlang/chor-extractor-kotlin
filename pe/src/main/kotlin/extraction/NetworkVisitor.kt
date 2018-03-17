package extraction

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import NetworkBaseVisitor
import NetworkParser.*
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.*
import kotlin.collections.HashMap


class NetworkVisitor : NetworkBaseVisitor<SPNode>() {
    override fun visitNetwork(ctx: NetworkContext): SPNode {
        val network = TreeMap<String, ProcessBehaviour>()
        for (i in 0..ctx.processBehaviour().size - 1) {
            network.put(ctx.process(i).text, visit(ctx.processBehaviour(i)) as ProcessBehaviour)
        }
        return Network(network)
    }

    override fun visitProcessBehaviour(ctx: ProcessBehaviourContext): SPNode {
        val procedures = HashMap<String, ProcedureDefinitionSP>()
        for (i in 0..ctx.procedureDefinition().size - 1) {
            procedures.put(ctx.procedure(i).text, visit(ctx.procedureDefinition(i)) as ProcedureDefinitionSP)
        }

        return ProcessBehaviour(procedures, visit(ctx.behaviour()) as Behaviour)
    }

    override fun visitSending(ctx: SendingContext): SPNode {
        return Sending(visit(ctx.behaviour()) as Behaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitReceiving(ctx: ReceivingContext): SPNode {
        return Receiving(visit(ctx.behaviour()) as Behaviour, ctx.process().text)
    }

    override fun visitSelection(ctx: SelectionContext): SPNode {
        return SelectionSP(visit(ctx.behaviour()) as Behaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitOffering(ctx: OfferingContext): SPNode {
        val labeledBehaviour = HashMap<String, Behaviour>()
        for (lb in ctx.labeledBehaviour()) {
            labeledBehaviour.put(lb.expression().text, visit(lb.behaviour()) as Behaviour)
        }

        return Offering(ctx.process().text, labeledBehaviour)
    }

    override fun visitCondition(ctx: ConditionContext): SPNode {
        return ConditionSP(ctx.expression().text, visit(ctx.behaviour(0)) as Behaviour, visit(ctx.behaviour(1)) as Behaviour)
    }

    override fun visitProcedureDefinition(ctx: ProcedureDefinitionContext): SPNode {
        return ProcedureDefinitionSP(visit(ctx.behaviour()) as Behaviour)
    }

    override fun visitProcedureInvocation(ctx: ProcedureInvocationContext): SPNode {
        return ProcedureInvocationSP(ctx.procedure().text)
    }

    override fun visitTerminal(node: TerminalNode?): SPNode {
        return TerminationSP()
    }
}