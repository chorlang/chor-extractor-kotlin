package ast.cc.nodes

import ast.cc.interfaces.Interaction

data class Communication(override val sender: String, override val receiver: String, val expression: String): Interaction {
    override fun toString(): String = "$sender.$expression->$receiver"
}
