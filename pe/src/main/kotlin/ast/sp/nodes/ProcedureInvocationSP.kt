package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour

data class ProcedureInvocationSP(val procedure: String) : Behaviour {

    var visited: Boolean
    init { visited = false }

    override fun toString(): String { return procedure + ifVisited() }

    override fun findRecProcCall(procname: String): Boolean {
        return procedure == procname
    }

    private fun ifVisited(): String {
        if (visited)
            return "*"
        else
            return ""
    }

    override fun copy(): Behaviour {
        val prin = ProcedureInvocationSP(""+procedure)
        prin.visited = this.visited
        return prin
    }

    override fun equals(b: Behaviour): Boolean {
        return b is ProcedureInvocationSP && b.procedure == procedure && b.visited == visited
    }
}
