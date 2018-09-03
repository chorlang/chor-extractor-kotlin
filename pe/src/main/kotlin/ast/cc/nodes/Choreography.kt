package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Behaviour

data class Choreography(val main: Behaviour, val procedures: List<ProcedureDefinition>, val processes: Set<String> = emptySet<String>()) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val sb = StringBuilder()
        for (p in procedures.sortedWith(compareBy({ it.procedure })))
            sb.append(p.toString())

        sb.append("main {$main}")
        return sb.toString()
    }

    private fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
        return Comparator { a, b -> compareValuesBy(a, b, *selectors) }
    }
}