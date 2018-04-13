package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.InteractionSP
import kotlin.collections.HashMap

data class Offering(val pr: String, val labels: HashMap<String, Behaviour>) : InteractionSP(pr) {
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

    override fun findRecProcCall(procname: String): Boolean {
        for (value in labels.values) {
            if (value.findRecProcCall(procname)) {
                return true
            }
        }
        return false
    }

    override fun copy(): InteractionSP {
        val lblcopy = HashMap<String,Behaviour>()
        for (l in labels){
            lblcopy.put(""+l.key, l.value.copy())
        }

        return Offering(""+process, lblcopy)
    }

    override fun equals(b: Behaviour): Boolean {
        if (!(b is Offering) || process != b.process) return false
        else {
            for (label in labels) {
                val bl = b.labels.get(label.key)
                if (bl == null || !label.value.equals(bl)) return false
            }
        }
        return true
    }
}
