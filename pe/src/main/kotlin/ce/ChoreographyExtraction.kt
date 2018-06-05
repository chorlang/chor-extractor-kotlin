package ce

import NetworkLexer
import NetworkParser
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.logging.log4j.LogManager
import java.io.File

object ChoreographyExtraction{
    private val log = LogManager.getLogger();

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

    private fun extractChoreography(parsedInput: ParsedInput): Program {
        val stream = ANTLRInputStream(parsedInput.chor)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.network()
        val networkVisitor = NetworkVisitor()
        val network = networkVisitor.visitNetwork(tree) as Network

        if (parsedInput.livelocked.isEmpty() || network.processes.keys.containsAll(parsedInput.livelocked)){
            return NetworkExtraction.run(network, parsedInput.strategy, parsedInput.livelocked, parsedInput.debugMode)
        }
        else throw Exception("Malformed call - list of livelocked processes contains not existent processes")
    }

    fun parseStrategy(strategy: String): Strategy {
        val s = Strategy.values().find { it.name == strategy }
        return s ?: Strategy.SelectFirst
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