package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode

class Program (val choreographyList: ArrayList<Choreography>): CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val sb = StringBuilder()
        choreographyList.forEach {sb.append("$it || ")}
        sb.delete(sb.length-4, sb.length)
        return sb.toString()
    }
}