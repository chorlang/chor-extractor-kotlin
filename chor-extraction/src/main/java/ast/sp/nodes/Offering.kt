package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction
import java.util.*

data class Offering(val process: String, val labels: HashMap<String, Behaviour>) : Interaction {

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
        for ((key, value) in labels) {
            if (value.findRecProcCall(procname)) {
                return true
            }
        }
        return false
    }

    override fun copy(): Interaction {
        val tempmap = mutableMapOf<String, Behaviour>()
        labels.forEach { str, bhv -> tempmap.put(str, bhv.copy())  }
        return Offering(process, tempmap as HashMap<String, Behaviour>)
    }
}
