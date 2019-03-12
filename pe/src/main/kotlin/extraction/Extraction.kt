@file:Suppress("DEPRECATION")

package extraction

import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.interfaces.Behaviour
import ast.sp.labels.ExtractionLabel
import ast.sp.nodes.*
import org.jgrapht.graph.DirectedPseudograph
import util.ParseUtils
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

/**
 * services are allowed to be livelocked
 */
// TODO we're assuming that the network is well-formed (all process actions point to other processes that actually exist and there are no self-comms): CHECK FOR THIS!!
class Extraction(private val strategy: Strategy, private val services: ArrayList<String>) {
    companion object {
//        @Throws(Exception::class)
//        @JvmStatic
//        fun main(args: Array<String>) {
//            var strategy = Strategy.Default
//            var network = ""
//            val livelocked = ArrayList<String>()
//            var debugMode = false
//
//            val iterator = args.toList().listIterator()
//            while (iterator.hasNext()) {
//                when (iterator.next()) {
//                    "-s" -> {
//                        val i = iterator.nextIndex()
//                        if (args.size >= i + 1) {
//                            strategy = parseStrategy(args[i])
//                        } else throw Exception("Malformed call - strategy for body generating was expected.")
//                    }
//                    "-c" -> {
//                        val i = iterator.nextIndex()
//                        if (args.size >= i + 1)
//                            network = args[i]
//                        else throw Exception("Malformed call - body was expected.")
//                    }
//                    "-f" -> {
//                        val i = iterator.nextIndex()
//                        if (args.size >= i + 1) {
//                            val f = File(args[i])
//                            network = f.readText()
//                        } else throw Exception("Malformed call - body file name was expected.")
//                    }
//                    "-l" -> {
//                        val i = iterator.nextIndex()
//                        if (args.size >= i + 1) {
//                            val livelockProcesses = args[i]
//                            livelocked.addAll(livelockProcesses.replace(" ", "").split(","))
//                        }
//                    }
//                    "-d" -> {
//                        debugMode = true
//                    }
//
//                }
//            }
//
//            if (network == null)
//                throw IllegalArgumentException("No network provided")
//
//            extractChoreography(network, strategy, livelocked, debugMode)
//        }

//        private fun parseStrategy(strategy: String): Strategy {
//            val s = Strategy.values().find { it.name == strategy }
//            return s ?: Strategy.SelectionFirst
//        }

        fun extractChoreography(network: String, strategy: Strategy = Strategy.Default, livelocked: ArrayList<String> = ArrayList()): Program {
            val network = ParseUtils.stringToNetwork(network)

            // TODO We should split the network into independent parallel components here, to speed up extraction
            val parallelNetworks = ParallelNetworks(arrayListOf(network))

            val program = ArrayList<Choreography?>()
            val statistics = ArrayList<GraphStatistics>()
            for (network in parallelNetworks.networkList) {
                if (livelocked.isEmpty() || network.processes.keys.containsAll(livelocked)) {
                    val extraction = Extraction(strategy, livelocked).extract(network)
                    program.add(extraction.first)
                    statistics.add(extraction.second)
                } else throw IllegalArgumentException("List of services processes contains not existing processes")
            }
            return Program(program, statistics)
        }
    }

    private var nodeIdCounter = 0
    private val nodeHashMap = HashMap<NodeHash, ArrayList<ConcreteNode>>()
    private var choicePaths = HashMap<String, ArrayList<ConcreteNode>>() // global map of nodes used in badNodesList loop calculations
    private var badLoopCounter = 0

