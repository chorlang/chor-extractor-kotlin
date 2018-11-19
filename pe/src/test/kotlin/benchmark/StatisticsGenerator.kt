package benchmark

import ce.ChoreographyExtraction
import ce.NetworkStatistics
import np.NetworkProjection
import org.junit.jupiter.api.Test
import util.choreographyStatistic.LengthOfProcedures
import util.choreographyStatistic.NumberOfActions
import java.io.File
import java.text.ParseException
import javax.naming.OperationNotSupportedException
import kotlin.math.roundToInt

class StatisticsGenerator {
    /**
     * for each file with choreographies
     * 1. generate networks and write them to the file %original_file_name% networks
     * 2. get statistic and write to the file %original_file_name% statistics
     */
    @Test
    fun networkProjectionStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithChoreographies("tests") //HashMap<filename, HashMap<choreography_id, choreography_body>>
        filesWithNetworks.forEach { filename, choreographyData ->
            writeNetworksToFile(choreographyData, "$filename networks")
            writeNetworkStatisticsToFile(choreographyData, "$filename statistics")
        }

    }

    @Test
    fun 

    @Test
    fun choreographyExtractionStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, network_body>>

        val choreographies = HashMap<String, String>()

        filesWithNetworks.forEach { filename, networks ->
            File(OUTPUT_DIR, "${filename.dropLast(13)} choreography.txt").printWriter().use { out ->
                out.println("id, time(sec),nodes,badLoops,mainLength,numOfProcedures,minProcedureLength,maxProcedureLength,avgProcedureLength")

                networks.forEach { choreographyId, network ->
                    //value idToChoreography = HashMap<String, String>()

                    val start = System.currentTimeMillis()
                    val program = ChoreographyExtraction.main(arrayListOf("-c", network, "-d"))
                    val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000

                    choreographies[choreographyId] = program.choreographyList.first().toString()

                    if (program.choreographyList.size == 1 && program.statistic.size == 1) {
                        val statistic = program.statistic.first()
                        val choreography = program.choreographyList.first()
                        val lengthOfProcedures = LengthOfProcedures().getLength(choreography!!)

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

            File(OUTPUT_DIR, "${filename.dropLast(13)} new choreographies.txt").printWriter().use { out ->
                out.println("ID,Choreography")
                choreographies.forEach { id, choreography -> out.println("$id,$choreography") }
            }
        }

    }

    @Test
    fun screwDataStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, network_body>>

        filesWithNetworks.forEach { filename, networks ->
            val screwedExecutionStatistic = ArrayList<ScrewedExecutionStatistic>()

            File(OUTPUT_DIR, "${filename.dropLast(13)} screwed.cvs").printWriter().use { out ->
                //file header
                out.println("choreographyId, " +
                        "screwedId, " +
                        "screwedNetwork, " +
                        "addProcessPosition, " +
                        "removeProcessPosition, " +
                        "swapProcessPosition, " +
                        "extractionTime, " +
                        "badLoops, " +
                        "nodes")

                val badLoopsList = ArrayList<Int>()
                val nodesList = ArrayList<Int>()
                val executionTimeList = ArrayList<Double>()

                networks.forEach { choreographyId, network ->
                    //screw network
                    val timesToScrew = 10
                    var counter = 1
                    val networkBody = ChoreographyExtraction.generateNetwork(network)

                    (0..timesToScrew).forEach {
                        val screwedId = "$choreographyId-${counter++}"
                        val (screwedNetwork, screwedInformation) = NetworkScrewer().screwNetwork(networkBody)

                        //try and fail to extract choreography
                        val start = System.currentTimeMillis()
                        val program = ChoreographyExtraction.main(arrayListOf("-c", network, "-d"))
                        val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000
                        val graphStatistic = program.statistic.first()


                        //collect data
                        out.println("$choreographyId," +
                                "$screwedId," +
                                "$screwedNetwork," +
                                "${screwedInformation.add}," +
                                "${screwedInformation.remove}," +
                                "${screwedInformation.swap}," +
                                "${executionTime.roundToInt()}," +
                                "${graphStatistic.badLoops}," +
                                "${graphStatistic.nodes}"
                        )

                        badLoopsList.add(graphStatistic.badLoops)
                        nodesList.add(graphStatistic.nodes)
                        executionTimeList.add(executionTime)
                    }

                    screwedExecutionStatistic.add(ScrewedExecutionStatistic(choreographyId,
                            executionTimeList.min() ?: 0.0, executionTimeList.max() ?: 0.0, executionTimeList.average(),
                            nodesList.min() ?: 0, nodesList.max() ?: 0, nodesList.average().toInt(),
                            badLoopsList.min() ?: 0, badLoopsList.max() ?: 0, badLoopsList.average().toInt()
                    ))
                }
            }
            File(OUTPUT_DIR, "${filename.dropLast(13)} screwed statistic.csv").printWriter().use { out ->
                out.println("choreographyId," +
                        "minExecutionTime,maxExecutionTime,avgExecutionTime," +
                        "minNodes,maxNodes,avgNodes," +
                        "minBadLoops,maxBadLoops,avgBadLoops")

                screwedExecutionStatistic.forEach { elem ->
                    out.println("${elem.choreographyId}," +
                            "${elem.minExecutionTime},${elem.maxExecutionTime},${elem.avgExecutionTime.roundToInt()}," +
                            "${elem.minNodes},${elem.maxNodes},${elem.avgNodes}," +
                            "${elem.minBadLoops},${elem.maxBadLoops},${elem.avgBadLoops}")
                }
            }
        }
    }

    @Suppress("PrivatePropertyName")
    private val OUTPUT_DIR = "tests"

    private fun checkOutputFolder() {
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
    private fun writeNetworkStatisticsToFile(chorData: HashMap<String, String>, filename: String) {
        File(OUTPUT_DIR, "$filename.txt").printWriter().use { out ->
            out.println("id, length, numberOfProcesses, numberOfProcedures, numberOfConditionals, " +
                    "minLengthOfProcesses, maxLengthOfProcesses, avgLengthOfProcesses, " +
                    "minNumberOfProceduresInProcesses, maxNumberOfProceduresInProcesses, avgNumberOfProceduresInProcesses, " +
                    "minNumberOfConditionalsInProcesses, maxNumberOfConditionalsInProcesses, avgNumberOfConditionalsInProcesses, numberOfProcessesWithConditionals, " +
                    "minProcedureLengthInProcesses, maxProcedureLengthInProcesses, avgProcedureLengthInProcesses")
            chorData.forEach { choreographyId, choreography ->
                val network = NetworkProjection.project(choreography).toString()
                val networkStatistic = NetworkStatistics.getNetworkStatistic(network)
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
     * @input: choreography data: (choreography id, choreography), filename
     */
    private fun writeNetworksToFile(chorData: HashMap<String, String>, filename: String) {
        //var count = 1
        File(OUTPUT_DIR, "$filename.txt").printWriter().use { out ->
            out.println("choreographyId,networkId,Network")
            chorData.forEach { choreographyId, choreography ->
                val network = NetworkProjection.project(choreography)
                out.println("$choreographyId, $network")
            }
        }
    }

    /**
     * @input: dir_path
     * @output: HashMap<filename, HashMap<choreography_id, choreography_body>>
     */
    private fun parseFolderWithFilesWithChoreographies(dirPath: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val fileToChoreographyMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            if (fileName.contains("chor-")) {
                val file = File("$dirPath/$fileName")
                fileToChoreographyMap[fileName] = parseFileWithChoreographies(file)
            }
        }

        return fileToChoreographyMap
    }

    /**
     * @output: HashMap<choreography_id, choreography_body>>
     */
    private fun parseFileWithChoreographies(file: File): HashMap<String, String> {
        val choreography = ArrayList<String>()
        var name = ""

        val choreographyMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("***") -> name = line.substring(4, 7).trim()
                line.startsWith("def") -> choreography.add(line)
                line.startsWith("main") -> choreography.add(line)
                line.isEmpty() -> {
                    choreographyMap[name] = choreography.joinToString(separator = " ")
                    choreography.clear()
                    name = ""
                }
                else -> throw ParseException("line $line was unexpected", 0)
            }
        }

        if (choreography.isNotEmpty() && name != "") {
            choreographyMap[name] = choreography.joinToString(separator = " ")
        }

        return choreographyMap
    }

    data class ScrewedExecutionStatistic(val choreographyId: String,
                                         val minExecutionTime: Double, val maxExecutionTime: Double, val avgExecutionTime: Double,
                                         val minNodes: Int, val maxNodes: Int, val avgNodes: Int,
                                         val minBadLoops: Int, val maxBadLoops: Int, val avgBadLoops: Int)
}