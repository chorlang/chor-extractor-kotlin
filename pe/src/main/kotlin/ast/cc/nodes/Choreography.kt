package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody

data class Choreography(val main: ChoreographyBody, val procedures: List<ProcedureDefinition>, val processes: Set<String> = emptySet<String>()) : CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String = "${procedures.sortedWith( compareBy( { it.name } ) ).joinToString( separator = " " )} main {$main}"

    private fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
        return Comparator { a, b -> compareValuesBy(a, b, *selectors) }
    }
}