package np

import ast.cc.interfaces.CCNode
import ast.cc.interfaces.CCVisitor
import ast.cc.nodes.*
import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import util.choreographyStatistic.UsedProcesses
import java.util.*
import kotlin.collections.HashMap

/**
 * @author Fabrizio Montesi <famontesi></famontesi>@gmail.com>
 * From choreography project network
 */
class BehaviourProjection : CCVisitor<SPNode> {
    private var processName: String = ""
    private var procedures: MutableList<Behaviour> = mutableListOf()
    private var usedProcesses:Map<String,Set<String>> = emptyMap()

    override fun visit(n: Program): SPNode {
        TODO("not implemented")
        /** this is not implemented because parsing of choreographies list was done earlier without use of visitor */
    }

    override fun visit(n: CommunicationSelection): SPNode {
        val continuation = n.continuation.accept(this)
        val interaction = n.eta

        when (interaction) {
            is Selection -> {
                return when (processName) {
                    interaction.sender -> SelectionSP(continuation as IBehaviour, interaction.receiver, interaction.label)
                    interaction.receiver -> {
                        val labels = HashMap<String, IBehaviour>()
                        labels[interaction.label] = continuation as IBehaviour
                        OfferingSP(interaction.sender, labels)
                    }
                    else -> continuation
                }
            }
            is Communication -> {
                return when (processName) {
                    interaction.sender -> SendingSP(continuation as IBehaviour, interaction.receiver, interaction.expression)
                    interaction.receiver -> ReceiveSP(continuation as IBehaviour, interaction.sender)
                    else -> continuation
                }
            }
            else -> throw NotImplementedError()
        }
    }

    override fun visit(n: Multicom): SPNode {
        TODO("not implemented")
    }

    fun getProcessTerm(node: CCNode, processName: String): SPNode {
        this.processName = processName
        procedures = ArrayList()
        return node.accept(this)
    }

    override fun visit(n: Condition): SPNode {
        return if (processName == n.process) {
            ConditionSP(n.expression, n.thenChoreography.accept(this) as IBehaviour, n.elseChoreography.accept(this) as IBehaviour)
        } else {
            Merging.merge(n.thenChoreography.accept(this), n.elseChoreography.accept(this))
        }
    }

    override fun visit(n: Termination): SPNode {
        return TerminationSP()
    }

    override fun visit(n: ProcedureDefinition): SPNode {
        val node = n.body.accept(this)
        val pd = Behaviour(node as IBehaviour)
        procedures.add(pd)
        return pd
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

        val procedures = HashMap<String, IBehaviour>()
        for (procedure in n.procedures) {
            try {
                procedures[procedure.name] = procedure.accept(this) as IBehaviour
            } catch( e:Merging.MergingException ) {
                val newException = Merging.MergingException( "(name ${procedure.name}): ${e.message!!}" )
                newException.stackTrace = e.stackTrace
                throw newException
            }
        }
        return ProcessTerm(procedures, n.main.accept(this) as IBehaviour)
    }
}