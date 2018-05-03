package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

class TerminationSP : IBehaviour {
    private val termination = "stop"

    override fun toString(): String {
        return termination
    }

    override fun copy(): IBehaviour {
        return TerminationSP()
    }

    override fun equals(b: IBehaviour): Boolean {
        return b is TerminationSP && b.termination == termination
    }
}