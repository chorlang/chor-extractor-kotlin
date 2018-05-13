package ast.sp.labels

import ast.sp.labels.interfaces.InteractionLabel

data class SelectionLabel(val sender: String, val receiver: String, val label: String) : InteractionLabel(sender, receiver, label, false) {

    override fun toString() = "$sender->$receiver[$label]"

    override fun equals(other: Any?): Boolean {
        return (this === other)
    }
}
