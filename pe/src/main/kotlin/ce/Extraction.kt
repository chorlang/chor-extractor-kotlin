package ce

import ast.cc.interfaces.CCNode
import ast.sp.nodes.interfaces.IBehaviour
import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.*
import ast.sp.nodes.*
import ast.cc.interfaces.Choreography
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.nodes.interfaces.ActionSP
import ast.cc.nodes.Communication
import ast.sp.labels.interfaces.InteractionLabel
import org.apache.logging.log4j.LogManager
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

typealias ProcessMap = HashMap<String, ProcessTerm>
typealias GraphNode = Pair<Network, InteractionLabel>

class NetworkExtraction {
    private val log = LogManager.getLogger()
    private var nodeIdCounter = 0

    //region Main
    /**
     * 1. build graph with nodes as networks and edges as choreography actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading choreography actions
     * @param n a processes from which a choreography will be extracted
     * @return Program representation of resulted choreography
     */
    private fun extract(n: Network, strategy: Strategy): Program {
        val graph = DefaultDirectedGraph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val marking = HashMap<ProcessName, Boolean>()

        n.processes.forEach({ name, term ->
            marking.put(name, term.main is TerminationSP)
        })

        val node = ConcreteNode(n, "0", nextNodeId(), ArrayList(), marking)
        graph.addVertex(node)
        addToChoicePathMap(node)

        buildGraph(node, graph as DirectedGraph<ConcreteNode, ExtractionLabel>, strategy)

        val fklist = unrollGraph(node, graph as DirectedGraph<Node, ExtractionLabel>)
        return buildChoreography(node, fklist, graph)
    }

    private fun nextNodeId(): Int {
        return nodeIdCounter++
    }

    companion object {
        fun run(n: Network, s: Strategy): Program {
            return NetworkExtraction().extract(preprocess(n), Strategy.SelectFirst)
        }

        private fun preprocess(network: Network): Network {
            val processes: HashMap<ProcessName, ProcessTerm>

            for (process in network.processes){
                for (procedure in process.component2().procedures){
                    if (procedure.component2() is TerminationSP){

                    }

                }

            }
            return network
        }
    }

