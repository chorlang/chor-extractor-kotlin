package ast.sp.nodes

import ast.sp.interfaces.SPNode
import java.util.*

data class Network(val network: SortedMap<String, ProcessBehaviour>) : SPNode {

    override fun toString(): String {
        val builder = StringBuilder()
        network.forEach { t, u -> builder.append(t).append(u.toString()).append(" | ")}
        if (builder.length >= 3) { builder.delete(builder.length - 3, builder.length) }
        return builder.toString()
    }

    fun copy(): Network{
        val temp = mutableMapOf<String, ProcessBehaviour>()
        network.forEach { str, pb -> temp.put(str,pb.copy())  }
        return Network(temp.toSortedMap())
    }

}
