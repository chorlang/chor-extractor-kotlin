package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor

data class ConditionSP(val expression: String, val thenBehaviour: Behaviour, val elseBehaviour: Behaviour) : Behaviour {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString() = "if $expression then $thenBehaviour else $elseBehaviour"

    override fun copy(): Behaviour = ConditionSP(expression, thenBehaviour.copy(), elseBehaviour.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConditionSP) return false

        return expression == other.expression &&
                thenBehaviour == other.thenBehaviour &&
                elseBehaviour == other.elseBehaviour
    }

    override fun hashCode(): Int {
        var result = expression.hashCode()
        result = 31 * result + thenBehaviour.hashCode()
        result = 31 * result + elseBehaviour.hashCode()
        return result
    }
}
