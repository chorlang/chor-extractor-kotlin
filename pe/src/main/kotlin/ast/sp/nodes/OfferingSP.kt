package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP
import kotlin.collections.HashMap

data class OfferingSP(val sender: String, val labels: HashMap<String, IBehaviour>) : ActionSP(sender) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(process + "&{")
        for ((key, value) in labels) {
            builder.append("$key: $value, ")
        }
        builder.delete(builder.length - 2, builder.length)
        builder.append("}")
        return builder.toString()
    }

    override fun copy(): ActionSP {
        val lblcopy = HashMap<String, IBehaviour>()
        for (l in labels){
            lblcopy.put(""+l.key, l.value.copy())
        }

        return OfferingSP(""+process, lblcopy)
    }

    override fun equals(b: IBehaviour): Boolean {
        if (!(b is OfferingSP) || process != b.process) return false
        else {
            for (label in labels) {
                val bl = b.labels.get(label.key)
                if (bl == null || !label.value.equals(bl)) return false
            }
        }
        return true
    }
}
