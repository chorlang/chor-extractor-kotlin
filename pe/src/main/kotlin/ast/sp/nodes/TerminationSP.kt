package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

class TerminationSP : IBehaviour {
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