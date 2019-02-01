package epp

import ast.cc.interfaces.CCVisitor
import ast.cc.nodes.*
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import util.choreographyStatistic.UsedProcesses
import java.util.*
import javax.naming.OperationNotSupportedException
import kotlin.collections.HashMap

/**
 * @author Fabrizio Montesi <famontesi@gmail.com>
 * From choreography project network
 */
class BehaviourProjection(private val processName:String) : CCVisitor<SPNode> {
    private val procedures: ArrayList<Behaviour> = ArrayList() // TODO Why do we need Behaviour here?
    private var usedProcesses:Map<String,Set<String>> = emptyMap()

    override fun visit(n: CommunicationSelection): SPNode {
        val continuation = n.continuation.accept(this)
        val eta = n.eta

        return when (eta) {
            is Communication -> {
                when (processName) {
                    eta.sender -> SendSP(continuation as Behaviour, eta.receiver, eta.expression)
                    eta.receiver -> ReceiveSP(continuation as Behaviour, eta.sender)
                    else -> continuation
                }
            }
            is Selection -> {
                when (processName) {
                    eta.sender -> SelectionSP(continuation as Behaviour, eta.receiver, eta.label)
                    eta.receiver -> {
                        val labels = HashMap<String, Behaviour>()
                        labels[eta.label] = continuation as Behaviour
                        OfferingSP(eta.sender, labels)
                    }
                    else -> continuation
                }
            }
            else -> throw NotImplementedError() // Unreachable code
        }
    }

    override fun visit(n: Multicom): SPNode {
        TODO("not implemented")
        throw OperationNotSupportedException()
    }

    override fun visit(n: Condition): SPNode {
        return if (processName == n.process) {
            ConditionSP(n.expression, n.thenChoreography.accept(this) as Behaviour, n.elseChoreography.accept(this) as Behaviour)
        } else {
            Merging.merge(n.thenChoreography.accept(this), n.elseChoreography.accept(this))
        }
    }

    override fun visit(n: Termination): SPNode {
        return TerminationSP()
    }

    override fun visit(n: ProcedureDefinition): SPNode {
        val node = n.body.accept(this)
        procedures.add(node as Behaviour)
        return node
    }

    override fun visit(n: ProcedureInvocation): SPNode {
        return if (usedProcesses[n.procedure]!!.contains(processName)) {
            ProcedureInvocationSP(n.procedure)
        } else {
            TerminationSP()
        }
    }

    override fun visit(n: Choreography): SPNode {
        usedProcesses = UsedProcesses.usedProcesses(n)

        val procedures = HashMap<String, Behaviour>()
        for (procedure in n.procedures) {
            try {
                procedures[procedure.name] = procedure.accept(this) as Behaviour
            } catch( e:Merging.MergingException ) {
                val newException = Merging.MergingException( "(name ${procedure.name}): ${e.message!!}" )
                newException.stackTrace = e.stackTrace
                throw newException
            }
        }
        return ProcessTerm(procedures, n.main.accept(this) as Behaviour)
    }

    override fun visit(n: Program): SPNode {
        // Not implemented because behaviour projection does not act on programs, only choreographies.
        throw OperationNotSupportedException()
    }

    companion object {
        fun project(choreography: Choreography, processName: String): SPNode {
            val projector = BehaviourProjection(processName)
            return choreography.accept(projector)
        }
    }
}