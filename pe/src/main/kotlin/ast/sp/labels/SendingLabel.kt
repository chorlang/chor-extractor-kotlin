package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel
import java.util.*

data class SendingLabel(val sender: String, val receiver: String, val expression: String) : ExtractionLabel(flipped = false) {

    override fun toString(): String {
        return "$sender.$expression->$receiver"
    }

    fun equals(e: SendingLabel): Boolean{
        return diff == e.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }

}
