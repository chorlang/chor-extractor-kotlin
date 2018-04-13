package ast.cc.nodes

import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import ast.cc.interfaces.Interaction

data class Multicom(val actions: ArrayList<CCNode>) : Choreography {
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("(")
        for (a in actions){
            sb.append(a.toString()).append(", ")
        }


        return sb.toString()
    }
}
