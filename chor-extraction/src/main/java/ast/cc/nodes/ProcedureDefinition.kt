package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import epp.MergingException
import java.util.*

data class ProcedureDefinition(val procedure: String, val choreography: CCNode, val processes: HashSet<String>) : CCNode {

    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }
}
