package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ActionSP
import ast.sp.interfaces.SPVisitor

data class ReceiveSP(val continuation: Behaviour, val sender: String) : ActionSP(sender) {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = "$sender?; $continuation"

    override fun copy(): ActionSP = ReceiveSP(continuation.copy(), process)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is ReceiveSP && other.sender == sender && other.continuation == continuation
    }

    override fun hashCode(): Int {
        var result = continuation.hashCode()
        result = 31 * result + sender.hashCode()
        return result
    }
}
