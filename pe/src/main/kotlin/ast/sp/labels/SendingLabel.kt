package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel
import java.util.*

data class SendingLabel(val sender: String, val receiver: String, val expression: String) : InteractionLabel(sender, receiver, expression, false) {

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
