package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

data class ProcedureInvocationSP(val procedure: String) : IBehaviour {

    override fun toString() = procedure

    override fun copy(): IBehaviour = ProcedureInvocationSP(procedure)

    override fun equals(b: IBehaviour) = b is ProcedureInvocationSP && b.procedure == procedure
}
