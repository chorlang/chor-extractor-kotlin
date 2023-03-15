package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ActionSP
import ast.sp.interfaces.SPVisitor

data class SelectionSP(val continuation: Behaviour, val receiver: String, val label: String) : ActionSP(receiver) {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = "$process + $label; $continuation"

    override fun copy(): ActionSP = SelectionSP(continuation.copy(), process, label)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is SelectionSP &&
                other.process == process &&
                other.label == label &&
                other.continuation == continuation
    }

    override fun hashCode(): Int {
        var result = continuation.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }
}
