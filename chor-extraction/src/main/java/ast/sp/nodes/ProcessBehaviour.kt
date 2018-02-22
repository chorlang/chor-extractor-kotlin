package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode

class ProcessBehaviour(val procedures: Map<String, ProcedureDefinitionSP>, var main: Behaviour) : SPNode {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        procedures.forEach { t, u -> builder.append(t).append("{").append(u.toString())}
        builder.append("main {").append(main.toString()).append("}}")

        return builder.toString()
    }

    fun copy(): ProcessBehaviour{
        val tempmap = mutableMapOf<String, ProcedureDefinitionSP>()
        procedures.forEach { str, prdef -> tempmap.put(str,prdef.copy())  }
        return ProcessBehaviour(tempmap, main.copy())
    }
}
