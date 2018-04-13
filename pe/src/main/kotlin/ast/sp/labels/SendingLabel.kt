package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel
import java.util.*

data class SendingLabel(override val sender: String, override val receiver: String, val expression: String) : InteractionLabel(sender, receiver, false) {

    override fun toString(): String {
        return "$sender.$expression->$receiver"
    }

    fun equals(e: SendingLabel): Boolean{
        return diff == e.diff
    }
}
