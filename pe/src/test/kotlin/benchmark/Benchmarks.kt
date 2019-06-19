package benchmark

import ast.cc.nodes.GraphStatistics
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import bisim.bisimilar
import epp.EndPointProjection
import extraction.Extraction
import extraction.ExtractionStrategy
import org.junit.Test
import util.ParseUtils
import util.choreographyStatistics.ChoreographyStatistics
import util.choreographyStatistics.LengthOfProcedures
import util.choreographyStatistics.NumberOfActions
import util.networkStatistics.NetworkStatistics
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.ParseException

// TODO Still have to go through the screwing
class Benchmarks <I> {
    //data class StatHeader(val length: String, val numOfProcesses: String, val numOfCondition: String, val numOfProcedures: String)

    data class ScrewedExecutionStatistics(val choreographyId: String,
                                          val minExecutionTime: Long, val maxExecutionTime: Long, val avgExecutionTime: Double,
                                          val minNodes: Int, val maxNodes: Int, val avgNodes: Int,
                                          val minBadLoops: Int, val maxBadLoops: Int, val avgBadLoops: Int)

    companion object {
        private const val TEST_DIR = "tests"
        private const val CHOREOGRAPHY_PREFIX = "choreography-"
        private const val PROJECTION_PREFIX = "projection-"
        private const val EXTRACTION_PREFIX = "extraction-"
        private const val PROJECTION_STATISTICS_PREFIX = "stats-projection-"
        private const val EXTRACTION_STATISTICS_PREFIX = "stats-extraction-"
        private const val SCREWED_PROJECTION_STATISTICS_PREFIX = "stats-screwed-projection-"
        private const val SCREWED_EXTRACTION_STATISTICS_PREFIX = "stats-screwed-extraction-"
        private const val COMBINED_STATISTICS_PREFIX = "stats-"
        private const val SEP = "\t"

        private val PROJECTION_STATISTICS_HEADER =
                arrayOf("testId","numberOfActions","numberOfProcesses","numberOfProcedures","numberOfConditionals",
                "minLengthOfProcesses","maxLengthOfProcesses","avgLengthOfProcesses",
                "minNumberOfProceduresInProcesses","maxNumberOfProceduresInProcesses","avgNumberOfProceduresInProcesses",
                "minNumberOfConditionalsInProcesses","maxNumberOfConditionalsInProcesses","avgNumberOfConditionalsInProcesses","numberOfProcessesWithConditionals",
                "minProcedureLengthInProcesses","maxProcedureLengthInProcesses","avgProcedureLengthInProcesses").joinToString(SEP)
        private val EXTRACTION_STATISTICS_HEADER = arrayOf("testId","strategy","time(msec)","nodes","badLoops","mainLength","numOfProcedures","minProcedureLength","maxProcedureLength","avgProcedureLength").joinToString(SEP)
        private val SCREWED_PROJECTION_STATISTICS_HEADER = arrayOf("testId",
                "screwedId",
                "screwedNetwork",
                "addProcessPosition",
                "removeProcessPosition",
                "swapProcessPosition",
                "time(msecs)",
                "badLoops",
                "nodes").joinToString(SEP)
        private val SCREWED_EXTRACTION_STATISTICS_HEADER = arrayOf("testId",
                "minExecutionTime","maxExecutionTime","avgExecutionTime",
                "minNodes","maxNodes","avgNodes",
                "minBadLoops","maxBadLoops","avgBadLoops").joinToString(SEP)
    }

    /**
     * for each file with choreographies
     * 1. generate networks and write them to the file %original_file_name% networks
     * 2. get statistics and write to the file %original_file_name% statistics
     */

