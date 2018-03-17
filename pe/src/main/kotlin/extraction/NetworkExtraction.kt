package extraction

import ast.cc.interfaces.CCNode
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ExtractionLabel
import ast.sp.labels.*
import ast.sp.nodes.*
import ast.cc.interfaces.Choreography
import ast.cc.nodes.*
import ast.sp.interfaces.Interaction
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

typealias network = SortedMap<String, ProcessBehaviour>

class NetworkExtraction {
    private var gmap = HashMap<String, ArrayList<ConcreteNode>>()
    private var count = 0

    interface Node

    data class ConcreteNode(val nodenet: Network, val str: String, val bad: Bad) : Node {
        fun copy(): ConcreteNode {
            val newnet = nodenet.copy()
            val newb = HashSet<ConcreteNode>()
            bad.badset.mapTo(newb) { it.copy() }

            return ConcreteNode(newnet, "" + str, Bad(newb))
        }
    }

    data class Bad(val badset: HashSet<ConcreteNode>)

    data class FakeNode(val procedureName: String, val source: Node) : Node

    fun extract(n: Network): Program {
        val graph = DefaultDirectedGraph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val node = ConcreteNode(n, "0", Bad(HashSet()))
        graph.addVertex(node)
        addToGlobalMap(node)

        buildGraph(node, graph as DirectedGraph<ConcreteNode, ExtractionLabel>)
        val fklist = unroll(node, graph as DirectedGraph<Node, ExtractionLabel>)
        return buildChoreography(node, fklist, graph)
    }

    private fun buildChoreography(root: Node, fklist: ArrayList<FakeNode>, graph: DirectedGraph<Node, ExtractionLabel>): Program {
        val main = bh(root, graph, fklist)
        val procedures = ArrayList<ProcedureDefinition>()
        for (fk in fklist) {
            procedures.add(ProcedureDefinition(fk.procedureName, bh(fk, graph, fklist), HashSet()))
        }

        return Program(main as Choreography, procedures)
    }

