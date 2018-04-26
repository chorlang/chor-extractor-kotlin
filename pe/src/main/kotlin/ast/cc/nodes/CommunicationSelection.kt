package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import ast.cc.interfaces.Interaction

class CommunicationSelection(val node: Interaction, val continuation: CCNode): Choreography{
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(node.toString()).append(continuation.toString())
        return sb.toString()
    }
}