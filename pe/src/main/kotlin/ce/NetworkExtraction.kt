package ce

import ast.cc.interfaces.CCNode
import ast.sp.nodes.interfaces.Behaviour
import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.*
import ast.sp.nodes.*
import ast.cc.interfaces.Choreography
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.nodes.interfaces.InteractionSP
import ast.cc.nodes.Communication
import ast.sp.labels.interfaces.InteractionLabel
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

typealias network = HashMap<String, ProcessBehaviour>

class NetworkExtraction {
    private var gmap = HashMap<String, ArrayList<ConcreteNode>>()

    @Volatile
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

        val fklist = unrollGraph(node, graph as DirectedGraph<Node, ExtractionLabel>)
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

                    is SendingLabel -> {
                        Communication(e.sender, e.receiver, e.expression, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    is SelectionLabel -> {
                        Selection(e.receiver, e.sender, e.label, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    is MulticomLabel -> {
                        val act = ArrayList<CCNode>()
                        for (l in e.labels){
                        }
                        Multicom(act)
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

    private fun unrollGraph(root: ConcreteNode, graph: DirectedGraph<Node, ExtractionLabel>): ArrayList<FakeNode> {
        val fklist = ArrayList<FakeNode>()

        var c = 1
        val rnodes = HashMap<ConcreteNode, String>()


        graph.vertexSet().forEach { node -> if (node is ConcreteNode && graph.incomingEdgesOf(node).size > 1) rnodes.put(node, c++.toString()) }

        rnodes.forEach { node ->
            node.run {
                val fk = FakeNode("X" + node.value, node.key)
                graph.addVertex(fk)
                fklist.add(fk)
                val labels = graph.outgoingEdgesOf(node.key)

                val targets = ArrayList<LabelTarget>()
                labels.forEach { label -> targets.add(LabelTarget(label, graph.getEdgeTarget(label))) }

                graph.removeAllEdges(ArrayList<ExtractionLabel>(graph.outgoingEdgesOf(node.key)))
                targets.forEach { s -> graph.addEdge(fk, s.target, s.lbl) }
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

    private fun buildGraph(bldgrphnode: ConcreteNode, graph: DirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        val node = bldgrphnode.copy()

        val unfolded = HashSet<String>()
        //for (p in node.nodenet.network) {if (unfold(p.key, node.nodenet.network)) unfolded.add(p.key) }

        val nodesorted = sortProcesses(node)

        for (p in nodesorted) {
            if (unfold(p.key, nodesorted)) unfolded.add(p.key)

            val findComm = findCommunication(p.key, nodesorted)
            if (findComm!=null) {
                val (tgt, lbl) = getCommunication(nodesorted, findComm)

                when (lbl) {
                    is SendingLabel -> {
                        unfolded.remove(lbl.receiver)
                        unfolded.remove(lbl.sender)
                    }
                    is SelectionLabel -> {
                        unfolded.remove(lbl.receiver)
                        unfolded.remove(lbl.sender)
                    }
                }

                unfolded.forEach { tgt.network[it]?.main = bldgrphnode.nodenet.network[it]?.main?.copy()!! }

                if (isAllProceduresVisited(tgt)) {
                    lbl.flipped = true
                    wash(tgt)
                }

                /* case1*/
                val nodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt) && (bldgrphnode.str).startsWith(i.str) }.findAny()
                if (!nodeInGraph.isPresent) {
                    val newnode = createNewNode(tgt, lbl, node, bldgrphnode)
                    addNewNode(bldgrphnode, newnode, lbl, graph)
                    return if (buildGraph(newnode, graph)) true else continue
                }
                /* case 2 */
                else {
                    if (nodesorted.values.filter { pr -> isfinite(pr.main) && !isterminated(pr) }.isEmpty()) {

                        if (addNewEdge(bldgrphnode, nodeInGraph.get(), lbl, graph)) return true
                        else {
                            cleanNode(findComm, nodesorted, bldgrphnode)
                        }
                    } else {
                        cleanNode(findComm, nodesorted, bldgrphnode)
                    }

                }

            } else if (findCondition(nodesorted)) {
                val (tgt1, lbl1, tgt2, lbl2) = getCondition(nodesorted)

                /*unfolded.remove(lbl1.process)
                unfolded.remove(lbl2.process)
                unfolded.forEach { pr ->
                    val pp1 = tgt1.network[pr.key]
                    pp1?.main = pp1?.procedures?.get(pr.value)?.behaviour?.copy() ?: throw Exception()
                    val pp2 = tgt2.network[pr.key]
                    pp2?.main = pp2?.procedures?.get(pr.value)?.behaviour?.copy() ?: throw Exception()
                }*/

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
                val tnodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt1) && (bldgrphnode.str + "0").startsWith(i.str) }.findAny()
                if (!tnodeInGraph.isPresent) {
                    thenNode = createNewNode(tgt1, lbl1, node, bldgrphnode)
                    addNewNode(bldgrphnode, thenNode, lbl1, graph)
                    if (!buildGraph(thenNode, graph)) continue
                }
                /* case 5 */
                else {
                    thenNode = tnodeInGraph.get()
                    addNewEdge(bldgrphnode, thenNode, lbl1, graph)
                }

                /* case 7 */
                var elseNode: Node
                val enodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt2) && (bldgrphnode.str + "1").startsWith(i.str) }.findAny()
                if (!enodeInGraph.isPresent) {
                    elseNode = createNewNode(tgt2, lbl2, node, bldgrphnode)
                    addNewNode(bldgrphnode, elseNode, lbl2, graph)
                    if (!buildGraph(elseNode, graph)) continue
                }
                /* case 8 */
                else {
                    elseNode = enodeInGraph.get()
                    addNewEdge(bldgrphnode, elseNode, lbl2, graph)
                }

                relabel(thenNode)
                relabel(elseNode)
                return true
            } else if (allTerminated(nodesorted)) {
                return true
            } else { /* try to find multicom*/

                val tmpnode = node.copy()
                val tmpnodenet = tmpnode.nodenet.network

                for (pr in nodesorted){

                    val actions = ArrayList<InteractionLabel>()
                    val waiting = ArrayList<InteractionLabel>()
                    val b = getInteractionLabel(pr.key, nodesorted)
                    if (b!=null) waiting.add(b)


                    while (!waiting.isEmpty()){
                        val f = waiting.first()
                        waiting.remove(f)
                        actions.add(f)

                        val receiver = f.receiver
                        val sender = f.sender

                        val rcvpb = nodesorted[receiver]
                        if (rcvpb != null) populateWaiting(sender, receiver, rcvpb)?.filterNotTo(waiting) { actions.contains(it) }
                    }

                    if (actions.size >= 2){

                        val t = createMulticomNode(actions, tmpnodenet)
                        ConcreteNode(Network(t), tmpnode.str, tmpnode.bad)

                        val lbl = MulticomLabel(actions)
                        val net = node.nodenet.network
                        for (label in actions){
                            label.sender
                            label.receiver
                            net.get(label.sender)
                            net.get(label.receiver)

                        }

                        val nodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tmpnode) && (bldgrphnode.str).startsWith(i.str) }.findAny()

                        if (!nodeInGraph.isPresent) {
                            val newnode = createNewNode(tmpnode.nodenet, lbl, node, bldgrphnode)
                            addNewNode(bldgrphnode, newnode, lbl, graph)
                            return if (buildGraph(newnode, graph)) true else continue
                        }
                        /* case 2 */
                        else {
                            if (nodesorted.values.filter { pr -> isfinite(pr.main) && !isterminated(pr) }.isEmpty()) {

                                if (addNewEdge(bldgrphnode, nodeInGraph.get(), lbl, graph)) return true
                                /*else {
                                    cleanNode(findComm, nodesorted, bldgrphnode)
                                }*/
                            } /*else {
                                cleanNode(findComm, nodesorted, bldgrphnode)
                            }*/

                        }
                    } else return false

                }

                return true
            }
        }

        throw ProcessStarvationException("Process starvation at node" + node.toString())
    }

