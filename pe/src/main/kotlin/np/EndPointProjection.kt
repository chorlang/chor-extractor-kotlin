package np

import ast.cc.interfaces.CCNode
import ast.cc.interfaces.ChoreographyBody
import ast.cc.nodes.*
import ast.sp.nodes.Network
import ast.sp.nodes.ParallelNetworks
import ast.sp.nodes.ProcessTerm
import org.apache.logging.log4j.LogManager
import util.ChoreographyASTToProgram
import util.ParseUtils
import util.ParseUtils.parseChoreography
import util.choreographyStatistic.ChoreographyStatisticsData
import util.choreographyStatistic.NumberOfActions
import util.choreographyStatistic.NumberOfConditionals
import java.io.File
import java.util.*
import javax.naming.OperationNotSupportedException
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.streams.asSequence


object EndPointProjection {
    private val log = LogManager.getLogger()

    /**
     * @param choreography
     * @return (parallel) networks projected from the initial choreography
     */
    fun project(choreography: String): ParallelNetworks {
        val program = ParseUtils.stringToProgram(choreography)

        //project choreographies to networks
        val behaviourProjection = BehaviourProjection()
        val choreographyList = program.choreographies
        val network = HashMap<String, ProcessTerm>()
        val networkList = ArrayList<Network>()
        for (chor in choreographyList) {
            for (process in chor!!.processes) {
                try {
                    network[process] = behaviourProjection.getProcessTerm(chor, process) as ProcessTerm
                } catch (e: Merging.MergingException) {
                    val newE = Merging.MergingException("Process $process ${e.message!!}")
                    newE.stackTrace = e.stackTrace
                    throw newE
                }
            }
            networkList.add(Network(network))
        }
        return ParallelNetworks(networkList)

    }

    fun getStatistic(choreography: String): ChoreographyStatisticsData {
        val tree = parseChoreography(choreography)
        val choreographyVisitor = ChoreographyASTToProgram()
        val program = choreographyVisitor.getProgram(tree) //returns Program

        val choreographyList = (program as Program).choreographies
        if (choreographyList.size == 1) {
            return getChoreographyStatistic(choreographyList.first()!!)
        } else {
            throw OperationNotSupportedException()
        }
    }


    private fun parseInput(args: Array<String>): String {
        val iter = args.toList().listIterator()
        while (iter.hasNext()) {
            when (iter.next()) {
                "-c" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1)
                        return args[i]
                    else throw Exception("Malformed call - body name was expected.")
                }
                "-f" -> {
                    val i = iter.nextIndex()
                    if (args.size >= i + 1) {
                        val f = File(args[i])
                        return f.readText()
                    } else throw Exception("Malformed call - body file name was expected.")
                }
                "-g" -> {
                    log.info("generate body")
                    val i = iter.nextIndex()
                    if (args.size >= i + 2) {
                        val pr = args[i].toInt()
                        val cond = args[i + 1].toInt()
                        return generateChoreography(pr, cond)

                    } else throw Exception("Malformed call - parameters for body generating were expected.")
                }

            }
        }
        return "-1"
    }

    private fun generateChoreography(pr: Int, cond: Int): String {
        when (pr) {
            0 -> return ""
            1 -> return "main {stop}"
        }

        val prset = generateProcessesList(pr)
        val chor = generateStructure(prset, pr, cond)

        return chor.toString()
    }

    private fun generateStructure(prset: ArrayList<String>, pr: Int, cond: Int): CCNode {
        val main = generateMain(prset, HashSet(), pr, cond)
        val procedures = emptyList<ProcedureDefinition>() //TODO procedures generation

        return Choreography(main, procedures)
    }

    private fun generateMain(prset: ArrayList<String>, visited: HashSet<String>, pr: Int, cond: Int): ChoreographyBody {
        val expression = generateExpression()

        if (Math.random() < 0.5 && cond != 0) {
            //generate condition

            val process = prset[Random().nextInt(prset.size)]
            val tpr = if (visited.contains(process)) pr else pr - 1
            visited.add(process)

            val branch = generateMain(prset, visited, tpr, cond - 1)

            return Condition(process, expression, thenChoreography = branch, elseChoreography = branch)

        } else {
            //generate communication

            val sr = getSenderReceiver(prset)
            val sender = sr.sender
            val receiver = sr.receiver

            val bs = visited.contains(sender)
            val br = visited.contains(receiver)

            val tpr =
                    if (bs && br) pr
                    else if (bs || br) pr - 1
                    else pr - 2

            visited.add(sender)
            visited.add(receiver)

            return if (visited.size == prset.size && cond == 0) {
                if (Math.random() < 0.5)
                    CommunicationSelection(Communication(sender, receiver, expression), Termination())
                else
                    CommunicationSelection(Selection(sender, receiver, expression), Termination())
            } else {
                val continuation = generateMain(prset, visited, tpr, cond)

                if (Math.random() < 0.5)
                    CommunicationSelection(Communication(sender, receiver, expression), continuation)
                else
                    CommunicationSelection(Selection(sender, receiver, expression), continuation)
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
        var i2: Int
        do {
            i2 = Random().nextInt(prset.size)
        } while (i1 == i2)

        return SenderReceiver(prset[i1], prset[i2])
    }

    private fun generateProcessesList(i: Int): ArrayList<String> {
        val source = "pqrstvwxyz"
        val prset = ArrayList<String>()

        val ln: Long = if (i <= source.length) 1 else 3

        do {
            val pr = Random().ints(ln, 0, source.length)
                    .asSequence()
                    .map(source::get)
                    .joinToString("")
            if (!prset.contains(pr)) prset.add(pr)
        } while (prset.size != i)

        return prset
    }

    private fun getChoreographyStatistic(choreography: Choreography): ChoreographyStatisticsData {
        val numberOfProcedures = choreography.procedures.size
        val numberOfProcesses = choreography.processes.size
        val numberOfActions = NumberOfActions().visit(choreography)
        val numberOfConditionals = NumberOfConditionals().visit(choreography)

        return ChoreographyStatisticsData(numberOfActions, numberOfProcesses, numberOfProcedures, numberOfConditionals)
    }
}