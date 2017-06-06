package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class Selection(val sender: String, val receiver: String, val label: String) : ExtractionLabel() {

    override fun toString(): String {
        return "$sender->$receiver[$label]"
    }
}