    /**
     * 1. build graph with nodes as networks and edges as body actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading body actions
     * @param network the network to extract from
     * @return the extracted choreography
     */
    @Suppress("UNCHECKED_CAST")
    private fun extract(network: Network): Pair<Choreography?, GraphStatistics> {
        val graph = DirectedPseudograph<Node, ExtractionLabel>(ExtractionLabel::class.java)
        val marking = HashMap<ProcessName, Boolean>()

        // We initially mark all processes that are terminated or services.
        network.processes.forEach { name, term -> marking[name] = term.main is TerminationSP || services.contains(name) }

        val firstNode = ConcreteNode(network = network, choicePath = "0", id = nextNodeId(), badNodesList = ArrayList(), marking = marking)
        graph.addVertex(firstNode)
        addToChoicePathMap(firstNode)
        addToNodeHashMap(firstNode)

        return if (buildGraph(firstNode, graph as DirectedPseudograph<ConcreteNode, ExtractionLabel>)) {
            val unrolledGraphNodesList = unrollGraph(firstNode, graph)
            Pair(buildChoreography(firstNode, unrolledGraphNodesList, graph), GraphStatistics(graph.vertexSet().size, badLoopCounter))
        } else {
            Pair(null, GraphStatistics(graph.vertexSet().size, badLoopCounter))
        }
    }

    private fun buildCommunication(findCommunication: GraphNode, currentNode: ConcreteNode, unfoldedProcesses: HashSet<String>, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        val (targetNetwork, label) = findCommunication

        //remove processes that were unfoldedProcesses but don't participate in the current communication
        @Suppress("UNCHECKED_CAST")
        val targetMarking = currentNode.marking.clone() as Marking

        markProcess(targetMarking, label.receiver, unfoldedProcesses)
        markProcess(targetMarking, label.sender, unfoldedProcesses)

        // revert unfolding all processesInChoreography not involved in the communication
        revertUnfolding(unfoldedProcesses, targetNetwork, currentNode)

        //if all processes were "fired", flip all markings
        if (targetMarking.values.all { it }) flipAndWash(label, targetMarking, targetNetwork)

        //check if the eta with the same network and markings already exists in the graph
        val node = findNodeInGraph(targetNetwork, targetMarking, currentNode)

        /* case 1 */
        return if (node == null) {
            val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
            //if the edge can not be added - return false
            addNodeAndEdgeToGraph(currentNode, newNode, label, graph)

            if (buildGraph(newNode, graph)) true else {
                removeNodeFromGraph(graph, newNode)
                false
            }
        }
        /* case 2 */
        else addEdgeToGraph(currentNode, node, label, graph)
    }

    private fun markProcess(targetMarking: Marking, processName: String, unfoldedProcesses: HashSet<String>) {
        targetMarking[processName] = true
        unfoldedProcesses.remove(processName)
    }

    private fun revertUnfolding(unfoldedProcesses: HashSet<String>, targetNetwork: Network, currentNode: ConcreteNode) = unfoldedProcesses.forEach { targetNetwork.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }


    private fun buildGraph(currentNode: ConcreteNode, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        val unfoldedProcesses = HashSet<String>()
        val processes = copyAndSortProcesses(currentNode)

        // If the process is calling a procedure, try to unfold it
        processes.forEach { (processName, processTerm) ->
            if (unfold(processName, processTerm))
                unfoldedProcesses.add(processName)
        }

        // Try to find an interaction or a conditional
        for ((processName, processTerm) in processes) {
            // Copy all processes, for backtracking
            val processesCopy = copyProcesses(processes)

            // Try to find an interaction
            val communication = findCommunication(processesCopy, processName, processTerm)
            if (communication != null) {
                if (buildCommunication(communication, currentNode, unfoldedProcesses, graph)) return true else continue
            }

            // Try to find a conditional
            val conditional = findConditional(processesCopy, processName, processTerm)
            if (conditional != null) {
                if (buildConditional(conditional, currentNode, unfoldedProcesses, graph)) return true else continue
            }
        }

        // Check if there are no possible actions left
        // This is after the previous loop because we might need to unfold processes to realise this
        if (allTerminated(processes)) return true

        //try to find a multi-action communication
        for ((processName, processTerm) in processes) {
            val processesCopy = copyProcesses(processes)
            val actions = ArrayList<ExtractionLabel.InteractionLabel>()
            val waiting = ArrayList<ExtractionLabel.InteractionLabel>()

            val interactionLabel = createInteractionLabel(processName, processesCopy)
            if (interactionLabel != null) waiting.add(interactionLabel)
            val receivers = ArrayList<String>()

            collectMulticomActions(waiting, receivers, actions, processesCopy, currentNode)

            //if we managed to collect some actions for a multicom
            if (actions.size >= 2) {
                if (buildMulticom(actions, currentNode, processesCopy, unfoldedProcesses, graph)) return true else continue
            }

        }

        //System.err.println("No possible actions at eta $currentNode")
        return false
    }

