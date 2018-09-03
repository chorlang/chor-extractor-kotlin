package ast.sp.nodes

import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPVisitor

data class ConditionSP(val expression: String, val thenBehaviour: IBehaviour, val elseBehaviour: IBehaviour) : IBehaviour {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = "if $expression then $thenBehaviour else $elseBehaviour"

    override fun copy(): IBehaviour = ConditionSP(expression, thenBehaviour.copy(), elseBehaviour.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConditionSP) return false

        return expression == other.expression &&
                thenBehaviour.equals(other.thenBehaviour) &&
                elseBehaviour.equals(other.elseBehaviour)
    }

    override fun hashCode(): Int {
        var result = expression.hashCode()
        result = 31 * result + thenBehaviour.hashCode()
        result = 31 * result + elseBehaviour.hashCode()
        return result
    }
}
