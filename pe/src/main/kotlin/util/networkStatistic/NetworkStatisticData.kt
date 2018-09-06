package util.networkStatistic

data class NetworkStatisticData(
        val minLengthOfProcesses: Int,
        val maxLengthOfProcesses: Int,
        val avgLengthOfProcesses: Int,
        val minNumberOfProceduresInProcesses: Int,
        val maxNumberOfProceduresInProcesses: Int,
        val avgNumberOfProceduresInProcesses: Int,
        val minNumberOfConditionalsInProcesses: Int,
        val maxNumberOfConditionalsInProcesses: Int,
        val avgNumberOfConditionalsInProcesses: Int,
        val numberOfProcessesWithConditionals: Int,
        val minProcedureLengthInProcesses: Int,
        val maxProcedureLengthInProcesses: Int,
        val avgProcedureLengthInProcesses: Int
)

data class NetworkProcessStatisticData(val lengthOfProcesses: Int, val numberOfProcedures: Int, val numOfConditions: Int, val lengthOfProcedures: Int)

