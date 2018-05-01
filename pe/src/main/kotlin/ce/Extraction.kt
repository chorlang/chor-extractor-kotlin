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
import org.apache.logging.log4j.LogManager
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

typealias network = HashMap<String, ProcessBehaviour>

class NetworkExtraction {
    private val log = LogManager.getLogger()

    //region Main
    /**
     * entry point for choreography extraction algorithm:
     * 1. build graph with nodes as networks and edges as choreography actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading choreography actions
     * @param n a network from which a choreography will be extracted
     * @return Program representation of resulted choreography
     */
    fun extract(n: Network, s: Strategy): Program {
        val graph = DefaultDirectedGraph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val node = ConcreteNode(n, "0", Random().nextInt(), ArrayList())
        graph.addVertex(node)
        addToGmap(node)

        buildGraph(node, graph as DirectedGraph<ConcreteNode, ExtractionLabel>, s)

        val fklist = unrollGraph(node, graph as DirectedGraph<Node, ExtractionLabel>)
        return buildChoreography(node, fklist, graph)
    }

    private fun buildGraph(root_node: ConcreteNode, graph: DirectedGraph<ConcreteNode, ExtractionLabel>, strategy: Strategy): Boolean {
        //val node = root_node.copy()
        val unfolded = HashSet<String>() //Storing unfolded procedures
        val node_net_sorted = sortProcesses(root_node, strategy) //Sorting processes by the strategy passed from the outside

        //region Try to find a single-action communication
        for (p in node_net_sorted) {

            //if the process has procedure invocation on top, try to unfold it
            if (unfold(p.key, p.value)) unfolded.add(p.key)

            val findComm = findCommunication(p.key, node_net_sorted)
            if (findComm != null) {
                val (tgt, lbl) = getCommunication(node_net_sorted, findComm)

                //remove processes that were unfolded but don't participate in the current communication
                when (lbl) {
                    is InteractionLabel -> {
                        unfolded.remove(lbl.rcv)
                        unfolded.remove(lbl.snd)
                    }
                }
                unfolded.forEach { tgt.network[it]?.main = root_node.nodenet.network[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (isAllProceduresVisited(tgt.network)) {
                    lbl.flipped = true
                    wash(tgt)
                }

                /* case1*/
                val nodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt) && (root_node.str).startsWith(i.str) }.findAny()
                if (!nodeInGraph.isPresent) {
                    val newnode = createNewNode(tgt, lbl, root_node)
                    addNewNode(root_node, newnode, lbl, graph)
                    log.debug(lbl)
                    return if (buildGraph(newnode, graph, strategy)) true else continue
                }
                /* case 2 */
                else if (node_net_sorted.values.none { pr -> isfinite(pr.main) && !isterminated(pr) }) {

                    if (addNewEdge(root_node, nodeInGraph.get(), lbl, graph)) return true
                    else {
                        cleanNode(findComm, node_net_sorted, root_node)
                        unfolded.forEach{u -> unfold(u, node_net_sorted[u]!!)}
                    }
                } else {
                    cleanNode(findComm, node_net_sorted, root_node)
                    unfolded.forEach{u -> unfold(u, node_net_sorted[u]!!)}
                }

            } else if (findCondition(node_net_sorted)) {
                val (tgt1, lbl1, tgt2, lbl2) = getCondition(node_net_sorted)

                unfolded.remove(lbl1.process)

                unfolded.forEach { tgt1.network[it]?.main = root_node.nodenet.network[it]?.main?.copy()!! }
                unfolded.forEach { tgt2.network[it]?.main = root_node.nodenet.network[it]?.main?.copy()!! }

                if (isAllProceduresVisited(tgt1.network)) {
                    wash(tgt1)
                    lbl1.flipped = true
                }
                if (isAllProceduresVisited(tgt2.network)) {
                    wash(tgt2)
                    lbl2.flipped = true
                }

                /* case4 */
                var thenNode: Node
                val tnodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt1) && (root_node.str + "0").startsWith(i.str) }.findAny()
                if (!tnodeInGraph.isPresent) {
                    thenNode = createNewNode(tgt1, lbl1, root_node)
                    addNewNode(root_node, thenNode, lbl1, graph)
                    log.debug(lbl1)
                    if (!buildGraph(thenNode, graph, strategy)) continue
                }
                /* case 5 */
                else {
                    thenNode = tnodeInGraph.get()
                    addNewEdge(root_node, thenNode, lbl1, graph)
                }

                /* case 7 */
                var elseNode: Node
                val enodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(tgt2) && (root_node.str + "1").startsWith(i.str) }.findAny()
                if (!enodeInGraph.isPresent) {
                    elseNode = createNewNode(tgt2, lbl2, root_node)
                    addNewNode(root_node, elseNode, lbl2, graph)
                    log.debug(lbl2)
                    if (!buildGraph(elseNode, graph, strategy)) continue
                }
                /* case 8 */
                else {
                    elseNode = enodeInGraph.get()
                    addNewEdge(root_node, elseNode, lbl2, graph)
                }

