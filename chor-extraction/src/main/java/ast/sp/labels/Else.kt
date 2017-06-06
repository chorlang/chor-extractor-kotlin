package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class Else(val process: String, val expression: String) : ExtractionLabel() {

    override fun toString(): String {
        return "if$process.$expression else "
    }
}
