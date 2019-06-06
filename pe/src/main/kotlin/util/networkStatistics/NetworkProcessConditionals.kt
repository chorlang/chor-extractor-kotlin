package util.networkStatistics

import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkProcessConditionals: SPVisitor<Int> {
    override fun visit(n: ConditionSP): Int {
        return 1 + n.elseBehaviour.accept(this) + n.thenBehaviour.accept(this)
    }

    override fun visit(n: Network): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): Int {
        return (n.branches.values.sumBy { behaviour -> behaviour.accept(this) }.toDouble() / n.branches.size).toInt()
    }

    override fun visit(n: ParallelNetworks): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): Int {
        return 0
    }

    override fun visit(n: ProcessTerm): Int {
        return n.main.accept(this)
    }

    override fun visit(n: ReceiveSP): Int {
        return n.continuation.accept(this)
    }

    override fun visit(n: SelectionSP): Int {
        return n.continuation.accept(this)
    }

    override fun visit(n: SendSP): Int {
        return n.continuation.accept(this)
    }

    override fun visit(n: TerminationSP): Int {
        return 0
    }
}