package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction

data class SelectionSP(val continuation: Behaviour, val process: String, val expression: String) : Interaction {

    override fun toString(): String {
        return process + " + " + expression + "; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): Interaction {
        return SelectionSP(continuation.copy(), process, expression)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is SelectionSP &&
                b.process == process &&
                b.expression == expression &&
                b.continuation.equals(continuation)
    }
}
