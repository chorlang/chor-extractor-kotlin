package util.choreographyStatistic

data class ChoreographyStatisticsData(
        val length: Int, //numberOfActions
        val numberOfProcesses: Int,
        val numberOfProcedures: Int,
        val numberOfConditionals: Int
)

/*
def T { p -> u[l3]; d -> o[l4]; K }
def K { o.m3 -> u; o.m4 -> v; o -> p[l5]; p.m5 -> u; K }
main { u.m1 -> v; o -> v[l1]; v -> u[l2]; u.m2 -> d; T }
*/
