/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epp

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import epp.MergingProjection.*

import java.util.*
import kotlin.collections.HashMap

/**

 * @author Fabrizio Montesi <famontesi></famontesi>@gmail.com>
 */
class BehaviourProjection : CCVisitor<SPNode> {
    private var processName: String? = null
    private var procedures: MutableList<ProcedureDefinitionSP>? = null

    fun getSPAST(node: CCNode, processName: String): SPNode {
        this.processName = processName
        procedures = ArrayList<ProcedureDefinitionSP>()
        return node.accept(this)
    }

    override fun visit(n: Selection): SPNode {
        val continuation = n.continuation.accept(this)
        val retVal: SPNode

        if (processName == n.sender) {
            retVal = SelectionSP(continuation as Behaviour, n.receiver, n.label)
        } else if (processName == n.receiver) {
            retVal = Offering(n.sender, object : HashMap<String, Behaviour>() {
                init {
                    put(n.label, continuation as Behaviour)
                }
            })
        } else {
            retVal = continuation
        }

        return retVal
    }

    override fun visit(n: Communication): SPNode {
        val continuation = n.continuation.accept(this)

        val retVal: SPNode
        if (processName == n.sender) {
            retVal = Sending(continuation as Behaviour, n.receiver, n.expression)
        } else if (processName == n.receiver) {
            retVal = Receiving(continuation as Behaviour, n.sender)
        } else {
            retVal = continuation
        }

        return retVal
    }

    override fun visit(n: Condition): SPNode {
        if (processName == n.process) {
            return ConditionSP(n.expression, n.thenChoreography.accept(this) as Behaviour, n.elseChoreograpy.accept(this) as Behaviour)
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
        val pd = ProcedureDefinitionSP(node as Behaviour)
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
        val procedures = HashMap<String, ProcedureDefinitionSP>()
        for (procedure in n.procedures) {
            procedures.put(procedure.procedure, procedure.accept(this) as ProcedureDefinitionSP)
        }
        return ProcessBehaviour(procedures, n.main.accept(this) as Behaviour)
    }
}