    @Test
    fun epp(){
        checkOutputFolder()
        val choreographyFiles = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX) //HashMap<filename, HashMap<choreography_id, choreography_body>>
        choreographyFiles.forEach { fileId, choreographyMap ->
            val projectionMap = HashMap<String, Pair<Program, Network>>()
            choreographyMap.forEach { choreographyId, choreography ->
                println("Projecting $choreographyId in $CHOREOGRAPHY_PREFIX$fileId")
                val program = ParseUtils.stringToProgram(choreography)
                projectionMap[choreographyId] = Pair(program, EndPointProjection.project(program))
            }
            writeNetworksToFile(projectionMap, "$PROJECTION_PREFIX$fileId")
            writeNetworkStatisticsToFile(projectionMap, "$PROJECTION_STATISTICS_PREFIX$fileId")
        }
    }

    private fun writeNetworksToFile(projectionMap: Map<String, Pair<Program, Network>>, filename: String) {
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println("testId${SEP}network")
            projectionMap.forEach { id, pair -> out.println("$id$SEP${pair.second}") }
        }
    }

    private fun writeNetworkStatisticsToFile(projectionMap: Map<String, Pair<Program, Network>>, filename: String) {
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println(PROJECTION_STATISTICS_HEADER)
            projectionMap.forEach { id, pair ->
                val networkStatistics = NetworkStatistics.compute(pair.second)
                val choreographyStatistics = ChoreographyStatistics.compute(pair.first)
                out.println("$id$SEP" +
                        "${choreographyStatistics.numberOfActions}$SEP" +
                        "${choreographyStatistics.numberOfProcesses}$SEP" +
                        "${choreographyStatistics.numberOfProcedures}$SEP" +
                        "${choreographyStatistics.numberOfConditionals}$SEP" +
                        "${networkStatistics.minLengthOfProcesses}$SEP" +
                        "${networkStatistics.maxLengthOfProcesses}$SEP" +
                        "${networkStatistics.avgLengthOfProcesses}$SEP" +
                        "${networkStatistics.minNumberOfProceduresInProcesses}$SEP" +
                        "${networkStatistics.maxNumberOfProceduresInProcesses}$SEP" +
                        "${networkStatistics.avgNumberOfProceduresInProcesses}$SEP" +
                        "${networkStatistics.minNumberOfConditionalsInProcesses}$SEP" +
                        "${networkStatistics.maxNumberOfConditionalsInProcesses}$SEP" +
                        "${networkStatistics.avgNumberOfConditionalsInProcesses}$SEP" +
                        "${networkStatistics.numberOfProcessesWithConditionals}$SEP" +
                        "${networkStatistics.minProcedureLengthInProcesses}$SEP" +
                        "${networkStatistics.maxProcedureLengthInProcesses}$SEP" +
                        "${networkStatistics.avgProcedureLengthInProcesses}"
                )
            }
        }
    }

    /* private fun cleanTestFolder(dirPath: String, prefix: String) {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        for (fileName in dir.list()) {
            if (!fileName.startsWith(prefix)) {
                val file = File("$dirPath/$fileName")
                file.delete()
            }
        }
    } */

    @Test
    fun extractionSoundness() {
        val originalChoreographies = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX)
        val extractedChoreographies = parseExtractionFiles(TEST_DIR, EXTRACTION_PREFIX)
        originalChoreographies.forEach { fileId, choreographyData ->
//            if ( !fileId.startsWith("50") ) {
                choreographyData.forEach { id, choreography ->
                    System.out.println("Checking $id in $fileId")
                    if (!bisimilar(choreography, (extractedChoreographies[fileId]!!)[id]!!)) {
                        println("$id failed the bisimilarity check")
                    }
                }
//            }
        }
    }

    /*@Test
fun extractionSoundnessC41() {
    //NOTE that "10-6-0-0" doesn't contain "C41"
    val originalChoreographies = parseChoreographyFiles(TEST_DIR, CHOREOGRAPHY_PREFIX)
    val extractedChoreographies = parseExtractionFiles(TEST_DIR, EXTRACTION_PREFIX)

    assert(bisimilar((originalChoreographies["10-6-0-0"]!!)["C41"]!!, (extractedChoreographies["10-6-0-0"]!!)["C41"]!!))
}*/

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

    private fun writeExtractionsToFile(extractionMap: Map<String, Pair<Program, Long>>, filename: String, strategyName: String) {
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println("testId${SEP}strategy${SEP}choreography")
            extractionMap.forEach { id, pair -> out.println("${id}${SEP}$strategyName${SEP}${pair.first}") }
        }
    }

    private fun writeExtractionStatisticsToFile(extractionMap: Map<String, Pair<Program, Long>>, filename: String, strategyName: String) {
        File(OUTPUT_DIR, filename).printWriter().use { out ->
            out.println(EXTRACTION_STATISTICS_HEADER)
            extractionMap.forEach { id, pair ->
                val program = pair.first
                val statistics = program.statistics.fold( GraphStatistics( 0, 0 ), { one, two -> GraphStatistics( one.nodes + two.nodes, one.badLoops + two.badLoops ) } )
                val choreographyProcedures = program.choreographies.map { it!!.procedures.size }.fold( 0, Int::plus )
                val lengthOfProcedures = program.choreographies.flatMap { LengthOfProcedures().getLength(it!!) }
                val numberOfActions = program.choreographies.map { NumberOfActions.compute(it!!) }.fold( 0, Int::plus )

                out.println("$id$SEP" +
                        "$strategyName$SEP" +
                        "${pair.second}$SEP" +
                        "${statistics.nodes}$SEP" +
                        "${statistics.badLoops}$SEP" +
                        "${numberOfActions}$SEP" +
                        "${choreographyProcedures}$SEP" +
                        "${lengthOfProcedures.min() ?: 0}$SEP" +
                        "${lengthOfProcedures.max() ?: 0}$SEP" +
                        "${lengthOfProcedures.average().toInt()}"
                )
            }
        }
    }

    @Test
    fun extractionTest() = ExtractionStrategy.values().forEach { if ( it != ExtractionStrategy.Default ) extraction(it) }

    fun extraction(strategy: ExtractionStrategy) {
        checkOutputFolder()

        val networkFiles = parseNetworkFiles(TEST_DIR, PROJECTION_PREFIX) // HashMap<filename, HashMap<id, network_body>>

        networkFiles.forEach { (fileId, networkMap) ->
            //            if (!fileId.startsWith("50-6")) {
//            if ( fileId != "50-6-50-0" && fileId != "50-6-40-0" && fileId != "50-6-30-0" ) {
//            if ( /* fileId == "50-6-50-0" || */ fileId == "50-6-40-0" ) {
//            if ( fileId != "50-6-50-0" ) {
            if ( Files.notExists( Paths.get( "$OUTPUT_DIR/$EXTRACTION_PREFIX${strategy.name}-$fileId" ) ) ) {
                val extractionMap = HashMap<String, Pair<Program, Long>>()
                networkMap
//                        .filter { (id, network) -> id == "C129" }
                        .forEach { id, network ->
                            println("Extracting $id from $PROJECTION_PREFIX$fileId with strategy ${strategy.name}")
                            val start = System.currentTimeMillis()
                            val program = Extraction.extractChoreography(network, strategy, ArrayList())
                            val executionTime = System.currentTimeMillis() - start

                            extractionMap[id] = Pair(program, executionTime)
                        }
                writeExtractionsToFile(extractionMap, "$EXTRACTION_PREFIX${strategy.name}-$fileId", strategy.name)
                writeExtractionStatisticsToFile(extractionMap, "$EXTRACTION_STATISTICS_PREFIX${strategy.name}-$fileId", strategy.name)
//            }
//            }
            }
        }
    }

    //@Test
    fun screwDataStatistics() {
        checkOutputFolder()
        val filesWithNetworks = parseNetworkFiles(TEST_DIR, PROJECTION_PREFIX) //HashMap<filename, HashMap<choreography_id, network_body>>

        filesWithNetworks.forEach { fileId, networks ->
            val screwedExecutionStatistic = ArrayList<ScrewedExecutionStatistics>()

            File(OUTPUT_DIR, "$SCREWED_PROJECTION_STATISTICS_PREFIX$fileId").printWriter().use { out ->
                //file header
                out.println(SCREWED_PROJECTION_STATISTICS_HEADER)

                val badLoopsList = ArrayList<Int>()
                val nodesList = ArrayList<Int>()
                val executionTimeList = ArrayList<Long>()

                networks.forEach { choreographyId, network ->
                    //screw network
                    val timesToScrew = 10
                    var counter = 1
                    val networkBody = ParseUtils.stringToNetwork(network)

                    repeat((0..timesToScrew).count()) {
                        val screwedId = "$choreographyId-${counter++}"
                        val (screwedNetwork, screwedInformation) = NetworkScrewer().screwNetwork(networkBody)

                        //try and fail to extract body
                        val start = System.currentTimeMillis()
                        val program = Extraction.extractChoreography(network, ExtractionStrategy.Default, ArrayList())
                        val executionTime = System.currentTimeMillis() - start
                        val graphStatistic = program.statistics.first()


                        //collect data
                        out.println("$choreographyId$SEP" +
                                "$screwedId$SEP" +
                                "$screwedNetwork$SEP" +
                                "${screwedInformation.add}$SEP" +
                                "${screwedInformation.remove}$SEP" +
                                "${screwedInformation.swap}$SEP" +
                                "$executionTime$SEP" +
                                "${graphStatistic.badLoops}$SEP" +
                                "${graphStatistic.nodes}"
                        )

                        badLoopsList.add(graphStatistic.badLoops)
                        nodesList.add(graphStatistic.nodes)
                        executionTimeList.add(executionTime)
                    }

                    screwedExecutionStatistic.add(ScrewedExecutionStatistics(choreographyId,
                            executionTimeList.min() ?: 0, executionTimeList.max()
                            ?: 0, executionTimeList.average(),
                            nodesList.min() ?: 0, nodesList.max() ?: 0, nodesList.average().toInt(),
                            badLoopsList.min() ?: 0, badLoopsList.max() ?: 0, badLoopsList.average().toInt()
                    ))
                }
            }
            File(OUTPUT_DIR, "$SCREWED_EXTRACTION_STATISTICS_PREFIX$fileId").printWriter().use { out ->
                out.println(SCREWED_EXTRACTION_STATISTICS_HEADER)

                screwedExecutionStatistic.forEach { elem ->
                    out.println("${elem.choreographyId}$SEP" +
                            "${elem.minExecutionTime}$SEP${elem.maxExecutionTime}$SEP${elem.avgExecutionTime}$SEP" +
                            "${elem.minNodes}$SEP${elem.maxNodes}$SEP${elem.avgNodes}$SEP" +
                            "${elem.minBadLoops}$SEP${elem.maxBadLoops}$SEP${elem.avgBadLoops}")
                }
            }
        }
    }

    @Suppress("PrivatePropertyName")
    private val OUTPUT_DIR = TEST_DIR

    private fun checkOutputFolder() {
        val dir = File(OUTPUT_DIR)
        if (!dir.exists())
            dir.mkdirs()
        else if (!dir.isDirectory)
            throw InternalError("$OUTPUT_DIR already exists and is not a directory")
    }

    private fun parseNetworkFiles(dirPath: String, prefix: String): HashMap<String, HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory)

        val fileToNetworksMap = HashMap<String, HashMap<String, String>>()

        for (fileName in dir.list()) {
            if (fileName.startsWith(prefix)) {
                val file = File("$dirPath/$fileName")
                fileToNetworksMap[fileName.substringAfter(prefix)] = parseNetworkFile(file)
            }
        }

        return fileToNetworksMap
    }

    private fun parseNetworkFile(file: File): HashMap<String, String> {
        val networks = HashMap<String, String>()

        file.forEachLine { line ->
            val separator = line.indexOf(SEP)
            if (separator != -1) {
                networks[line.substring(0, separator)] = line.substring(separator + 1)
            }
        }

        networks.remove("testId") //remove csv title

        return networks
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
                    val (id, choreography) = line.split(SEP, limit = 2)
                    choreographyMap[id] = choreography
                }
            }
        }

        return choreographyMap
    }

    /*private fun parseStatName(name: String): StatHeader {
        val stat = name.split("-".toRegex())
        return StatHeader(stat[stat.size - 4], stat[stat.size - 3], stat[stat.size - 2], stat[stat.size - 1])
    }*/

    @Test
    fun runAllBenchmarks() {
        epp()
        extractionTest()
        screwDataStatistics()
    }

    @Test
    fun makeCombinedStatistics() {
        combineStatistics("comms-only", "(\\d+)-6-0-0")
        combineStatistics("increasing-ifs-no-recursion", "50-6-(\\d+)-0")
        combineStatistics("increasing-ifs-procedures", "200-5-(\\d+)-(\\d+)")
        combineStatistics("increasing-processes", "500-(\\d+)-0-0")
        combineStatistics("increasing-ifs-with-recursion", "100-50-(\\d+)-5")
        combineStatistics("test1", "(100|[1-9]0)-6-0-0")
        combineStatistics("test2", "50-6-[1-5]0-0")
        combineStatistics("test3", "10-5-[0-5]+-[0-5]+")
        combineStatistics("test3-0", "10-5-0-[0-5]+")
        combineStatistics("test3-5", "10-5-5-[0-5]+")
        combineStatistics("test4", "40-(5|100|[1-9][0|5])+-0-0")
        combineStatistics("all", ".*")
    }

    private fun retrieveTestFileData(prefix: String, regexStr: String): HashMap<String, String> {
        val dir = File(TEST_DIR)
        val regex = regexStr.toRegex()

        val data = HashMap<String, String>()

        for (filename in dir.list()) {
            if (filename.startsWith(prefix) && regex.matches(filename.removePrefix(prefix))) {
                val lines = File("$TEST_DIR/$filename").readLines()
                var i = 1
                while (i < lines.size) {
                    val split = lines[i].split(SEP, limit = 2)
                    data[split[0]] = split[1]
                    i++
                }
            }
        }

        return data
    }

    private fun combineStatistics(filename: String, regexStr: String) {
        val outputFile = File("$TEST_DIR/$COMBINED_STATISTICS_PREFIX$filename")

        val statsToCombine = arrayOf(PROJECTION_STATISTICS_PREFIX, EXTRACTION_STATISTICS_PREFIX)
        val headersToCombine = arrayOf(PROJECTION_STATISTICS_HEADER, EXTRACTION_STATISTICS_HEADER)

        val bigData = HashMap<String, Map<String, String>>() // prefix -> choreography_id -> data
        for (prefix in statsToCombine) {
            bigData[prefix] = retrieveTestFileData(prefix, regexStr)
        }

        outputFile.printWriter().use { out ->
            out.println("id$SEP" + headersToCombine.joinToString(SEP) { it.split(SEP, limit = 2)[1] })

            for (key in bigData[statsToCombine[0]]!!.keys.map { Integer.parseInt(it.substring(1)) }.sorted()) {
                val ckey = "C$key"
                out.print("$ckey$SEP")
                out.println(bigData.map { it.value[ckey] }.joinToString(SEP))
            }
        }
    }
}