package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.Choreography
import epp.MergingException
import java.util.*

data class ProcedureInvocation(val procedure: String, val processes: HashSet<String>) : Choreography {

    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

}
