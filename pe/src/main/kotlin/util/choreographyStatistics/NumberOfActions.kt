package util.choreographyStatistics

import ast.cc.interfaces.CCVisitor
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class NumberOfActions private constructor(): CCVisitor<Int> {
    override fun visit(n: CommunicationSelection): Int = n.continuation.accept(this) + 1

    override fun visit(n: Multicom): Int = n.continuation.accept(this) + 1

    override fun visit(n: Condition): Int = n.thenChoreography.accept(this) + n.elseChoreography.accept(this) + 1

    override fun visit(n: Termination): Int = 1

    override fun visit(n: ProcedureDefinition): Int = throw OperationNotSupportedException()

    override fun visit(n: ProcedureInvocation): Int = 1

    override fun visit(n: Choreography): Int = throw OperationNotSupportedException()

    override fun visit(n: Program): Int = throw OperationNotSupportedException()

    companion object {
        fun compute(choreography:Choreography):Int =
            choreography.procedures.map { it.body.accept(NumberOfActions()) }.fold(0, Int::plus)
    }
}