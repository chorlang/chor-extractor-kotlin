package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.SPNode

class ProcessBehaviour(val procedures: HashMap<String, ProcedureDefinitionSP>, var main: Behaviour) : SPNode {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        procedures.forEach { t, u -> builder.append("def "+ t).append("{").append(u.toString()).append("} ")}
        builder.append("main {").append(main.toString()).append("}}")

        return builder.toString()
    }

    fun copy(): ProcessBehaviour{
        val prcopy = HashMap<String, ProcedureDefinitionSP>()
        for (p in procedures){
            prcopy.put(""+p.key, p.value.copy())
        }

        return ProcessBehaviour(prcopy, main.copy())
    }

    fun equals(pb: ProcessBehaviour): Boolean{
        if (!main.equals(pb.main)) return false
        for (pr in procedures){
            val pbpr = pb.procedures.get(pr.key)
            if (pbpr == null || !pbpr.behaviour.equals(pr.value)) return false
        }
        return true
    }
}
