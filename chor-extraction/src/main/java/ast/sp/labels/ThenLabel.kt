package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

data class ThenLabel(val process: String, val expression: String) : ExtractionLabel(false) {

    override fun toString(): String {
        return "if $process.$expression then "
    }
}
