package ce

import antlrgen.NetworkLexer
import antlrgen.NetworkParser
import ast.cc.nodes.Choreography
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import ast.sp.nodes.ParallelNetworks
import ast.sp.nodes.ProcessTerm
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.logging.log4j.LogManager
import util.networkStatistic.*
import java.io.File
import javax.naming.OperationNotSupportedException

object ChoreographyExtraction{
    private val log = LogManager.getLogger()

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: ArrayList<String>): String {
        val parsedInput = parseInput(args)

        if (parsedInput!=null) {
            val chor = extractChoreography(parsedInput)
            //log.info(chor.toString())

            return chor.toString()
        }
        else throw Exception("Malformed call - choreography was expected.")
    }

    fun getStat(input: String): NetworkStatisticData {
        val stream = ANTLRInputStream(input)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.parallelNetworks()
        val networkVisitor = NetworkVisitor()
        val parallelNetworks = networkVisitor.visitParallelNetworks(tree) as ParallelNetworks

        if (parallelNetworks.networkList.size == 1) {
            return getNetworkStatistic(parallelNetworks.networkList.first())
        } else throw OperationNotSupportedException()
    }

    private fun getNetworkStatistic(n:Network): NetworkStatisticData {
        val lengthOfProcesses = ArrayList<Int>()
        val lengthOfProcedures = ArrayList<Int>()
        val numberOfConditionals = ArrayList<Int>()
        val numberOfProcedures = ArrayList<Int>()

        n.processes.forEach { processName, processTerm ->
            val processStatistic = getNetworkProcessStatistic(processTerm)

            lengthOfProcesses.add(processStatistic.lengthOfProcesses)
            lengthOfProcedures.add(processStatistic.lengthOfProcedures)
            numberOfConditionals.add(processStatistic.numOfConditions)
            numberOfProcedures.add(processStatistic.numberOfProcedures)
        }

        return NetworkStatisticData(
                minLengthOfProcesses = lengthOfProcesses.min()?:0,
                maxLengthOfProcesses = lengthOfProcesses.max()?:0,
                avgLengthOfProcesses = lengthOfProcesses.average().toInt(),
                minNumberOfProceduresInProcesses = numberOfProcedures.min()?:0,
                maxNumberOfProceduresInProcesses = numberOfProcedures.max()?:0,
                avgNumberOfProceduresInProcesses = numberOfProcedures.average().toInt(),
                minNumberOfConditionalsInProcesses = numberOfConditionals.min()?:0,
                maxNumberOfConditionalsInProcesses = numberOfConditionals.max()?:0,
                avgNumberOfConditionalsInProcesses = numberOfConditionals.average().toInt(),
                numberOfProcessesWithConditionals = numberOfConditionals.filter { it.equals(0) }.size,
                minProcedureLengthInProcesses = lengthOfProcedures.min()?:0,
                maxProcedureLengthInProcesses = lengthOfProcedures.max()?:0,
                avgProcedureLengthInProcesses = lengthOfProcedures.average().toInt()
        )

    }

    private fun getNetworkProcessStatistic(n: ProcessTerm): NetworkProcessStatisticData {
        val networkProcessConditionals = NetworkProcessConditions().visit(n)
        val networkProcessActionProcedures = NetworkProcessActionsProcedures().visit(n)
        val networkProcessActions = networkProcessActionProcedures + NetworkProcessActionsMain().visit(n)
        val networkProcessProcedures = n.procedures.size

        return NetworkProcessStatisticData(networkProcessActions, networkProcessProcedures, networkProcessConditionals, networkProcessActionProcedures)
    }

    private fun extractChoreography(parsedInput: ParsedInput): Program {
        val stream = ANTLRInputStream(parsedInput.chor)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.parallelNetworks()
        val networkVisitor = NetworkVisitor()
        val parallelNetworks = networkVisitor.visitParallelNetworks(tree) as ParallelNetworks

        val program = ArrayList<Choreography>()
        for (network in parallelNetworks.networkList) {
            if (parsedInput.livelocked.isEmpty() || network.processes.keys.containsAll(parsedInput.livelocked)) {
                program.add(NetworkExtraction.run(network, parsedInput.strategy, parsedInput.livelocked, parsedInput.debugMode))
            } else throw Exception("Malformed call - list of livelocked processesInChoreography contains not existent processesInChoreography")
        }
        return Program(program)
    }

    fun parseStrategy(strategy: String): Strategy {
        val s = Strategy.values().find { it.name == strategy }
        return s ?: Strategy.SelectionFirst
    }

    private fun parseInput(args: ArrayList<String>): ParsedInput? {
        var str = Strategy.Default
        var chor = ""
        val livelocked = ArrayList<String>()
        var debugMode = false

        val iter = args.toList().listIterator()
        while(iter.hasNext()) {
            when (iter.next()){
                "-s" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        str = parseStrategy(args.get(i))
                    }
                    else throw Exception("Malformed call - strategy for choreography generating was expected.")
                }
                "-c" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1)
                        chor = args.get(i)
                    else throw Exception("Malformed call - choreography was expected.")
                }
                "-f" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        val f = File(args.get(i))
                        chor = f.readText()
                    }
                    else throw Exception("Malformed call - choreography file name was expected.")
                }
                "-l" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        val livelockProcesses = args.get(i)
                        livelocked.addAll(livelockProcesses.replace(" ", "").split(","))
                    }
                }
                "-d" -> {
                    debugMode = true
                }

            }
        }

        if (chor == "") return null
        else return (ParsedInput(chor, str, livelocked, debugMode))
    }

    data class ParsedInput(val chor: String, val strategy: Strategy, val livelocked: ArrayList<String>, val debugMode: Boolean)

}