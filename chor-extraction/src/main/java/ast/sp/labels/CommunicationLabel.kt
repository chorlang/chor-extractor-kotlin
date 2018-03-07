package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class CommunicationLabel(val sender: String, val receiver: String, val expression: String) : ExtractionLabel(flipped = false) {

    override fun toString(): String {
        return "$sender.$expression->$receiver"
    }
}