    private fun createMulticomNode(actions: ArrayList<InteractionLabel>, tmpnodenet: HashMap<String, ProcessBehaviour>) : HashMap<String, ProcessBehaviour> {
        for (a in actions) {
            val sndr = a.sender
            val sndrmain = tmpnodenet.get(sndr)?.main
            val sndrcont = (sndrmain as Sending).continuation

            val rcv = a.receiver



            tmpnodenet.replace(sndr, ProcessBehaviour(tmpnodenet.get(sndr)?.procedures!!, sndrcont))
            val rcvmain = tmpnodenet.get(rcv)?.main
            val rcvcont = when (rcvmain) {
                is Sending -> {
                    gotContinuation(rcvmain, sndr)
                }
                is SelectionSP -> {
                    gotContinuation(rcvmain, sndr)
                }
                is Receiving -> gotContinuation(rcvmain, sndr)
                else -> throw Exception("Nothing except selection and sending should be possible at this point")
            }

            tmpnodenet.replace(rcv, ProcessBehaviour(tmpnodenet.get(rcv)?.procedures!!, rcvcont))
        }
        return tmpnodenet
    }

    private fun gotContinuation(r: Behaviour?, sender: String): Behaviour {
        return when (r) {
            is Receiving -> {
                if (r.pr.equals(sender)) r.continuation
                else throw Exception("Nothing except selection and sending should be possible at this point")
            }
            is Sending -> {
                Sending(gotContinuation(r.continuation, sender), r.pr , r.expression)
            }
            is SelectionSP -> {
                SelectionSP(gotContinuation(r.continuation, sender), r.pr , r.expression)
            }
            else -> throw Exception("Nothing except selection and sending should be possible at this point")
        }
    }

