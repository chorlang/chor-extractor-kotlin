package util.choreographyStatistic

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class UsedProcesses : CCVisitor<Set<String>> {

    override fun visit(n:Termination):Set<String> {
        return HashSet()
    }

    override fun visit(n:ProcedureInvocation):Set<String> {
        return HashSet()
    }

    override fun visit(n:Condition):Set<String> {
        return n.thenChoreography.accept(this) + n.elseChoreography.accept(this) + n.process
    }

    override fun visit(n:CommunicationSelection):Set<String> {
        return n.continuation.accept(this) + n.eta.sender + n.eta.receiver
    }

    override fun visit(n:Multicom):Set<String> {
        var ret = n.continuation.accept(this)
        n.actions.forEach { ret = ret + it.sender + it.receiver }
        return ret
    }

    override fun visit(n:ProcedureDefinition):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n:Choreography):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n:Program):Set<String> {
        throw OperationNotSupportedException()
    }

    companion object {
        private fun freeProcessNames(n: CCNode):Set<String> {
            return n.accept(UsedProcesses())
        }

        fun usedProcesses(choreography:Choreography):Map<String,Set<String>> {
            val calls = HashMap<String,Set<String>>()
            choreography.procedures.forEach { calls[it.name] = UsedProcedures.usedProcedures(it.body) }
            val oldUsedProcesses = HashMap<String,Set<String>>()
            val newUsedProcesses = HashMap<String,Set<String>>()
            choreography.procedures.forEach {
                newUsedProcesses[it.name] = freeProcessNames(it.body)
            }

            while(oldUsedProcesses != newUsedProcesses) {
                choreography.procedures.forEach { oldUsedProcesses[it.name] = newUsedProcesses[it.name]!! }
                choreography.procedures.forEach { procedure ->
                    calls[procedure.name]!!.forEach { call ->
                        newUsedProcesses[procedure.name] = newUsedProcesses[procedure.name]!! + oldUsedProcesses[call]!!
                    }
                }
            }

            return oldUsedProcesses
        }
    }
}