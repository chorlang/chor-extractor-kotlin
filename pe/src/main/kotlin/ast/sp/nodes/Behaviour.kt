
package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.SPNode

data class Behaviour(val behaviour: Behaviour) : SPNode {
    override fun toString(): String {
        return behaviour.toString()
    }

    fun findRecProcCall(procname: String): Boolean {
        return behaviour.findRecProcCall(procname)
    }

    fun copy(): ast.sp.nodes.Behaviour {
        return Behaviour(behaviour.copy())
    }

    fun equals(b: SPNode): Boolean {
        return b is ast.sp.nodes.Behaviour && behaviour.equals(b.behaviour)
    }
}