package np

import ChoreographyLexer
import ChoreographyParser
import ast.sp.nodes.Network
import ast.sp.nodes.ProcessBehaviour
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import java.util.*
import java.io.File

object NetworkProjection {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val ch = parseInput(args)
        if (!ch.equals("-1"))  {
            println(project(ch).toString())
        }
        else System.out.println("Malformed request")

    }

    private fun parse(grammar: String): ChoreographyParser.ProgramContext {

        val stream = ANTLRInputStream(grammar)
        val lexer = ChoreographyLexer(stream)
        val parser = ChoreographyParser(CommonTokenStream(lexer))
        val tree = parser.program()
        return tree
    }

    fun project(choreography: String): Network {
        val tree = this.parse(choreography)
        val network = HashMap<String, ProcessBehaviour>()

        val choreographyVisitor = ChoreographyVisitor()
        val ccast = choreographyVisitor.getCCAST(tree)
        val processes = choreographyVisitor.processes

        val behaviourProjection = BehaviourProjection()
        for (process in processes) {
            network.put(process, behaviourProjection.getSPAST(ccast, process) as ProcessBehaviour)
        }
        return Network(network)
    }

    private fun parseInput(args: Array<String>): String {
        val iter = args.toList().listIterator()
        while(iter.hasNext()) {
            when (iter.next()){
                "-c" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1)
                        return args.get(i)
                    else throw Exception("Malformed call - choreography name was expected.")
                }
                "-f" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        val f = File(args.get(i))
                        return f.readText()
                    }
                    else throw Exception("Malformed call - choreography file name was expected.")
                }
                "-g" -> {
                    val i = iter.nextIndex() + 2
                    if (args.size >= i + 2) {
                        val pr = args.get(i).toInt()
                        val cond = args.get(i + 1).toInt()
                        return generateChoreography(pr, cond)

                    }
                    else throw Exception("Malformed call - parameters for choreography generating were expected.")
                }

            }
        }
        return "-1"
    }

    private fun generateChoreography(pr: Int, cond: Int): String {
        return "stop"
    }
}