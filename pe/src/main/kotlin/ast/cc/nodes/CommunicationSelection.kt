package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction

class CommunicationSelection(val eta: Interaction, val continuation: ChoreographyBody): ChoreographyBody() {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String = "$eta; $continuation"
}