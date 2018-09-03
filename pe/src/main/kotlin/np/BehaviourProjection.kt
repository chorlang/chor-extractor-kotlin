package np

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import np.MergingProjection.*
import util.UsedProcesses

import java.util.*
import kotlin.collections.HashMap

/**
 * @author Fabrizio Montesi <famontesi></famontesi>@gmail.com>
 */
class BehaviourProjection : CCVisitor<SPNode> {
    override fun visit(n: Program): SPNode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(n: CommunicationSelection): SPNode {
        val continuation = n.continuation.accept(this)
        val nn = n.node

        when (nn) {
            is Selection -> {
                return when (processName) {
                    nn.sender -> SelectionSP(continuation as IBehaviour, nn.receiver, nn.label)
                    nn.receiver -> {
                        val labels = HashMap<String, IBehaviour>()
                        labels[nn.label] = continuation as IBehaviour
                        OfferingSP(nn.sender, labels)
                    }
                    else -> continuation
                }
            }
            is Communication -> {
                return when (processName) {
                    nn.sender -> SendingSP(continuation as IBehaviour, nn.receiver, nn.expression)
                    nn.receiver -> ReceiveSP(continuation as IBehaviour, nn.sender)
                    else -> continuation
                }
            }
            else -> throw NotImplementedError()
        }
    }

    override fun visit(n: Multicom): SPNode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var processName: String? = null
    private var procedures: MutableList<Behaviour>? = null
    private var usedProcesses:Map<String,Set<String>>? = null

    fun getProcessTerm(node: CCNode, processName: String): SPNode {
        this.processName = processName
        procedures = ArrayList()
        return node.accept(this)
    }

    override fun visit(n: Condition): SPNode {
        return if (processName == n.process) {
            ConditionSP(n.expression, n.thenChoreography.accept(this) as IBehaviour, n.elseChoreography.accept(this) as IBehaviour)
        } else {
            MergingProjection().merge(n.thenChoreography.accept(this), n.elseChoreography.accept(this))
        }
    }

    override fun visit(n: Termination): SPNode {
        return TerminationSP()
    }

    override fun visit(n: ProcedureDefinition): SPNode {
        val node = n.choreography.accept(this)
        val pd = Behaviour(node as IBehaviour)
        procedures!!.add(pd)
        return pd
    }

    override fun visit(n: ProcedureInvocation): SPNode {
        return if (usedProcesses!![n.procedure]!!.contains(processName)) {
            ProcedureInvocationSP(n.procedure)
        } else {
            TerminationSP()
        }
    }

    override fun visit(n: Choreography): SPNode {
        usedProcesses = UsedProcesses.usedProcesses(n)
        //val chorStat = ChoreographyStatistics().getStat(n)

        val procedures = HashMap<String, IBehaviour>()
        for (procedure in n.procedures) {
            try {
                procedures[procedure.procedure] = procedure.accept(this) as IBehaviour
            } catch( e:MergingException ) {
                val newE = MergingProjection.MergingException( "(procedure ${procedure.procedure}): ${e.message!!}" )
                newE.stackTrace = e.stackTrace
                throw newE
            }
        }
        return ProcessTerm(procedures, n.main.accept(this) as IBehaviour)
    }
}