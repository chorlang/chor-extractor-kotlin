package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.Choreography
import java.util.*

data class ProcedureInvocation(val procedure: String, val processes: HashSet<String>) : Choreography {
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String{
        return procedure
    }

}
