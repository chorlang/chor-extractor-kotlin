package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.ChoreographyBody
import java.util.*

data class ProcedureInvocation(val procedure: String) : ChoreographyBody() {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = procedure
}
