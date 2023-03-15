package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody

data class Choreography(val main: ChoreographyBody, val procedures: List<ProcedureDefinition>, val processes: Set<String> = emptySet()) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val procedures = procedures.sortedWith(compareBy({ it.name })).joinToString(separator = " ")
        //we need this because procedures might be empty and we will get space in front of main
        return if (procedures.isNotBlank()) "$procedures main {$main}" else "main {$main}"
    }

    private fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> = Comparator { a, b -> compareValuesBy(a, b, *selectors) }
}