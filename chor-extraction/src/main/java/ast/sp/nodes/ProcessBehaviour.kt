package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode

class ProcessBehaviour(val procedures: HashMap<String, ProcedureDefinitionSP>, var main: Behaviour) : SPNode {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        procedures.forEach { t, u -> builder.append(t).append("{").append(u.toString()).append("} ")}
        builder.append("main {").append(main.toString()).append("}}")

        return builder.toString()
    }

    fun copy(): ProcessBehaviour{
        //val tempmap = mutableMapOf<String, ProcedureDefinitionSP>()
        //procedures.forEach { str, prdef -> tempmap.put(str,prdef.copy())  }

        val temp = procedures.clone() as HashMap<String, ProcedureDefinitionSP>
        assert(procedures.equals(temp))
        val maincopy = main.copy()
        assert(main.equals(maincopy))
        return ProcessBehaviour(temp, main.copy())
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
