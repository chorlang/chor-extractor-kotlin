package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Behaviour
import ast.cc.interfaces.Interaction

class CommunicationSelection(val node: Interaction, val continuation: CCNode): Behaviour{
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(node.toString()).append(continuation.toString())
        return sb.toString()
    }
}