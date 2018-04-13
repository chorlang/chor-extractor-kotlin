
package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.SPNode

data class ProcedureDefinitionSP(val behaviour: Behaviour) : SPNode {
    override fun toString(): String {
        return behaviour.toString()
    }

    fun findRecProcCall(procname: String): Boolean {
        return behaviour.findRecProcCall(procname)
    }

    fun copy(): ProcedureDefinitionSP {
        return ProcedureDefinitionSP(behaviour.copy())
    }

    fun equals(b: SPNode): Boolean {
        return b is ProcedureDefinitionSP && behaviour.equals(b.behaviour)
    }
}