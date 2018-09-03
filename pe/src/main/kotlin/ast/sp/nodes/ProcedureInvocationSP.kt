package ast.sp.nodes

import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPVisitor

data class ProcedureInvocationSP(val procedure: String) : IBehaviour {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

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
