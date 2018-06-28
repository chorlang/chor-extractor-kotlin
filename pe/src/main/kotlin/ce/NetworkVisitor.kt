package ce

import antlrgen.NetworkBaseVisitor
import antlrgen.NetworkParser
import ast.sp.nodes.*
import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.SPNode
import org.antlr.v4.runtime.tree.TerminalNode
import antlrgen.NetworkParser.ParallelNetworksContext
import antlrgen.NetworkParser.SendingContext
import antlrgen.NetworkParser.ReceivingContext
import antlrgen.NetworkParser.SelectionContext
import antlrgen.NetworkParser.OfferingContext
import antlrgen.NetworkParser.ConditionContext
import antlrgen.NetworkParser.ProcedureDefinitionContext
import antlrgen.NetworkParser.ProcedureInvocationContext

class NetworkVisitor : NetworkBaseVisitor<SPNode>() {
    override fun visitParallelNetworks(ctx: ParallelNetworksContext): SPNode {
        val networksList = ArrayList<Network>()
        ctx.network().mapTo(networksList) { visit(it) as Network }
        return ParallelNetworks(networksList)
    }

    override fun visitNetwork(ctx: NetworkParser.NetworkContext): SPNode {
        val network = HashMap<String, ProcessTerm>()
        for (i in 0 until ctx.processBehaviour().size) {
            network[ctx.process(i).text] = visit(ctx.processBehaviour(i)) as ProcessTerm
        }
        return Network(network)
    }

    override fun visitProcessBehaviour(ctx: NetworkParser.ProcessBehaviourContext): SPNode {
        val procedures = HashMap<String, IBehaviour>()
        for (i in 0 until ctx.procedureDefinition().size) {
            procedures[ctx.procedure(i).text] = visit(ctx.procedureDefinition(i)) as IBehaviour
        }

        return ProcessTerm(procedures, visit(ctx.behaviour()) as IBehaviour)
    }

    override fun visitSending(ctx: SendingContext): SPNode {
        return SendingSP(visit(ctx.behaviour()) as IBehaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitReceiving(ctx: ReceivingContext): SPNode {
        return ReceiveSP(visit(ctx.behaviour()) as IBehaviour, ctx.process().text)
    }

    override fun visitSelection(ctx: SelectionContext): SPNode {
        return SelectionSP(visit(ctx.behaviour()) as IBehaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitOffering(ctx: OfferingContext): SPNode {
        val labeledBehaviour = HashMap<String, IBehaviour>()
        for (lb in ctx.labeledBehaviour()) {
            labeledBehaviour[lb.expression().text] = visit(lb.behaviour()) as IBehaviour
        }

        return OfferingSP(ctx.process().text, labeledBehaviour)
    }

    override fun visitCondition(ctx: ConditionContext): SPNode {
        return ConditionSP(ctx.expression().text, visit(ctx.behaviour(0)) as IBehaviour, visit(ctx.behaviour(1)) as IBehaviour)
    }

    override fun visitProcedureDefinition(ctx: ProcedureDefinitionContext): SPNode {
        return visit(ctx.behaviour()) as IBehaviour
    }

    override fun visitProcedureInvocation(ctx: ProcedureInvocationContext): SPNode {
        return ProcedureInvocationSP(ctx.procedure().text)
    }

    override fun visitTerminal(node: TerminalNode?): SPNode {
        return TerminationSP()
    }
}