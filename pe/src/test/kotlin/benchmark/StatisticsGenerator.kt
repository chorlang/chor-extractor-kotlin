package benchmark

import bisim.bisimilar
import ce.Extraction
import ce.NetworkStatistics
import np.EndPointProjection
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import util.choreographyStatistic.LengthOfProcedures
import util.choreographyStatistic.NumberOfActions
import java.io.File
import java.text.ParseException
import javax.naming.OperationNotSupportedException
import kotlin.math.roundToInt

class StatisticsGenerator {
    companion object {
        private const val TEST_DIR = "tests"
        private const val CHOREOGRAPHY_PREFIX = "choreography-"
        private const val PROJECTION_PREFIX = "projection-"
        private const val EXTRACTION_PREFIX = "extraction-"
        private const val SCREWED_PROJECTION_STATISTICS_PREFIX = "stats-screwed-projection-"
        private const val SCREWED_EXTRACTION_STATISTICS_PREFIX = "stats-screwed-extraction-"
        private const val PROJECTION_STATISTICS_PREFIX = "stats-projection-"
        private const val EXTRACTION_STATISTICS_PREFIX = "stats-extraction-"
        private const val COMBINED_STATISTICS_PREFIX = "stats-"

        @JvmStatic
        @BeforeAll
        internal fun setUp() {
            //delete all files except the ones started with CHOREOGRAPHY_PREFIX
            cleanTestFolder(TEST_DIR, CHOREOGRAPHY_PREFIX)
        }

        private fun cleanTestFolder(dirPath: String, prefix: String) {
            val dir = File(dirPath)
            require(dir.exists() && dir.isDirectory)

            for (fileName in dir.list()) {
                if (!fileName.startsWith(prefix)) {
                    val file = File("$dirPath/$fileName")
                    file.delete()
                }
            }
        }

    }

    //@Test
    fun timeToNodes() {
        checkOutputFolder()
        networkProjectionStatistics()
        choreographyExtractionStatistics()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, network_body>>

        filesWithNetworks.forEach { fileId, networks ->
            val choreographies = HashMap<String, String>()


            File(OUTPUT_DIR, "$EXTRACTION_STATISTICS_PREFIX$-timeToNode").printWriter().use { out ->
                out.println("time nodes")

                networks.forEach { choreographyId, network ->
                    val start = System.currentTimeMillis()
                    val program = Extraction.main(arrayListOf("-c", network, "-d"))
                    val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000

                    choreographies[choreographyId] = program.choreographies.first().toString()

                    if (program.choreographies.size == 1 && program.statistics.size == 1) {
                        out.println(
                                "$executionTime," +
                                        "${program.statistics.first().nodes}"
                        )
                    } else throw OperationNotSupportedException()
                }
            }
        }
    }

    @Test
    fun gatherALLStatistics() {
        networkProjectionStatistics()
        choreographyExtractionStatistics()
        screwDataStatistics()
        accumulateStatistics(TEST_DIR, COMBINED_STATISTICS_PREFIX)
    }

    private fun accumulateStatistics(dirPath: String, prefix: String) {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val testFileNames = retrieveTestFileNames()

        for (testFileName in testFileNames) {
            val file = File("$dirPath/$prefix$testFileName")
            file.appendText("$testFileName\n")

            for (fileName in dir.list()) {
                if (fileName.endsWith(testFileName)) {
                    file.appendText("$fileName\n")
                    file.appendText(File("$dirPath/$fileName").readText())
                }
            }
        }
    }

    private fun retrieveTestFileNames(): HashSet<String> {
        val dir = File(TEST_DIR)
        require(dir.exists() && dir.isDirectory)
        val testNames = HashSet<String>()


        for (fileName in dir.list()) {
            if (fileName.startsWith(CHOREOGRAPHY_PREFIX)) {
                testNames.add(fileName.removePrefix(CHOREOGRAPHY_PREFIX))
            }
        }

        return testNames
    }

