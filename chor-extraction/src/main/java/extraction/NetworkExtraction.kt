package extraction

import ast.cc.interfaces.CCNode
import ast.cc.nodes.Termination
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ExtractionLabel
import ast.sp.interfaces.SPNode
import ast.sp.labels.*
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
        buildChoreography(unroll())
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

    private fun canUnfold(n: Network): Boolean {
        return n.network.entries.stream().filter { i -> i.value.main is ProcedureInvocationSP}.findFirst().isPresent
    }

    private fun notTerminated(n: Network): Boolean {
        return n.network.entries.stream().filter { i -> i.value.main !is TerminationSP}.findFirst().isPresent
    }

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
            markprocedures(behaviour, true)
            n.network.replace(t, ProcessBehaviour(u.procedures, behaviour))
        } }

        return n
    }

    private fun markprocedures(b: Behaviour, s: Boolean) {
        when(b){
            is ProcedureInvocationSP -> {
                b.isVisited = s
            }
            is ConditionSP -> {
                markprocedures(b.elseBehaviour, s)
                markprocedures(b.thenBehaviour, s)
            }
            is Sending ->{
                markprocedures(b.continuation, s)
            }
            is Receiving -> {
                markprocedures(b.continuation, s)
            }
            is Offering -> {
                b.labels.values.forEach { b -> markprocedures(b, s) }
            }
            is SelectionSP -> {
                markprocedures(b.continuation, s)
            }
        }

    }

    private fun isAllProceduresVisited(n: Network): Boolean {
        n.network.values.forEach { i -> run {
            if (isProcedureVisited(i.main) == false){
                return false
            }

        } }
        return true
    }

    private fun isProcedureVisited(b: Behaviour):Boolean{
        when(b){
            is ProcedureInvocationSP -> {
                if (b.isVisited == false){
                    return false
                }

            }
            is ConditionSP -> {
                isProcedureVisited(b.elseBehaviour)
                isProcedureVisited(b.thenBehaviour)
            }
            is Sending ->{
                isProcedureVisited(b.continuation)
            }
            is Receiving -> {
                isProcedureVisited(b.continuation)
            }
            is Offering -> {
                b.labels.values.forEach { b -> isProcedureVisited(b) }
            }
            is SelectionSP -> {
                isProcedureVisited(b.continuation)
            }
        }
        return true
    }

    private fun wash(n: Network): Network {
        if (isAllProceduresVisited(n)){
            n.network.values.forEach { b -> markprocedures(b.main, false) }
        }
        return n
    }

    private fun unroll(): DefaultDirectedGraph<SPNode, ExtractionLabel>{
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

    private fun buildChoreography(g: DefaultDirectedGraph<SPNode, ExtractionLabel>){
        val get = g.vertexSet().stream().filter { it == root }.findFirst().get()
        when(g.incomingEdgesOf(get).size){
            0 -> {
                 Termination ()
            }
            1 -> {
                val edge = g.incomingEdgesOf(get).iterator().next()
                if (edge is Communication) {
                    ast.cc.nodes.Communication (edge.sender, edge.receiver, edge.expression, buildChoreography (g.getEdgeTarget()));
                } else if (edge is Selection) {
                    ast.cc.nodes.Selection (edge.sender, edge.receiver, edge.label, buildChoreography(g.getEdgeTarget()));
            }
            2 -> {
                /*val labels = g.incomingEdgesOf(get).to(that = Array<ExtractionLabel>)
                val thenLabel =(labels[0] instanceof Then) ? (Then) labels[0] : (Then) labels[1];
                val elseLabel =(labels[0] instanceof Then) ? (Else) labels[1] : (Else) labels[0];

                // throw exception is processName or expression is different in the two nodes

                retval = new Condition (
                        thenLabel.getProcess(),
                thenLabel.getExpression(),
                extractNodeForward(graph.getEdgeTarget(thenLabel)),
                extractNodeForward(graph.getEdgeTarget(elseLabel))
                );*/
            }
        }
    }
}
