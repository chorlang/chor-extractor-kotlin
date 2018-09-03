package ast.sp.nodes

import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPVisitor

class TerminationSP : IBehaviour {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    private val termination = "stop"

    override fun toString() = termination

    override fun copy(): IBehaviour = TerminationSP()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        return other is TerminationSP &&
                other.termination == termination
    }

    override fun hashCode(): Int {
        return termination.hashCode()
    }
}