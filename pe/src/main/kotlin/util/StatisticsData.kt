package util

data class StatisticsData(
        val time: Long, //time to run the extraction
        val nodes: Int, //num of nodes in the graph
        val badLoops: Int, //num of badNodes loops in the graph
        val length: Int, //length of the main body
        val numOfProcedures: Int, //num of procedures in the body
        val minProcedureLength: Int,
        val maxProcedureLength: Int,
        val avgProcedureLength: Int
)