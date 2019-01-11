package np

import antlrgen.ChoreographyBaseVisitor
import antlrgen.ChoreographyParser
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody
import ast.cc.nodes.*

/**
 * parse context received from antlr to choreography
 */
class ChoreographyVisitor : ChoreographyBaseVisitor<CCNode>() {
    override fun visitProgram(ctx: ChoreographyParser.ProgramContext): CCNode {
        val choreographies = ArrayList<Choreography?>()

        ctx.choreography().forEach { chor -> choreographies.add(visitChoreography(chor)) }

        return Program(choreographies = choreographies, statistics = ArrayList())
    }

    override fun visitChoreography(ctx: ChoreographyParser.ChoreographyContext): Choreography {
        val main = visitBehaviour(ctx.main().behaviour())
        val procedures = ArrayList<ProcedureDefinition>()
        ctx.procedureDefinition().forEach { procedure -> procedures.add(visitProcedureDefinition(procedure)) }

        return Choreography(main = main, procedures = procedures, processes = emptySet())
    }

    override fun visitBehaviour(ctx: ChoreographyParser.BehaviourContext): ChoreographyBody {
        return when {
            !ctx.condition().isEmpty -> visitCondition(ctx.condition())
            !ctx.interaction().isEmpty -> visitInteraction(ctx.interaction())
            !ctx.procedureInvocation().isEmpty -> visitProcedureInvocation(ctx.procedureInvocation())
            else -> return Termination()
        }
    }


    override fun visitProcedureDefinition(ctx: ChoreographyParser.ProcedureDefinitionContext): ProcedureDefinition {
        return ProcedureDefinition(name = ctx.procedure().text, body = visitBehaviour(ctx.behaviour()), usedProcesses = hashSetOf())
        TODO("used processes should be somehow counted")
    }

    override fun visitCondition(ctx: ChoreographyParser.ConditionContext): Condition {
        return Condition(process = ctx.process().text, expression = ctx.expression().text, thenChoreography = visitBehaviour(ctx.behaviour(0)), elseChoreography = visitBehaviour(ctx.behaviour(1)))
    }

    override fun visitInteraction(ctx: ChoreographyParser.InteractionContext): ChoreographyBody {
        return when {
            !ctx.communication().isEmpty -> {
                val communication = ctx.communication()
                CommunicationSelection(eta = Communication(sender = communication.process(0).text, receiver = communication.process(1).text, expression = communication.expression().text), continuation = visitBehaviour(communication.behaviour()))
            }
            !ctx.selection().isEmpty -> {
                val selection = ctx.selection()
                CommunicationSelection(eta = Selection(sender = selection.process(0).text, receiver = selection.process(1).text, label = selection.expression().text), continuation = visitBehaviour(selection.behaviour()))
            }
            else -> throw Exception()
        }
    }

    override fun visitProcedureInvocation(ctx: ChoreographyParser.ProcedureInvocationContext): ProcedureInvocation {
        return ProcedureInvocation(procedure = ctx.procedure().text, processes = hashSetOf())
    }
}