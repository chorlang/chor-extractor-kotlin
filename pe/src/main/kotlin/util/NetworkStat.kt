package util

data class NetworkStat(
        val numberOfProcesses: Int,
        val processStat: HashMap<String, ProcessStat>,
        val minAmountofProced: Int,
        val maxAmountofProced: Int,
        val avgAmountofProced: Int,
        val minAmountofCond: Int,
        val maxAmountofCond: Int,
        val avgAmountofCond: Int)

data class ProcessStat(val numOfProcedures: Int, val numOfConditions: Int)