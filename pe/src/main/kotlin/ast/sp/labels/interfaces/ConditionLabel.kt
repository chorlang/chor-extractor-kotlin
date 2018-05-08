package ast.sp.labels.interfaces

import java.util.*

abstract class ConditionLabel (open val process: String, open val expression: String, flipped: Boolean): ExtractionLabel(flipped) {

    override fun equals(other: Any?) = if (other !is ConditionLabel) false else other.diff == diff

    override fun hashCode(): Int = Objects.hash(diff)
}