                relabel(thenNode)
                relabel(elseNode)
                return true
            } else if (allTerminated(node_net_sorted)) {
                return true
            }
        }
        //endregion
        //region Try to find a multi-action communication

        for (p in node_net_sorted) {

            //val tmp_node = node.copy()
            //val tmp_node_net = tmp_node.nodenet.network

            val actions = ArrayList<InteractionLabel>()
            val waiting = ArrayList<InteractionLabel>()
            val b = createInteractionLabel(p.key, node_net_sorted)
            if (b != null) waiting.add(b)


            while (!waiting.isEmpty()) {
                val lbl = waiting.first()
                waiting.remove(lbl)
                actions.add(lbl)

                val receiver = lbl.rcv
                val sender = lbl.snd

                val rcv_pb = node_net_sorted[receiver] //val rcv_pb = tmp_node_net[receiver]

                //fill the list of waiting actions with sending/selection actions from the receiver process behaviour if it is in correct format
                //return the new receiver pb if success and null otherwise. waiting list is populated implicitly
                val new_rcv_b = fillWaiting(waiting, actions, lbl, rcv_pb!!.main, node_net_sorted[receiver]!!.procedures)
                if (new_rcv_b!=null) {
                    val snd_pb = node_net_sorted[sender] //val snd_pb = tmp_node_net[sender]
                    val snd_pb_main = snd_pb!!.main

                    node_net_sorted.replace(receiver, ProcessBehaviour(rcv_pb.procedures, new_rcv_b)) // tmp_node_net.replace(receiver, ProcessBehaviour(rcv_pb.procedures, new_rcv_b))
                    val snd_cont = if (snd_pb_main is SendingSP) snd_pb_main.continuation else if (snd_pb_main is SelectionSP) snd_pb_main.continuation else throw UnsupportedOperationException()

                    node_net_sorted.replace(sender, ProcessBehaviour(snd_pb.procedures, snd_cont)) // tmp_node_net.replace(sender, ProcessBehaviour(snd_pb.procedures, snd_cont))
                }
            }

            //if we managed to collect some actions for a multicom
            if (actions.size >= 2) {
                //create label
                val lbl = MulticomLabel(actions)

                //fold back unfolded procedures that were not participating in communication
                lbl.labels.forEach { l ->
                    unfolded.remove(l.rcv)
                    unfolded.remove(l.snd)
                }
                unfolded.forEach { node_net_sorted[it]?.main = root_node.nodenet.network[it]?.main?.copy()!! } // unfolded.forEach { tmp_node_net[it]?.main = root_node.nodenet.network[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (isAllProceduresVisited(node_net_sorted)) {
                    lbl.flipped = true
                    wash(Network(node_net_sorted))
                }

                //add new edge or node to the graph
                val nodeInGraph = graph.vertexSet().stream().filter { i -> i.nodenet.equals(Network(node_net_sorted)) && (root_node.str).startsWith(i.str) }.findAny()
                if (!nodeInGraph.isPresent) {
                    val newnode = createNewNode(Network(node_net_sorted), lbl, root_node)
                    addNewNode(root_node, newnode, lbl, graph)
                    log.debug(lbl)
                    return if (buildGraph(newnode, graph, strategy)) true else continue
                }
                else {
                    if (node_net_sorted.values.filter { pr -> isfinite(pr.main) && !isterminated(pr) }.isEmpty()) {

                        if (addNewEdge(root_node, nodeInGraph.get(), lbl, graph)) return true
                        /*else {
                            cleanNode(findComm, node_net_sorted, root_node)
                            TODO ("replace with tmp_node")
                        }*/
                    } /*else {
                                cleanNode(findComm, node_net_sorted, root_node)
                                TODO ("replace with tmp_node")
                            }*/
                }
            }

        }
        //endregion
        //region Throw exception, if there is no possible actions
        throw ProcessStarvationException("Process starvation at node" + root_node.toString())
        //endregion
    }

    private fun fillWaiting(waiting: ArrayList<InteractionLabel>, actions: ArrayList<InteractionLabel>, lbl: InteractionLabel, rcv_pb_main: Behaviour, rcv_proc: HashMap<String, ProcedureDefinitionSP>): Behaviour?
    {
        when (rcv_pb_main){
            is SendingSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val snd_cont = fillWaiting(waiting, actions, lbl, rcv_pb_main.continuation, rcv_proc)

                if (snd_cont!=null){
                    val new_lbl = SendingLabel(lbl.rcv, rcv_pb_main.pr, rcv_pb_main.expression)
                    if (!actions.contains(new_lbl)) waiting.add(new_lbl)
                    return SendingSP(snd_cont, new_lbl.rcv, new_lbl.expr)
                } else {
                    return null
                }
            }
            is SelectionSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val sel_cont = fillWaiting(waiting, actions, lbl, rcv_pb_main.continuation, rcv_proc)

                if (sel_cont!=null){
                    val new_lbl = SelectionLabel(lbl.rcv, rcv_pb_main.pr, rcv_pb_main.expression)
                    if (!actions.contains(new_lbl)) waiting.add(new_lbl)
                    return SelectionSP(sel_cont, lbl.rcv, lbl.expr)
                }
            }
            is ReceivingSP -> if (lbl.snd == rcv_pb_main.pr && lbl is SendingLabel) return rcv_pb_main.continuation

            is OfferingSP -> if (lbl.snd == rcv_pb_main.pr && lbl is SelectionLabel) return rcv_pb_main.labels[lbl.label]!!

            is ProcedureInvocationSP -> {
                val new_pb = ProcessBehaviour(rcv_proc, rcv_pb_main)
                if (!rcv_pb_main.visited) unfold(lbl.rcv, new_pb)
                return fillWaiting(waiting, actions, lbl, new_pb.main, rcv_proc)
            }
        }
        return null
    }

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
                        if (p.value.main !is TerminationSP)
                            throw Exception("Bad graph. No more edges found, but not all processes were terminated.")
                        else return Termination()
                    }

                }

            }
            1 -> {
                val e = edges.first()
                return when (e) {

                    is SendingLabel -> {
                        val com = Communication(e.sender, e.receiver, e.expression)
                        CommunicationSelection(com, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    is SelectionLabel -> {
                        val sel = Selection(e.receiver, e.sender, e.label)
                        CommunicationSelection(sel, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    is MulticomLabel -> {
                        val act = ArrayList<Interaction>()
                        for (l in e.labels) {
                            when (l) {
                                is SelectionLabel -> {
                                    act.add(Selection(l.snd, l.rcv, l.label))
                                }
                                is SendingLabel -> {
                                    act.add(Communication(l.snd, l.rcv, l.expr))
                                }
                                else -> throw NotImplementedError()
                            }
                        }
                        Multicom(act, bh(graph.getEdgeTarget(e), graph, fklist))
                    }

                    else -> throw Exception("Unexpected label type, can't build choreography")
                }
            }
            2 -> {
                val ef = edges.first()
                val el = edges.last()

                return when (ef) {
                    is ThenLabel -> Condition(ef.process, ef.expression, bh(graph.getEdgeTarget(ef), graph, fklist), bh(graph.getEdgeTarget(el), graph, fklist))
                    is ElseLabel -> Condition(ef.process, ef.expression, bh(graph.getEdgeTarget(el), graph, fklist), bh(graph.getEdgeTarget(ef), graph, fklist))
                    else -> throw Exception("Bad graph. Was waiting for conditional edges, but got unexpected type.")
                }
            }
        }
        return Termination()
    }
    //endregion
    //region Unfold
    private fun unfold(p: String, pb: ProcessBehaviour): Boolean {
        val pb_main = pb.main

        return if (pb_main is ProcedureInvocationSP) {

            val pr = pb_main.procedure
            val pr_def = pb.procedures[pr]

            pb.main = pr_def?.behaviour?.copy() ?: throw Exception("Can't unfold the process") //TODO("meaningful exception for unfold")
            markProcedure(pb_main, pr, pb.procedures, true)

            if (pr_def.behaviour is ProcedureInvocationSP) {
                unfold(p, pb)
            }
            true
        } else false
    }
    private fun wash(n: Network) {
        n.network.values.forEach { b -> unmarkProcedure(b.main) }
    }
    //endregion
    //region Procedures marking and checking
    private fun markProcedure(bh: Behaviour, pr: String, pr_def_list: HashMap<String, ProcedureDefinitionSP>, b: Boolean) {
        when (bh) {
            is ProcedureInvocationSP -> {
                //if (bh.procedure.equals(pr)) bh.visited = b
                if (bh.visited != b) {
                    bh.visited = b
                    //mark procedures iteratively
                    markProcedure(pr_def_list[pr]!!.behaviour, bh.procedure, pr_def_list, b)
                }

            }
            is ConditionSP -> {
                markProcedure(bh.thenBehaviour, pr, pr_def_list, b)
                markProcedure(bh.elseBehaviour, pr, pr_def_list, b)
            }
            is OfferingSP -> {
                for (l in bh.labels) {
                    markProcedure(l.value, pr, pr_def_list, b)
                }
            }
            is SendingSP -> {
                markProcedure(bh.continuation, pr, pr_def_list, b)
            }
            is SelectionSP -> {
                markProcedure(bh.continuation, pr, pr_def_list, b)
            }
            is ReceivingSP -> {
                markProcedure(bh.continuation, pr, pr_def_list, b)
            }
        }

    }
    private fun unmarkProcedure(bh: Behaviour) {
        when (bh) {
            is ProcedureInvocationSP -> {
                bh.visited = false
            }
            is ConditionSP -> {
                unmarkProcedure(bh.thenBehaviour)
                unmarkProcedure(bh.elseBehaviour)
            }
            is OfferingSP -> {
                for (l in bh.labels) {
                    unmarkProcedure(l.value)
                }
            }
            is SendingSP -> {
                unmarkProcedure(bh.continuation)
            }
            is SelectionSP -> {
                unmarkProcedure(bh.continuation)
            }
            is ReceivingSP -> {
                unmarkProcedure(bh.continuation)
            }
        }

    }
    private fun isAllProceduresVisited(n: network): Boolean {
        return n.values.all{isProcedureVisited(it.main)}
    }
    private fun isProcedureVisited(bh: Behaviour): Boolean {
        return when (bh) {
            is SendingSP -> isProcedureVisited(bh.continuation)
            is ReceivingSP -> isProcedureVisited(bh.continuation)
            is SelectionSP -> isProcedureVisited(bh.continuation)
            is OfferingSP -> bh.labels.all{isProcedureVisited(it.component2())}
            is ConditionSP -> isProcedureVisited(bh.thenBehaviour) && isProcedureVisited(bh.elseBehaviour)
            is ProcedureInvocationSP -> bh.visited
            is TerminationSP -> true
            else -> false
        }
    }
    //endregion
    //region Termination checks
    private fun isfinite(pr: Behaviour): Boolean {
        when (pr) {
            is ProcedureInvocationSP -> return false
            is TerminationSP -> return true
            is SendingSP -> return isfinite(pr.continuation)
            is ReceivingSP -> return isfinite(pr.continuation)
            is SelectionSP -> return isfinite(pr.continuation)
            is OfferingSP -> {
                pr.labels.forEach { label -> if (!isfinite(label.value)) return false }
                return true
            }
            is ConditionSP -> {
                return isfinite(pr.elseBehaviour) && isfinite(pr.thenBehaviour)
            }
        }
        return false
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
    //endregion
    //region Creating interaction and condition nodes
    private fun findCommunication(p: String, n: network): GetCommunication? {
        val pb = n[p]

        val pbm = pb?.main
        when (pbm) {
            is SendingSP -> {
                val receivpr = n[pbm.process]
                if (receivpr != null && receivpr.main is ReceivingSP && (receivpr.main as ReceivingSP).pr == p) {
                    return SendReceive(pb, receivpr)

                }
            }

            is ReceivingSP -> {
                val sendpr = n[(pb.main as ReceivingSP).process]
                if (sendpr != null && sendpr.main is SendingSP && (sendpr.main as SendingSP).pr == p) {
                    return SendReceive(sendpr, pb)
                }
            }

            is SelectionSP -> {
                val offer = n[pbm.process]
                if (offer != null && offer.main is OfferingSP && (offer.main as OfferingSP).pr == p) {
                    return SelectOffer(pb, offer)
                }
            }

            is OfferingSP -> {
                val select = n[pbm.process]
                if (select != null && select.main is SelectionSP && (select.main as SelectionSP).pr == p) {
                    return SelectOffer(select, pb)
                }
            }

        }
        return null
    }

    private fun getCommunication(n: network, findComm: GetCommunication): GraphNode {
        when (findComm) {
            is SendReceive -> {
                val pbl = network()
                n.forEach { key, value -> pbl.put(""+key, value.copy()) }

                val snd_prb = findComm.sender
                val rcv_prb = findComm.receiver

                val rcv_pr = (snd_prb.main as SendingSP).pr
                val snd_pr = (rcv_prb.main as ReceivingSP).pr

                pbl.replace(rcv_pr, ProcessBehaviour(rcv_prb.procedures, (rcv_prb.main as ReceivingSP).continuation))
                pbl.replace(snd_pr, ProcessBehaviour(snd_prb.procedures, (snd_prb.main as SendingSP).continuation))

                val label = SendingLabel(snd_pr, rcv_pr, (snd_prb.main as SendingSP).expression)

                return GraphNode(Network(pbl), label)
            }

            is SelectOffer -> {
                val pbl = HashMap<String, ProcessBehaviour>(n)

                val selection = findComm.select
                val offering = findComm.offer

                val selectionProcess = (offering.main as OfferingSP).process
                val offeringProcess = (selection.main as SelectionSP).process

                val offeringBehavior = (offering.main as OfferingSP).labels[(selection.main as SelectionSP).expression]
                        ?: throw Exception("Trying to select the labal that wasn't offered")

                pbl.replace(offeringProcess, ProcessBehaviour(offering.procedures, offeringBehavior))
                pbl.replace(selectionProcess, ProcessBehaviour(selection.procedures, (selection.main as SelectionSP).continuation))

                val label = SelectionLabel(offeringProcess, selectionProcess, (selection.main as SelectionSP).expression)

                return GraphNode(Network(pbl), label)
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
    //endregion
    //region Manipulations with nodes
    private fun createNewNode(tgt: Network, lbl: ExtractionLabel, node: ConcreteNode): ConcreteNode {
        var str = node.str
        when (lbl) {
            is ThenLabel -> str = node.str + "0"
            is ElseLabel -> str = node.str + "1"
        }

        return if (lbl.flipped) {
            ConcreteNode(tgt, "" + str, Random().nextInt(), ArrayList())
        } else {

            //node.bad.badset.mapTo(newb) { it.copy() }
            val newnode = ConcreteNode(tgt, "" + str, Random().nextInt(), node.bad.clone() as ArrayList<Int>)
            newnode.bad.add(node.id)
            newnode
        }
    }

    private fun addNewNode(node: ConcreteNode, newnode: ConcreteNode, lbl: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>) {
        graph.addVertex(newnode)
        graph.addEdge(node, newnode, lbl)
        addToGmap(node)
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
    //endregion
    //region Checkloop and gmap manipulations
    private fun checkLoop(source_node: ConcreteNode, target_node: ConcreteNode, lbl: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        if (lbl.flipped) return true

        if (target_node.equals(source_node)) return false

        // if (!target_node.bad.badset.contains(source_node)) {
        if (!source_node.bad.contains(target_node.id)) {
            val nodeset = HashSet<ConcreteNode>()
            nodeset.addAll(nodeset)
            nodeset.add(source_node)

            val tomark = HashSet<ConcreteNode>()
            tomark.addAll(recompute(target_node, graph, tomark))

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
    private fun relabel(n: ConcreteNode) {
        val key = n.str.dropLast(1)
        addToGmap(ConcreteNode(n.nodenet, key, n.id, n.bad))
        removeFromGmap(n)
    }
    private fun checkPrefix(n: ConcreteNode): ConcreteNode? {
        for (node in gmap) {
            if (node.key.startsWith(n.str)) return node.value.first()
        }
        return null
    }

    private var gmap = HashMap<String, ArrayList<ConcreteNode>>() //global map of processes used in bad loop calculations
    private fun removeFromGmap(n: ConcreteNode) {
        val rn = gmap[n.str]
        if (rn != null) {
            val nd = rn.find { i -> i == n }
            if (nd != null) {
                rn.remove(nd)
            }
        }
    }
    private fun addToGmap(node: ConcreteNode) {
        val gnodes = gmap[node.str]
        gnodes?.add(node) ?: let {
            val list = ArrayList<ConcreteNode>()
            list.add(node)
            gmap.put(node.str, list)
        }

    }
    //endregion
    //region Exceptions
    class ProcessStarvationException(s: String) : Throwable()
    //endregion
    //region Data classes and interfaces
    data class LabelTarget(val lbl: ExtractionLabel, val target: Node)
    interface GetCommunication
    data class SendReceive(val sender: ProcessBehaviour, val receiver: ProcessBehaviour) : GetCommunication
    data class SelectOffer(val select: ProcessBehaviour, val offer: ProcessBehaviour) : GetCommunication
    interface Node
    data class ConcreteNode(val nodenet: Network, val str: String, val id: Int, val bad: ArrayList<Int>) : Node {
        fun copy(): ConcreteNode {
            val newnet = nodenet.copy()
            val newb = ArrayList<Int>()

            bad.forEach { newb.add(it) }

            return ConcreteNode(newnet, "" + str, id, newb)
        }

        fun equals(other: ConcreteNode): Boolean{
            return nodenet.equals(other.nodenet) && str == other.str && bad.equals(other.bad)
        }

        override fun toString(): String = ""
    }
    /*data class Bad(val badset: HashSet<ConcreteNode>){
        fun copy(): Bad {
            val newb = HashSet<ConcreteNode>()
            for (b in badset){
                newb.add(b.copy())
            }
            return Bad(newb)
        }

        fun equals(other: Bad): Boolean {
            if (badset.size != other.badset.size) return false
            return true
        }

        fun contains(node: ConcreteNode): Boolean {
            for (b in badset){
                if (b.equals(node)) return true
            }
            return false
        }
    }*/
    data class GraphNode(val target: Network, val label: ExtractionLabel)
    data class FakeNode(val procedureName: String, val source: Node) : Node
    //endregion
    //region Utils
    @Volatile
    private var count = 0
    private fun generateProcedureName(): String {
        return "X" + count.inc()
    }
    private fun sortProcesses(node: ConcreteNode, strategy: Strategy): HashMap<String, ProcessBehaviour> {
        val net = node.nodenet.network
        val copynet = LinkedHashMap<String, ProcessBehaviour>()

        //put Selection/OfferingSP on top
        net.forEach { pr -> if (pr.value.main is SelectionSP || pr.value.main is OfferingSP) copynet.put("" + pr.key, pr.value.copy()) }
        net.forEach { pr -> copynet.put("" + pr.key, pr.value.copy()) }

        return copynet
    }
    private fun createInteractionLabel(p: String, nodesorted: HashMap<String, ProcessBehaviour>): InteractionLabel? {
        val pmain = nodesorted.get(p)?.main
        when (pmain) {
            is SendingSP -> {
                return SendingLabel(p, pmain.pr, pmain.expression)
            }
            is SelectionSP -> {
                return SelectionLabel(p, pmain.pr, pmain.expression)
            }
            else -> return null
        }
    }
    //endregion
}
