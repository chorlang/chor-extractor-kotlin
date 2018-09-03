package util

import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkProcessStatistics: SPVisitor<ProcessStat> {
    override fun visit(n: ProcessTerm): ProcessStat {
        val numOfConditions = NetworkProcessConditions()
        return ProcessStat(n.procedures.size, numOfConditions.visit(n))
    }

    override fun visit(n: ConditionSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Network): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ParallelNetworks): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ReceiveSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SelectionSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SendingSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: TerminationSP): ProcessStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Behaviour): ProcessStat {
        throw OperationNotSupportedException()
    }
}