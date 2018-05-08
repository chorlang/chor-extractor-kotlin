package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import java.util.*

data class ProcedureDefinition(val procedure: String, val choreography: CCNode, val processes: HashSet<String>) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = "def " + procedure + " { " + choreography.toString() + " } "
}
