package ast.sp.nodes

import ast.sp.nodes.interfaces.SPNode
import kotlin.collections.HashMap

typealias ProcessName = String

data class Network(val processes: HashMap<ProcessName, ProcessTerm>) : SPNode {
    override fun toString(): String {
        val builder = StringBuilder()
        processes.forEach { t, u -> builder.append(t).append(u.toString()).append(" | ")}
        if (builder.length >= 3) { builder.delete(builder.length - 3, builder.length) }
        return builder.toString()
    }

    fun copy(): Network{
        val temp = HashMap<String, ProcessTerm>()
        for (p in processes){
            temp[p.key] = p.value.copy()
        }

        return Network(temp)
    }

    fun equals(n: Network): Boolean{
        for(p in processes) {
            val process = p.key
            val np = n.processes.get(process)
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


