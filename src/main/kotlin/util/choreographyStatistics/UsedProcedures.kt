package util.choreographyStatistics

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class UsedProcedures : CCVisitor<Set<String>> {

    override fun visit(n: Termination):Set<String> {
        return HashSet()
    }

    override fun visit(n: ProcedureInvocation):Set<String> {
        return HashSet<String>() + n.procedure
    }

    override fun visit(n: Condition):Set<String> {
        return n.thenChoreography.accept(this) + n.elseChoreography.accept(this)
    }

    override fun visit(n: CommunicationSelection):Set<String> {
        return n.continuation.accept(this)
    }

    override fun visit(n: Multicom):Set<String> {
        return n.continuation.accept(this)
    }

    override fun visit(n: ProcedureDefinition):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Choreography):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Program):Set<String> {
        throw OperationNotSupportedException()
    }

    companion object {
        fun usedProcedures(n: CCNode):Set<String> {
            return n.accept(UsedProcedures())
        }
    }
}
