package epp

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.ChoreographyBody
import ast.cc.nodes.*
import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*
import util.choreographyStatistics.UsedProcesses
import javax.naming.OperationNotSupportedException
import kotlin.collections.HashMap

/**
 * @author Fabrizio Montesi <famontesi@gmail.com>
 * From choreography project network
 */
class BehaviourProjection private constructor(private val processName:String, private val usedProcesses: Map<String,Set<String>>) : CCVisitor<Behaviour> {
    override fun visit(n: CommunicationSelection): Behaviour {
        val continuation = n.continuation.accept(this)
        val eta = n.eta

        return when (eta) {
            is Communication -> {
                when (processName) {
                    eta.sender -> SendSP(continuation, eta.receiver, eta.expression)
                    eta.receiver -> ReceiveSP(continuation, eta.sender)
                    else -> continuation
                }
            }
            is Selection -> {
                when (processName) {
                    eta.sender -> SelectionSP(continuation, eta.receiver, eta.label)
                    eta.receiver -> {
                        val labels = HashMap<String, Behaviour>()
                        labels[eta.label] = continuation
                        OfferingSP(eta.sender, labels)
                    }
                    else -> continuation
                }
            }
            else -> throw NotImplementedError() // Unreachable code
        }
    }

    override fun visit(n: Multicom): Behaviour = throw OperationNotSupportedException() // TODO : implement this

    override fun visit(n: Condition): Behaviour {
        return if (processName == n.process) {
            ConditionSP(n.expression, n.thenChoreography.accept(this), n.elseChoreography.accept(this))
        } else {
            Merging.merge(n.thenChoreography.accept(this), n.elseChoreography.accept(this))
        }
    }

    override fun visit(n: Termination): Behaviour {
        return TerminationSP()
    }

    override fun visit(n: ProcedureDefinition): Behaviour = throw OperationNotSupportedException()

    override fun visit(n: ProcedureInvocation): Behaviour {
        return if (usedProcesses[n.procedure]!!.contains(processName)) {
            ProcedureInvocationSP(n.procedure)
        } else {
            TerminationSP()
        }
    }

    override fun visit(n: Choreography): Behaviour = throw OperationNotSupportedException()

    override fun visit(n: Program): Behaviour = throw OperationNotSupportedException()

    companion object {
        private fun project(body: ChoreographyBody, processName: String, usedProcesses: Map<String,Set<String>>): Behaviour {
            return body.accept(BehaviourProjection(processName, usedProcesses))
        }

        fun project(choreography: Choreography, processName: String): ProcessTerm {
            val usedProcesses = UsedProcesses.usedProcesses(choreography)

            val procedureProjections = HashMap<String, Behaviour>()
            for (procedure in choreography.procedures) {
                try {
                    procedureProjections[procedure.name] = project(procedure.body, processName, usedProcesses)
                } catch( e:Merging.MergingException ) {
                    val newException = Merging.MergingException( "(name ${procedure.name}): ${e.message!!}" )
                    newException.stackTrace = e.stackTrace
                    throw newException
                }
            }
            return ProcessTerm(procedureProjections, project(choreography.main, processName, usedProcesses))
        }
    }
}