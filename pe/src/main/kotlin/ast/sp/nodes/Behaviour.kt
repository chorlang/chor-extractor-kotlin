
package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.SPNode

data class Behaviour(val behaviour: IBehaviour) : IBehaviour {
    override fun equals(b: IBehaviour) = b is Behaviour && behaviour.equals(b.behaviour)

    override fun toString() = behaviour.toString()

    override fun copy(): Behaviour = Behaviour(behaviour.copy())

    fun equals(b: SPNode) = b is Behaviour && behaviour.equals(b.behaviour)
}