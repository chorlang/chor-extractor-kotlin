package util.networkStatistics

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import java.lang.UnsupportedOperationException
import javax.naming.OperationNotSupportedException

class WellFormedness(val self:ProcessName, val processNames:Set<ProcessName>): SPVisitor<Boolean> {
    override fun visit(n: Network): Boolean = throw OperationNotSupportedException()

    override fun visit(n: ParallelNetworks): Boolean = throw OperationNotSupportedException()

    override fun visit(n: ProcedureInvocationSP): Boolean = true

    override fun visit(n: ProcessTerm): Boolean = throw UnsupportedOperationException()

    override fun visit(n: SendSP): Boolean = n.receiver != self && n.continuation.accept(this)

    override fun visit(n: ReceiveSP): Boolean = n.sender != self && n.continuation.accept(this)

    override fun visit(n: SelectionSP): Boolean = n.receiver != self && n.continuation.accept(this)

    override fun visit(n: OfferingSP): Boolean = n.sender != self && n.branches.all { (label, behaviour) -> behaviour.accept(this) }

    override fun visit(n: ConditionSP): Boolean = n.elseBehaviour.accept(this) && n.thenBehaviour.accept(this)

    override fun visit(n: TerminationSP): Boolean = true

    companion object {
        fun compute(n:Network):Boolean {
            val processNames = n.processes.keys
            return n.processes.all { (name, term) ->
                checkWellFormedness(term.main, name, processNames) && term.procedures.all { (procName, procBehaviour) ->
                    isGuarded( hashSetOf(procName), procBehaviour, term.procedures ) && checkWellFormedness(procBehaviour, name, processNames)
                }
            }
        }

        private fun checkWellFormedness(behaviour: Behaviour, name:ProcessName, processNames: Set<ProcessName>) = behaviour.accept( WellFormedness(name, processNames) )

        private fun isGuarded(procedureNames: Set<String>, b:Behaviour, procedures:Map<String, Behaviour>):Boolean {
            return when( b ) {
                is ProcedureInvocationSP -> {
                    val newProcedureNames = HashSet( procedureNames )
                    newProcedureNames.add( b.procedure )
                    !procedureNames.contains( b.procedure ) && isGuarded( newProcedureNames, procedures[b.procedure]!!, procedures )
                }
                else -> true
            }
        }
    }
}