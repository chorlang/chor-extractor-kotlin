package util.choreographyStatistic

import ast.cc.interfaces.CCVisitor
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class LengthOfProcedures: CCVisitor<Int> {
    override fun visit(n: Condition): Int {
        return n.thenChoreography.accept(this) + n.elseChoreography.accept(this)
    }

    override fun visit(n: Termination): Int {
        return 1
    }

    override fun visit(n: ProcedureDefinition): Int {
        return n.body.accept(this)
    }

    override fun visit(n: ProcedureInvocation): Int {
        return 1
    }

    override fun visit(n: Choreography): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Program): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Multicom): Int {
        return n.continuation.accept(this) + 1
    }

    override fun visit(n: CommunicationSelection): Int {
        return n.continuation.accept(this) + 1
    }

    fun getLength(n: Choreography): ArrayList<Int>{
        val stat = ArrayList<Int>()
        n.procedures.forEach { procedure -> stat.add(procedure.accept(this))}
        return stat
    }
}