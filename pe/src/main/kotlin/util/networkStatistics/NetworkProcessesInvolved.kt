package util.networkStatistics

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkProcessesInvolved : SPVisitor<HashSet<String>> {
    override fun visit(n: ConditionSP): HashSet<String> {
        return n.elseBehaviour.accept(this).plus(n.thenBehaviour.accept(this)) as HashSet<String>
    }

    override fun visit(n: Network): HashSet<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): HashSet<String> {
        val processesSet = hashSetOf<String>()
        n.branches.values.forEach { behaviour -> processesSet.addAll(behaviour.accept(this)) }
        return processesSet
    }

    override fun visit(n: ParallelNetworks): HashSet<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): HashSet<String> {
        return hashSetOf()
    }

    override fun visit(n: ProcessTerm): HashSet<String> {
        val processesSet = hashSetOf<String>()
        processesSet.addAll(n.main.accept(this))
        n.procedures.forEach { p -> processesSet.addAll(p.value.accept(this)) }
        return processesSet
    }

    override fun visit(n: ReceiveSP): HashSet<String> {
        val ret = hashSetOf(n.sender)
        ret.addAll(n.continuation.accept(this))
        return ret
    }

    override fun visit(n: SelectionSP): HashSet<String> {
        val ret = hashSetOf(n.receiver)
        ret.addAll(n.continuation.accept(this))
        return ret
    }

    override fun visit(n: SendSP): HashSet<String> {
        val ret = hashSetOf(n.receiver)
        ret.addAll(n.continuation.accept(this))
        return ret
    }

    override fun visit(n: TerminationSP): HashSet<String> {
        return hashSetOf()
    }

    fun visit(b: Behaviour): HashSet<String> {
        return b.accept(this)
    }
}