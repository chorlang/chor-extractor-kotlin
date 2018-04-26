package ast.sp.labels.interfaces

import java.util.*

abstract class ConditionLabel (open val process: String, open val expression: String, flipped: Boolean): ExtractionLabel(flipped) {

    override fun equals(other: Any?): Boolean {
        if (other !is ConditionLabel) return false

        return other.diff == diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
