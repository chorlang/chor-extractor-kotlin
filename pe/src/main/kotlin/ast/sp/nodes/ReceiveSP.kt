package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.ActionSP

data class ReceiveSP(val continuation: Behaviour, val sender: String) : ActionSP(sender) {
    override fun toString(): String {
        return process + "?; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): ActionSP {
        return ReceiveSP(continuation.copy(), ""+process)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is ReceiveSP &&
                b.process == process &&
                b.continuation.equals(continuation)
    }
}
