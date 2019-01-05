package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction

data class Multicom(val actions: ArrayList<Interaction>, val continuation: CCNode) : ChoreographyBody {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString() = "(${actions.joinToString(separator = ", ")}); $continuation"
}
