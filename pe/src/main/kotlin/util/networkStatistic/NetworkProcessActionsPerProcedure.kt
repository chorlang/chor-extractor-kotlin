package util.networkStatistic

import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkProcessActionsPerProcedure: SPVisitor<Int> {
    override fun visit(n: ConditionSP): Int {
        return n.elseBehaviour.accept(this) + n.thenBehaviour.accept(this)
    }

    override fun visit(n: Network): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): Int {
        return n.branches.values.sumBy { behaviour -> behaviour.accept(this) }
    }

    override fun visit(n: ParallelNetworks): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): Int {
        return 0
    }

    override fun visit(n: ProcessTerm): Int {
        return n.procedures.values.sumBy { pr -> pr.accept(this) }
    }

    override fun visit(n: ReceiveSP): Int {
        return n.continuation.accept(this) +1
    }

    override fun visit(n: SelectionSP): Int {
        return n.continuation.accept(this) + 1
    }

    override fun visit(n: SendingSP): Int {
        return n.continuation.accept(this) + 1
    }

    override fun visit(n: TerminationSP): Int {
        return 0
    }

    override fun visit(n: Behaviour): Int {
        throw OperationNotSupportedException()
    }

    fun getLength(n: ProcessTerm): ArrayList<Int> {
        val actionsProcedures = ArrayList<Int>()
        n.procedures.forEach { name, term -> actionsProcedures.add(term.accept(this)) }
        return actionsProcedures

    }
}