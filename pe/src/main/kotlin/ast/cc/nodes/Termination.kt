package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.Choreography

class Termination : Choreography {
    val termination = "0"

    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        return termination
    }
}
