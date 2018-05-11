package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

data class ProcedureInvocationSP(val procedure: String) : IBehaviour {

    override fun toString() = procedure

    override fun copy(): IBehaviour = ProcedureInvocationSP(procedure)

    override fun equals(other: Any?): Boolean {
        if( this === other ) return true
        return other is ProcedureInvocationSP && procedure == other.procedure
    }

    override fun hashCode(): Int {
        return procedure.hashCode()
    }
}
