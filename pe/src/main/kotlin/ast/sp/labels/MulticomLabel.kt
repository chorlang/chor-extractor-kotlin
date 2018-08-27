package ast.sp.labels

import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.interfaces.InteractionLabel
import kotlin.collections.ArrayList

data class MulticomLabel(val labels: ArrayList<InteractionLabel>) : ExtractionLabel(false) {
    override fun copy() = MulticomLabel(labels.clone() as ArrayList<InteractionLabel>)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("(")
        for (l in labels) {
            sb.append(l.toString()).append(", ")
        }
        if (sb.length >= 3) {
            sb.delete(sb.length - 2, sb.length)
        }
        sb.append(")")

        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        return (this === other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + labels.hashCode()
        return result
    }
}
