/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.SPNode
import ast.sp.nodes.*
import np.MergingProjection.*

import java.util.*
import kotlin.collections.HashMap

/**

 * @author Fabrizio Montesi <famontesi></famontesi>@gmail.com>
 */
class BehaviourProjection : CCVisitor<SPNode> {
    override fun visit(n: CommunicationSelection): SPNode {

        val continuation = n.continuation.accept(this)
        val nn = n.node

        when (nn){
            is Selection -> {
                return when (processName) {
                    nn.sender -> SelectionSP(continuation as IBehaviour, nn.receiver, nn.label)
                    nn.receiver -> {
                        val labels = HashMap<String, IBehaviour>()
                        labels.put(nn.label, continuation as IBehaviour)
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

    fun getSPAST(node: CCNode, processName: String): SPNode {
        this.processName = processName
        procedures = ArrayList<Behaviour>()
        return node.accept(this)
    }

    override fun visit(n: Condition): SPNode {
        if (processName == n.process) {
            return ConditionSP(n.expression, n.thenChoreography.accept(this) as IBehaviour, n.elseChoreograpy.accept(this) as IBehaviour)
        } else {
            try {
                return MergingProjection().merge(n.thenChoreography.accept(this), n.elseChoreograpy.accept(this))
            } catch (e: MergingException) {
                e.printStackTrace()
                throw e
            }

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
        if (n.processes.contains(processName)) {
            return ProcedureInvocationSP(n.procedure)
        } else {
            return TerminationSP()

        }
    }

    override fun visit(n: Program): SPNode {
        val procedures = HashMap<String, IBehaviour>()
        for (procedure in n.procedures) {
            procedures.put(procedure.procedure, procedure.accept(this) as IBehaviour)
        }
        return ProcessTerm(procedures, n.main.accept(this) as IBehaviour)
    }
}