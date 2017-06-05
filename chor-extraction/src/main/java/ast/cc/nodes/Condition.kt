package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import epp.MergingException

data class Condition(val process: String, val expression: String, val thenChoreography: CCNode, val elseChoreograpy: CCNode) : Choreography {


    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        return "if " + process + "." + expression + " then " + thenChoreography.toString() + " else " + elseChoreograpy.toString()
    }
}
