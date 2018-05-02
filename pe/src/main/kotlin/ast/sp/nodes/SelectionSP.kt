package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.ActionSP

data class SelectionSP(val continuation: Behaviour, val receiver: String, val expression: String) : ActionSP(receiver) {

    override fun toString(): String {
        return process + " + " + expression + "; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): ActionSP {
        return SelectionSP(continuation.copy(), ""+process, ""+expression)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is SelectionSP &&
                b.process == process &&
                b.expression == expression &&
                b.continuation.equals(continuation)
    }
}
