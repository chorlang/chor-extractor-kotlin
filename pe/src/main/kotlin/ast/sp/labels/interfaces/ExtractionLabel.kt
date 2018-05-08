package ast.sp.labels.interfaces

import java.util.*
import java.util.Objects

abstract class ExtractionLabel (var flipped: Boolean){
    val diff = Random().nextInt()

    override fun equals(other: Any?) = if (other !is ExtractionLabel) false else diff == other.diff

    override fun hashCode(): Int = Objects.hash(diff)
}
