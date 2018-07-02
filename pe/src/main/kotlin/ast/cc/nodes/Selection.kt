package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Interaction

/**
 * Created by fmontesi on 03/04/17.
 */
data class Selection(override val sender: String, override val receiver: String, val label: String) : Interaction {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(sender).append("->").append(receiver).append("[").append(label).append("]; ")
        return sb.toString()
    }

}
