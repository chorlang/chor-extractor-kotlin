import ast.cc.nodes.Program
import ce.ChoreographyExtraction
import np.NetworkProjection
import org.junit.jupiter.api.Test
import util.StatisticsData
import util.choreographyStatistic.LengthOfProcedures
import util.choreographyStatistic.NumberOfActions
import java.io.File
import java.sql.Time
import java.sql.Timestamp
import java.text.ParseException
import java.time.LocalTime
import java.time.Period
import java.time.temporal.TemporalUnit
import javax.naming.OperationNotSupportedException
import javax.xml.datatype.DatatypeConstants.SECONDS
import kotlin.system.measureTimeMillis

class StatisticsGenerator {
    /**
     * for each file with choreographies
     * 1. generate networks and write them to the file %original_file_name% networks
     * 2. get statistic and write to the file %original_file_name% statistics
     */
    @Test
    fun projectionStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithChoreographies("tests") //HashMap<filename, HashMap<choreography_id, choreography_body>>
        filesWithNetworks.forEach { filename, chorData ->
            generateNetworks(chorData, "$filename networks")
            getStatistics(chorData, "$filename statistics")
        }

    }

    @Test
    fun extractionStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, choreography_body>>

        val choreographies = HashMap<String, String>()

        filesWithNetworks.forEach { filename, networks ->
            File(OUTPUT_DIR, "${filename.dropLast(13)} choreography.txt").printWriter().use { out ->
                out.println("id, time(sec),nodes,badLoops,numOfProcedures,minProcedureLength,maxProcedureLength,avgProcedureLength")

                networks.forEach { choreographyId, network ->
                    //val idToChoreography = HashMap<String, String>()

                    val start = System.currentTimeMillis()
                    val program = ChoreographyExtraction.main(arrayListOf("-c", network, "-d"))
                    val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000

                    choreographies[choreographyId] = program.choreographyList.first().toString()

                    if (program.choreographyList.size == 1 && program.statistic.size == 1) {
                        val statistic = program.statistic.first()
                        val choreography = program.choreographyList.first()
                        val lengthOfProcedures = LengthOfProcedures().getLength(choreography)

                        out.println("$choreographyId," +
                                "$executionTime," +
                                "${statistic.nodes}," +
                                "${statistic.badLoops}," +
                                "${NumberOfActions().visit(choreography)}," +
                                "${choreography.procedures.size}," +
                                "${lengthOfProcedures.min() ?: 0}," +
                                "${lengthOfProcedures.max() ?: 0}," +
                                "${lengthOfProcedures.average().toInt()}"
                        )
                    } else throw OperationNotSupportedException()
                }
            }

            File(OUTPUT_DIR, "${filename.dropLast(13)} newchoreography.txt").printWriter().use { out ->
                out.println("ID,Choreography")
                choreographies.forEach { id, choreography -> out.println("$id,$choreography") }
            }
        }

    }

    val OUTPUT_DIR = "src/test/resources/statistics/"

    fun checkOutputFolder() {
        val dir = File(OUTPUT_DIR)
        if (!dir.exists() || !dir.isDirectory) {
            dir.mkdirs()
        }
    }

    private fun parseFolderWithFilesWithNetworks(dirPath: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val fileToNetworksMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            if (fileName.contains("networks")) {
                val file = File("$dirPath/$fileName")
                fileToNetworksMap[fileName] = parseNetworkFile(file)
            }
        }

        return fileToNetworksMap
    }

    private fun parseNetworkFile(file: File): HashMap<String, String> {
        val networks = HashMap<String, String>()

        file.forEachLine { line ->
            val separator = line.indexOf(",")
            if (separator != -1) {
                networks[line.substring(0, separator)] = line.substring(separator + 1)
            }
        }

        networks.remove("choreographyId") //remove csv title

        return networks
    }


    /**
     * @input: choreography data: HashMap<choreography_id, choreography_body>, filename
     */
    private fun getStatistics(chorData: HashMap<String, String>, filename: String) {
        File(OUTPUT_DIR, "$filename.txt").printWriter().use { out ->
            out.println("id, length, numberOfProcesses, numberOfProcedures, numberOfConditionals, " +
                    "minLengthOfProcesses, maxLengthOfProcesses, avgLengthOfProcesses, " +
                    "minNumberOfProceduresInProcesses, maxNumberOfProceduresInProcesses, avgNumberOfProceduresInProcesses, " +
                    "minNumberOfConditionalsInProcesses, maxNumberOfConditionalsInProcesses, avgNumberOfConditionalsInProcesses, numberOfProcessesWithConditionals, " +
                    "minProcedureLengthInProcesses, maxProcedureLengthInProcesses, avgProcedureLengthInProcesses")
            chorData.forEach { choreographyId, choreography ->
                val network = NetworkProjection.project(choreography).toString()
                val networkStatistic = ChoreographyExtraction.getStatistic(network)
                val choreographyStatistic = NetworkProjection.getStatistic(choreography)
                out.println("$choreographyId," +
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
     * @input: network data: (choreography id, choreography), filename
     */
    private fun generateNetworks(chorData: HashMap<String, String>, filename: String) {
        File(OUTPUT_DIR, "$filename.txt").printWriter().use { out ->
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
        val choreography = ArrayList<String>()
        var name = ""

        val choreographyMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("***") -> name = line.substring(4, 7).trim()
                line.startsWith("def") -> choreography.add(line)
                line.startsWith("main") -> choreography.add(line)
                line.isEmpty() -> {
                    choreographyMap.put(name, choreography.joinToString(separator = " "))
                    choreography.clear()
                    name = ""
                }
                else -> throw ParseException("line $line was unexpected", 0)
            }
        }

        if (choreography.isNotEmpty() && name != "") {
            choreographyMap.put(name, choreography.joinToString(separator = " "))
        }

        return choreographyMap
    }
}