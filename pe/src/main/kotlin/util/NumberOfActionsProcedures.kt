package util

import ast.cc.CCVisitor
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class NumberOfActionsProcedures: CCVisitor<Int> {
    private var amount = 0

    override fun visit(n: Condition): Int {
        return amount + n.elseChoreography.accept(this) + n.thenChoreography.accept(this)
    }

    override fun visit(n: Termination): Int {
        return amount + 1
    }

    override fun visit(n: ProcedureInvocation): Int {
        return amount + 1
    }

    override fun visit(n: Multicom): Int {
        return amount + 1 + n.continuation.accept(this)
    }

    override fun visit(n: CommunicationSelection): Int {
        return amount + 1 + n.continuation.accept(this)
    }

    override fun visit(n: ProcedureDefinition): Int {
        return n.choreography.accept(this)
        //throw OperationNotSupportedException()
    }

    override fun visit(n: Choreography): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Program): Int {
        throw OperationNotSupportedException()
    }
}