    private fun buildConditional(condition: ResultCondition, currentNode: ConcreteNode, unfoldedProcesses: HashSet<String>, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        val (targetNetworkThen, labelThen, targetNetworkElse, labelElse) = condition
        val targetMarking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

        if (unfoldedProcesses.contains(labelThen.process)) markProcess(targetMarking, labelThen.process, unfoldedProcesses)

        revertUnfolding(unfoldedProcesses, targetNetworkThen, currentNode)
        revertUnfolding(unfoldedProcesses, targetNetworkElse, currentNode)

        if (targetMarking.values.all { it }) {
            flipAndWash(labelThen, targetMarking, targetNetworkThen)
            flipAndWash(labelElse, targetMarking, targetNetworkElse)
        }

        //check if the eta with the same network and markings already exists in the graph
        val nodeThen = findNodeInGraph(targetNetworkThen, targetMarking, currentNode)
        val newNodeThen: ConcreteNode

        /* case4 */
        if (nodeThen == null) {
            newNodeThen = createNewNode(targetNetworkThen, labelThen, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newNodeThen, labelThen, graph)

            if (!buildGraph(newNodeThen, graph)) {
                removeNodeFromGraph(graph, newNodeThen)
                return false
            }
        }
        /* case 5 */
        else {
            newNodeThen = nodeThen
            if (!addEdgeToGraph(currentNode, nodeThen, labelThen, graph)) return false
        }

        //check if the eta with the same network and markings already exists in the graph
        val nodeElse = findNodeInGraph(targetNetworkElse, targetMarking, currentNode)
        val newNodeElse: ConcreteNode

        /* case 7 */
        if (nodeElse == null) {
            newNodeElse = createNewNode(targetNetworkElse, labelElse, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newNodeElse, labelElse, graph)

            if (!buildGraph(newNodeElse, graph)) {
                if (nodeThen == null) {
                    removeNodeFromGraph(graph, newNodeThen)
                } else graph.removeEdge(currentNode, newNodeThen)
                removeNodeFromGraph(graph, newNodeElse)
                return false
            }
        }
        /* case 8 */
        else {
            newNodeElse = nodeElse
            if (!addEdgeToGraph(currentNode, newNodeElse, labelElse, graph)) {
                if (nodeThen == null) removeNodeFromGraph(graph, newNodeThen)
                else graph.removeEdge(currentNode, newNodeThen)
                return false
            }
        }

        relabel(newNodeThen)
        relabel(newNodeElse)
        return true
    }

