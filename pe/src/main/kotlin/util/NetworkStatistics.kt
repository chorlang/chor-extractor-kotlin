package util

import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkStatistics: SPVisitor<NetworkStat> {
    override fun visit(n: Network): NetworkStat {
        val numberOfProcess = n.processes.size
        val processStatMap = HashMap<String, ProcessStat>()

        val processStatisticVisitor = NetworkProcessStatistics()

        n.processes.forEach { name, term -> processStatMap.put(name, processStatisticVisitor.visit(term))  }

        val minNumOfProcedures = processStatMap.values.minBy { v -> v.numOfProcedures }?.numOfProcedures ?:0
        val maxNumOfProcedures = processStatMap.values.maxBy { v -> v.numOfProcedures }?.numOfProcedures ?:0
        val avgNumOfProcedures = (minNumOfProcedures + maxNumOfProcedures) / 2

        val minNumOfConditions = processStatMap.values.minBy { v -> v.numOfConditions }?.numOfConditions ?:0
        val maxNumOfConditions = processStatMap.values.maxBy { v -> v.numOfConditions }?.numOfConditions ?:0
        val avgNumOfConditions = (minNumOfConditions + maxNumOfConditions) / 2

        return NetworkStat(
                numberOfProcess,
                processStatMap,
                minNumOfProcedures,
                maxNumOfProcedures,
                avgNumOfProcedures,
                minNumOfConditions,
                maxNumOfConditions,
                avgNumOfConditions)

    }

    override fun visit(n: ConditionSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: OfferingSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ParallelNetworks): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcessTerm): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ReceiveSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SelectionSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: SendingSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: TerminationSP): NetworkStat {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Behaviour): NetworkStat {
        throw OperationNotSupportedException()
    }
}