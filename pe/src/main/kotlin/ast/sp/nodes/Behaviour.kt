package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

data class Behaviour(val behaviour: IBehaviour) : IBehaviour {
    override fun toString() = behaviour.toString()

    override fun copy(): Behaviour = Behaviour(behaviour.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Behaviour) return false
        if (!behaviour.equals(other.behaviour)) return false

        return true
    }

    override fun hashCode(): Int {
        return behaviour.hashCode()
    }
}