package util

import ast.cc.nodes.Choreography
import ce.ChoreographyExtraction
import np.NetworkProjection
import org.junit.jupiter.api.Test
import util.choreographyStatistic.ChoreographyStatisticsData
import util.choreographyStatistic.NumberOfActions
import util.choreographyStatistic.NumberOfConditionals
import util.choreographyStatistic.UsedProcesses
import java.io.File
import java.text.ParseException

class StatisticsGenerator {
    /**
     * 1. take generated choreographies and collect statistics on them
     * 2. generate netoworks and collect statistics on them
     * 3. combine statistics and return as output
     */
    @Test
    fun run(){
        val filesWithNetworks = parseFolderWithFilesWithChoreographies("tests") //HashMap<filename, HashMap<choreography_id, choreography_body>>
        filesWithNetworks.forEach { filename, chorData ->
            generateNetworks(chorData, "$filename networks")
            getStatistics(chorData, "$filename statistics")
        }

    }

    /**
     * @input: choreography data: HashMap<choreography_id, choreography_body>
     * @output: HashMap<choreography_id, ChoreographyStatisticsData>
     *
     * foreach chor get stat in the format "id, length, numberOfProcesses, numberOfProcedures, numberOfConditionals"
     * return set of stat
     */
    private fun getStatistics(chorData: HashMap<String, String>, filename: String){
        File("$filename.txt").printWriter().use { out ->
            out.println("id, length, numberOfProcesses, numberOfProcedures, numberOfConditionals, " +
                    "minLengthOfProcesses, maxLengthOfProcesses, avgLengthOfProcesses, " +
                    "minNumberOfProceduresInProcesses, maxNumberOfProceduresInProcesses, avgNumberOfProceduresInProcesses, " +
                    "minNumberOfConditionalsInProcesses, maxNumberOfConditionalsInProcesses, avgNumberOfConditionalsInProcesses, numberOfProcessesWithConditionals, " +
                    "minProcedureLengthInProcesses, maxProcedureLengthInProcesses, avgProcedureLengthInProcesses")
            chorData.forEach { choreographyId, choreography ->
                val network = NetworkProjection.project(choreography).toString()
                val networkStatistic = ChoreographyExtraction.getStat(network)
                val choreographyStatistic = NetworkProjection.getStatistic(choreography)
                out.println(    "$choreographyId," +
                                "${choreographyStatistic.length}," +
                                "${choreographyStatistic.numberOfProcesses}," +
                                "${choreographyStatistic.numberOfProcedures}," +
                                "${choreographyStatistic.numberOfConditionals}," +
                                "${networkStatistic.minLengthOfProcesses}," +
                                "${networkStatistic.maxLengthOfProcesses}," +
                                "${networkStatistic.avgLengthOfProcesses}," +
                                "${networkStatistic.maxNumberOfProceduresInProcesses}," +
                                "${networkStatistic.maxNumberOfProceduresInProcesses}," +
                                "${networkStatistic.avgNumberOfProceduresInProcesses}," +
                                "${networkStatistic.minNumberOfConditionalsInProcesses}," +
                                "${networkStatistic.maxNumberOfConditionalsInProcesses}," +
                                "${networkStatistic.avgNumberOfConditionalsInProcesses}," +
                                "${networkStatistic.numberOfProcessesWithConditionals}," +
                                "${networkStatistic.minProcedureLengthInProcesses}," +
                                "${networkStatistic.maxProcedureLengthInProcesses}," +
                                "${networkStatistic.avgProcedureLengthInProcesses}"
                )
            }
        }

    }

    /**
     * @input: list of pairs (choreography id, choreography)
     * @output: list of pairs (choreography id, network)
     * foreach choreography generate network and save result as (choreography id, network)
     */
    private fun generateNetworks(chorData: java.util.HashMap<String, String>, filename: String) {
        File("$filename.txt").printWriter().use { out ->
            out.println("choreographyId,Network")
            chorData.forEach { choreographyId, choreography ->
                out.println("$choreographyId,${NetworkProjection.project(choreography)}")
            }
        }
    }

    /**
     * @input: dir_path
     * @output: HashMap<filename, HashMap<choreography_id, choreography_body>>
     */
    private fun parseFolderWithFilesWithChoreographies(dirPath: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory())

        val choreographyToNetworksMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            val file = File("$dirPath/$fileName")
            choreographyToNetworksMap.put(fileName, parseChoreographiesFile(file))
        }

        return choreographyToNetworksMap
    }

    /**
     * @output: HashMap<choreography_id, choreography_body>>
     */
    private fun parseChoreographiesFile(file: File): HashMap<String, String> {
        var choreography = ""
        var name = ""
        val choreographyMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("***") -> name = line.substring(4, 7).trim()
                line.startsWith("def") -> choreography = line
                line.startsWith("main") -> choreography = "$choreography $line"
                line.isEmpty() -> {
                    choreographyMap.put(name, choreography)
                    choreography = ""
                    name = ""
                }
                else -> throw ParseException("line $line was unexpected", 0)
            }
        }

        return  choreographyMap
    }
}