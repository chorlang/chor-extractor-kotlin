package util.choreographyStatistic

import ast.cc.nodes.*

class ChoreographyStatistics {
    fun getChoreographyStatistic(choreography: Choreography): ChoreographyStatisticsData {
        val usedProcesses = UsedProcesses.usedProcesses(choreography)
        val processes = HashSet<String>()
        usedProcesses.values.to(processes)

        val numberOfProcedures = usedProcesses.size
        val numberOfProcesses = processes.size
        val numberOfActions = NumberOfActions().visit(choreography)
        val numberOfConditionals = NumberOfConditionals().visit(choreography)

        return ChoreographyStatisticsData(numberOfActions, numberOfProcesses, numberOfProcedures, numberOfConditionals)
    }

}