package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.SPNode

typealias ProcedureName = String

class ProcessTerm(val procedures: HashMap<ProcedureName, Behaviour>, var main: Behaviour) : SPNode {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        procedures.forEach { t, u -> builder.append("def "+ t).append("{").append(u.toString()).append("} ")}
        builder.append("main {").append(main.toString()).append("}}")

        return builder.toString()
    }

    fun copy(): ProcessTerm{
        val prcopy = HashMap<String, Behaviour>()
        for (p in procedures){
            prcopy.put(""+p.key, p.value.copy())
        }

        return ProcessTerm(prcopy, main.copy())
    }

    fun equals(pb: ProcessTerm): Boolean{
        if (!main.equals(pb.main)) return false
        for (pr in procedures){
            val pbpr = pb.procedures.get(pr.key)
            if (pbpr == null || !pbpr.equals(pr.value)) return false
        }
        return true
    }
}
