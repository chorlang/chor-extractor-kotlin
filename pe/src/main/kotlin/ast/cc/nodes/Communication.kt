package ast.cc.nodes

import ast.cc.interfaces.Interaction

data class Communication(override val sender: String, override val receiver: String, val expression: String): Interaction {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(sender).append(".").append(expression).append("->").append(receiver).append("; ")
        return sb.toString()
    }
}
