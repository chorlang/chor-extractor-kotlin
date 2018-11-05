package ce

import ast.sp.nodes.ProcessTerm
import util.networkStatistic.*

object NetworkStatistics {
    fun getNetworkStatistic(input: String): NetworkStatisticData {
        val network = ChoreographyExtraction.generateNetwork(input)

        val lengthOfProcesses = ArrayList<Int>()
        val lengthOfProcedures = ArrayList<Int>()
        val numberOfConditionals = ArrayList<Int>()
        val numberOfProcedures = ArrayList<Int>()

        network.processes.forEach { _, processTerm ->
            val processStatistic = getNetworkProcessStatistic(processTerm)

            lengthOfProcesses.add(processStatistic.lengthOfProcesses)
            lengthOfProcedures.addAll(processStatistic.lengthOfProcedures)
            numberOfConditionals.add(processStatistic.numOfConditions)
            numberOfProcedures.add(processStatistic.numberOfProcedures)
        }

        return NetworkStatisticData(
                minLengthOfProcesses = lengthOfProcesses.min()?:0,
                maxLengthOfProcesses = lengthOfProcesses.max()?:0,
                avgLengthOfProcesses = lengthOfProcesses.average().toInt(),
                minNumberOfProceduresInProcesses = numberOfProcedures.min()?:0,
                maxNumberOfProceduresInProcesses = numberOfProcedures.max()?:0,
                avgNumberOfProceduresInProcesses = numberOfProcedures.average().toInt(),
                minNumberOfConditionalsInProcesses = numberOfConditionals.min()?:0,
                maxNumberOfConditionalsInProcesses = numberOfConditionals.max()?:0,
                avgNumberOfConditionalsInProcesses = numberOfConditionals.average().toInt(),
                numberOfProcessesWithConditionals = numberOfConditionals.filter { it != 0 }.size,
                minProcedureLengthInProcesses = lengthOfProcedures.min()?:0,
                maxProcedureLengthInProcesses = lengthOfProcedures.max()?:0,
                avgProcedureLengthInProcesses = lengthOfProcedures.average().toInt()
        )

    }

    private fun getNetworkProcessStatistic(n: ProcessTerm): NetworkProcessStatisticData {
        val networkProcessConditionals = NetworkProcessConditions().visit(n)
        val networkProcessActionProcedures = NetworkProcessActionsPerProcedure().getLength(n)
        val networkProcessActions = NetworkProcessActions().visit(n)
        val networkProcessProcedures = n.procedures.size

        return NetworkProcessStatisticData(networkProcessActions, networkProcessProcedures, networkProcessConditionals, networkProcessActionProcedures)
    }

}