package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.Choreography

class Termination : Choreography {
    private val termination = "stop"

    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = termination
}

