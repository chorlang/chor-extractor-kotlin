package util.networkStatistics

import ast.sp.nodes.Network

data class NetworkStatistics(
        val minLengthOfProcesses: Int,
        val maxLengthOfProcesses: Int,
        val avgLengthOfProcesses: Double,
        val minNumberOfProceduresInProcesses: Int,
        val maxNumberOfProceduresInProcesses: Int,
        val avgNumberOfProceduresInProcesses: Double,
        val minNumberOfConditionalsInProcesses: Int,
        val maxNumberOfConditionalsInProcesses: Int,
        val avgNumberOfConditionalsInProcesses: Double,
        val numberOfProcessesWithConditionals: Int,
        val minProcedureLengthInProcesses: Int,
        val maxProcedureLengthInProcesses: Int,
        val avgProcedureLengthInProcesses: Double
) {
    companion object {
        fun compute(network: Network): NetworkStatistics {
            val lengthOfProcesses = ArrayList<Int>()
            val lengthOfProcedures = ArrayList<Int>()
            val numberOfConditionals = ArrayList<Int>()
            val numberOfProcedures = ArrayList<Int>()

            network.processes.forEach { _, processTerm ->
                lengthOfProcesses.add(NetworkProcessActions().visit(processTerm))
                numberOfProcedures.add(processTerm.procedures.size)
                numberOfConditionals.add(NetworkProcessConditionals().visit(processTerm))
                lengthOfProcedures.addAll(NetworkProcessActionsPerProcedure().getLength(processTerm))
            }

            return NetworkStatistics(
                    minLengthOfProcesses = lengthOfProcesses.minOrNull()?:0,
                    maxLengthOfProcesses = lengthOfProcesses.maxOrNull()?:0,
                    avgLengthOfProcesses = lengthOfProcesses.average(),
                    minNumberOfProceduresInProcesses = numberOfProcedures.minOrNull()?:0,
                    maxNumberOfProceduresInProcesses = numberOfProcedures.maxOrNull()?:0,
                    avgNumberOfProceduresInProcesses = if (numberOfProcedures.isNotEmpty()) numberOfProcedures.average() else 0.0,
                    minNumberOfConditionalsInProcesses = numberOfConditionals.minOrNull()?:0,
                    maxNumberOfConditionalsInProcesses = numberOfConditionals.maxOrNull()?:0,
                    avgNumberOfConditionalsInProcesses = if (numberOfConditionals.isNotEmpty()) numberOfConditionals.average() else 0.0,
                    numberOfProcessesWithConditionals = numberOfConditionals.filter { it != 0 }.size,
                    minProcedureLengthInProcesses = lengthOfProcedures.minOrNull()?:0,
                    maxProcedureLengthInProcesses = lengthOfProcedures.maxOrNull()?:0,
                    avgProcedureLengthInProcesses = if (lengthOfProcedures.isNotEmpty()) lengthOfProcedures.average() else 0.0
            )
        }
    }
}