    private fun buildGraph(currentNode: ConcreteNode, graph: DirectedGraph<ConcreteNode, ExtractionLabel>, strategy: Strategy): Boolean {
        //val node = currentNode.copy()
        val unfoldedProcesses = HashSet<String>() //Storing unfoldedProcesses processes
        val processes = sortProcesses(currentNode, strategy) //Sorting processes by the strategy passed from the outside
        //val processes = currentNode.network.copy().processes

        //region Try to find a single-action communication
        for (processPair in processes) {

            //if the processPair has procedure invocation on top, try to unfold it
            if (unfold(processPair.key, processes[processPair.key]!!)) unfoldedProcesses.add(processPair.key)

            val findComm = findCommunication(processPair.key, processes)
            if (findComm != null) {
                val (targetNetwork, label) = getCommunication(processes, findComm)

                //remove processes that were unfoldedProcesses but don't participate in the current communication
                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                marking.put(label.rcv, true)
                unfoldedProcesses.remove(label.rcv)
                marking.put(label.snd, true)
                unfoldedProcesses.remove(label.snd)

                // revert unfolding all processes not involved in the communication
                unfoldedProcesses.forEach { targetNetwork.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (marking.values.all { it }) {
                    label.flipped = true
                    wash(marking, targetNetwork.processes)
                }

                /* case 1 */
                val nodeInGraph = graph.vertexSet().stream().filter { it.network.equals(targetNetwork) && currentNode.choicePath.startsWith(it.choicePath) && it.marking.equals(marking) }.findFirst()
                if (!nodeInGraph.isPresent) {
                    val newNode = createNewNode(targetNetwork, label, currentNode, marking)
                    addNewNode(currentNode, newNode, label, graph)
                    log.debug(label)
                    return if (buildGraph(newNode, graph, strategy)) true else continue
                }
                /* case 2 */
                else if (addNewEdge(currentNode, nodeInGraph.get(), label, graph))
                    return true
                else {
                    cleanNode(findComm, processes, currentNode)
                    unfoldedProcesses.forEach { unfold(it, processes[it]!!) }
                }
            } else if (findCondition(processes)) {
                val (targetNodeThen, labelThen, targetNodeElse, labelElse) = getCondition(processes)

                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                if (unfoldedProcesses.contains(labelThen.process)) {
                    marking[labelThen.process] = true
                    unfoldedProcesses.remove(labelThen.process)
                }

                unfoldedProcesses.forEach {
                    targetNodeThen.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!!
                    targetNodeElse.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!!
                }

                //if all procedures were visited, flip all markings
                if (marking.values.all { it }) {
                    labelThen.flipped = true
                    labelElse.flipped = true
                    wash(marking, targetNodeThen.processes)
                    wash(marking, targetNodeElse.processes)
                }

                /* case4 */
                var nodeThen: Node
                val tnodeInGraph = graph.vertexSet().stream().filter { it.network.equals(targetNodeThen) && (currentNode.choicePath + "0").startsWith(it.choicePath) && it.marking.equals(marking) }.findAny()
                if (!tnodeInGraph.isPresent) {
                    nodeThen = createNewNode(targetNodeThen, labelThen, currentNode, marking)
                    addNewNode(currentNode, nodeThen, labelThen, graph)
                    log.debug(labelThen)
                    if (!buildGraph(nodeThen, graph, strategy)) continue
                }
                /* case 5 */
                else {
                    nodeThen = tnodeInGraph.get()
                    addNewEdge(currentNode, nodeThen, labelThen, graph)
                }

                /* case 7 */
                var nodeElse: Node
                val enodeInGraph = graph.vertexSet().stream().filter { it.network.equals(targetNodeElse) && (currentNode.choicePath + "1").startsWith(it.choicePath) && it.marking.equals(marking) }.findAny()
                if (!enodeInGraph.isPresent) {
                    nodeElse = createNewNode(targetNodeElse, labelElse, currentNode, marking)
                    addNewNode(currentNode, nodeElse, labelElse, graph)
                    log.debug(labelElse)
                    if (!buildGraph(nodeElse, graph, strategy)) continue
                }
                /* case 8 */
                else {
                    nodeElse = enodeInGraph.get()
                    addNewEdge(currentNode, nodeElse, labelElse, graph)
                }

                relabel(nodeThen)
                relabel(nodeElse)
                return true
            } else if (allTerminated(processes)) {
                return true
            }
        }
        //endregion
        //region Try to find a multi-action communication

        for (p in processes) {

            //val tmp_node = node.copy()
            //val tmp_node_net = tmp_node.processes.processes

            val actions = ArrayList<InteractionLabel>()
            val waiting = ArrayList<InteractionLabel>()
            val b = createInteractionLabel(p.key, processes)
            if (b != null) waiting.add(b)


            val receivers = ArrayList<String>()
            while (!waiting.isEmpty()) {
                val lbl = waiting.first()
                waiting.remove(lbl)
                //multicom can't contain actions with the same receiver
                if (receivers.contains(lbl.rcv)) throw MulticomException("multicom can't contain actions with the same receiver")
                actions.add(lbl)
                receivers.add(lbl.rcv)

                val receiver = lbl.rcv
                val sender = lbl.snd

                val rcv_pb = processes[receiver] //val rcv_pb = tmp_node_net[receiverTerm]

                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fill the list of waiting actions with sending/selection actions from the receiverTerm process behaviour if it is in correct format
                //return the new receiverTerm pb if success and null otherwise. waiting list is populated implicitly
                val new_rcv_b = fillWaiting(waiting, actions, lbl, rcv_pb!!.main, processes[receiver]!!.procedures, marking)
                if (new_rcv_b != null) {
                    val snd_pb = processes[sender] //val snd_pb = tmp_node_net[senderTerm]
                    val snd_pb_main = snd_pb!!.main

                    processes.replace(receiver, ProcessTerm(rcv_pb.procedures, new_rcv_b)) // tmp_node_net.replace(receiverTerm, ProcessTerm(rcv_pb.procedures, new_rcv_b))
                    val snd_cont = if (snd_pb_main is SendingSP) snd_pb_main.continuation else if (snd_pb_main is SelectionSP) snd_pb_main.continuation else throw UnsupportedOperationException()

                    processes.replace(sender, ProcessTerm(snd_pb.procedures, snd_cont)) // tmp_node_net.replace(senderTerm, ProcessTerm(snd_pb.procedures, snd_cont))
                }
            }

            //if we managed to collect some actions for a multicom
            if (actions.size >= 2) {
                val label = MulticomLabel(actions)

                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fold back unfoldedProcesses procedures that were not participating in communication
                label.labels.forEach {
                    marking.put(it.rcv, true)
                    unfoldedProcesses.remove(it.rcv)
                    marking.put(it.snd, true)
                    unfoldedProcesses.remove(it.snd)
                }
                unfoldedProcesses.forEach { processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! } // unfoldedProcesses.forEach { tmp_node_net[it]?.main = currentNode.processes.processes[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (marking.values.all { it }) {
                    label.flipped = true
                    wash(marking, processes)
                }

                //add new edge or node to the graph
                val nodeInGraph = graph.vertexSet().stream().filter { it.network.equals(Network(processes)) && (currentNode.choicePath).startsWith(it.choicePath) && it.marking.equals(marking) }.findAny()
                if (!nodeInGraph.isPresent) {
                    val newnode = createNewNode(Network(processes), label, currentNode, marking)
                    addNewNode(currentNode, newnode, label, graph)
                    log.debug(label)
                    return if (buildGraph(newnode, graph, strategy)) true else continue
                } else {
                    if (addNewEdge(currentNode, nodeInGraph.get(), label, graph)) return true
                    /*else {
                        cleanNode(findComm, processes, currentNode)
                        TODO ("replace with tmp_node")
                    }*/
                }
            }

        }
        //endregion
        //region Throw exception, if there is no possible actions
        throw NoPossibleActionsException("No possible actions at node" + currentNode.toString())
        //endregion
    }

