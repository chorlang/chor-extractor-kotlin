package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP

data class ReceiveSP(val continuation: IBehaviour, val sender: String) : ActionSP(sender) {
    override fun toString(): String {
        return process + "?; " + continuation.toString()
    }

    override fun copy(): ActionSP {
        return ReceiveSP(continuation.copy(), ""+process)
    }

    override fun equals(b: IBehaviour): Boolean {
        return b is ReceiveSP &&
                b.process == process &&
                b.continuation.equals(continuation)
    }
}
