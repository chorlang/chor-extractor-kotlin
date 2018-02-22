package ast.sp.nodes

import ast.sp.interfaces.Behaviour

data class ProcedureDefinitionSP(val behaviour: Behaviour) : Behaviour {

    var visited: Boolean
    init {
        visited = false
    }

    override fun toString(): String {
        return behaviour.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return behaviour.findRecProcCall(procname)
    }

    override fun copy(): ProcedureDefinitionSP {
        return ProcedureDefinitionSP(behaviour.copy())
    }
}
