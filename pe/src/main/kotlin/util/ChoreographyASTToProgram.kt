package util

import antlrgen.ChoreographyBaseVisitor
import antlrgen.ChoreographyParser.*
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody
import ast.cc.nodes.*
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode

class ChoreographyASTToProgram : ChoreographyBaseVisitor<CCNode>() {
    private var iteration: Int = 0
    val processesInChoreography = ArrayList<HashSet<String>>()

    companion object {
        fun toProgram(parseTree: ParseTree): Program = ChoreographyASTToProgram().getProgram(parseTree) as Program
    }

    fun getProgram(parseTree: ParseTree): CCNode {
        return this.visit(parseTree)
    }

    override fun visitCommunication(ctx: CommunicationContext): ChoreographyBody {
        val sender = ctx.process(0).text
        val receiver = ctx.process(1).text
        val expression = ctx.expression().text

        processesInChoreography[iteration].add(sender)
        processesInChoreography[iteration].add(receiver)

        val continuation = visit(ctx.behaviour())

        return CommunicationSelection(Communication(sender, receiver, expression), continuation as ChoreographyBody)
    }

    override fun visitSelection(ctx: SelectionContext): ChoreographyBody {
        val sender = ctx.process(0).text
        val receiver = ctx.process(1).text
        val expression = ctx.expression().text

        processesInChoreography[iteration].add(sender)
        processesInChoreography[iteration].add(receiver)

        val continuation = visit(ctx.behaviour())

        return CommunicationSelection(Selection(sender, receiver, expression), continuation as ChoreographyBody)
    }

    override fun visitCondition(ctx: ConditionContext): ChoreographyBody {
        val process = ctx.process().text
        val expression = ctx.expression().text

        processesInChoreography[iteration].add(process)

        val thenChoreography = visit(ctx.behaviour(0))
        val elseChoreography = visit(ctx.behaviour(1))

        return Condition(process, expression, thenChoreography as ChoreographyBody, elseChoreography as ChoreographyBody)
    }

    override fun visitChoreography(ctx: ChoreographyContext): CCNode {
        val procedures = ArrayList<ProcedureDefinition>()
        ctx.procedureDefinition().stream().forEach { i -> procedures.add(visit(i) as ProcedureDefinition) }
        return Choreography(visit(ctx.main()) as ChoreographyBody, procedures, processesInChoreography[iteration])
    }

    override fun visitProgram(ctx: ProgramContext): CCNode {
        val choreographyList = ArrayList<Choreography?>()
        for (choreography in ctx.choreography()){
            processesInChoreography.add(HashSet())
            choreographyList.add(visit(choreography) as Choreography)
            iteration++
        }
        return Program(choreographyList)
    }

    override fun visitProcedureDefinition(ctx: ProcedureDefinitionContext): CCNode {
        return ProcedureDefinition(ctx.procedure().text, visit(ctx.behaviour()) as ChoreographyBody, processesInChoreography[iteration])
    }

    override fun visitMain(ctx: MainContext): CCNode {
        return visit(ctx.behaviour())
    }

    override fun visitProcedureInvocation(ctx: ProcedureInvocationContext): CCNode {
        val procedureName = ctx.procedure().text
        return ProcedureInvocation(procedureName, processesInChoreography[iteration])
    }

    override fun visitTerminal(node: TerminalNode?): CCNode {
        return Termination()
    }
}