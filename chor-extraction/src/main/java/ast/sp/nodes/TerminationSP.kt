package ast.sp.nodes

import ast.sp.interfaces.Behaviour

class TerminationSP : Behaviour {
    private val termination = "stop"

    override fun toString(): String {
        return "stop"
    }

    override fun findRecProcCall(procname: String): Boolean {
        return false
    }
}