package extraction

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ExtractionLabel
import ast.sp.labels.Communication
import ast.sp.labels.Else
import ast.sp.labels.Then
import ast.sp.nodes.*
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.HashSet


class NetworkExtraction @Throws(Exception::class)
constructor(network: Network) {
    private val graph: DirectedGraph<Network, ExtractionLabel>
    private val root: Network
    val pnames = HashSet<String>()

    init {
        graph = DefaultDirectedGraph<Network, ExtractionLabel>(ExtractionLabel::class.java)
        graph.addVertex(network)
        root = network.copy()

        extract(network)
    }

    private fun extract(network: Network){
        val tmp = network.copy()
        buildGraph(root, tmp)
        unroll()
        buildChoreography()
    }

    private fun buildGraph(n: Network, tmp: Network) {
        val findComm = findCommunication(tmp)
        if (findComm.sendreceive.isPresent || findComm.selectoffer.isPresent) {
            val (tmp0, lbl) = getCommunication(tmp, findComm)
            commit(n, lbl, tmp0)

        } else if (findCondition(tmp)) {
            val (tmp1, lbl1, tmp2, lbl2) = getCondition(tmp)
            commit(n, lbl1, tmp1)
            commit(n, lbl2, tmp2)

        } else if (canUnfold (tmp)){
            buildGraph(n, wash(unfold(tmp)))

        } else if (notTerminated(tmp)) {
            throw Exception("Deadlocked!");
        }
    }

    data class GetCommunication(val sendreceive: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>), val selectoffer: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>))

    private fun findCommunication(n: Network): GetCommunication {
        var communication: Optional<Pair<ProcessBehaviour, ProcessBehaviour>>
        communication = Optional.empty()

        n.network.forEach { t, u -> run {
            if (u.main is Sending) {
                val receiving = n.network.values.stream().filter { j -> j.main is Receiving && t == j.main.process}.findFirst()
                if (receiving.isPresent){
                    communication = Optional.of(Pair(u, receiving.get()))
                    return@forEach
                }
            }
        }}

        if (communication.isPresent){
            return GetCommunication(communication, Optional.empty())
        } else {
            n.network.forEach { t, u -> run {
                if (u.main is SelectionSP) {
                    val offering = n.network.values.stream().filter { j -> j.main is Offering && t == j.main.process}.findFirst()
                    if (offering.isPresent){
                        communication = Optional.of(Pair(u, offering.get()))
                        return@forEach
                    }

                }
            }}
            return GetCommunication(Optional.empty(), communication)
        }
    }

    private fun getCommunication(n: Network, findComm: GetCommunication): Pair<Network, ExtractionLabel> {
        if (findComm.sendreceive.isPresent){
            val sending = findComm.sendreceive.get().first
            val receiving = findComm.sendreceive.get().second

            val pbl = TreeMap<String, ProcessBehaviour>(n.network)

            val receivingProcess = (sending.main as Sending).process
            val sendingProcess = (receiving.main as Receiving).process

            pbl.replace(receivingProcess, ProcessBehaviour(receiving.procedures, receiving.main.continuation))
            pbl.replace(sendingProcess, ProcessBehaviour(sending.procedures, sending.main.continuation))

            val label = Communication(sendingProcess, receivingProcess, sending.main.expression)

            return Pair(Network(pbl), label)

        } else if (findComm.selectoffer.isPresent) {
            val selection = findComm.selectoffer.get().first
            val offering = findComm.selectoffer.get().second

            val pbl = TreeMap<String, ProcessBehaviour>(n.network)

            val selectionProcess = (offering.main as Offering).process
            val offeringProcess = (selection.main as SelectionSP).process

            val offeringBehavior = offering.main.labels.get(selection.main.expression) ?: throw Exception("Trying to select the labal that wasn't offered")

            pbl.replace(offeringProcess, ProcessBehaviour(offering.procedures, offeringBehavior))
            pbl.replace(selectionProcess, ProcessBehaviour(selection.procedures, selection.main.continuation))

            val label = Communication(offeringProcess, selectionProcess, selection.main.expression)

            return Pair(Network(pbl), label)

        } else throw IllegalArgumentException("getCommunication invoked, but no communication found")
    }

    private fun findCondition(n: Network): Boolean {
        return !n.network.values.filter { i -> i.main is ConditionSP}.isEmpty()
    }

    private fun getCondition(n: Network):ResultCondition {
        val conditionPB = n.network.entries.stream().filter { i -> i.value.main is ConditionSP }.findFirst().get().value
        val conditionMain = conditionPB.main as ConditionSP

        val pbelsemap = TreeMap<String, ProcessBehaviour>(n.network)
        val pbthenmap = TreeMap<String, ProcessBehaviour>(n.network)

        pbelsemap.replace(conditionMain.process, ProcessBehaviour(conditionPB.procedures ,conditionMain.elseBehaviour))
        pbthenmap.replace(conditionMain.process, ProcessBehaviour(conditionPB.procedures ,conditionMain.thenBehaviour))

        return ResultCondition(Network(pbelsemap), Then(conditionMain.process, conditionMain.expression), Network(pbthenmap), Else(conditionMain.process, conditionMain.expression))
    }

    private fun canUnfold(n: Network): Boolean {
        return n.network.entries.stream().filter { i -> i.value.main is ProcedureInvocationSP}.findFirst().isPresent
    }

    private fun notTerminated(n: Network): Boolean {
        return n.network.entries.stream().filter { i -> i.value.main !is TerminationSP}.findFirst().isPresent
    }

    data class ResultCondition(val tmp1: Network, val lb1: ExtractionLabel, val tmp2: Network, val lbl2: ExtractionLabel)

    private fun commit(n1: Network, label: ExtractionLabel, n2: Network){
        val source = graph.vertexSet().stream().filter { i -> i.toString() == n1.toString() }.findAny()
        val target = graph.vertexSet().stream().filter { i -> i.toString() == n2.toString() }.findAny()

        if (!target.isPresent){
            graph.addVertex(n2);
            graph.addEdge(source.get(), n2, label);
            buildGraph(n2, Network(TreeMap<String,ProcessBehaviour>(n2.network)))
        } else {
            graph.addEdge(source.get(), target.get(), label);
        }
    }

    private fun unfold(n: Network): Network {
        n.network.forEach { t, u -> if (u.main is ProcedureInvocationSP) run {
            val behaviour = u.procedures.get(u.main.procedure)?.behaviour ?: throw Exception("No procedure definition with the invoking name")
            markprocedures(behaviour)
            n.network.replace(t, ProcessBehaviour(u.procedures, behaviour))
        } }

        return n
    }

    private fun markprocedures(behaviour: Behaviour) {

    }

    private fun wash(n: Network): Network {
        return n
    }

    private fun unroll(){
        graph.vertexSet().filter { i -> (i == root && graph.incomingEdgesOf(root).size > 1) || graph.incomingEdgesOf(i).size > 2 }.forEach { i -> kotlin.run {

        } }
    }

    private fun randomName(): String{
        val a = "RSTUVWXYZ"
        val b = "0123456789"
        val rnd = Random()

        val s = a.get(rnd.nextInt(a.length)).toString() + b.get(rnd.nextInt(b.length)).toString()
        if (pnames.contains(s)) {
            return randomName()
        } else {
            return s
        }
    }

    private fun buildChoreography(){

    }
}
