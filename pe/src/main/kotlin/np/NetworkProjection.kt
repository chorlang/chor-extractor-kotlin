package np

import ChoreographyLexer
import ChoreographyParser
import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import ast.cc.nodes.*
import ast.sp.nodes.Network
import ast.sp.nodes.ProcessBehaviour
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import java.util.*
import java.io.File
import kotlin.collections.HashSet
import kotlin.streams.asSequence
import java.util.Random
import kotlin.collections.ArrayList


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
                    val i = iter.nextIndex()
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
        when (pr){
            0 -> return ""
            1 -> return "main {stop}"
        }

        val prset = generateProcessesList(pr)
        val chor = generateStructure(prset, pr, cond)

        return chor.toString()
    }

    private fun generateStructure(prset: ArrayList<String>, pr: Int, cond: Int): CCNode {
        val main = generateMain(prset, HashSet<String>(), pr, cond) as Choreography
        val procedures = emptyList<ProcedureDefinition>() //TODO procedures generation

        return Program(main, procedures)
    }

    private fun generateMain(prset: ArrayList<String>, visited: HashSet<String>, pr: Int, cond: Int):CCNode {
        val expression = generateExpression()

        if(Math.random() < 0.5 && cond!=0){
            //generate condition

            val process = prset[Random().nextInt(prset.size)]
            val t_pr = if (visited.contains(process)) pr else pr-1
            visited.add(process)

            val branch = generateMain(prset, visited, t_pr, cond-1)

            return Condition(process, expression, thenChoreography = branch, elseChoreograpy = branch)

        } else {
            //generate communication

            val sr = getSenderReceiver(prset)
            val sender = sr.sender
            val receiver = sr.receiver

            val bs = visited.contains(sender)
            val br = visited.contains(receiver)

            val t_pr =
                    if (bs && br) pr
                    else if (bs || br) pr-1
                    else pr-2

            visited.add(sender)
            visited.add(receiver)

            if (visited.size == prset.size && cond == 0) {
                if (Math.random() < 0.5)
                    return Communication(sender, receiver, expression, Termination())
                else
                    return Selection(sender, receiver, expression, Termination())
            } else {
                val continuation = generateMain(prset, visited, t_pr, cond)

                if (Math.random() < 0.5)
                    return Communication(sender, receiver, expression, continuation)
                else
                    return Selection(sender, receiver, expression, continuation)
            }
        }
    }

    private fun generateExpression(): String {
        val source = "eioua"
        return Random().ints(1, 0, source.length).asSequence().map(source::get).joinToString("")
    }

    data class SenderReceiver(val sender: String, val receiver: String)

    private fun getSenderReceiver(prset: ArrayList<String>): SenderReceiver {
        val i1 = Random().nextInt(prset.size)
        var i2 = 0
        do { i2 = Random().nextInt(prset.size) } while (i1==i2)

        return SenderReceiver(prset[i1], prset[i2])

    }

    private fun generateProcessesList(i: Int): ArrayList<String> {
        val source = "pqrstvwxyz"
        val prset = ArrayList<String>()

        val ln :Long = if (i<=source.length) 1 else 3

        (1..i).forEach{
                val pr = Random().ints(ln, 0, source.length)
                    .asSequence()
                    .map(source::get)
                    .joinToString("")
                prset.add(pr)
            }

        return prset
    }
}