package util

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class ChoreographyStatistics: CCVisitor<Int> {
    private var amount = 0
    private val actionsProceduresVisitor = NumberOfActionsProcedures()
    private lateinit var chorStat: ChoreographyStat

    override fun visit(n: Condition): Int {
        return amount + n.elseChoreography.accept(this) + n.thenChoreography.accept(this)
    }

    override fun visit(n: Termination): Int {
        return amount + 1
    }

    override fun visit(n: ProcedureDefinition): Int {
        return actionsProceduresVisitor.visit(n)
    }

    override fun visit(n: ProcedureInvocation): Int {
        return amount + 1
    }

    override fun visit(n: Choreography): Int {
        val actionsMain = n.main.accept(this)
        val actionsProcedures = ArrayList<Int>()

        n.procedures.forEach { procedure -> actionsProcedures.add(procedure.accept(this))}
        chorStat = ChoreographyStat(actionsMain + actionsProcedures.sum(), actionsProcedures.size, actionsProcedures.min()?:0, actionsProcedures.max()?:0)

        return 0

    }

    override fun visit(n: Program): Int {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Multicom): Int {
        return amount + 1 + n.continuation.accept(this)
    }

    override fun visit(n: CommunicationSelection): Int {
        return amount + 1 + n.continuation.accept(this)
    }

    fun getStat(n: CCNode): ChoreographyStat {
        n.accept(this)
        return chorStat
    }
}