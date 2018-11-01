package ce

import ast.cc.interfaces.CCNode
import ast.cc.interfaces.Behaviour
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.labels.*
import ast.sp.labels.interfaces.ExtractionLabel
import ast.sp.labels.interfaces.InteractionLabel
import ast.sp.nodes.*
import ast.sp.interfaces.IBehaviour
import org.jgrapht.graph.DefaultDirectedGraph

typealias ProcessMap = HashMap<String, ProcessTerm>
typealias GraphNode = Pair<Network, InteractionLabel>
typealias Marking = HashMap<ProcessName, Boolean>
typealias Hash = Int

class NetworkExtraction {
    private var nodeIdCounter = 0
    private val hashesMap = HashMap<Hash, ArrayList<ConcreteNode>>()
    private var choicePaths = HashMap<String, ArrayList<ConcreteNode>>() //global map of processesInChoreography used in badNodesList loop calculations
    private var badLoopCnt = 0

    //region Main
    companion object {
        private lateinit var livelocked: ArrayList<String>
        private var debugMode = false

        fun run(n: Network, s: Strategy = Strategy.Default, l: ArrayList<String> = ArrayList(), d: Boolean = false): Pair<Choreography?, GraphStatistic> {
            livelocked = l
            debugMode = d
            return NetworkExtraction().extract(n, s, livelocked)
        }
    }

    /**
     * 1. build graph with nodes as networks and edges as choreography actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading choreography actions
     * @param n a processesInChoreography from which a choreography will be extracted
     * @return Choreography representation of resulted choreography
     */
    @Suppress("UNCHECKED_CAST")
    private fun extract(n: Network, strategy: Strategy, livelocked: ArrayList<String>): Pair<Choreography?, GraphStatistic> {
        val graph = DefaultDirectedGraph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val marking = HashMap<ProcessName, Boolean>()

        //we mark as visited processesInChoreography which has no active actions in the main procedure or which are in the list of livelocked processesInChoreography
        n.processes.forEach { name, term -> marking[name] = term.main is TerminationSP || livelocked.contains(name) }

        val node = ConcreteNode(network = n, choicePath = "0", id = nextNodeId(), badNodesList = ArrayList(), marking = marking)
        graph.addVertex(node)
        addToChoicePathMap(node)
        addToHashMap(node)

        return try {
            buildGraph(node, graph as DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, strategy)
            val unrolledGraphNodesList = unrollGraph(node, graph as DefaultDirectedGraph<Node, ExtractionLabel>)
            Pair(buildChoreography(node, unrolledGraphNodesList, graph), GraphStatistic(graph.vertexSet().size, badLoopCnt))
        } catch (e: Exception) {
            Pair(null, GraphStatistic(graph.vertexSet().size, badLoopCnt))
        }
    }

