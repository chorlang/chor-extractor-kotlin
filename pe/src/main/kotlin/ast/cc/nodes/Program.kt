package ast.cc.nodes


import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography

data class Program(val main: Choreography, val procedures: List<ProcedureDefinition>) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val sb = StringBuilder()
        for (p in procedures.sortedWith(compareBy({ it.procedure })))
            sb.append(p.toString())

        sb.append("main {").append(main.toString()).append("}")

        return sb.toString()
    }

    fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
        return object : Comparator<T> {
            override fun compare(a: T, b: T): Int = compareValuesBy(a, b, *selectors)
        }
    }
}
