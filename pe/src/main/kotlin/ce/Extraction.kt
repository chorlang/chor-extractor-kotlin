package ce

import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Choreography
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.labels.*
import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.interfaces.InteractionLabel
import ast.sp.nodes.*
import ast.sp.nodes.interfaces.ActionSP
import ast.sp.nodes.interfaces.IBehaviour
import org.apache.logging.log4j.LogManager
import org.jgrapht.graph.DefaultDirectedGraph


typealias ProcessMap = HashMap<String, ProcessTerm>
typealias GraphNode = Pair<Network, InteractionLabel>
typealias Marking = HashMap<ProcessName, Boolean>
typealias Hash = Int

class NetworkExtraction {
    private val log = LogManager.getLogger()
    private var nodeIdCounter = 0
    private val hashesMap = HashMap<Hash, ArrayList<ConcreteNode>>()
    private var choicePaths = HashMap<String, ArrayList<ConcreteNode>>() //global map of processes used in bad loop calculations
    private var badLoopCnt = 0

    //region Main
    companion object {
        private lateinit var livelocked: ArrayList<String>
        private var debugMode = false

        fun run(n: Network, s: Strategy, l: ArrayList<String>, d: Boolean): Program {
            livelocked = l
            debugMode = d
            return NetworkExtraction().extract(n, s, livelocked)
        }
    }

    /**
     * 1. build graph with nodes as networks and edges as choreography actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading choreography actions
     * @param n a processes from which a choreography will be extracted
     * @return Program representation of resulted choreography
     */
    private fun extract(n: Network, strategy: Strategy, livelocked: ArrayList<String>): Program {
        val graph = DefaultDirectedGraph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val marking = HashMap<ProcessName, Boolean>()

        n.processes.forEach({ name, term -> marking[name] = term.main is TerminationSP || livelocked.contains(name) })

        val node = ConcreteNode(n, "0", nextNodeId(), ArrayList(), marking)
        graph.addVertex(node)
        addToChoicePathMap(node)
        addToHashMap(node)

        buildGraph(node, graph as DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, strategy)
        if (debugMode) {
            log.debug("Graph vertexes: ${graph.vertexSet().size}")
            log.debug("Backtracking: $badLoopCnt")
        }

        val fklist = unrollGraph(node, graph as DefaultDirectedGraph<Node, ExtractionLabel>)
        return buildChoreography(node, fklist, graph)
    }

