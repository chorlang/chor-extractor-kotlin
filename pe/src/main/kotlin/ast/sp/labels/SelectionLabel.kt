package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel

data class SelectionLabel(val sender: String, val receiver: String, val label: String) : InteractionLabel(sender, receiver, label, false) {

    override fun toString() = "$sender->$receiver[$label]"

    override fun equals(other: Any?): Boolean {
        return (this === other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + sender.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }
}