    private fun populateWaiting(senderpr: String, receiverpr: String, receiverprb: ProcessBehaviour): ArrayList<InteractionLabel>? {
        val recprbmain = receiverprb.main
        val w = ArrayList<InteractionLabel>()
        when (recprbmain) {
            is Sending -> {
                w.add(SendingLabel(receiverpr, recprbmain.pr, recprbmain.expression))
            }
            is SelectionSP -> {
                w.add(SendingLabel(receiverpr, recprbmain.pr, recprbmain.expression))
            }
            is Receiving -> {
                return if (recprbmain.pr.equals(senderpr)) w else null
            }
            is ProcedureInvocationSP -> {
                return w
                TODO("recursive clause")
            }
            else -> return w
        }
        return w
    }

    private fun getInteractionLabel(p: String, nodesorted: HashMap<String, ProcessBehaviour>): InteractionLabel? {
        val pmain = nodesorted.get(p)?.main
        when (pmain) {
            is Sending ->{
                return SendingLabel(p, pmain.pr, pmain.expression)
            }
            is SelectionSP ->{
                return SelectionLabel(p, pmain.pr, pmain.expression)
            }
            else -> return null
        }
    }

    private fun sortProcesses(node: NetworkExtraction.ConcreteNode): HashMap<String, ProcessBehaviour> {
        val net = node.nodenet.network
        val copynet = LinkedHashMap<String, ProcessBehaviour>()

        net.forEach { pr -> if (pr.value.main is SelectionSP || pr.value.main is Offering) copynet.put(""+pr.key, pr.value.copy()) }
        net.forEach { pr -> copynet.put(""+pr.key, pr.value.copy()) }

        return copynet
        //return net.toSortedMap(CompareObjects)
    }

    //private fun foldUnfolded(p: String, tgt: Network, nn: ConcreteNode) {}

