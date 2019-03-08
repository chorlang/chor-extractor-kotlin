package util

import antlrgen.NetworkBaseVisitor
import antlrgen.NetworkParser
import antlrgen.NetworkParser.*
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import org.antlr.v4.runtime.tree.TerminalNode

class NetworkASTToNetwork : NetworkBaseVisitor<SPNode>() {
    companion object {
        fun toNetwork(parseTree: NetworkParser.NetworkContext): Network = NetworkASTToNetwork().getNetwork(parseTree) as Network
    }

    fun getNetwork(parseTree: NetworkParser.NetworkContext): SPNode {
        return this.visit(parseTree)
    }

    override fun visitNetwork(ctx: NetworkParser.NetworkContext): SPNode {
        val network = HashMap<String, ProcessTerm>()
        for (i in 0 until ctx.processBehaviour().size) {
            network[ctx.process(i).text] = visit(ctx.processBehaviour(i)) as ProcessTerm
        }
        return Network(network)
    }

    override fun visitProcessBehaviour(ctx: NetworkParser.ProcessBehaviourContext): SPNode {
        val procedures = HashMap<String, Behaviour>()
        for (i in 0 until ctx.procedureDefinition().size) {
            procedures[ctx.procedure(i).text] = visit(ctx.procedureDefinition(i)) as Behaviour
        }

        return ProcessTerm(procedures, visit(ctx.behaviour()) as Behaviour)
    }

    override fun visitSending(ctx: SendingContext): SPNode {
        return SendSP(visit(ctx.behaviour()) as Behaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitReceiving(ctx: ReceivingContext): SPNode {
        return ReceiveSP(visit(ctx.behaviour()) as Behaviour, ctx.process().text)
    }

    override fun visitSelection(ctx: SelectionContext): SPNode {
        return SelectionSP(visit(ctx.behaviour()) as Behaviour, ctx.process().text, ctx.expression().text)
    }

    override fun visitOffering(ctx: OfferingContext): SPNode {
        val labeledBehaviour = HashMap<String, Behaviour>()
        for (lb in ctx.labeledBehaviour()) {
            labeledBehaviour[lb.expression().text] = visit(lb.behaviour()) as Behaviour
        }

        return OfferingSP(ctx.process().text, labeledBehaviour)
    }

    override fun visitCondition(ctx: ConditionContext): SPNode {
        return ConditionSP(ctx.expression().text, visit(ctx.behaviour(0)) as Behaviour, visit(ctx.behaviour(1)) as Behaviour)
    }

    override fun visitProcedureDefinition(ctx: ProcedureDefinitionContext): SPNode {
        return visit(ctx.behaviour()) as Behaviour
    }

    override fun visitProcedureInvocation(ctx: ProcedureInvocationContext): SPNode {
        return ProcedureInvocationSP(ctx.procedure().text)
    }

    override fun visitTerminal(node: TerminalNode?): SPNode {
        return TerminationSP()
    }
}