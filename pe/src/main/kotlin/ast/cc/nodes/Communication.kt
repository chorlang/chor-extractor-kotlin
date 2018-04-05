package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Interaction

data class Communication(val sender: String, val receiver: String, val expression: String, val continuation: CCNode) : Interaction {
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(sender).append(".").append(expression).append("->").append(receiver).append("; ").append(continuation.toString())
        return sb.toString()
    }
}
