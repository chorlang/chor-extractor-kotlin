
package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.SPNode

data class Behaviour(val behaviour: IBehaviour) : IBehaviour {
    override fun equals(b: IBehaviour): Boolean {
        return b is Behaviour && behaviour.equals(b.behaviour)
    }

    override fun toString(): String {
        return behaviour.toString()
    }

    override fun copy(): Behaviour {
        return Behaviour(behaviour.copy())
    }

    fun equals(b: SPNode): Boolean {
        return b is Behaviour && behaviour.equals(b.behaviour)
    }
}