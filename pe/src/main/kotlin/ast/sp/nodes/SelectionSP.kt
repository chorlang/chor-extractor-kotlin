package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP

data class SelectionSP(val continuation: IBehaviour, val receiver: String, val expression: String) : ActionSP(receiver) {
    override fun toString() = process + " + " + expression + "; " + continuation.toString()

    override fun copy(): ActionSP = SelectionSP(continuation.copy(), process, expression)

    override fun equals(b: IBehaviour) = b is SelectionSP && b.process == process && b.expression == expression && b.continuation.equals(continuation)
}
