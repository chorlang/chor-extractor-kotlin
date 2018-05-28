package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel

data class SendingLabel(val sender: String, val receiver: String, val expression: String) : InteractionLabel(sender, receiver, expression, false) {
    override fun toString() = "$sender.$expression->$receiver"

    override fun equals(other: Any?): Boolean {
        return (this === other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + sender.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + expression.hashCode()
        return result
    }
}
