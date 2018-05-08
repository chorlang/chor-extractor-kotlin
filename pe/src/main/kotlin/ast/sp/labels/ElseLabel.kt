package ast.sp.labels

import ast.sp.labels.interfaces.ConditionLabel
import ast.sp.labels.interfaces.ExtractionLabel
import java.util.*

data class ElseLabel(override val process: String, override val expression: String) : ConditionLabel(process, expression, false) {

    override fun toString() = "if$process.$expression else "

    fun equals(e: ElseLabel) = e.diff == diff

    override fun hashCode(): Int = Objects.hash(diff)
}
