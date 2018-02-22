package ast.sp.nodes

import ast.sp.interfaces.Behaviour

data class ProcedureInvocationSP(val procedure: String) : Behaviour {

    var isVisited: Boolean
    init { isVisited = false }

    override fun toString(): String { return procedure + ifVisited() }

    override fun findRecProcCall(procname: String): Boolean {
        return procedure == procname
    }

    private fun ifVisited(): String {
        if (isVisited)
            return "*"
        else
            return ""
    }

    override fun copy(): Behaviour {
        val prin = ProcedureInvocationSP(procedure)
        prin.isVisited = this.isVisited
        return prin
    }
}
