package ast.cc.nodes


import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography

data class Program(val main: Choreography, val procedures: List<ProcedureDefinition>) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (p in procedures){
            sb.append(p.toString())
        }

        sb.append("main { " + main.toString() + " }")

        return sb.toString()
    }
}
