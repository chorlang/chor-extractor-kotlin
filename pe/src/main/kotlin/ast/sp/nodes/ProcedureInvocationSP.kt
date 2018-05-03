package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

data class ProcedureInvocationSP(val procedure: String) : IBehaviour {

    var visited: Boolean
    init { visited = false }

    override fun toString(): String { return procedure + ifVisited() }

    private fun ifVisited(): String {
        if (visited)
            return "*"
        else
            return ""
    }

    override fun copy(): IBehaviour {
        val prin = ProcedureInvocationSP(""+procedure)
        prin.visited = this.visited
        return prin
    }

    override fun equals(b: IBehaviour): Boolean {
        return b is ProcedureInvocationSP && b.procedure == procedure && b.visited == visited
    }
}
