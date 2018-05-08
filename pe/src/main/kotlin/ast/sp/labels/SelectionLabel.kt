package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel
import java.util.*

data class SelectionLabel(val sender: String, val receiver: String, val label: String) : InteractionLabel(sender, receiver, label, false) {

    override fun toString() = "$sender->$receiver[$label]"

    fun equals(e: SelectionLabel) = diff == e.diff

    override fun hashCode(): Int = Objects.hash(diff)
}
