package ast.sp.labels.interfaces

import java.util.*

abstract class ExtractionLabel(var flipped: Boolean) {
    abstract override fun equals(other: Any?): Boolean
    override fun hashCode(): Int {
        return (Random::nextDouble).hashCode()
    }

    abstract fun copy() : ExtractionLabel

}
