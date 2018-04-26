package ast.sp.labels

import ast.sp.labels.interfaces.ConditionLabel
import ast.sp.labels.interfaces.ExtractionLabel
import java.util.*

data class ElseLabel(override val process: String, override val expression: String) : ConditionLabel(process, expression, false) {

    override fun toString(): String {
        return "if$process.$expression else "
    }

    fun equals(e: ElseLabel): Boolean{
        return e.diff == diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
