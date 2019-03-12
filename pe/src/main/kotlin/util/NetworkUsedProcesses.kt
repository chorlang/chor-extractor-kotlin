package util

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkUsedProcesses : SPVisitor<HashSet<String>> {
    companion object {
        fun compute(n: ProcessTerm): HashSet<String> = NetworkUsedProcesses().visit(n)
    }
    override fun visit(n: ConditionSP): HashSet<String> {
        return n.elseBehaviour.accept(this).plus(n.thenBehaviour.accept(this)) as HashSet<String>
    }

    override fun visit(n: Network): HashSet<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): HashSet<String> {
        val processes = hashSetOf(n.sender)
        n.branches.values.forEach { behaviour -> processes.addAll(behaviour.accept(this)) }
        return processes
    }

    override fun visit(n: ParallelNetworks): HashSet<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): HashSet<String> {
        return hashSetOf()
    }

    override fun visit(n: ProcessTerm): HashSet<String> {
        val processes = hashSetOf<String>()
        processes.addAll(n.main.accept(this))
        n.procedures.forEach { p -> processes.addAll(p.value.accept(this)) }
        return processes
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