    private fun buildGraph(currentNode: ConcreteNode, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, strategy: Strategy): Boolean {
        val unfoldedProcesses = HashSet<String>()
        val processes = copyAndSortProcesses(currentNode, strategy)

        //region Try to find a single-action communication
        for (processPair in processes) {
            //if the processPair has procedure invocation on top, try to unfold it
            if (unfold(processPair.key, processes[processPair.key]!!)) unfoldedProcesses.add(processPair.key)

            val processesCopy = processesCopy(processes)

            //region Try to find interaction
            val findCommunication = getCommunication(processesCopy, processPair.key)
            if (findCommunication != null) {
                val (targetNetwork, label) = findCommunication

                //remove processesInChoreography that were unfoldedProcesses but don't participate in the current communication
                @Suppress("UNCHECKED_CAST")
                val targetMarking = currentNode.marking.clone() as Marking

                targetMarking[label.rcv] = true
                unfoldedProcesses.remove(label.rcv)
                targetMarking[label.snd] = true
                unfoldedProcesses.remove(label.snd)

                // revert unfolding all processesInChoreography not involved in the communication
                unfoldedProcesses.forEach { targetNetwork.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }

                //if all procedures were visited, flip all markings
                if (targetMarking.values.all { it }) {
                    flipAndWash(label, targetMarking, targetNetwork)
                }

                //check if the node with the same network and markings already exists in the graph
                val existingNodes = hashesMap[hash(targetNetwork, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNode = existingNodes?.firstOrNull { it.network == targetNetwork && it.marking == targetMarking }

                /* case 1 */
                if (existingNode == null) { // || existingNodes.isEmpty()) {
                    val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
                    assert(addNodeAndEdgeToGraph(currentNode, newNode, label, graph))

                    val size = graph.vertexSet().size + 100
                    return if (buildGraph(newNode, graph, strategy)) true else {
                        assert(size == graph.vertexSet().size)
                        removeNodeFromGraph(graph, newNode)
                        continue
                    }
                }
                /* case 2 */
                else {
                    if (addEdgeToGraph(currentNode, existingNode, label, graph)) return true
                    else continue
                }
            }
            //endregion

            //region Try to find condition
            val findCondition = getCondition(processesCopy)
            if (findCondition != null) {
                val (targetNetworkThen, labelThen, targetNetworkElse, labelElse) = findCondition
                @Suppress("UNCHECKED_CAST")
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
                    flipAndWash(labelThen, targetMarking, targetNetworkThen)
                    flipAndWash(labelElse, targetMarking, targetNetworkElse)
                }

                //check if the node with the same network and markings already exists in the graph
                val existingNodesThen = hashesMap[hash(targetNetworkThen, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNodeThen = existingNodesThen?.firstOrNull { it.network == targetNetworkThen && it.marking == targetMarking }
                val nodeThen: ConcreteNode

                /* case4 */
                if (existingNodeThen == null) {
                    nodeThen = createNewNode(targetNetworkThen, labelThen, currentNode, targetMarking)
                    addNodeAndEdgeToGraph(currentNode, nodeThen, labelThen, graph)

                    val size = graph.vertexSet().size + 100
                    if (!buildGraph(nodeThen, graph, strategy)) {
                        removeNodeFromGraph(graph, nodeThen)
                        assert(size == (graph.vertexSet().size))
                        continue
                    }
                }
                /* case 5 */
                else {
                    nodeThen = existingNodeThen
                    val edges = graph.edgeSet().size
                    if (!addEdgeToGraph(currentNode, nodeThen, labelThen, graph)) {
                        assert(edges == graph.edgeSet().size)
                        continue
                    } //else assert(edges == (graph.edgeSet().size + 1))
                }

                val existingNodesElse = hashesMap[hash(targetNetworkElse, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
                val existingNodeElse = existingNodesElse?.firstOrNull { it.network == targetNetworkElse && it.marking == targetMarking }
                val nodeElse: ConcreteNode

                /* case 7 */
                if (existingNodeElse == null) {
                    nodeElse = createNewNode(targetNetworkElse, labelElse, currentNode, targetMarking)
                    addNodeAndEdgeToGraph(currentNode, nodeElse, labelElse, graph)

                    val size = graph.vertexSet().size + 100
                    if (!buildGraph(nodeElse, graph, strategy)) {
                        if (existingNodeThen == null) {
                            removeNodeFromGraph(graph, nodeThen)
                        } else graph.removeEdge(currentNode, nodeThen)
                        removeNodeFromGraph(graph, nodeElse)
                        assert(size == graph.vertexSet().size)
                        return false
                    }
                }
                /* case 8 */
                else {
                    var edges = graph.edgeSet().size
                    nodeElse = existingNodeElse
                    if (!addEdgeToGraph(currentNode, nodeElse, labelElse, graph)) {
                        if (existingNodeThen == null) {
                            edges -=  graph.edgesOf(nodeThen).size
                            removeNodeFromGraph(graph, nodeThen)
                        }
                        else {
                            graph.removeEdge(currentNode, nodeThen)
                            edges -= 1
                        }
                        assert(edges == graph.edgeSet().size)
                        continue
                    }
//                    else assert(edges == (graph.edgeSet().size + 1 ))
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

                @Suppress("UNCHECKED_CAST")
                val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fill the list of waiting actions with sending/selection actions from the receiverTerm process behaviour if it is in correct format
                //return the new receiverTerm pb if success and null otherwise. waiting list is populated implicitly
                val newReceiverBehaviour = fillWaiting(waiting, actions, label, receiverProcessTerm!!.main, processesCopy[receiver]!!.procedures, marking)
                if (newReceiverBehaviour != null) {
                    val senderProcessTerm = processesCopy[sender]
                    val senderBehaviour = senderProcessTerm!!.main

                    processesCopy.replace(receiver, ProcessTerm(receiverProcessTerm.procedures, newReceiverBehaviour))
                    val senderBehaviourContinuation = (senderBehaviour as? SendingSP)?.continuation
                            ?: ((senderBehaviour as? SelectionSP)?.continuation
                                    ?: throw UnsupportedOperationException())

                    processesCopy.replace(sender, ProcessTerm(senderProcessTerm.procedures, senderBehaviourContinuation))
                }
            }

            //if we managed to collect some actions for a multicom
            if (actions.size >= 2) {
                val label = MulticomLabel(actions)

                @Suppress("UNCHECKED_CAST")
                val targetMarking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

                //fold back unfoldedProcesses procedures that were not participating in communication
                label.labels.forEach {
                    targetMarking[it.rcv] = true
                    unfoldedProcesses.remove(it.rcv)
                    targetMarking[it.snd] = true
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
                val existingNode = existingNodes?.firstOrNull { it.network == targetNetwork && it.marking == targetMarking }

                if (existingNode == null) {
                    val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
                    assert(addNodeAndEdgeToGraph(currentNode, newNode, label, graph))
                    return if (buildGraph(newNode, graph, strategy)) true else {
                        continue
                    }
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

    private fun flipAndWash(label: ExtractionLabel, targetMarking: Marking, targetNetwork: Network) {
        label.flipped = true
        wash(targetMarking, targetNetwork.processes)
    }

    private fun unrollGraph(root: ConcreteNode, graph: DefaultDirectedGraph<Node, ExtractionLabel>): ArrayList<FakeNode> {
        val fakeNodesList = ArrayList<FakeNode>()

        var count = 1
        val recursiveNodes = HashMap<String, ConcreteNode>()

        if (graph.incomingEdgesOf(root).size == 1) recursiveNodes[count++.toString()] = root
        graph.vertexSet().forEach { node ->
            if (node is ConcreteNode && graph.incomingEdgesOf(node).size > 1) recursiveNodes[count++.toString()] = node
        }

        recursiveNodes.forEach { node ->
            node.run {
                val fakeNode = FakeNode("X${node.key}", node.value)
                graph.addVertex(fakeNode)
                fakeNodesList.add(fakeNode)
                val labels = graph.outgoingEdgesOf(node.value)

                val targets = ArrayList<LabelTarget>()
                labels.forEach { label -> targets.add(LabelTarget(label.copy(), graph.getEdgeTarget(label))) }

                graph.removeAllEdges(ArrayList<ExtractionLabel>(labels))
                targets.forEach { target -> graph.addEdge(fakeNode, target.target, target.lbl) }
            }
        }

        return fakeNodesList
    }

    private fun buildChoreography(root: Node, fakeNodesList: ArrayList<FakeNode>, graph: DefaultDirectedGraph<Node, ExtractionLabel>): Choreography {
        val main = bh(root, graph, fakeNodesList)
        val procedures = ArrayList<ProcedureDefinition>()
        for (fk in fakeNodesList) {
            procedures.add(ProcedureDefinition(fk.procedureName, bh(fk, graph, fakeNodesList), HashSet()))
        }

        return Choreography(main as Behaviour, procedures)
    }

    private fun bh(node: Node, graph: DefaultDirectedGraph<Node, ExtractionLabel>, fakeNodesList: ArrayList<FakeNode>): CCNode {
        val edges = graph.outgoingEdgesOf(node)

        when (edges.size) {
            0 -> {
                if (node is ConcreteNode) {
                    for (fakeNode in fakeNodesList) {
                        if (fakeNode.node == node) return ProcedureInvocation(fakeNode.procedureName, HashSet())
                    }
                    for (process in node.network.processes) {
                        if (process.value.main !is TerminationSP) {
                            //log.debug(node.toString())
                            throw Exception("Bad graph. No more edges found, but not all processesInChoreography were terminated.")
                        } else return Termination()
                    }
                }
            }
            1 -> {
                val edge = edges.first()
                return when (edge) {
                    is SendingLabel -> {
                        val com = Communication(edge.sender, edge.receiver, edge.expression)
                        CommunicationSelection(com, bh(graph.getEdgeTarget(edge), graph, fakeNodesList))
                    }
                    is SelectionLabel -> {
                        val sel = Selection(edge.receiver, edge.sender, edge.label)
                        CommunicationSelection(sel, bh(graph.getEdgeTarget(edge), graph, fakeNodesList))
                    }
                    is MulticomLabel -> {
                        val actions = ArrayList<Interaction>()
                        for (label in edge.labels) {
                            when (label) {
                                is SelectionLabel -> {
                                    actions.add(Selection(label.snd, label.rcv, label.label))
                                }
                                is SendingLabel -> {
                                    actions.add(Communication(label.snd, label.rcv, label.expr))
                                }
                                else -> throw NotImplementedError()
                            }
                        }
                        Multicom(actions, bh(graph.getEdgeTarget(edge), graph, fakeNodesList))
                    }
                    else -> throw Exception("Unexpected label type, can't build choreography")
                }
            }
            2 -> {
                val left = edges.first()
                val right = edges.last()

                return when (left) {
                    is ThenLabel -> Condition(left.process, left.expression, bh(graph.getEdgeTarget(left), graph, fakeNodesList), bh(graph.getEdgeTarget(right), graph, fakeNodesList))
                    is ElseLabel -> Condition(left.process, left.expression, bh(graph.getEdgeTarget(right), graph, fakeNodesList), bh(graph.getEdgeTarget(left), graph, fakeNodesList))
                    else -> throw Exception("Bad graph. Was waiting for conditional edges, but got unexpected type.")
                }
            }
            else -> throw Exception("Bad graph. A node has more than 2 outgoing edges.")
        }
        return Termination()
    }
    //endregion

    //region Unfold
    private fun unfold(p: String, processTerm: ProcessTerm): Boolean {
        val processTermMain = processTerm.main

        return if (processTermMain is ProcedureInvocationSP) {
            val processTermProcedure = processTermMain.procedure
            val procedureBehaviour = processTerm.procedures[processTermProcedure]

            processTerm.main = procedureBehaviour?.copy() ?: throw Exception("Can't unfold the process")

            if (procedureBehaviour is ProcedureInvocationSP) {
                unfold(p, processTerm)
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
            if (p.value.main !is TerminationSP) return false
        }
        return true
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
            newNode = ConcreteNode(targetNetwork, str, nextNodeId(), ArrayList(predecessorNode.badNodesList), marking)
            newNode.badNodesList.add(predecessorNode.id)
        }

        return newNode
    }

    private fun addToHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        hashesMap.compute(hash) { _, value ->
            if (value == null) {
                val array = ArrayList<ConcreteNode>()
                assert(array.add(newNode))
                array
            } else {
                assert(value.add(newNode))
                value
            }
        }
    }

    private fun removeFromHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        hashesMap.compute(hash) { _, value ->
            if (value != null) {
                assert(value.remove(newNode))
                value
            } else {
                throw Exception("Vertex is in the graph but not registered in the map of hashes")
            }
        }
    }

    private fun addNodeAndEdgeToGraph(currentNode: ConcreteNode, newNode: ConcreteNode, label: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        //check if we can add a new node and an edge
        return if (graph.addVertex(newNode) && graph.addEdge(currentNode, newNode, label)) {
            addToChoicePathMap(newNode)
            addToHashMap(newNode)
            true
        } else false
    }

    private fun removeNodeFromGraph(graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>, removingNode: ConcreteNode) {
        graph.removeVertex(removingNode)
        removeFromHashMap(removingNode)
        removeFromChoicePathMap(removingNode)
    }

    private fun addEdgeToGraph(nn: ConcreteNode, newnode: ConcreteNode, lbl: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        if (checkPrefix(newnode)) {
            val l = checkLoop(nn, newnode, lbl, graph)
            if (l) {
                assert(graph.addEdge(nn, newnode, lbl))
                assert(graph.edgeSet().contains(lbl))
                return true
            }
        }
        badLoopCnt++
        return false
    }
    //endregion

    //region Checkloop and choicePaths manipulations
    private fun checkLoop(source_node: ConcreteNode, target_node: ConcreteNode, lbl: ExtractionLabel, graph: DefaultDirectedGraph<ConcreteNode, ExtractionLabel>): Boolean {
        if (lbl.flipped) return true

        if (target_node.equals(source_node)) return false

        // if (!target_node.badNodesList.badset.contains(source_node)) {
        return if (!source_node.badNodesList.contains(target_node.id)) {
            val nodeset = HashSet<ConcreteNode>()
            nodeset.addAll(nodeset)
            nodeset.add(source_node)

            val tomark = HashSet<ConcreteNode>()
            tomark.addAll(recompute(target_node, graph, tomark))

            true
        } else false

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
        addToChoicePathMap(ConcreteNode(node.network, key, node.id, node.badNodesList, node.marking))
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
        choicePaths.compute(node.choicePath) { _, value ->
            if (value == null) {
                val array = ArrayList<ConcreteNode>()
                array.add(node)
                array
            } else {
                value.add(node)
                value
            }
        }
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
    data class ConcreteNode(val network: Network, val choicePath: String, val id: Int, val badNodesList: ArrayList<Int>, val marking: HashMap<ProcessName, Boolean>) : Node {
        @Suppress("UNCHECKED_CAST")
        fun copy(): ConcreteNode {
            val networkCopy = network.copy()
            val badNodesListCopy = ArrayList<Int>()

            badNodesList.forEach { badNodesListCopy.add(it) }

            return ConcreteNode(networkCopy, choicePath, id, badNodesListCopy, marking.clone() as HashMap<ProcessName, Boolean>)
        }

        fun equals(other: ConcreteNode): Boolean {
            return network == other.network && choicePath == other.choicePath && badNodesList == other.badNodesList && marking == other.marking
        }
    }

    data class FakeNode(val procedureName: String, val node: Node) : Node
    //endregion

    //region Utils
    private fun copyAndSortProcesses(node: ConcreteNode, strategy: Strategy): HashMap<String, ProcessTerm> {
        val net = HashMap<String, ProcessTerm>()
        node.network.processes.forEach { k, v -> net[k] = v.copy() }
        return strategy.sort(node.marking, net)
    }

    private fun createInteractionLabel(p: String, nodesorted: HashMap<String, ProcessTerm>): InteractionLabel? {
        val pmain = nodesorted[p]?.main
        return when (pmain) {
            is SendingSP -> {
                SendingLabel(p, pmain.receiver, pmain.expression)
            }
            is SelectionSP -> {
                SelectionLabel(p, pmain.receiver, pmain.expression)
            }
            else -> null
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

    private fun fillWaiting(waiting: ArrayList<InteractionLabel>, actions: ArrayList<InteractionLabel>, label: InteractionLabel, receiverProcessTermMain: IBehaviour, receiverProcesses: HashMap<String, IBehaviour>, marking: HashMap<ProcessName, Boolean>): IBehaviour? {
        when (receiverProcessTermMain) {
            is SendingSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val sendingContinuation = fillWaiting(waiting, actions, label, receiverProcessTermMain.continuation, receiverProcesses, marking)

                return if (sendingContinuation != null) {
                    val newLabel = SendingLabel(label.rcv, receiverProcessTermMain.receiver, receiverProcessTermMain.expression)
                    if (!actions.contains(newLabel)) waiting.add(newLabel)
                    SendingSP(sendingContinuation, newLabel.rcv, newLabel.expr)
                } else {
                    null
                }
            }
            is SelectionSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val selectionContinuation = fillWaiting(waiting, actions, label, receiverProcessTermMain.continuation, receiverProcesses, marking)

                if (selectionContinuation != null) {
                    val newLabel = SelectionLabel(label.rcv, receiverProcessTermMain.receiver, receiverProcessTermMain.expression)
                    if (!actions.contains(newLabel)) waiting.add(newLabel)
                    return SelectionSP(selectionContinuation, label.rcv, label.expr)
                }
            }
            is ReceiveSP -> if (label.snd == receiverProcessTermMain.sender && label is SendingLabel) return receiverProcessTermMain.continuation

            is OfferingSP -> if (label.snd == receiverProcessTermMain.sender && label is SelectionLabel) return receiverProcessTermMain.branches[label.label]!!

            is ProcedureInvocationSP -> {
                val newProcessTerm = ProcessTerm(receiverProcesses, receiverProcessTermMain)
                // if (!marking.get(label.rcv)!!) {
                unfold(label.rcv, newProcessTerm)
                marking[label.rcv] = true
                //}
                return fillWaiting(waiting, actions, label, newProcessTerm.main, receiverProcesses, marking)
            }
        }
        return null
    }
    //endregion
}