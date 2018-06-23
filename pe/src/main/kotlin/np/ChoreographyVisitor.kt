package np

import ChoreographyBaseVisitor
import ChoreographyParser.*
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import ast.cc.nodes.*
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.HashSet
import kotlin.collections.ArrayList

class ChoreographyVisitor : ChoreographyBaseVisitor<CCNode>() {

    val processes: HashSet<String> = HashSet()

    fun getCCAST(parseTree: ParseTree): CCNode {
        return this.visit(parseTree)
    }

    override fun visitCommunication(ctx: CommunicationContext): CCNode {
        val sender = ctx.process(0).text
        val receiver = ctx.process(1).text
        val expression = ctx.expression().text

        val continuation = visit(ctx.choreography())

        processes.add(sender)
        processes.add(receiver)

        return CommunicationSelection(Communication(sender, receiver, expression), continuation)
    }

    override fun visitSelection(ctx: SelectionContext): CCNode {

        val sender = ctx.process(0).text
        val receiver = ctx.process(1).text
        val expression = ctx.expression().text

        val continuation = visit(ctx.choreography())

        processes.add(sender)
        processes.add(receiver)

        return CommunicationSelection(Selection(sender, receiver, expression), continuation)
    }

    override fun visitCondition(ctx: ConditionContext): CCNode {
        val process = ctx.process().text
        val expression = ctx.expression().text
        val thenChoreography = visit(ctx.choreography(0))
        val elseChoreography = visit(ctx.choreography(1))

        processes.add(process)

        return Condition(process, expression, thenChoreography, elseChoreography)
    }

    override fun visitProgram(ctx: ProgramContext): CCNode {
        val procedures = ArrayList<ProcedureDefinition>()
        ctx.procedureDefinition().stream().forEach { i -> procedures.add(visit(i) as ProcedureDefinition) }
        return Program(visit(ctx.main()) as Choreography, procedures)
    }

    override fun visitProcedureDefinition(ctx: ProcedureDefinitionContext): CCNode {
        return ProcedureDefinition(ctx.procedure().text, visit(ctx.choreography()), processes)
    }

    override fun visitMain(ctx: MainContext): CCNode {
        return visit(ctx.choreography())
    }

    override fun visitProcedureInvocation(ctx: ProcedureInvocationContext): CCNode {
        val procedureName = ctx.procedure().text
        return ProcedureInvocation(procedureName, processes)
    }

    override fun visitTerminal(node: TerminalNode?): CCNode {
        return Termination()
    }
}