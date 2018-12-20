package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction

data class Multicom(val actions: ArrayList<Interaction>, val continuation: CCNode) : ChoreographyBody {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val sb = StringBuilder()

        if (actions.isNotEmpty()){
            sb.append("(")
            for (a in actions){
                sb.append(a.toString()).delete(sb.length - 2, sb.length).append(", ")
            }
            sb.delete(sb.length - 2, sb.length)
            sb.append("); ")
        }

        sb.append(continuation.toString())

        return sb.toString()
    }
}
