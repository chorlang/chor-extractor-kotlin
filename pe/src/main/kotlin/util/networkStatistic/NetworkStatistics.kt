package util.networkStatistic

import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkStatistics: SPVisitor<NetworkStatisticData> {
    override fun visit(n: Network): NetworkStatisticData {
        throw OperationNotSupportedException()

    }

    override fun visit(n: ConditionSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ParallelNetworks): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcessTerm): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ReceiveSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SelectionSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SendingSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: TerminationSP): NetworkStatisticData {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Behaviour): NetworkStatisticData {
        throw OperationNotSupportedException()
    }
}