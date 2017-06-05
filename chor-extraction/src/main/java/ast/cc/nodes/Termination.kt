package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.Choreography
import epp.MergingException

class Termination : Choreography {
    val termination = "stop"

    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        return termination
    }
}
