package ast.sp.nodes

import ast.sp.interfaces.SPNode
import java.util.*

data class Network(val network: TreeMap<String, ProcessBehaviour>) : SPNode {
    override fun toString(): String {
        val builder = StringBuilder()
        network.forEach { t, u -> builder.append(t).append(u.toString()).append(" | ")}
        if (builder.length >= 3) { builder.delete(builder.length - 3, builder.length) }
        return builder.toString()
    }

    fun copy(): Network{
        val temp = network.clone()
        return Network(temp as TreeMap<String, ProcessBehaviour>)
    }

    fun equals(n: Network): Boolean{
        for(p in network) {
            val process = p.key
            val np = n.network.get(process)
            if (np!=null){
                if (!p.value.main.equals(np.main)) return false
                for (pr in p.value.procedures){
                    val procedure = pr.key
                    val npr = np.procedures.get(procedure)
                    if ((npr==null) || !pr.value.equals(npr)) return false
                }
            } else return false
        }
        return true
    }
}