    /**
     * for each file with choreographies
     * 1. generate networks and write them to the file %original_file_name% networks
     * 2. get statistics and write to the file %original_file_name% statistics
     */
    //@Test
    fun networkProjectionStatistics() {
        checkOutputFolder()
        val choreographyFiles = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX) //HashMap<filename, HashMap<choreography_id, choreography_body>>
        choreographyFiles.forEach { fileId, choreographyData ->
            writeNetworksToFile(choreographyData, "$PROJECTION_PREFIX$fileId")
            writeNetworkStatisticsToFile(choreographyData, "$PROJECTION_STATISTICS_PREFIX$fileId")
        }
    }

    @Test
    fun extractionSoundness() {
        val originalChoreographies = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX)
        val extractedChoreographies = parseExtractionFiles(TEST_DIR, EXTRACTION_PREFIX)
        originalChoreographies.forEach { fileId, choreographyData ->
            choreographyData.forEach { id, choreography ->
                if (!bisimilar(choreography, (extractedChoreographies[fileId]!!)[id]!!)) {
                    println("$id failed the bisimilarity check")
                }
            }
        }
    }

    @Test
    fun extractionSoundnessC41() {
        //NOTE that "10-6-0-0" doesn't contain "C41"
        val originalChoreographies = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX)
        val extractedChoreographies = parseExtractionFiles(TEST_DIR, EXTRACTION_PREFIX)

        assert(bisimilar((originalChoreographies["10-6-0-0"]!!)["C41"]!!, (extractedChoreographies["10-6-0-0"]!!)["C41"]!!))
    }

    private fun parseExtractionFiles(dirPath: String, prefix: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val fileToChoreographyMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            if (fileName.startsWith(prefix)) {
                val file = File("$dirPath/$fileName")
                fileToChoreographyMap[fileName.substringAfter(prefix)] = parseExtractionFile(file)
            }
        }

        return fileToChoreographyMap
    }

    //@Test
    fun choreographyExtractionStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, network_body>>

        filesWithNetworks.forEach { fileId, networks ->
            val choreographies = HashMap<String, String>()
            File(OUTPUT_DIR, "$EXTRACTION_STATISTICS_PREFIX$fileId").printWriter().use { out ->
                out.println("id,time(sec),nodes,badLoops,mainLength,numOfProcedures,minProcedureLength,maxProcedureLength,avgProcedureLength")

                networks.forEach { choreographyId, network ->
                    //value idToChoreography = HashMap<String, String>()

                    val start = System.currentTimeMillis()
                    val program = Extraction.main(arrayListOf("-c", network, "-d"))
                    val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000

                    choreographies[choreographyId] = program.choreographies.first().toString()

                    if (program.choreographies.size == 1 && program.statistics.size == 1) {
                        val statistic = program.statistics.first()
                        val choreography = program.choreographies.first()
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

            File(OUTPUT_DIR, "$EXTRACTION_PREFIX$fileId").printWriter().use { out ->
                out.println("id,body")
                choreographies.forEach { id, choreography -> out.println("$id,$choreography") }
            }
        }

    }

    //@Test
    fun screwDataStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseFolderWithFilesWithNetworks(OUTPUT_DIR) //HashMap<filename, HashMap<choreography_id, network_body>>

        filesWithNetworks.forEach { fileId, networks ->
            val screwedExecutionStatistic = ArrayList<ScrewedExecutionStatistic>()

            File(OUTPUT_DIR, "$SCREWED_PROJECTION_STATISTICS_PREFIX$fileId").printWriter().use { out ->
                //file header
                out.println("choreographyId," +
                        "screwedId," +
                        "screwedNetwork," +
                        "addProcessPosition," +
                        "removeProcessPosition," +
                        "swapProcessPosition," +
                        "time(msecs)," +
                        "badLoops," +
                        "nodes")

                val badLoopsList = ArrayList<Int>()
                val nodesList = ArrayList<Int>()
                val executionTimeList = ArrayList<Double>()

                networks.forEach { choreographyId, network ->
                    //screw network
                    val timesToScrew = 10
                    var counter = 1
                    val networkBody = Extraction.generateNetwork(network)

                    (0..timesToScrew).forEach {
                        val screwedId = "$choreographyId-${counter++}"
                        val (screwedNetwork, screwedInformation) = NetworkScrewer().screwNetwork(networkBody)

                        //try and fail to extract body
                        val start = System.currentTimeMillis()
                        val program = Extraction.main(arrayListOf("-c", network, "-d"))
                        val executionTime = (System.currentTimeMillis() - start).toDouble() / 1000
                        val graphStatistic = program.statistics.first()


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
            File(OUTPUT_DIR, "$SCREWED_EXTRACTION_STATISTICS_PREFIX$fileId").printWriter().use { out ->
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
    private val OUTPUT_DIR = TEST_DIR

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
            if (fileName.startsWith(PROJECTION_PREFIX)) {
                val file = File("$dirPath/$fileName")
                fileToNetworksMap[fileName.substringAfter(PROJECTION_PREFIX)] = parseNetworkFile(file)
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
     * @input: body data: HashMap<choreography_id, choreography_body>, filename
     */
    private fun writeNetworkStatisticsToFile(chorData: HashMap<String, String>, filename: String) {
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println("id,length,numberOfProcesses,numberOfProcedures,numberOfConditionals," +
                    "minLengthOfProcesses,maxLengthOfProcesses,avgLengthOfProcesses," +
                    "minNumberOfProceduresInProcesses,maxNumberOfProceduresInProcesses,avgNumberOfProceduresInProcesses," +
                    "minNumberOfConditionalsInProcesses,maxNumberOfConditionalsInProcesses,avgNumberOfConditionalsInProcesses,numberOfProcessesWithConditionals," +
                    "minProcedureLengthInProcesses,maxProcedureLengthInProcesses,avgProcedureLengthInProcesses")
            chorData.forEach { choreographyId, choreography ->
                val network = EndPointProjection.project(choreography).toString()
                val networkStatistic = NetworkStatistics.getNetworkStatistic(network)
                val choreographyStatistic = EndPointProjection.getStatistic(choreography)
                out.println("$choreographyId," +
                        "${choreographyStatistic.length}," +
                        "${choreographyStatistic.numberOfProcesses}," +
                        "${choreographyStatistic.numberOfProcedures}," +
                        "${choreographyStatistic.numberOfConditionals}," +
                        "${networkStatistic.minLengthOfProcesses}," +
                        "${networkStatistic.maxLengthOfProcesses}," +
                        "${networkStatistic.avgLengthOfProcesses}," +
                        "${networkStatistic.minNumberOfProceduresInProcesses}," +
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
     * @input: body data: (body id, body), filename
     */
    private fun writeNetworksToFile(chorData: HashMap<String, String>, filename: String) {
        //var count = 1
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println("choreographyId,network")
            chorData.forEach { choreographyId, choreography ->
                println("Projecting $choreographyId in $filename")
                val network = EndPointProjection.project(choreography)
                out.println("$choreographyId, $network")
            }
        }
    }

    /**
     * @input: dir_path
     * @output: HashMap<file_id, HashMap<choreography_id, choreography_body>>
     */
    private fun parseChoreographyFiles(dirPath: String, prefix: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val fileToChoreographyMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            if (fileName.startsWith(prefix)) {
                val file = File("$dirPath/$fileName")
                fileToChoreographyMap[fileName.substringAfter(prefix)] = parseChoreographyFile(file)
            }
        }

        return fileToChoreographyMap
    }

    /**
     * @output: HashMap<choreography_id, choreography_body>>
     */
    private fun parseChoreographyFile(file: File): HashMap<String, String> {
        val choreography = ArrayList<String>()
        var name = ""

        val choreographyMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("***") -> name = line.substring(4).substringBefore("***").trim()
                line.startsWith("def") -> choreography.add(line)
                line.startsWith("main") -> choreography.add(line)
                line.isEmpty() -> {
                    choreographyMap[name] = choreography.joinToString(separator = " ")
                    choreography.clear()
                    name = ""
                }
                else -> throw ParseException("line $line was unexpected in $file", 0)
            }
        }

        if (choreography.isNotEmpty() && name != "") {
            choreographyMap[name] = choreography.joinToString(separator = " ")
        }

        return choreographyMap
    }

    private fun parseExtractionFile(file: File): HashMap<String, String> {
        val choreographyMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("id") -> {
                }
                else -> {
                    val (id, choreography) = line.split(",", limit = 2)
                    choreographyMap[id] = choreography
                }
            }
        }

        return choreographyMap
    }

    data class ScrewedExecutionStatistic(val choreographyId: String,
                                         val minExecutionTime: Double, val maxExecutionTime: Double, val avgExecutionTime: Double,
                                         val minNodes: Int, val maxNodes: Int, val avgNodes: Int,
                                         val minBadLoops: Int, val maxBadLoops: Int, val avgBadLoops: Int)
}