package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import java.util.HashSet
import ast.cc.interfaces.ChoreographyBody

data class ProcedureDefinition(val name: String, val body: ChoreographyBody, val usedProcesses: HashSet<String>) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = "def $name { $body }"
}
