package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel
import java.util.*

data class InteractionLabel(val sender: String, val receiver: String, val expression: String) : ExtractionLabel(flipped = false) {

    override fun toString(): String {
        return "$sender.$expression->$receiver"
    }

    fun equals(e: InteractionLabel): Boolean{
        return diff == e.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }

}