    private fun fillWaiting(waiting: ArrayList<InteractionLabel>, actions: ArrayList<InteractionLabel>, lbl: InteractionLabel, rcv_pb_main: IBehaviour, rcv_proc: HashMap<String, IBehaviour>, marking: HashMap<ProcessName, Boolean>): IBehaviour? {
        when (rcv_pb_main) {
            is SendingSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val snd_cont = fillWaiting(waiting, actions, lbl, rcv_pb_main.continuation, rcv_proc, marking)

                if (snd_cont != null) {
                    val new_lbl = SendingLabel(lbl.rcv, rcv_pb_main.receiver, rcv_pb_main.expression)
                    if (!actions.contains(new_lbl)) waiting.add(new_lbl)
                    return SendingSP(snd_cont, new_lbl.rcv, new_lbl.expr)
                } else {
                    return null
                }
            }
            is SelectionSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val sel_cont = fillWaiting(waiting, actions, lbl, rcv_pb_main.continuation, rcv_proc, marking)

                if (sel_cont != null) {
                    val new_lbl = SelectionLabel(lbl.rcv, rcv_pb_main.receiver, rcv_pb_main.expression)
                    if (!actions.contains(new_lbl)) waiting.add(new_lbl)
                    return SelectionSP(sel_cont, lbl.rcv, lbl.expr)
                }
            }
            is ReceiveSP -> if (lbl.snd == rcv_pb_main.sender && lbl is SendingLabel) return rcv_pb_main.continuation

            is OfferingSP -> if (lbl.snd == rcv_pb_main.sender && lbl is SelectionLabel) return rcv_pb_main.branches[lbl.label]!!

