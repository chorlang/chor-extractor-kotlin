package util

import ce.ChoreographyExtraction
import np.NetworkProjection
import org.junit.jupiter.api.Test
import java.io.File
import java.text.ParseException

class StatisticsGenerator {
    /**
     * for each file with choreographies
     * 1. generate networks and write them to the file %original_file_name% networks
     * 2. get statistic and write to the file %original_file_name% statistics
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
     * @input: choreography data: HashMap<choreography_id, choreography_body>, filename
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
     * @input: chor data: (choreography id, choreography), filename
     */
    private fun generateNetworks(chorData: HashMap<String, String>, filename: String) {
        File("$filename.txt").printWriter().use { out ->
            out.println("choreographyId,Network")
            chorData.forEach { choreographyId, choreography ->
                val network = NetworkProjection.project(choreography)
                out.println("$choreographyId,$network")
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