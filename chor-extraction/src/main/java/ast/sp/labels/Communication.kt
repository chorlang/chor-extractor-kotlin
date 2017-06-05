package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

class Communication(val sender: String, val receiver: String, val expression: String) : ExtractionLabel {

    override fun toString(): String {
        return "$sender.$expression->$receiver"
    }
}
