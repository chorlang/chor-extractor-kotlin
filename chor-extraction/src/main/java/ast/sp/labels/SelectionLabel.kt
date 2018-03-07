package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class SelectionLabel(val sender: String, val receiver: String, val label: String) : ExtractionLabel(false) {

    override fun toString(): String {
        return "$sender->$receiver[$label]"
    }
}
