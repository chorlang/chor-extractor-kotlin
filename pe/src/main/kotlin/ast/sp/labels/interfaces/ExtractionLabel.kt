package ast.sp.labels.interfaces

import java.util.*
import java.util.Objects

abstract class ExtractionLabel (var flipped: Boolean){
    val diff = Random().nextInt()

    override fun equals(other: Any?): Boolean {
        if (other !is ExtractionLabel) return false

        return diff == other.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
