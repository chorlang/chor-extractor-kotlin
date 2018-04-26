package ast.sp.labels.interfaces

import java.util.*

abstract class InteractionLabel (val snd: String, val rcv: String, val expr: String, flipped: Boolean): ExtractionLabel(flipped) {

    override fun equals(other: Any?): Boolean {
        if (other !is InteractionLabel) return false

        return other.diff == diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
