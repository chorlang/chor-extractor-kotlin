package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP

data class SelectionSP(val continuation: IBehaviour, val receiver: String, val expression: String) : ActionSP(receiver) {
    override fun toString() = "$process + $expression; $continuation"

    override fun copy(): ActionSP = SelectionSP(continuation.copy(), process, expression)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is SelectionSP &&
                other.process == process &&
                other.expression == expression &&
                other.continuation.equals(continuation)
    }

    override fun hashCode(): Int {
        var result = continuation.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + expression.hashCode()
        return result
    }
}
