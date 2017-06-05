package ast.sp.nodes

import ast.sp.interfaces.Behaviour

data class ProcedureDefinitionSP(val behaviour: Behaviour) : Behaviour {

    override fun toString(): String {
        return behaviour.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return behaviour.findRecProcCall(procname)
    }
}
