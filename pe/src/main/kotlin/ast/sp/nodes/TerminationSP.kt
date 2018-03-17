package ast.sp.nodes

import ast.sp.interfaces.Behaviour

class TerminationSP : Behaviour {
    private val termination = "stop"

    override fun toString(): String {
        return termination
    }

    override fun findRecProcCall(procname: String): Boolean {
        return false
    }

    override fun copy(): Behaviour {
        return TerminationSP()
    }

    override fun equals(b: Behaviour): Boolean {
        return b is TerminationSP && b.termination == termination
    }
}