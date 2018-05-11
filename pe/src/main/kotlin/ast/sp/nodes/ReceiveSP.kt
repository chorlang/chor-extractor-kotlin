package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP

data class ReceiveSP(val continuation: IBehaviour, val sender: String) : ActionSP(sender) {
    override fun toString() = process + "?; " + continuation.toString()

    override fun copy(): ActionSP = ReceiveSP(continuation.copy(), process)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is ReceiveSP && other.process == process && other.continuation.equals(continuation)
    }

    override fun hashCode(): Int {
        var result = continuation.hashCode()
        result = 31 * result + sender.hashCode()
        return result
    }
}
