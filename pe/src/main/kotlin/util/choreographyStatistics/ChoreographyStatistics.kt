package util.choreographyStatistics

import ast.cc.nodes.Choreography
import util.ParseUtils
import javax.naming.OperationNotSupportedException

data class ChoreographyStatistics(
        val numberOfActions: Int,
        val numberOfProcesses: Int,
        val numberOfProcedures: Int,
        val numberOfConditionals: Int
) {
    companion object {
        fun compute(choreography: String): ChoreographyStatistics {
            val program = ParseUtils.stringToProgram(choreography)

            val choreographyList = program.choreographies
            if (choreographyList.size == 1) {
                return getChoreographyStatistics(choreographyList.first()!!)
            } else {
                throw OperationNotSupportedException()
            }
        }

        private fun getChoreographyStatistics(choreography: Choreography): ChoreographyStatistics =
                ChoreographyStatistics(
                        NumberOfActions.compute(choreography),
                        choreography.processes.size,
                        choreography.procedures.size,
                        NumberOfConditionals.compute(choreography)
                )
    }
}