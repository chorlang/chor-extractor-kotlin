package ast.cc.nodes


import ast.cc.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import epp.MergingException

data class Program(val main: Choreography, val procedures: List<ProcedureDefinition>) : CCNode {

    @Throws(MergingException::class)
    override fun <T> accept(visitor: CCVisitor<T>): T {
        return visitor.visit(this)
    }
}
