package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.nodes.interfaces.ActionSP
import kotlin.collections.HashMap

data class OfferingSP(val sender: String, val branches: HashMap<String, IBehaviour>) : ActionSP(sender) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("$process&{")
        for ((key, value) in branches) {
            builder.append("$key: $value, ")
        }
        builder.delete(builder.length - 2, builder.length)
        builder.append("}")
        return builder.toString()
    }

    override fun copy(): ActionSP {
        val branchesCopy = HashMap<String, IBehaviour>()
        branches.forEach { branchesCopy[it.key] = it.value.copy() }

        return OfferingSP(process, branchesCopy)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OfferingSP) return false
        if (process != other.process) return false

        for (label in branches) {
            val bl = other.branches[label.key]
            if (bl == null || label.value != bl) return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = sender.hashCode()
        result = 31 * result + branches.hashCode()
        return result
    }
}
