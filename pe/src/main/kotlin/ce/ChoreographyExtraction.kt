package ce

import ast.cc.nodes.Program
import ast.sp.nodes.Network
import NetworkLexer
import NetworkParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.logging.log4j.LogManager
import java.io.File

object ChoreographyExtraction{
    private val log = LogManager.getLogger();

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>): String {
        val parsedInput = parseInput(args)

        if (parsedInput!=null) {
            val chor = extractChoreography(parsedInput)
            log.info(chor.toString())

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
            return NetworkExtraction.run(network, parsedInput.strategy, parsedInput.livelocked)
        }
        else throw Exception("Malformed call - list of livelocked processes contains not existent processes")
    }

    private fun parseStrategy(strategy: String): Strategy {
        val s = Strategy.values().asList().find { i -> i.equals(strategy) }
        return s ?: Strategy.SelectFirst
    }

    private fun parseInput(args: Array<String>): ParsedInput? {
        var str = Strategy.Default
        var chor = ""
        val livelocked = ArrayList<String>()

        val iter = args.toList().listIterator()
        while(iter.hasNext()) {
            when (iter.next()){
                "-s" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        str = parseStrategy(args.get(i))
                    }
                    else throw Exception("Malformed call - parameters for choreography generating were expected.")
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

            }
        }

        if (chor == "") return null
        else return (ParsedInput(chor, str, livelocked))
    }

    data class ParsedInput(val chor: String, val strategy: Strategy, val livelocked: ArrayList<String>)

}