    private fun buildGraph(currentNode: ConcreteNode, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, strategy: Strategy): Boolean {
        val unfoldedProcesses = HashSet<String>() //Storing unfoldedProcesses processes
        val processes = copyAndSortProcesses(currentNode, strategy) //Sorting processes by the strategy passed from the outside

        //region Try to find a single-action communication
        for (processPair in processes) {
            //if the processPair has procedure invocation on top, try to unfold it
            if (unfold(processPair.key, processes[processPair.key]!!)) unfoldedProcesses.add(processPair.key)

            val processesCopy = processesCopy(processes)

            //region Try to find interaction
            val findCommunication = getCommunication(processesCopy, processPair.key)
            if (findCommunication != null) {
                val (targetNetwork, label) = findCommunication

                //remove processes that were unfoldedProcesses but don't participate in the current communication
                val targetMarking = currentNode.marking.clone() as Marking

                targetMarking[label.rcv] = true
                unfoldedProcesses.remove(label.rcv)
                targetMarking[label.snd] = true
                unfoldedProcesses.remove(label.snd)

                // revert unfolding all processes not involved in the communication
                unfoldedProcesses.forEach { targetNetwork.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (targetMarking.values.all { it }) {
                    label.flipped = true
                    wash(targetMarking, targetNetwork.processes)
                }

                //check if the node with the same network and markings already exists in the graph
                val existingNodes = hashesMap[hash(targetNetwork, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNode = existingNodes?.firstOrNull { it.network.equals(targetNetwork) && it.marking.equals(targetMarking) }

                /* case 1 */
                if (existingNode == null) { // || existingNodes.isEmpty()) {
                    val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
                    assert(addNodeToGraph(currentNode, newNode, label, graph))
                    return if (buildGraph(newNode, graph, strategy)) true else {
                        removeNodeFromGraph(graph, currentNode, newNode)
                        continue
                    }
                }
                /* case 2 */
                else {
                    if (addEdgeToGraph(currentNode, existingNode, label, graph)) return true
                    else {
                        //cleanNode(findCommunication, processes, currentNode)
                        unfoldedProcesses.forEach { unfold(it, processes[it]!!) } // Larisa will explain this
                    }
                }
            }
            //endregion

            //region Try to find condition
            //val processesCopy = processesCopy(processes)
            val findCondition = getCondition(processesCopy)
            if (findCondition != null) {
                val (targetNetworkThen, labelThen, targetNetworkElse, labelElse) = findCondition
                val targetMarking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                if (unfoldedProcesses.contains(labelThen.process)) {
                    targetMarking[labelThen.process] = true
                    unfoldedProcesses.remove(labelThen.process)
                }

                unfoldedProcesses.forEach {
                    targetNetworkThen.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!!
                    targetNetworkElse.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!!
                }

                //if all procedures were visited, flip all markings
                if (targetMarking.values.all { it }) {
                    labelThen.flipped = true
                    labelElse.flipped = true
                    wash(targetMarking, targetNetworkThen.processes)
                    wash(targetMarking, targetNetworkElse.processes)
                }

                //check if the node with the same network and markings already exists in the graph
                val existingNodesThen = hashesMap[hash(targetNetworkThen, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNodeThen = existingNodesThen?.firstOrNull { it.network.equals(targetNetworkThen) && it.marking.equals(targetMarking) }

                val nodeThen: ConcreteNode
                val nodeElse: ConcreteNode

                /* case4 */
                if (existingNodeThen == null) {
                    nodeThen = createNewNode(targetNetworkThen, labelThen, currentNode, targetMarking)
                    assert(addNodeToGraph(currentNode, nodeThen, labelThen, graph))

                    if (!buildGraph(nodeThen, graph, strategy)) {
                        removeNodeFromGraph(graph, currentNode, nodeThen)
                        continue
                    }
                }
                /* case 5 */
                else {
                    nodeThen = existingNodeThen
                    if (!addEdgeToGraph(currentNode, nodeThen, labelThen, graph)) {
                        continue
                    }
                }

                val existingNodesElse = hashesMap[hash(targetNetworkElse, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNodeElse = existingNodesElse?.firstOrNull { it.network.equals(targetNetworkElse) && it.marking.equals(targetMarking) }

                /* case 7 */
                if (existingNodeElse == null) {
                    nodeElse = createNewNode(targetNetworkElse, labelElse, currentNode, targetMarking)
                    assert(addNodeToGraph(currentNode, nodeElse, labelElse, graph))
                    if (!buildGraph(nodeElse, graph, strategy)) {
                        if (existingNodeThen == null) {
                            graph.removeVertex(nodeThen) // Larisa: Why graph.removeVertex instead of removeNodefromGraph?
                            removeFromHashMap(nodeThen)
                        }
                        graph.removeEdge(currentNode, nodeThen)

                        removeNodeFromGraph(graph, currentNode, nodeElse)
                        return false
                    }
                }
                /* case 8 */
                else {
                    nodeElse = existingNodeElse
                    if (!addEdgeToGraph(currentNode, nodeElse, labelElse, graph)) {
                        if (existingNodeThen == null) {
                            graph.removeVertex(nodeThen)
                            removeFromHashMap(nodeThen)
                        }
                        graph.removeEdge(currentNode, nodeThen)
                        graph.removeEdge(currentNode, nodeElse)
                        continue
                    }
                }

                relabel(nodeThen)
                relabel(nodeElse)
                return true
            }
            //endregion

            //check if there are no possible actions left
            if (allTerminated(processes)) return true
        }
        //endregion

        //region Try to find a multi-action communication
        for (p in processes) {
            val processesCopy = processesCopy(processes)

            val actions = ArrayList<InteractionLabel>()
            val waiting = ArrayList<InteractionLabel>()
            val interactionLabel = createInteractionLabel(p.key, processesCopy)
            if (interactionLabel != null) waiting.add(interactionLabel)


            val receivers = ArrayList<String>()
            while (!waiting.isEmpty()) {
                val label = waiting.first()
                waiting.remove(label)

                //multicom can't contain actions with the same receiver
                if (receivers.contains(label.rcv)) throw MulticomException("multicom can't contain actions with the same receiver")
                actions.add(label)
                receivers.add(label.rcv)

                val receiver = label.rcv
                val sender = label.snd

                val receiverProcessTerm = processesCopy[receiver] //val receiverProcessTerm = tmp_node_net[receiverTerm]

                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fill the list of waiting actions with sending/selection actions from the receiverTerm process behaviour if it is in correct format
                //return the new receiverTerm pb if success and null otherwise. waiting list is populated implicitly
                val newReceiverBehaviour = fillWaiting(waiting, actions, label, receiverProcessTerm!!.main, processesCopy[receiver]!!.procedures, marking)
                if (newReceiverBehaviour != null) {
                    val senderProcessTerm = processesCopy[sender]
                    val senderBehaviour = senderProcessTerm!!.main

                    processesCopy.replace(receiver, ProcessTerm(receiverProcessTerm.procedures, newReceiverBehaviour))
                    val senderBehaviourContinuation = if (senderBehaviour is SendingSP) senderBehaviour.continuation else if (senderBehaviour is SelectionSP) senderBehaviour.continuation else throw UnsupportedOperationException()

                    processesCopy.replace(sender, ProcessTerm(senderProcessTerm.procedures, senderBehaviourContinuation))
                }
            }

            //if we managed to collect some actions for a multicom
            if (actions.size >= 2) {
                val label = MulticomLabel(actions)

                val targetMarking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fold back unfoldedProcesses procedures that were not participating in communication
                label.labels.forEach {
                    targetMarking.put(it.rcv, true)
                    unfoldedProcesses.remove(it.rcv)
                    targetMarking.put(it.snd, true)
                    unfoldedProcesses.remove(it.snd)
                }
                unfoldedProcesses.forEach { processesCopy[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (targetMarking.values.all { it }) {
                    label.flipped = true
                    wash(targetMarking, processesCopy)
                }

                //check if the node with the same network and markings already exists in the graph
                val targetNetwork = Network(processesCopy)

                val existingNodes = hashesMap[hash(targetNetwork, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNode = existingNodes?.firstOrNull { it.network.equals(targetNetwork) && it.marking.equals(targetMarking) }

                if (existingNode == null) {
                    val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
                    assert(addNodeToGraph(currentNode, newNode, label, graph))
                    return if (buildGraph(newNode, graph, strategy)) true else continue
                } else {
                    if (addEdgeToGraph(currentNode, existingNode, label, graph)) return true
                }
            }

        }
        //endregion

        //region Throw exception, if there is no possible actions
        throw NoPossibleActionsException("No possible actions at node" + currentNode.toString())
        //endregion
    }

    private fun unrollGraph(root: ConcreteNode, graph: DefaultDirectedGraph<Node, ExtractionLabel>): ArrayList<FakeNode> {
        val fakeNodesList = ArrayList<FakeNode>()

        var count = 1
        val recursiveNodes = HashMap<ConcreteNode, String>()

        if (graph.incomingEdgesOf(root).size == 1) recursiveNodes[root] = count++.toString()
        graph.vertexSet().forEach { node -> if (node is ConcreteNode && graph.incomingEdgesOf(node).size > 1) recursiveNodes[node] = count++.toString() }

        recursiveNodes.forEach { node ->
            node.run {
                val fk = FakeNode("X" + node.value, node.key)
                graph.addVertex(fk)
                fakeNodesList.add(fk)
                val labels = graph.outgoingEdgesOf(node.key)

                val targets = ArrayList<LabelTarget>()
                labels.forEach { label -> targets.add(LabelTarget(label, graph.getEdgeTarget(label))) }

                graph.removeAllEdges(ArrayList<ExtractionLabel>(graph.outgoingEdgesOf(node.key)))
                targets.forEach { target -> graph.addEdge(fk, target.target, target.lbl) }
            }
        }


        /*if (graph.incomingEdgesOf(root).size == 1) {
            val fakeNode = FakeNode("X" + count.inc().toString(), root)
            graph.addVertex(fakeNode)
            fakeNodesList.add(fakeNode)
            val label = graph.outgoingEdgesOf(root).first()
            val target = graph.getEdgeTarget(label)
            graph.removeEdge(label)
            graph.addEdge(fakeNode, target, label)

        }*/

        return fakeNodesList
    }

    private fun buildChoreography(root: Node, fakeNodesList: ArrayList<FakeNode>, graph: DefaultDirectedGraph<Node, ExtractionLabel>): Program {
        val main = bh(root, graph, fakeNodesList)
        val procedures = ArrayList<ProcedureDefinition>()
        for (fk in fakeNodesList) {
            procedures.add(ProcedureDefinition(fk.procedureName, bh(fk, graph, fakeNodesList), HashSet()))
        }

        return Program(main as Choreography, procedures)
    }

    private fun bh(node: Node, graph: DefaultDirectedGraph<Node, ExtractionLabel>, fklist: ArrayList<FakeNode>): CCNode {
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
            else -> throw Exception("Bad graph. A node has more than 2 outgoing edges.")
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
        for (key in marking.keys) {
            marking[key] = processes[key]!!.main is TerminationSP || livelocked.contains(key)
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
    private fun getCommunication(processes: ProcessMap, processName: String): GraphNode? {
        val processTerm = processes[processName]
        val mainBehaviour = processTerm?.main

        when (mainBehaviour) {
            is SendingSP -> {
                val receiverTerm = processes[mainBehaviour.process]
                if (receiverTerm != null && receiverTerm.main is ReceiveSP && (receiverTerm.main as ReceiveSP).sender == processName) {
                    return consumeCommunication(processes, processTerm, receiverTerm)
                }
            }
            is ReceiveSP -> {
                val senderTerm = processes[(processTerm.main as ReceiveSP).process]
                if (senderTerm != null && senderTerm.main is SendingSP && (senderTerm.main as SendingSP).receiver == processName) {
                    return consumeCommunication(processes, senderTerm, processTerm)
                }
            }
            is SelectionSP -> {
                val offerTerm = processes[mainBehaviour.process]
                if (offerTerm != null && offerTerm.main is OfferingSP && (offerTerm.main as OfferingSP).sender == processName) {
                    return consumeSelection(processes, offerTerm, processTerm)
                }
            }
            is OfferingSP -> {
                val selectTerm = processes[mainBehaviour.process]
                if (selectTerm != null && selectTerm.main is SelectionSP && (selectTerm.main as SelectionSP).receiver == processName) {
                    return consumeSelection(processes, processTerm, selectTerm)
                }
            }
        }

        return null
    }

    private fun consumeSelection(processes: ProcessMap, offerTerm: ProcessTerm, selectTerm: ProcessTerm): GraphNode {
        val newProcesses = ProcessMap()
        processes.forEach { key, value -> newProcesses[key] = value.copy() }

        val selectionProcess = (offerTerm.main as OfferingSP).process
        val offeringProcess = (selectTerm.main as SelectionSP).process

        val offeringBehavior = (offerTerm.main as OfferingSP).branches[(selectTerm.main as SelectionSP).expression]
                ?: throw Exception("Trying to senderTerm the label that wasn't offered")

        newProcesses.replace(offeringProcess, ProcessTerm(offerTerm.procedures, offeringBehavior))
        newProcesses.replace(selectionProcess, ProcessTerm(selectTerm.procedures, (selectTerm.main as SelectionSP).continuation))

        val label = SelectionLabel(offeringProcess, selectionProcess, (selectTerm.main as SelectionSP).expression)

        return GraphNode(Network(newProcesses), label)
    }

    private fun consumeCommunication(processes: ProcessMap, senderTerm: ProcessTerm, receiverTerm: ProcessTerm): GraphNode {
        val newProcesses = ProcessMap()
        processes.forEach { key, value -> newProcesses[key] = value.copy() }
        val receiverName = (senderTerm.main as SendingSP).receiver
        val senderName = (receiverTerm.main as ReceiveSP).sender

        newProcesses.replace(receiverName, ProcessTerm(receiverTerm.procedures, (receiverTerm.main as ReceiveSP).continuation))
        newProcesses.replace(senderName, ProcessTerm(senderTerm.procedures, (senderTerm.main as SendingSP).continuation))

        val label = SendingLabel(senderName, receiverName, (senderTerm.main as SendingSP).expression)

        return GraphNode(Network(newProcesses), label)
    }

    private fun getCondition(n: ProcessMap): ResultCondition? {
        val c = n.entries.stream().filter { it.value.main is ConditionSP }.findFirst()
        if (c.isPresent) {
            val condition = c.get()
            val process = condition.key
            val conditionProcessTerm = condition.value
            val conditionMain = conditionProcessTerm.main as ConditionSP

            val elseProcessesMap = HashMap<String, ProcessTerm>(n)
            val thenProcessesMap = HashMap<String, ProcessTerm>(n)

            elseProcessesMap.replace(process, ProcessTerm(conditionProcessTerm.procedures, conditionMain.elseBehaviour))
            thenProcessesMap.replace(process, ProcessTerm(conditionProcessTerm.procedures, conditionMain.thenBehaviour))

            return ResultCondition(Network(thenProcessesMap), ThenLabel(process, conditionMain.expression), Network(elseProcessesMap), ElseLabel(process, conditionMain.expression))
        }
        return null
    }
    //endregion

    //region Manipulations with nodes in the graph and auxiliary data structures
    private fun createNewNode(targetNetwork: Network, label: ExtractionLabel, predecessorNode: ConcreteNode, marking: HashMap<ProcessName, Boolean>): ConcreteNode {
        val str = when (label) {
            is ThenLabel -> predecessorNode.choicePath + "0"
            is ElseLabel -> predecessorNode.choicePath + "1"
            else -> predecessorNode.choicePath
        }

        val newNode: ConcreteNode
        if (label.flipped) {
            newNode = ConcreteNode(targetNetwork, str, nextNodeId(), ArrayList(), marking)
        } else {
            newNode = ConcreteNode(targetNetwork, str, nextNodeId(), ArrayList(predecessorNode.bad), marking)
            newNode.bad.add(predecessorNode.id)
        }

        return newNode
    }

    private fun addToHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        hashesMap.compute(hash, { key, value ->
            if (value == null) {
                val array = ArrayList<ConcreteNode>()
                assert(array.add(newNode))
                array
            } else {
                assert(value.add(newNode))
                value
            }
        })
    }

    private fun removeFromHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        hashesMap.compute(hash, { key, value ->
            if (value != null) {
                assert(value.remove(newNode))
                value
            } else {
                throw Exception("Vertex is in the graph but not registered in the map of hashes")
            }
        })
    }

    private fun addNodeToGraph(currentNode: ConcreteNode, newNode: ConcreteNode, label: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        //check if we can add a new node and an edge
        return if (graph.addVertex(newNode) && graph.addEdge(currentNode, newNode, label)) {
            addToChoicePathMap(newNode)
            addToHashMap(newNode)
            true
        } else false
    }

    private fun removeNodeFromGraph(graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, currentNode: ConcreteNode, removingNode: ConcreteNode) {
        graph.removeEdge(currentNode, removingNode)
        graph.removeVertex(removingNode)
        removeFromHashMap(removingNode)
    }

    private fun addEdgeToGraph(nn: ConcreteNode, newnode: ConcreteNode, lbl: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        //val exstnode = checkPrefix(newnode)
        if (checkPrefix(newnode)) {
            val l = checkLoop(nn, newnode, lbl, graph)
            if (l) {
                graph.addEdge(nn, newnode, lbl)
                return true
            } //else throw BadLoopException("Bad loop!")
        }
        badLoopCnt++
        return false
    }
    //endregion

    //region Checkloop and choicePaths manipulations
    private fun checkLoop(source_node: ConcreteNode, target_node: ConcreteNode, lbl: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
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

    private fun recompute(n: ConcreteNode, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, tomark: HashSet<ConcreteNode>): HashSet<ConcreteNode> {
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

    private fun relabel(node: ConcreteNode) {
        val key = node.choicePath.dropLast(1)
        addToChoicePathMap(ConcreteNode(node.network, key, node.id, node.bad, node.marking))
        removeFromChoicePathMap(node)
    }

    private fun checkPrefix(n: ConcreteNode): Boolean {
        for (node in choicePaths) {
            if (node.key.startsWith(n.choicePath) && node.value.isNotEmpty())
                return true //node.value.first()
        }
        return false
    }

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

    data class ResultCondition(val tmp1: Network, val lb1: ThenLabel, val tmp2: Network, val lbl2: ElseLabel)

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
    }
    data class FakeNode(val procedureName: String, val source: Node) : Node
    //endregion

    //region Utils
    private fun copyAndSortProcesses(node: ConcreteNode, strategy: Strategy): HashMap<String, ProcessTerm> {
        val net = HashMap<String, ProcessTerm>()
        node.network.processes.forEach { k, v -> net.put(k, v.copy()) }
        return strategy.sort(node.marking, net)
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

    private fun nextNodeId(): Int {
        return nodeIdCounter++
    }

    private fun hash(n: Network, marking: Marking): Int {
        return Pair(n, marking).hashCode()
    }

    private fun processesCopy(processes: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
        val processesCopy = HashMap<String, ProcessTerm>()
        processes.forEach { t, u -> processesCopy[t] = u.copy() }
        return processesCopy
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
                // if (!marking.get(lbl.rcv)!!) {
                unfold(lbl.rcv, new_pb)
                marking[lbl.rcv] = true
                //}
                return fillWaiting(waiting, actions, lbl, new_pb.main, rcv_proc, marking)
            }
        }
        return null
    }
    //endregion
}