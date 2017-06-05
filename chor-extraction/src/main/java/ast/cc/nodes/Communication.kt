package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Interaction
import epp.MergingException

data class Communication(val sender: String, val receiver: String, val expression: String, val continuation: CCNode) : Interaction {

    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        return sender + "." + expression + "->" + receiver + "; " + continuation.toString()
    }
}
