package util.choreographyStatistic

data class ChoreographyStatisticsData(
        val length: Int, //numberOfActions
        val numberOfProcesses: Int,
        val numberOfProcedures: Int,
        val numberOfConditionals: Int
)