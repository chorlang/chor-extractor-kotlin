package ast.cc

import ast.cc.nodes.*

/**
 * Created by fmontesi on 03/04/17.
 */
interface CCVisitor<T> {
    fun visit(n: Condition): T

    fun visit(n: Termination): T

    fun visit(n: ProcedureDefinition): T

    fun visit(n: ProcedureInvocation): T

    fun visit(n: Choreography): T

    fun visit(n: Program): T

    fun visit(n: Multicom): T

    fun visit(n: CommunicationSelection): T
}