    private fun cleanNode(findComm: GetCommunication, n: network, nn: ConcreteNode) {
        val first = if (findComm is SendReceive) findComm.sender else (findComm as SelectOffer).select
        val second = if (findComm is SendReceive) findComm.receiver else (findComm as SelectOffer).offer

        val prk1 = (first.main as InteractionSP).process
        val prb1new = nn.nodenet.network.get(prk1)
        val prb1 = n[prk1]
        val prk2 = (second.main as InteractionSP).process
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

    private fun allTerminated(n: HashMap<String, ProcessBehaviour>): Boolean {
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
        val processbehaviour = n[p]
        return if (processbehaviour?.main is ProcedureInvocationSP) {
            val procedureInvocation = processbehaviour.main as ProcedureInvocationSP
            val procedurename = procedureInvocation.procedure
            val procedureDefinition = processbehaviour.procedures[procedurename]

            processbehaviour.main = procedureDefinition?.behaviour?.copy() ?: throw Exception("Can't unfold the process")
            markProcedure(processbehaviour.main, true)

            if (procedureDefinition.behaviour is ProcedureInvocationSP){
                unfold(p, n)
            }
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

    interface GetCommunication
    data class SendReceive (val sender: ProcessBehaviour, val receiver: ProcessBehaviour): GetCommunication
    data class SelectOffer (val select: ProcessBehaviour, val offer: ProcessBehaviour): GetCommunication

    private fun findCommunication(p: String, n: network): GetCommunication? {
        val pb = n[p]

        val pbm = pb?.main
        when (pbm){
            is Sending -> {
                val receivpr = n[pbm.process]
                if (receivpr != null && receivpr.main is Receiving && (receivpr.main as Receiving).pr == p) {
                    return SendReceive(pb, receivpr)

                }
            }

            is Receiving -> {
                val sendpr = n[(pb.main as Receiving).process]
                if (sendpr!= null && sendpr.main is Sending && (sendpr.main as Sending).pr == p){
                    return SendReceive(sendpr, pb)
                }
            }

            is SelectionSP -> {
                val offer = n[pbm.process]
                if (offer!=null && offer.main is Offering && (offer.main as Offering).pr == p){
                    return SelectOffer(pb, offer)
                }
            }

            is Offering -> {
                val select = n[pbm.process]
                if (select != null && select.main is SelectionSP && (select.main as SelectionSP).pr == p) {
                    return SelectOffer(select, pb)
                }
            }

        }
        return null
    }

    private fun getCommunication(n: network, findComm: GetCommunication): Pair<Network, ExtractionLabel> {
        when (findComm){
            is SendReceive -> {
                val pbl = HashMap<String, ProcessBehaviour>(n)

                val sendb = findComm.sender
                val receivb = findComm.receiver

                val receivpr = (sendb.main as Sending).pr
                val sendpr = (receivb.main as Receiving).pr

                pbl.replace(receivpr, ProcessBehaviour(receivb.procedures, (receivb.main as Receiving).continuation))
                pbl.replace(sendpr, ProcessBehaviour(sendb.procedures, (sendb.main as Sending).continuation))

                val label = SendingLabel(sendpr, receivpr, (sendb.main as Sending).expression)

                return Pair(Network(pbl), label)
            }

            is SelectOffer -> {
                val pbl = HashMap<String, ProcessBehaviour>(n)

                val selection = findComm.select
                val offering = findComm.offer

                val selectionProcess = (offering.main as Offering).process
                val offeringProcess = (selection.main as SelectionSP).process

                val offeringBehavior = (offering.main as Offering).labels[(selection.main as SelectionSP).expression]
                        ?: throw Exception("Trying to select the labal that wasn't offered")

                pbl.replace(offeringProcess, ProcessBehaviour(offering.procedures, offeringBehavior))
                pbl.replace(selectionProcess, ProcessBehaviour(selection.procedures, (selection.main as SelectionSP).continuation))

                val label = SelectionLabel(offeringProcess, selectionProcess, (selection.main as SelectionSP).expression)

                return Pair(Network(pbl), label)
            }

            else -> throw Exception("FindComm object doesn't belong to SendReceive or SelectOffer types")
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

        val pbelsemap = HashMap<String, ProcessBehaviour>(n)
        val pbthenmap = HashMap<String, ProcessBehaviour>(n)

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
