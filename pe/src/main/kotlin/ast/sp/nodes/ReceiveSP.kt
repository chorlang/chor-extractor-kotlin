package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP

data class ReceiveSP(val continuation: IBehaviour, val sender: String) : ActionSP(sender) {
    override fun toString() = process + "?; " + continuation.toString()

    override fun copy(): ActionSP = ReceiveSP(continuation.copy(), process)

    override fun equals(b: IBehaviour) = b is ReceiveSP && b.process == process && b.continuation.equals(continuation)
}
