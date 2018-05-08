package ast.sp.labels.interfaces

import java.util.*

abstract class InteractionLabel (val snd: String, val rcv: String, val expr: String, flipped: Boolean): ExtractionLabel(flipped) {

    override fun equals(other: Any?) = if (other !is InteractionLabel) false else other.diff == diff

    override fun hashCode(): Int = Objects.hash(diff)
}
