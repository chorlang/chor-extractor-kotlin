package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class Then(val process: String, val expression: String) : ExtractionLabel() {

    override fun toString(): String {
        return "if$process.$expression then "
    }
}
