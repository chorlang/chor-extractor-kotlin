package ce

import antlrgen.NetworkLexer
import antlrgen.NetworkParser
import ast.cc.nodes.Choreography
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import ast.sp.nodes.ParallelNetworks
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.logging.log4j.LogManager
import util.NetworkStat
import util.NetworkStatistics
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

    fun getStat(input: String): NetworkStat{
        val stream = ANTLRInputStream(input)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.parallelNetworks()
        val networkVisitor = NetworkVisitor()
        val parallelNetworks = networkVisitor.visitParallelNetworks(tree) as ParallelNetworks

        if (parallelNetworks.networkList.size == 1) {
            val ns = NetworkStatistics()
            return ns.visit(parallelNetworks.networkList.first())
        } else throw OperationNotSupportedException()
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