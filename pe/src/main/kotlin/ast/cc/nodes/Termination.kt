package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.ChoreographyBody

class Termination : ChoreographyBody {
    private val termination = "stop"

    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = termination
}

