package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel
import java.util.*

data class SelectionLabel(val sender: String, val receiver: String, val label: String) : ExtractionLabel(false) {

    override fun toString(): String {
        return "$sender->$receiver[$label]"
    }

    fun equals(e: SelectionLabel): Boolean{
        return diff == e.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
