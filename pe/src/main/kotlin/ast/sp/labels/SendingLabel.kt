package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel
import java.util.*

data class SendingLabel(val sender: String, val receiver: String, val expression: String) : InteractionLabel(sender, receiver, expression, false) {
    override fun toString() = "$sender.$expression->$receiver"

    fun equals(e: SendingLabel) = diff == e.diff

    override fun hashCode(): Int = Objects.hash(diff)
}
