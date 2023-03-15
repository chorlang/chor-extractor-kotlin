package ast.cc.nodes

import ast.cc.interfaces.Interaction

/**
 * Created by fmontesi on 03/04/17.
 */
data class Selection(override val sender: String, override val receiver: String, val label: String) : Interaction {
    override fun toString(): String = "$sender->$receiver[$label]"
}
