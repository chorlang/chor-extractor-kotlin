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
        val ch = parseInput(args)

        if (ch!=null) {
            val chor = extractChoreography(ch.chor)
            log.info(chor.toString())

            return chor.toString()
        }
        else log.error("Malformed request")

        return ""
    }

    private fun extractChoreography(grammar: String): Program {
        val stream = ANTLRInputStream(grammar)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.network()
        val networkVisitor = NetworkVisitor()
        val network = networkVisitor.visitNetwork(tree) as Network
        return NetworkExtraction().extract(network, Strategy.SelectFirst)
    }

    private fun parseStrategy(strategy: String): Strategy {
        val s = Strategy.values().asList().find { i -> i.equals(strategy) }
        return s ?: Strategy.SelectFirst
    }

    private fun parseInput(args: Array<String>): ParsedInput? {
        var str = Strategy.Default
        var chor = ""

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
                    else throw Exception("Malformed call - choreography name was expected.")
                }
                "-f" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        val f = File(args.get(i))
                        chor = f.readText()
                    }
                    else throw Exception("Malformed call - choreography file name was expected.")
                }

            }
        }

        if (chor == "") return null
        else return (ParsedInput(chor, str))
    }

    data class ParsedInput(val chor: String, val strategy: Strategy)

}
