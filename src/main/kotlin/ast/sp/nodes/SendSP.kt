package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ActionSP
import ast.sp.interfaces.SPVisitor

/**
 * Created by fmontesi on 03/04/17.
 */
data class SendSP(val continuation: Behaviour, val receiver: String, val expression: String) : ActionSP(receiver) {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = "$process!<$expression>; $continuation"

    override fun copy(): ActionSP = SendSP(continuation.copy(), process, expression)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is SendSP &&
                other.process == process &&
                other.expression == expression &&
                other.continuation == continuation
    }

    override fun hashCode(): Int {
        var result = continuation.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + expression.hashCode()
        return result
    }
}
