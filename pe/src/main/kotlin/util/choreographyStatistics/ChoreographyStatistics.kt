package util.choreographyStatistics

import ast.cc.nodes.Choreography
import ast.cc.nodes.Program
import javax.naming.OperationNotSupportedException

data class ChoreographyStatistics(
        val numberOfActions: Int,
        val numberOfProcesses: Int,
        val numberOfProcedures: Int,
        val numberOfConditionals: Int
) {
    companion object {
        fun compute(program: Program): ChoreographyStatistics {
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