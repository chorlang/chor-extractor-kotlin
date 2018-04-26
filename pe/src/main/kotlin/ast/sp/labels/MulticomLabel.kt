package ast.sp.labels

import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.interfaces.InteractionLabel
import java.util.*
import kotlin.collections.ArrayList

data class MulticomLabel(val labels: ArrayList<InteractionLabel>) : ExtractionLabel(false) {

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("(")
        for (l in labels){
            sb.append(l.toString()). append(", ")
        }
        if (sb.length >= 3) { sb.delete(sb.length - 3, sb.length) }
        sb.append(")")

        return sb.toString()
    }

    fun equals(e: MulticomLabel): Boolean{
        return e.diff == diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