            is ProcedureInvocationSP -> {
                val new_pb = ProcessTerm(rcv_proc, rcv_pb_main)
                if (!marking.get(lbl.rcv)!!) {
                    unfold(lbl.rcv, new_pb)
                    marking[lbl.rcv] = true
                }
                return fillWaiting(waiting, actions, lbl, new_pb.main, rcv_proc, marking)
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
                targets.forEach { target -> graph.addEdge(fk, target.target, target.lbl) }
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

                    for (p in node.network.processes) {
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
    private fun unfold(p: String, pb: ProcessTerm): Boolean {
        val pb_main = pb.main

        return if (pb_main is ProcedureInvocationSP) {

            val pr = pb_main.procedure
            val pr_def = pb.procedures[pr]

            pb.main = pr_def?.copy() ?: throw Exception("Can't unfold the process") //TODO("meaningful exception for unfold")
            //markProcedure(pb.main, true)

            if (pr_def is ProcedureInvocationSP) {
                unfold(p, pb)
            }
            true
        } else false
    }

    private fun wash(marking: HashMap<ProcessName, Boolean>, processes: ProcessMap) {
        val keys = marking.keys
        for (key in keys) {
            marking.put(key, processes[key]!!.main is TerminationSP)
        }
    }

    //endregion

    //region Termination checks
    private fun doesNotContainInvocations(pr: IBehaviour): Boolean {
        return !containsInvocation(pr)
    }

    private fun containsInvocation(pr: IBehaviour): Boolean {
        when (pr) {
            is ProcedureInvocationSP -> return true
            is TerminationSP -> return false
            is SendingSP -> return doesNotContainInvocations(pr.continuation)
            is ReceiveSP -> return doesNotContainInvocations(pr.continuation)
            is SelectionSP -> return doesNotContainInvocations(pr.continuation)
            is OfferingSP -> {
                pr.branches.forEach { label -> if (containsInvocation(label.value)) return true }
            }
            is ConditionSP -> {
                return containsInvocation(pr.elseBehaviour) || containsInvocation(pr.thenBehaviour)
            }
        }
        return false
    }

    private fun allTerminated(n: HashMap<String, ProcessTerm>): Boolean {
        for (p in n) {
            if (!isTerminated(p.value)) return false
        }
        return true
    }

    private fun isTerminated(p: ProcessTerm): Boolean {
        return p.main is TerminationSP
    }

    //endregion
    //region Creating interaction and condition nodes
    private fun findCommunication(processName: String, processMap: ProcessMap): GetCommunication? {
        val processTerm = processMap[processName]

        val mainBehaviour = processTerm?.main
        when (mainBehaviour) {
            is SendingSP -> {
                val receive = processMap[mainBehaviour.process]
                if (receive != null && receive.main is ReceiveSP && (receive.main as ReceiveSP).sender == processName) {
                    return SendReceive(processTerm, receive)
                }
            }

            is ReceiveSP -> {
                val send = processMap[(processTerm.main as ReceiveSP).process]
                if (send != null && send.main is SendingSP && (send.main as SendingSP).receiver == processName) {
                    return SendReceive(send, processTerm)
                }
            }

            is SelectionSP -> {
                val offer = processMap[mainBehaviour.process]
                if (offer != null && offer.main is OfferingSP && (offer.main as OfferingSP).sender == processName) {
                    return SelectOffer(processTerm, offer)
                }
            }

            is OfferingSP -> {
                val select = processMap[mainBehaviour.process]
                if (select != null && select.main is SelectionSP && (select.main as SelectionSP).receiver == processName) {
                    return SelectOffer(select, processTerm)
                }
            }

        }
        return null
    }

    private fun getCommunication(processes: ProcessMap, findComm: GetCommunication): GraphNode {
        when (findComm) {
            is SendReceive -> {
                val newProcesses = ProcessMap()
                processes.forEach { key, value -> newProcesses.put(key, value.copy()) }

                val senderTerm = findComm.senderTerm
                val receiverTerm = findComm.receiverTerm

                val receiverName = (senderTerm.main as SendingSP).receiver
                val senderName = (receiverTerm.main as ReceiveSP).sender

                newProcesses.replace(receiverName, ProcessTerm(receiverTerm.procedures, (receiverTerm.main as ReceiveSP).continuation))
                newProcesses.replace(senderName, ProcessTerm(senderTerm.procedures, (senderTerm.main as SendingSP).continuation))

                val label = SendingLabel(senderName, receiverName, (senderTerm.main as SendingSP).expression)

                return GraphNode(Network(newProcesses), label)
            }

            is SelectOffer -> {
                val newProcesses = HashMap<String, ProcessTerm>(processes)

                val selection = findComm.senderTerm
                val offering = findComm.receiverTerm

                val selectionProcess = (offering.main as OfferingSP).process
                val offeringProcess = (selection.main as SelectionSP).process

                val offeringBehavior = (offering.main as OfferingSP).branches[(selection.main as SelectionSP).expression]
                        ?: throw Exception("Trying to senderTerm the label that wasn't offered")

                newProcesses.replace(offeringProcess, ProcessTerm(offering.procedures, offeringBehavior))
                newProcesses.replace(selectionProcess, ProcessTerm(selection.procedures, (selection.main as SelectionSP).continuation))

                val label = SelectionLabel(offeringProcess, selectionProcess, (selection.main as SelectionSP).expression)

                return GraphNode(Network(newProcesses), label)
            }

            else -> throw Exception("FindComm object doesn't belong to SendReceive or SelectOffer types")
        }
    }

    private fun findCondition(n: ProcessMap): Boolean {
        return !n.values.none { it.main is ConditionSP }
    }

    data class ResultCondition(val tmp1: Network, val lb1: ThenLabel, val tmp2: Network, val lbl2: ElseLabel)

    private fun getCondition(n: ProcessMap): ResultCondition {
        val c = n.entries.stream().filter { it.value.main is ConditionSP }.findFirst().get()
        val process = c.key
        val conditionPB = c.value
        val conditionMain = conditionPB.main as ConditionSP

        val pbelsemap = HashMap<String, ProcessTerm>(n)
        val pbthenmap = HashMap<String, ProcessTerm>(n)

        pbelsemap.replace(process, ProcessTerm(conditionPB.procedures, conditionMain.elseBehaviour))
        pbthenmap.replace(process, ProcessTerm(conditionPB.procedures, conditionMain.thenBehaviour))

        return ResultCondition(Network(pbthenmap), ThenLabel(process, conditionMain.expression), Network(pbelsemap), ElseLabel(process, conditionMain.expression))
    }

    //endregion
    //region Manipulations with nodes
    private fun createNewNode(targetNetwork: Network, label: ExtractionLabel, predecessorNode: ConcreteNode, marking: HashMap<ProcessName, Boolean>): ConcreteNode {
        val str = when (label) {
            is ThenLabel -> predecessorNode.choicePath + "0"
            is ElseLabel -> predecessorNode.choicePath + "1"
            else -> predecessorNode.choicePath
        }

        return if (label.flipped) {
            ConcreteNode(targetNetwork, str, nextNodeId(), ArrayList(), marking)
        } else {
            val newnode = ConcreteNode(targetNetwork, "" + str, nextNodeId(), ArrayList(predecessorNode.bad), marking)
            newnode.bad.add(predecessorNode.id)
            newnode
        }
    }

    private fun addNewNode(currentNode: ConcreteNode, newNode: ConcreteNode, label: ExtractionLabel, graph: DirectedGraph<ConcreteNode, ExtractionLabel>) {
        graph.addVertex(newNode)
        graph.addEdge(currentNode, newNode, label)
        addToChoicePathMap(currentNode)
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

    private fun cleanNode(findComm: GetCommunication, n: ProcessMap, nn: ConcreteNode) {
        val first = if (findComm is SendReceive) findComm.senderTerm else (findComm as SelectOffer).senderTerm
        val second = if (findComm is SendReceive) findComm.receiverTerm else (findComm as SelectOffer).receiverTerm

        val prk1 = (first.main as ActionSP).process
        val prb1new = nn.network.processes.get(prk1)
        val prb1 = n[prk1]
        val prk2 = (second.main as ActionSP).process
        val prb2new = nn.network.processes.get(prk2)
        val prb2 = n[prk2]

        prb1?.main = prb1new?.main?.copy()!!
        prb2?.main = prb2new?.main?.copy()!!
    }

    //endregion
    //region Checkloop and choicePaths manipulations
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
        val key = n.choicePath.dropLast(1)
        addToChoicePathMap(ConcreteNode(n.network, key, n.id, n.bad, n.marking))
        removeFromChoicePathMap(n)
    }

    private fun checkPrefix(n: ConcreteNode): ConcreteNode? {
        for (node in choicePaths) {
            if (node.key.startsWith(n.choicePath) && node.value.isNotEmpty())
                return node.value.first()
        }
        return null
    }

    private var choicePaths = HashMap<String, ArrayList<ConcreteNode>>() //global map of processes used in bad loop calculations
    private fun removeFromChoicePathMap(node: ConcreteNode) {
        val nodesList = choicePaths[node.choicePath]
        if (nodesList != null) {
            val nd = nodesList.find { it == node }
            if (nd != null) {
                nodesList.remove(nd)
            }
        }
    }

    private fun addToChoicePathMap(node: ConcreteNode) {
        choicePaths.compute(node.choicePath, { key, value ->
            if (value == null) {
                val array = ArrayList<ConcreteNode>()
                array.add(node)
                array
            } else {
                value.add(node)
                value
            }
        })
    }

    //endregion
    //region Exceptions
    class NoPossibleActionsException(override val message: String) : Exception(message)

    class MulticomException(override val message: String) : Exception(message)
    //endregion
    //region Data classes and interfaces
    data class LabelTarget(val lbl: ExtractionLabel, val target: Node)

    interface GetCommunication
    data class SendReceive(val senderTerm: ProcessTerm, val receiverTerm: ProcessTerm) : GetCommunication
    data class SelectOffer(val senderTerm: ProcessTerm, val receiverTerm: ProcessTerm) : GetCommunication
    interface Node
    data class ConcreteNode(val network: Network, val choicePath: String, val id: Int, val bad: ArrayList<Int>, val marking: HashMap<ProcessName, Boolean>) : Node {
        fun copy(): ConcreteNode {
            val newnet = network.copy()
            val newb = ArrayList<Int>()

            bad.forEach { newb.add(it) }

            return ConcreteNode(newnet, "" + choicePath, id, newb, marking.clone() as HashMap<ProcessName, Boolean>)
        }

        fun equals(other: ConcreteNode): Boolean {
            return network.equals(other.network) && choicePath == other.choicePath && bad.equals(other.bad) && marking.equals(other.marking)
        }
        //override fun toString(): String = ""
    }

    //    data class GraphNode(val target: Network, val label: ExtractionLabel)
    data class FakeNode(val procedureName: String, val source: Node) : Node

    //endregion
    //region Utils
    @Volatile
    private var count = 0

    private fun generateProcedureName(): String {
        return "X" + count.inc()
    }

    private fun sortProcesses(node: ConcreteNode, strategy: Strategy): HashMap<String, ProcessTerm> {
        val net = node.network.processes
        val copynet = LinkedHashMap<String, ProcessTerm>()

        //put Selection/OfferingSP on top
        net.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) copynet.put("" + process.key, process.value.copy()) }
        net.forEach { process -> copynet.put("" + process.key, process.value.copy()) }

        return copynet
    }

    private fun createInteractionLabel(p: String, nodesorted: HashMap<String, ProcessTerm>): InteractionLabel? {
        val pmain = nodesorted.get(p)?.main
        when (pmain) {
            is SendingSP -> {
                return SendingLabel(p, pmain.receiver, pmain.expression)
            }
            is SelectionSP -> {
                return SelectionLabel(p, pmain.receiver, pmain.expression)
            }
            else -> return null
        }
    }
    //endregion
}