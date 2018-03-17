package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Interaction

/**
 * Created by fmontesi on 03/04/17.
 */
data class Selection(val sender: String, val receiver: String, val label: String, val continuation: CCNode) : Interaction {
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        return sender + "->" + receiver + "[" + label + "]; " + continuation.toString()
    }

}
