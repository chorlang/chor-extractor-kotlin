@file:Suppress("DEPRECATION")

package ce

import antlrgen.NetworkLexer
import antlrgen.NetworkParser
import ast.cc.nodes.Choreography
import ast.cc.nodes.GraphStatistics
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import ast.sp.nodes.ParallelNetworks
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File

object ChoreographyExtraction{
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: ArrayList<String>): Program {
        val parsedInput = parseInput(args)

        return if (parsedInput!=null) extractChoreography(parsedInput)
        else throw Exception("Malformed call - body was expected.")
    }

    fun generateNetwork(network: String): Network{
        val stream = ANTLRInputStream(network)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.parallelNetworks()
        val networkVisitor = NetworkVisitor()
        return (networkVisitor.visitParallelNetworks(tree) as ParallelNetworks).networkList.first()
    }

    private fun extractChoreography(parsedInput: ParsedInput): Program {
        val network = generateNetwork(parsedInput.network)

        val program = ArrayList<Choreography?>()
        val statistic = ArrayList<GraphStatistics>()
        //for (network in parallelNetworks.networkList) {
            if (parsedInput.livelocked.isEmpty() || network.processes.keys.containsAll(parsedInput.livelocked)) {
                val extraction = NetworkExtraction.run(network, parsedInput.strategy, parsedInput.livelocked, parsedInput.debugMode)
                program.add(extraction.first)
                statistic.add(extraction.second)
            } else throw Exception("Malformed call - list of livelocked processesInChoreography contains not existent processesInChoreography")
        //}
        return Program(program, statistic)
    }

    fun parseStrategy(strategy: String): Strategy {
        val s = Strategy.values().find { it.name == strategy }
        return s ?: Strategy.SelectionFirst
    }

    private fun parseInput(args: ArrayList<String>): ParsedInput? {
        var str = Strategy.Default
        var network = ""
        val livelocked = ArrayList<String>()
        var debugMode = false

        val iterator = args.toList().listIterator()
        while(iterator.hasNext()) {
            when (iterator.next()){
                "-s" -> {
                    val i = iterator.nextIndex()
                    if (args.size >= i + 1) {
                        str = parseStrategy(args[i])
                    }
                    else throw Exception("Malformed call - strategy for body generating was expected.")
                }
                "-c" -> {
                    val i = iterator.nextIndex()
                    if (args.size >= i + 1)
                        network = args[i]
                    else throw Exception("Malformed call - body was expected.")
                }
                "-f" -> {
                    val i = iterator.nextIndex()
                    if (args.size >= i + 1) {
                        val f = File(args[i])
                        network = f.readText()
                    }
                    else throw Exception("Malformed call - body file name was expected.")
                }
                "-l" -> {
                    val i = iterator.nextIndex()
                    if (args.size >= i + 1) {
                        val livelockProcesses = args[i]
                        livelocked.addAll(livelockProcesses.replace(" ", "").split(","))
                    }
                }
                "-d" -> {
                    debugMode = true
                }

            }
        }

        return if (network == "") null
        else (ParsedInput(network, str, livelocked, debugMode))
    }

    data class ParsedInput(val network: String, val strategy: Strategy, val livelocked: ArrayList<String>, val debugMode: Boolean)

}