    private fun collectMulticomActions(waiting: ArrayList<ExtractionLabel.InteractionLabel>, receivers: ArrayList<String>, actions: ArrayList<ExtractionLabel.InteractionLabel>, processesCopy: HashMap<String, ProcessTerm>, currentNode: ConcreteNode) {
        while (!waiting.isEmpty()) {
            val label = waiting.first()
            waiting.remove(label)

            //multicom can't contain actions with the same receiver
            if (receivers.contains(label.receiver)) throw MulticomException("multicom can't contain actions with the same receiver")
            actions.add(label)
            receivers.add(label.receiver)

            val receiver = label.receiver
            val sender = label.sender

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
                val senderBehaviourContinuation = (senderBehaviour as? SendSP)?.continuation
                        ?: ((senderBehaviour as? SelectionSP)?.continuation
                                ?: throw UnsupportedOperationException())

                processesCopy.replace(sender, ProcessTerm(senderProcessTerm.procedures, senderBehaviourContinuation))
            }
        }
    }

    private fun buildMulticom(actions: ArrayList<ExtractionLabel.InteractionLabel>, currentNode: ConcreteNode, processesCopy: HashMap<String, ProcessTerm>, unfoldedProcesses: HashSet<String>, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        val label = ExtractionLabel.MulticomLabel(actions)

        @Suppress("UNCHECKED_CAST")
        val targetMarking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>
        val targetNetwork = Network(processesCopy)

        //fold back unfoldedProcesses procedures that were not participating in communication
        label.labels.forEach {
            markProcess(targetMarking, it.receiver, unfoldedProcesses)
            markProcess(targetMarking, it.sender, unfoldedProcesses)
        }

        revertUnfolding(unfoldedProcesses, targetNetwork, currentNode)

        //if all procedures were visited, flip all markings
        if (targetMarking.values.all { it }) flipAndWash(label, targetMarking, targetNetwork)

        //check if the eta with the same network and markings already exists in the graph
        val existingNode = findNodeInGraph(targetNetwork, targetMarking, currentNode)

        if (existingNode == null) {
            val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newNode, label, graph)
            return buildGraph(newNode, graph)
        } else {
            if (addEdgeToGraph(currentNode, existingNode, label, graph)) return true
        }

        return false
    }

    private fun findNodeInGraph(targetNetwork: Network, targetMarking: Marking, currentNode: ConcreteNode): ConcreteNode? {
        val existingNodes = nodeHashMap[hash(targetNetwork, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
        return existingNodes?.firstOrNull { it.network == targetNetwork && it.marking == targetMarking }
    }

    /***
     * if all processes in the target node were already exploited we need to set it to the initial state
     * we mark the label between the source and target node as it was "flipped" and
     * we wash all previous markings from the target node
     */
    private fun flipAndWash(label: ExtractionLabel, targetMarking: Marking, targetNetwork: Network) {
        label.flipped = true
        wash(targetMarking, targetNetwork.processes)
    }

    private fun unrollGraph(root: ConcreteNode, graph: DirectedPseudograph<Node, ExtractionLabel>): ArrayList<FakeNode> {
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

    private fun buildChoreography(root: Node, fakeNodesList: ArrayList<FakeNode>, graph: DirectedPseudograph<Node, ExtractionLabel>): Choreography {
        val main = bh(root, graph, fakeNodesList)
        val procedures = ArrayList<ProcedureDefinition>()
        for (fk in fakeNodesList) {
            procedures.add(ProcedureDefinition(fk.procedureName, bh(fk, graph, fakeNodesList), HashSet()))
        }

        return Choreography(main, procedures)
    }

    private fun bh(node: Node, graph: DirectedPseudograph<Node, ExtractionLabel>, fakeNodesList: ArrayList<FakeNode>): ChoreographyBody {
        val edges = graph.outgoingEdgesOf(node)

        when (edges.size) {
            0 -> {
                if (node is ConcreteNode) {
                    for (fakeNode in fakeNodesList) {
                        if (fakeNode.node == node) return ProcedureInvocation(fakeNode.procedureName, HashSet())
                    }
                    for (process in node.network.processes) {
                        if (process.value.main !is TerminationSP) {
                            throw Exception("Bad graph. No more edges found, but not all processesInChoreography were terminated.")
                        } else return Termination()
                    }
                }
            }
            1 -> {
                val edge = edges.first()
                return when (edge) {
                    is ExtractionLabel.InteractionLabel.CommunicationLabel -> CommunicationSelection(Communication(edge.sender, edge.receiver, edge.expression), bh(graph.getEdgeTarget(edge), graph, fakeNodesList))
                    is ExtractionLabel.InteractionLabel.SelectionLabel -> CommunicationSelection(Selection(edge.receiver, edge.sender, edge.label), bh(graph.getEdgeTarget(edge), graph, fakeNodesList))

                    is ExtractionLabel.MulticomLabel -> {
                        val actions = ArrayList<Interaction>()
                        for (label in edge.labels) {
                            when (label) {
                                is ExtractionLabel.InteractionLabel.SelectionLabel -> actions.add(Selection(label.sender, label.receiver, label.label))
                                is ExtractionLabel.InteractionLabel.CommunicationLabel -> actions.add(Communication(label.sender, label.receiver, label.expression))

                            }
                        }
                        Multicom(actions, bh(graph.getEdgeTarget(edge), graph, fakeNodesList))
                    }

                    is ExtractionLabel.ConditionLabel -> {
                        TODO("Something very wrong has just happened, the universe should collapse now")
                    }
                }
            }
            2 -> {
                val left = edges.first() as ExtractionLabel.ConditionLabel
                val right = edges.last()

                return when (left) {
                    is ExtractionLabel.ConditionLabel.ThenLabel -> Condition(left.process, left.expression, bh(graph.getEdgeTarget(left), graph, fakeNodesList), bh(graph.getEdgeTarget(right), graph, fakeNodesList))
                    is ExtractionLabel.ConditionLabel.ElseLabel -> Condition(left.process, left.expression, bh(graph.getEdgeTarget(right), graph, fakeNodesList), bh(graph.getEdgeTarget(left), graph, fakeNodesList))
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

    /***
     * if the process is not terminated yet and not in the list of the livelocked processess
     * we mark it as false meaning that it wasn't "fired" yet
     */
    private fun wash(marking: HashMap<ProcessName, Boolean>, processes: ProcessMap) {
        for (key in marking.keys) {
            marking[key] = processes[key]!!.main is TerminationSP || services.contains(key)
        }
    }
    //endregion

    //region Termination checks
    private fun doesNotContainInvocations(pr: Behaviour): Boolean {
        return !containsInvocation(pr)
    }

    private fun containsInvocation(pr: Behaviour): Boolean {
        when (pr) {
            is ProcedureInvocationSP -> return true
            is TerminationSP -> return false
            is SendSP -> return doesNotContainInvocations(pr.continuation)
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
    private fun findCommunication(processes: ProcessMap, processName: String, processTerm: ProcessTerm): GraphNode? {
        val behaviour = processTerm.main

        when (behaviour) {
            is SendSP -> {
                val receiverTerm = processes[behaviour.process]!!
                if (receiverTerm.main is ReceiveSP && (receiverTerm.main as ReceiveSP).sender == processName) {
                    return consumeCommunication(processes, processTerm, receiverTerm)
                }
            }
            is ReceiveSP -> {
                val senderTerm = processes[(processTerm.main as ReceiveSP).process]!!
                if (senderTerm.main is SendSP && (senderTerm.main as SendSP).receiver == processName) {
                    return consumeCommunication(processes, senderTerm, processTerm)
                }
            }
            is SelectionSP -> {
                val offerTerm = processes[behaviour.process]!!
                if (offerTerm.main is OfferingSP && (offerTerm.main as OfferingSP).sender == processName) {
                    return consumeSelection(processes, offerTerm, processTerm)
                }
            }
            is OfferingSP -> {
                val selectTerm = processes[behaviour.process]!!
                if (selectTerm.main is SelectionSP && (selectTerm.main as SelectionSP).receiver == processName) {
                    return consumeSelection(processes, processTerm, selectTerm)
                }
            }
        }

        return null
    }

    private fun consumeCommunication(processes: ProcessMap, senderTerm: ProcessTerm, receiverTerm: ProcessTerm): GraphNode {
        val processCopy = copyProcesses(processes)
        val receiverName = (senderTerm.main as SendSP).receiver
        val senderName = (receiverTerm.main as ReceiveSP).sender

        processCopy.replace(receiverName, ProcessTerm(receiverTerm.procedures, (receiverTerm.main as ReceiveSP).continuation))
        processCopy.replace(senderName, ProcessTerm(senderTerm.procedures, (senderTerm.main as SendSP).continuation))

        val label = ExtractionLabel.InteractionLabel.CommunicationLabel(senderName, receiverName, (senderTerm.main as SendSP).expression)

        return GraphNode(Network(processCopy), label)
    }

    private fun consumeSelection(processes: ProcessMap, offerTerm: ProcessTerm, selectTerm: ProcessTerm): GraphNode {
        val processCopy = copyProcesses(processes)

        val selectionProcess = (offerTerm.main as OfferingSP).process
        val offeringProcess = (selectTerm.main as SelectionSP).process

        val offeringBehavior = (offerTerm.main as OfferingSP).branches[(selectTerm.main as SelectionSP).label]
                ?: throw IllegalStateException("${selectionProcess} is trying to select label ${(selectTerm.main as SelectionSP).label} from ${offeringProcess}, which does not offer it")

        processCopy.replace(offeringProcess, ProcessTerm(offerTerm.procedures, offeringBehavior))
        processCopy.replace(selectionProcess, ProcessTerm(selectTerm.procedures, (selectTerm.main as SelectionSP).continuation))

        val label = ExtractionLabel.InteractionLabel.SelectionLabel(offeringProcess, selectionProcess, (selectTerm.main as SelectionSP).label)

        return GraphNode(Network(processCopy), label)
    }

    private fun findConditional(processes: ProcessMap, processName: String, processTerm: ProcessTerm): ResultCondition? {
        if ( !(processTerm.main is ConditionSP) )
            return null

        val conditional = processTerm.main as ConditionSP
        val thenProcessesMap = HashMap<String, ProcessTerm>(processes)
        val elseProcessesMap = HashMap<String, ProcessTerm>(processes)

        thenProcessesMap.replace(processName, ProcessTerm(processTerm.procedures, conditional.thenBehaviour))
        elseProcessesMap.replace(processName, ProcessTerm(processTerm.procedures, conditional.elseBehaviour))

        return ResultCondition(Network(thenProcessesMap), ExtractionLabel.ConditionLabel.ThenLabel(processName, conditional.expression), Network(elseProcessesMap), ExtractionLabel.ConditionLabel.ElseLabel(processName, conditional.expression))
    }
    //endregion

    //region Manipulations with nodes in the graph and auxiliary data structures
    private fun createNewNode(targetNetwork: Network, label: ExtractionLabel, predecessorNode: ConcreteNode, marking: HashMap<ProcessName, Boolean>): ConcreteNode {
        val str = when (label) {
            is ExtractionLabel.ConditionLabel.ThenLabel -> "${predecessorNode.choicePath}0"
            is ExtractionLabel.ConditionLabel.ElseLabel -> "${predecessorNode.choicePath}1"
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

    private fun addToNodeHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        nodeHashMap.compute(hash) { _, value ->
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

    private fun removeFromNodeHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        nodeHashMap.compute(hash) { _, value ->
            if (value != null) {
                assert(value.remove(newNode))
                value
            } else {
                throw Exception("Vertex is in the graph but not registered in the map of hashes")
            }
        }
    }

    private fun addNodeAndEdgeToGraph(currentNode: ConcreteNode, newNode: ConcreteNode, label: ExtractionLabel, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        //check if we can add a new eta and an edge
        return if (graph.addVertex(newNode) && graph.addEdge(currentNode, newNode, label)) {
            addToChoicePathMap(newNode)
            addToNodeHashMap(newNode)
            true
        } else false
    }

    private fun removeNodeFromGraph(graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>, removingNode: ConcreteNode) {
        graph.removeVertex(removingNode)
        removeFromNodeHashMap(removingNode)
        removeFromChoicePathMap(removingNode)
    }

    private fun addEdgeToGraph(sourceNode: ConcreteNode, targetNode: ConcreteNode, lbl: ExtractionLabel, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
        if (checkPrefix(targetNode) && checkLoop(sourceNode, targetNode, lbl, graph))
            return graph.addEdge(sourceNode, targetNode, lbl)

        badLoopCounter++
        return false
    }
    //endregion

    //region Checkloop and choicePaths manipulations
    private fun checkLoop(source_node: ConcreteNode, target_node: ConcreteNode, lbl: ExtractionLabel, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>): Boolean {
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

    private fun recompute(n: ConcreteNode, graph: DirectedPseudograph<ConcreteNode, ExtractionLabel>, tomark: HashSet<ConcreteNode>): HashSet<ConcreteNode> {
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
                return true //eta.value.first()
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

    data class ResultCondition(val tmp1: Network, val lb1: ExtractionLabel.ConditionLabel.ThenLabel, val tmp2: Network, val lbl2: ExtractionLabel.ConditionLabel.ElseLabel)

    interface Node
    data class ConcreteNode(val network: Network, val choicePath: String, val id: Int, val badNodesList: ArrayList<Int>, val marking: HashMap<ProcessName, Boolean>) : Node {
        @Suppress("UNCHECKED_CAST")
        fun copy(): ConcreteNode {
            val networkCopy = network.copy()
            val badNodesListCopy = ArrayList<Int>()

            badNodesList.forEach { badNodesListCopy.add(it) }

            return ConcreteNode(networkCopy, choicePath, id, badNodesListCopy, marking.clone() as HashMap<ProcessName, Boolean>)
        }

        fun equals(other: ConcreteNode): Boolean = network == other.network && choicePath == other.choicePath && badNodesList == other.badNodesList && marking == other.marking
    }

    data class FakeNode(val procedureName: String, val node: Node) : Node
    //endregion

    //region Utils
    private fun copyAndSortProcesses(node: ConcreteNode): HashMap<String, ProcessTerm> = strategy.copyAndSort(node)

    private fun createInteractionLabel(processName: String, processes: HashMap<String, ProcessTerm>): ExtractionLabel.InteractionLabel? {
        val processTerm = processes[processName]?.main
        return when (processTerm) {
            is SendSP -> {
                ExtractionLabel.InteractionLabel.CommunicationLabel(processName, processTerm.receiver, processTerm.expression)
            }
            is SelectionSP -> {
                ExtractionLabel.InteractionLabel.SelectionLabel(processName, processTerm.receiver, processTerm.label)
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

    private fun copyProcesses(processes: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
        val copy = HashMap<String, ProcessTerm>()
        processes.forEach { t, u -> copy[t] = u.copy() }
        return copy
    }

    private fun fillWaiting(waiting: ArrayList<ExtractionLabel.InteractionLabel>, actions: ArrayList<ExtractionLabel.InteractionLabel>, label: ExtractionLabel.InteractionLabel, receiverProcessTermMain: Behaviour, receiverProcesses: HashMap<String, Behaviour>, marking: HashMap<ProcessName, Boolean>): Behaviour? {
        when (receiverProcessTermMain) {
            is SendSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val sendingContinuation = fillWaiting(waiting, actions, label, receiverProcessTermMain.continuation, receiverProcesses, marking)

                return if (sendingContinuation != null) {
                    val newLabel = ExtractionLabel.InteractionLabel.CommunicationLabel(label.receiver, receiverProcessTermMain.receiver, receiverProcessTermMain.expression)
                    if (!actions.contains(newLabel)) waiting.add(newLabel)
                    SendSP(sendingContinuation, newLabel.receiver, newLabel.expression)
                } else {
                    null
                }
            }
            is SelectionSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val selectionContinuation = fillWaiting(waiting, actions, label, receiverProcessTermMain.continuation, receiverProcesses, marking)

                if (selectionContinuation != null) {
                    val newLabel = ExtractionLabel.InteractionLabel.SelectionLabel(label.receiver, receiverProcessTermMain.receiver, receiverProcessTermMain.label)
                    if (!actions.contains(newLabel)) waiting.add(newLabel)
                    return SelectionSP(selectionContinuation, label.receiver, label.expression)
                }
            }
            is ReceiveSP -> if (label.sender == receiverProcessTermMain.sender && label is ExtractionLabel.InteractionLabel.CommunicationLabel) return receiverProcessTermMain.continuation

            is OfferingSP -> if (label.sender == receiverProcessTermMain.sender && label is ExtractionLabel.InteractionLabel.SelectionLabel) return receiverProcessTermMain.branches[label.label]!!

            is ProcedureInvocationSP -> {
                val newProcessTerm = ProcessTerm(receiverProcesses, receiverProcessTermMain)
                // if (!marking.get(label.receiver)!!) {
                unfold(label.receiver, newProcessTerm)
                marking[label.receiver] = true
                //}
                return fillWaiting(waiting, actions, label, newProcessTerm.main, receiverProcesses, marking)
            }
        }
        return null
    }
    //endregion
}