package ast.sp.nodes

import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPVisitor

data class Behaviour(val behaviour: IBehaviour) : IBehaviour {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = behaviour.toString()

    override fun copy(): Behaviour = Behaviour(behaviour.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Behaviour) return false
        if (behaviour != other.behaviour) return false

        return true
    }

    override fun hashCode() = behaviour.hashCode()
}