    private fun bh(node: Node, graph: DirectedGraph<Node, ExtractionLabel>, fklist: ArrayList<FakeNode>): CCNode {
        val edges = graph.outgoingEdgesOf(node)

        when (edges.size) {
            0 -> {
                if (node is ConcreteNode) {
                    for (fk in fklist) {
                        if (fk.source.equals(node)) return ProcedureInvocation(fk.procedureName, HashSet())
                    }

                    for (p in node.nodenet.network) {
                        if (p.value.main !is TerminationSP) throw Exception("Bad graph. No more edges found, but not all processes were terminated.")
                        else return Termination()
                    }

                }

            }
            1 -> {
                val e = edges.first()
                return when (e) {

                    is InteractionLabel -> {
                        Communication(e.sender, e.receiver, e.expression, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    is SelectionLabel -> {
                        Selection(e.receiver, e.sender, e.label, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    else -> throw Exception("Unexpected label type, can't build choreography")
                }
            }
            2 -> {
                val e1 = edges.first()

                return when (e1) {
                    is ThenLabel -> Condition(e1.process, e1.expression, bh(graph.getEdgeTarget(e1), graph, fklist), bh(graph.getEdgeTarget(edges.last()), graph, fklist))
                    is ElseLabel -> Condition(e1.process, e1.expression, bh(graph.getEdgeTarget(edges.last()), graph, fklist), bh(graph.getEdgeTarget(e1), graph, fklist))
                    else -> throw Exception("Bad graph. Was waiting for conditional edges, but got unexpected type.")
                }
            }
        }
        return Termination()
    }

    data class LabelTarget(val lbl: ExtractionLabel, val target: Node)

    private fun unroll(root: ConcreteNode, graph: DirectedGraph<Node, ExtractionLabel>): ArrayList<FakeNode> {
        val fklist = ArrayList<FakeNode>()

        graph.vertexSet().parallelStream().forEach { node ->
            node.run {
                if (node is ConcreteNode && graph.incomingEdgesOf(node).size > 1) {
                    val fk = FakeNode(generateProcedureName(), node)
                    graph.addVertex(fk)
                    fklist.add(fk)
                    val labels = graph.outgoingEdgesOf(node)

                    val targets = ArrayList<LabelTarget>()
                    labels.forEach { label -> targets.add(LabelTarget(label, graph.getEdgeTarget(label))) }

                    graph.removeAllEdges(ArrayList<ExtractionLabel>(graph.outgoingEdgesOf(node)))
                    targets.forEach { s -> graph.addEdge(fk, s.target, s.lbl) }

                }
            }
        }


        if (graph.incomingEdgesOf(root).size == 1) {
            val fk = FakeNode(generateProcedureName(), root)
            graph.addVertex(fk)
            fklist.add(fk)
            val label = graph.outgoingEdgesOf(root).first()
            val target = graph.getEdgeTarget(label)
            graph.removeEdge(label)
            graph.addEdge(fk, target, label)

        }

        return fklist
    }

    private fun generateProcedureName(): String {
        return "X" + count.inc()
    }

    private fun buildGraph(nn: ConcreteNode, graph: DirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        val node = nn.copy()
        val n = node.nodenet.network

        for (p in n) {
            unfold(p.key, n)

            val findComm = findCommunication(p.key, n)

            if (findComm.sendreceive.isPresent || findComm.selectoffer.isPresent) {
                val (tgt, lbl) = getCommunication(n, findComm)
                if (isAllProceduresVisited(tgt)) {
                    lbl.flipped = true
                    wash(tgt)
                }

                /* case1*/
                val nodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt) && (nn.str).startsWith(i.str) }.findAny()
                if (!nodeInGraph.isPresent) {
                    val newnode = createNewNode(tgt, lbl, node, nn)
                    addNewNode(nn, newnode, lbl, graph)
                    return if (buildGraph(newnode, graph)) true else continue
                }
                /* case 2 */
                else {
                    if (n.values.filter { pr -> isfinite(pr.main) && !isterminated(pr) }.isEmpty()) {

                        if (addNewEdge(nn, nodeInGraph.get(), lbl, graph)) return true
                        else {
                            cleanNode(findComm, n, nn)
                        }
                    }
                    else {
                        cleanNode(findComm, n, nn)
                    }

                }

            } else if (findCondition(n)) {
                val (tgt1, lbl1, tgt2, lbl2) = getCondition(n)
                if (isAllProceduresVisited(tgt1)) {
                    wash(tgt1)
                    lbl1.flipped = true
                }
                if (isAllProceduresVisited(tgt2)) {
                    wash(tgt2)
                    lbl2.flipped = true
                }

                /* case4 */
                var thenNode: Node
                val tnodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt1) && (nn.str + "0").startsWith(i.str) }.findAny()
                if (!tnodeInGraph.isPresent) {
                    thenNode = createNewNode(tgt1, lbl1, node, nn)
                    addNewNode(nn, thenNode, lbl1, graph)
                    if (!buildGraph(thenNode, graph)) continue
                }
                /* case 5 */
                else {
                    thenNode = tnodeInGraph.get()
                    addNewEdge(nn, thenNode, lbl1, graph)
                }

                /* case 7 */
                var elseNode: Node
                val enodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt2) && (nn.str + "1").startsWith(i.str) }.findAny()
                if (!enodeInGraph.isPresent) {
                    elseNode = createNewNode(tgt2, lbl2, node, nn)
                    addNewNode(nn, elseNode, lbl2, graph)
                    if (!buildGraph(elseNode, graph)) continue
                }
                /* case 8 */
                else {
                    elseNode = enodeInGraph.get()
                    addNewEdge(nn, elseNode, lbl2, graph)
                }

                relabel(thenNode)
                relabel(elseNode)
                return true
            } else if (allTerminated(n)) {
                return true
            }
        }
        throw ProcessStarvationException("Process starvation at node" + node.toString())
    }

    private fun cleanNode(findComm: GetCommunication, n: network, nn: ConcreteNode) {
        val first = if (findComm.sendreceive.isPresent) findComm.sendreceive.get().first else findComm.selectoffer.get().first
        val second = if (findComm.sendreceive.isPresent) findComm.sendreceive.get().second else findComm.selectoffer.get().second

        val prk1 = (first.main as Interaction).process
        val prb1new = nn.nodenet.network.get(prk1)
        val prb1 = n[prk1]
        val prk2 = (second.main as Interaction).process
        val prb2new = nn.nodenet.network.get(prk2)
        val prb2 = n[prk2]

        prb1?.main = prb1new?.main?.copy()!!
        prb2?.main = prb2new?.main?.copy()!!
    }

    private fun isfinite(pr: Behaviour): Boolean {
        when (pr) {
            is ProcedureInvocationSP -> return false
            is TerminationSP -> return true
            is Sending -> return isfinite(pr.continuation)
            is Receiving -> return isfinite(pr.continuation)
            is SelectionSP -> return isfinite(pr.continuation)
            is Offering -> {
                pr.labels.forEach { label -> if (!isfinite(label.value)) return false }
                return true
            }
            is ConditionSP -> {
                return isfinite(pr.elseBehaviour) && isfinite(pr.thenBehaviour)
            }
        }
        return false
    }

    class ProcessStarvationException(s: String) : Throwable()

    private fun createNewNode(tgt: Network, lbl: ExtractionLabel, node: ConcreteNode, nn: ConcreteNode): ConcreteNode {
        var str = node.str
        when (lbl) {
            is ThenLabel -> str = node.str + "0"
            is ElseLabel -> str = node.str + "1"
        }

        return if (lbl.flipped) {
            ConcreteNode(tgt, "" + str, Bad(HashSet()))
        } else {

            val newb = HashSet<ConcreteNode>()
            node.bad.badset.mapTo(newb) { it.copy() }

            val newnode = ConcreteNode(tgt, "" + str, Bad(newb))
            newnode.bad.badset.add(nn)
            newnode
        }
    }

    private fun allTerminated(n: SortedMap<String, ProcessBehaviour>): Boolean {
        for (p in n) {
            if (!isterminated(p.value)) return false
        }
        return true
    }

    private fun isterminated(p: ProcessBehaviour): Boolean {
        return p.main is TerminationSP
    }

    private fun addNewNode(node: ConcreteNode, newnode: ConcreteNode, lbl: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>) {
        graph.addVertex(newnode)
        graph.addEdge(node, newnode, lbl)
        addToGlobalMap(node)
    }

    private fun addNewEdge(nn: ConcreteNode, newnode: ConcreteNode, lbl: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        val exstnode = checkPrefix(newnode)
        if (exstnode != null) {
            val l = checkLoop(nn, newnode, lbl, graph)
            if (l) {
                graph.addEdge(nn, newnode, lbl)
                return true

            } //else throw BadLoopException("Bad loop!")
        }
        return false
    }

    class BadLoopException(s: String) : Throwable()

    private fun relabel(n: ConcreteNode) {
        val key = n.str.dropLast(1)
        addToGlobalMap(ConcreteNode(n.nodenet, key, n.bad))
        removeFromGmap(n)
    }

    private fun removeFromGmap(n: ConcreteNode) {
        val rn = gmap[n.str]
        if (rn != null) {
            val nd = rn.find { i -> i == n }
            if (nd != null) {
                rn.remove(nd)
            }
        }
    }

    fun unfold(p: String, n: network): Boolean {
        val pb = n[p]
        return if (pb?.main is ProcedureInvocationSP) {
            val pi = pb.main as ProcedureInvocationSP
            val pn = pi.procedure
            val pd = pb.procedures[pn]
            pb.main = pd?.behaviour?.copy() ?: throw Exception("Can't unfold the process")
            markProcedure(pb.main, true)
            true
        } else false
    }

    private fun markProcedure(bh: Behaviour, b: Boolean) {
        when (bh) {
            is ProcedureInvocationSP -> {
                bh.visited = b
            }
            is ConditionSP -> {
                markProcedure(bh.thenBehaviour, b)
                markProcedure(bh.elseBehaviour, b)
            }
            is Offering -> {
                for (l in bh.labels) {
                    markProcedure(l.value, b)
                }
            }
            is Sending -> {
                markProcedure(bh.continuation, b)
            }
            is SelectionSP -> {
                markProcedure(bh.continuation, b)
            }
            is Receiving -> {
                markProcedure(bh.continuation, b)
            }
        }

    }

    private fun checkPrefix(n: ConcreteNode): ConcreteNode? {
        for (node in gmap) {
            if (node.key.startsWith(n.str)) return node.value.first()
        }
        return null
    }

    private fun checkLoop(nn: ConcreteNode, tnode: ConcreteNode, lbl: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        if (lbl.flipped) return true

        if (tnode.equals(nn)) return false

        if (!tnode.bad.badset.contains(nn)) {
            val nodeset = HashSet<ConcreteNode>()
            nodeset.addAll(nodeset)
            nodeset.add(nn)

            val tomark = HashSet<ConcreteNode>()
            tomark.addAll(recompute(tnode, graph, tomark))

            return true
        } else return false

    }

    private fun recompute(n: ConcreteNode, graph: DirectedGraph<ConcreteNode, ExtractionLabel>, tomark: HashSet<ConcreteNode>): HashSet<ConcreteNode> {
        val edges = graph.outgoingEdgesOf(n)
        for (e in edges) {
            if (!e.flipped) {
                val tn = graph.getEdgeTarget(e)
                tomark.add(tn)
                tomark.addAll(recompute(tn, graph, tomark))
            }
        }
        return tomark
    }


    private fun addToGlobalMap(node: ConcreteNode) {
        val gnodes = gmap[node.str]
        gnodes?.add(node) ?: let {
            val list = ArrayList<ConcreteNode>()
            list.add(node)
            gmap.put(node.str, list)
        }

    }

    data class GetCommunication(val sendreceive: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>), val selectoffer: (Optional<Pair<ProcessBehaviour, ProcessBehaviour>>))

    private fun findCommunication(p: String, n: network): GetCommunication {
        var communication: Optional<Pair<ProcessBehaviour, ProcessBehaviour>>
        communication = Optional.empty()

        val pb = n[p]

        if (pb?.main is Sending) {
            val receiving = n.values.stream().filter { j -> j.main is Receiving && p == (j.main as Receiving).process }.findFirst()
            if (receiving.isPresent) {
                communication = Optional.of(Pair(pb, receiving.get()))
            }
        } else if (pb?.main is Receiving) {
            val sending = n.values.stream().filter { j -> j.main is Sending && p == (j.main as Sending).process }.findFirst()
            if (sending.isPresent) {
                communication = Optional.of(Pair(sending.get(), pb))
            }
        }

        if (communication.isPresent) {
            return GetCommunication(communication, Optional.empty())
        } else {
            if (pb?.main is SelectionSP) {
                val offering = n.values.stream().filter { j -> j.main is Offering && p == (j.main as Offering).process }.findFirst()
                if (offering.isPresent) {
                    communication = Optional.of(Pair(pb, offering.get()))
                }
            } else if (pb?.main is Offering) {
                val selection = n.values.stream().filter { j -> j.main is SelectionSP && p == (j.main as SelectionSP).process }.findFirst()
                if (selection.isPresent) {
                    communication = Optional.of(Pair(selection.get(), pb))
                }
            }
        }
        return GetCommunication(Optional.empty(), communication)
    }

    private fun getCommunication(n: network, findComm: GetCommunication): Pair<Network, ExtractionLabel> {
        when {
            findComm.sendreceive.isPresent -> {
                val sending = findComm.sendreceive.get().first
                val receiving = findComm.sendreceive.get().second

                val pbl = TreeMap<String, ProcessBehaviour>(n)

                val receivingProcess = (sending.main as Sending).process
                val sendingProcess = (receiving.main as Receiving).process

                pbl.replace(receivingProcess, ProcessBehaviour(receiving.procedures, (receiving.main as Receiving).continuation))
                pbl.replace(sendingProcess, ProcessBehaviour(sending.procedures, (sending.main as Sending).continuation))

                val label = InteractionLabel(sendingProcess, receivingProcess, (sending.main as Sending).expression)

                return Pair(Network(pbl), label)

            }
            findComm.selectoffer.isPresent -> {
                val selection = findComm.selectoffer.get().first
                val offering = findComm.selectoffer.get().second

                val pbl = TreeMap<String, ProcessBehaviour>(n)

                val selectionProcess = (offering.main as Offering).process
                val offeringProcess = (selection.main as SelectionSP).process

                val offeringBehavior = (offering.main as Offering).labels[(selection.main as SelectionSP).expression]
                        ?: throw Exception("Trying to select the labal that wasn't offered")

                pbl.replace(offeringProcess, ProcessBehaviour(offering.procedures, offeringBehavior))
                pbl.replace(selectionProcess, ProcessBehaviour(selection.procedures, (selection.main as SelectionSP).continuation))

                val label = SelectionLabel(offeringProcess, selectionProcess, (selection.main as SelectionSP).expression)

                return Pair(Network(pbl), label)

            }
            else -> throw IllegalArgumentException("getCommunication invoked, but no communication found")
        }
    }

    private fun findCondition(n: network): Boolean {
        return !n.values.none { i -> i.main is ConditionSP }
    }

    data class ResultCondition(val tmp1: Network, val lb1: ThenLabel, val tmp2: Network, val lbl2: ElseLabel)

    private fun getCondition(n: network): ResultCondition {
        val c = n.entries.stream().filter { i -> i.value.main is ConditionSP }.findFirst().get()
        val process = c.key
        val conditionPB = c.value
        val conditionMain = conditionPB.main as ConditionSP

        val pbelsemap = TreeMap<String, ProcessBehaviour>(n)
        val pbthenmap = TreeMap<String, ProcessBehaviour>(n)

        pbelsemap.replace(process, ProcessBehaviour(conditionPB.procedures, conditionMain.elseBehaviour))
        pbthenmap.replace(process, ProcessBehaviour(conditionPB.procedures, conditionMain.thenBehaviour))

        return ResultCondition(Network(pbthenmap), ThenLabel(process, conditionMain.expression), Network(pbelsemap), ElseLabel(process, conditionMain.expression))
    }

    private fun isAllProceduresVisited(n: Network): Boolean {
        if (!n.network.values.stream().filter { i -> i.main is ProcedureInvocationSP }.findAny().isPresent) return false

        return n.network.values
                .map { it.main }
                .none { it is ProcedureInvocationSP && !it.visited }
    }

    private fun wash(n: Network) {
        n.network.values.forEach { b -> markProcedure(b.main, false) }
    }
}
