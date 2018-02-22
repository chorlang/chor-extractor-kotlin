package extraction

import ast.sp.interfaces.ExtractionLabel
import ast.sp.labels.*
import ast.sp.nodes.*
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.ArrayList


class NetworkExtraction @Throws(Exception::class)
constructor(network: Network) {
    private val graph: DirectedGraph<Network, ExtractionLabel>
    private val network = network

    init{
        graph = DefaultDirectedGraph<Network, ExtractionLabel>(ExtractionLabel::class.java)
        graph.addVertex(network)
    }

    fun extract(){
        buildGraph(network)
        graph.vertexSet().stream().forEach { i -> println(i.network.toString()) }
        //unroll(graph)
        //buildChoreography()
    }

    private fun buildGraph(n: Network) {
        val stack = Stack<Network>()
        val r = ArrayList<CommCond>()
        stack.push(n)
        while (!stack.empty()){
            val node = stack.pop()
            var tmp = node.copy()

            for (p in node.network){
                if (canUnfold(p, tmp))
                    unfold(p.key, tmp)

                val findComm = findCommunication(p.key, tmp)
                if (findComm.sendreceive.isPresent || findComm.selectoffer.isPresent) {
                    val (tgt, lbl) = getCommunication(tmp, findComm)
                    val b = isAllProceduresVisited(tgt)
                    if (b) {
                        wash(tgt)
                    }
                    val ok = checkLoop(n, tgt, b)
                    if (ok) {
                        r.add(Comm(tgt, lbl))
                    }
                } else if (findCondition(tmp)) {
                    val (tgt1, lbl1, tgt2, lbl2) = getCondition(tmp)
                    val b1 = isAllProceduresVisited(tgt1)
                    if (b1) {
                        wash(tgt1)
                    }
                    val b2 = isAllProceduresVisited(tgt2)
                    if (b2) {
                        wash(tgt2)
                    }
                    val ok = checkLoop(n, tgt1, b1) && checkLoop(n, tgt2, b2)
                    if (ok) {
                        r.add(Cond(tgt1, lbl1, tgt2, lbl2))
                    }
                }
            }
            if (!r.isEmpty()) {
                val c = choose(r) //Need to implement choose
                if (c is Comm) {
                    commit(n, c.lbl, c.node)
                } else if (c is Cond) {
                    commit(n, c.lbl1, c.node1)
                    commit(n, c.lbl2, c.node2)
                }
            }
        }
    }

    data class GetCommunication(val sendreceive: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>), val selectoffer: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>))

    private fun findCommunication(p: String, n: Network): GetCommunication {
        var communication: Optional<Pair<ProcessBehaviour, ProcessBehaviour>>
        communication = Optional.empty()

        val pb = n.network.get(p)

        if (pb?.main is Sending){
            val receiving = n.network.values.stream().filter { j -> j.main is Receiving && p == (j.main as Receiving).process}.findFirst()
            if (receiving.isPresent) {
                communication = Optional.of(Pair(pb, receiving.get()))
            }
        } else if (pb?.main is Receiving) {
            val sending = n.network.values.stream().filter { j -> j.main is Sending && p == (j.main as Sending).process }.findFirst()
            if (sending.isPresent) {
                communication = Optional.of(Pair(sending.get(), pb))
            }
        }

        if (communication.isPresent){
            return GetCommunication(communication, Optional.empty())
        } else {
                if (pb?.main is SelectionSP) {
                    val offering = n.network.values.stream().filter { j -> j.main is Offering && p == (j.main as Offering).process}.findFirst()
                    if (offering.isPresent){
                        communication = Optional.of(Pair(pb, offering.get()))
                    }
                } else if (pb?.main is Offering) {
                    val selection = n.network.values.stream().filter { j -> j.main is SelectionSP && p == (j.main as SelectionSP).process}.findFirst()
                    if (selection.isPresent){
                        communication = Optional.of(Pair(selection.get(), pb))
                    }
                }
            }
        return GetCommunication(Optional.empty(), communication)
        }

    private fun getCommunication(n: Network, findComm: NetworkExtraction.GetCommunication): Pair<Network, ExtractionLabel> {
        if (findComm.sendreceive.isPresent){
            val sending = findComm.sendreceive.get().first
            val receiving = findComm.sendreceive.get().second

            val pbl = TreeMap<String, ProcessBehaviour>(n.network)

            val receivingProcess = (sending.main as Sending).process
            val sendingProcess = (receiving.main as Receiving).process

            pbl.replace(receivingProcess, ProcessBehaviour(receiving.procedures, (receiving.main as Receiving).continuation))
            pbl.replace(sendingProcess, ProcessBehaviour(sending.procedures, (sending.main as Sending).continuation))

            val label = Communication(sendingProcess, receivingProcess, (sending.main as Sending).expression)

            return Pair(Network(pbl), label)

        } else if (findComm.selectoffer.isPresent) {
            val selection = findComm.selectoffer.get().first
            val offering = findComm.selectoffer.get().second

            val pbl = TreeMap<String, ProcessBehaviour>(n.network)

            val selectionProcess = (offering.main as Offering).process
            val offeringProcess = (selection.main as SelectionSP).process

            val offeringBehavior = (offering.main as Offering).labels.get((selection.main as SelectionSP).expression) ?: throw Exception("Trying to select the labal that wasn't offered")

            pbl.replace(offeringProcess, ProcessBehaviour(offering.procedures, offeringBehavior))
            pbl.replace(selectionProcess, ProcessBehaviour(selection.procedures, (selection.main as SelectionSP).continuation))

            val label = Communication(offeringProcess, selectionProcess, (selection.main as SelectionSP).expression)

            return Pair(Network(pbl), label)

        } else throw IllegalArgumentException("getCommunication invoked, but no communication found")
    }

    private fun findCondition(n: Network): Boolean {
        return !n.network.values.filter { i -> i.main is ConditionSP}.isEmpty()
    }

    data class ResultCondition(val tmp1: Network, val lb1: ExtractionLabel, val tmp2: Network, val lbl2: ExtractionLabel)

    private fun getCondition(n: Network):ResultCondition {
        val conditionPB = n.network.entries.stream().filter { i -> i.value.main is ConditionSP }.findFirst().get().value
        val conditionMain = conditionPB.main as ConditionSP

        val pbelsemap = TreeMap<String, ProcessBehaviour>(n.network)
        val pbthenmap = TreeMap<String, ProcessBehaviour>(n.network)

        pbelsemap.replace(conditionMain.process, ProcessBehaviour(conditionPB.procedures ,conditionMain.elseBehaviour))
        pbthenmap.replace(conditionMain.process, ProcessBehaviour(conditionPB.procedures ,conditionMain.thenBehaviour))

        return ResultCondition(Network(pbelsemap), Then(conditionMain.process, conditionMain.expression), Network(pbthenmap), Else(conditionMain.process, conditionMain.expression))
    }

    private fun canUnfold(p: MutableMap.MutableEntry<String, ProcessBehaviour>, n: Network): Boolean {
        return n.network.get(p.key)?.main is ProcedureInvocationSP
    }

    private fun unfold(p: String, n: Network){
        val pb = n.network.get(p)
        val pi = (pb?.main as ProcedureInvocationSP).procedure  //add casting exception
        val pd = pb.procedures.get(pi)
        pb.main = pd?.behaviour?.copy() ?: throw Exception("No procedure definition with the invoking name")
        pd.visited = true
    }

    private fun commit(n1: Network, label: ExtractionLabel, n2: Network){
        val source = graph.vertexSet().find { i -> i.equals(n1)}
        val target = graph.vertexSet().stream().filter { i -> i.toString() == n2.toString() }.findAny()

        if (!target.isPresent){
            graph.addVertex(n2);
            graph.addEdge(source, n2, label);
            buildGraph(n2)//, Network(TreeMap<String,ProcessBehaviour>(n2.network)))
        } else {
            graph.addEdge(source, target.get(), label);
        }
    }

    private fun markprocedures(pname: String, pb: ProcessBehaviour, s: Boolean) {
        (pb.procedures.get(pname) as ProcedureDefinitionSP).visited = s
    }

    private fun checkLoop(n: Network, tgt: Network, b: Boolean): Boolean{
        return true
    }

    private fun isAllProceduresVisited(n: Network): Boolean {
        n.network.values.forEach { pr -> run {
                pr.procedures.forEach { procdef -> run {
                    if (!procdef.value.visited) return false }
                } } }
        return true
    }

    private fun wash(n: Network) {
        if (isAllProceduresVisited(n)) {
            n.network.values.forEach { pr -> run {
                pr.procedures.forEach { procdef -> run {
                    procdef.value.visited = true }
                } } }
        }
    }

    private fun choose(r: ArrayList<CommCond>): CommCond{
        return r.first()
    }

    /*private fun unroll(): DefaultDirectedGraph<SPNode, ExtractionLabel>{
        val g = DefaultDirectedGraph<SPNode, ExtractionLabel>(ExtractionLabel::class.java)
        graph.vertexSet().stream().forEach { i -> g.vertexSet().add(i.copy()) }
        graph.edgeSet().stream().forEach { i -> run {
            when(i){
                is Communication -> {
                    g.edgeSet().add(i.copy())
                }
                is Else -> {
                    g.edgeSet().add(i.copy())
                }
                is Then -> {
                    g.edgeSet().add(i.copy())
                }
                is Selection -> {
                    g.edgeSet().add(i.copy())
                }
                else -> {}//throw UnsupportedOperationException("New type of edge processing need to be added")}
            }
        } }

        graph.vertexSet().filter { i -> (i == root && graph.incomingEdgesOf(root).size > 0) || graph.incomingEdgesOf(i).size > 1 }.forEach { i -> kotlin.run {
            val fake = FakeProcedureInvocation(randomName(), i)
            g.addVertex(fake)
            val fakenode = g.vertexSet().stream().filter { it == fake }.findFirst().get()
            graph.incomingEdgesOf(i).stream().forEach {
                run {
                    val edgeSource = graph.getEdgeSource(it)
                    g.addEdge(edgeSource, fakenode, FakeLabel(it.toString()))
                    g.removeEdge(it)
                } }
        } }

        return g
    }*/
    /*private fun randomName(): String{
        val a = "RSTUVWXYZ"
        val b = "0123456789"
        val rnd = Random()

        val s = a.get(rnd.nextInt(a.length)).toString() + b.get(rnd.nextInt(b.length)).toString()
        if (pnames.contains(s)) {
            return randomName()
        } else {
            return s
        }
    }*/
    /* private fun markAllProcedures(tgt: Network, b: Boolean){
        tgt.network.forEach { t, u -> markprocedures(u.main, b) }
    }*/
    /*private fun notTerminated(n: Network): Boolean {
        return n.network.entries.stream().filter { i -> i.value.main !is TerminationSP}.findFirst().isPresent
    }*/
}
