package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode

// TODO: We want to revisit choreographies: ArrayList<Choreography?> later.
class Program (val choreographies: List<Choreography?>, val statistics: List<GraphStatistics> = ArrayList()): CCNode {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String = choreographies.joinToString( separator = " || " )
}

data class GraphStatistics( val nodes: Int, val badLoops: Int )