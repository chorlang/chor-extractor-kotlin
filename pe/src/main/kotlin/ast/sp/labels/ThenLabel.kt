package ast.sp.labels

import ast.sp.labels.interfaces.ExtractionLabel
import java.util.*

data class ThenLabel(val process: String, val expression: String) : ExtractionLabel(false) {

    override fun toString(): String {
        return "if $process.$expression then "
    }

    fun equals(e: ThenLabel): Boolean{
        return diff == e.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
