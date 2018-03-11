package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel
import java.util.*

data class ElseLabel(val process: String, val expression: String) : ExtractionLabel(false) {

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
