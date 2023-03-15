package ast.cc.nodes

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.ChoreographyBody

data class Condition(val process: String, val expression: String, val thenChoreography: ChoreographyBody, val elseChoreography: ChoreographyBody) : ChoreographyBody() {
    override fun <T> accept(visitor: CCVisitor<T>): T = visitor.visit(this)

    override fun toString(): String = "if $process.$expression then $thenChoreography else $elseChoreography"
}
