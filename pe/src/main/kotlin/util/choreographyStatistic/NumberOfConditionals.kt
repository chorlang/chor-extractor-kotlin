package util.choreographyStatistic

import ast.cc.interfaces.CCVisitor
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class NumberOfConditionals: CCVisitor<Int> {
    override fun visit(n: Condition): Int {
        return n.thenChoreography.accept(this) + n.elseChoreography.accept(this) + 1
    }

    override fun visit(n: Termination): Int {
        return 0
    }

    override fun visit(n: ProcedureDefinition): Int {
        return n.choreography.accept(this)
    }

    override fun visit(n: ProcedureInvocation): Int {
        return 0
    }

    override fun visit(n: Choreography): Int {
        return n.procedures.foldRight(0) { procedure, next -> procedure.accept(this) + next } + n.main.accept(this)
    }

    override fun visit(n: Program): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Multicom): Int {
        return n.continuation.accept(this)
    }

    override fun visit(n: CommunicationSelection): Int {
        return n.continuation.accept(this)
    }
}