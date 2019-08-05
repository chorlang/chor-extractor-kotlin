package util.fuzzing

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class ProcessSize: SPVisitor<Int> {
    override fun visit(n: ConditionSP): Int = n.thenBehaviour.accept(this) + n.elseBehaviour.accept(this) + 1

    override fun visit(n: Network): Int = throw OperationNotSupportedException()

    override fun visit(n: OfferingSP): Int = n.branches.values.sumBy { it.accept(this) } + 1

    override fun visit(n: ParallelNetworks): Int = throw OperationNotSupportedException()

    override fun visit(n: ProcedureInvocationSP): Int = 1

    override fun visit(n: ProcessTerm): Int = throw OperationNotSupportedException()

    override fun visit(n: ReceiveSP): Int = n.continuation.accept(this) + 1

    override fun visit(n: SelectionSP): Int = n.continuation.accept(this) + 1

    override fun visit(n: SendSP): Int = n.continuation.accept(this) + 1

    override fun visit(n: TerminationSP): Int = 0

    fun visit(b: Behaviour): Int = b.accept(this)

    companion object {
        fun compute(b:Behaviour): Int = ProcessSize().visit(b)
    }
}