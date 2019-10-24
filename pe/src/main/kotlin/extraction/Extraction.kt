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
import util.NetworkUsedProcesses
import util.NetworkUtils
import util.networkStatistics.WellFormedness
import java.lang.IllegalStateException

typealias ProcessMap = HashMap<String, ProcessTerm>
typealias CommunicationEdgeTo = Pair<Network, ExtractionLabel.InteractionLabel>

data class ConditionEdgesTo(val thenNetwork: Network, val thenLabel: ExtractionLabel.ConditionLabel.ThenLabel, val elseNetwork: Network, val elseLabel: ExtractionLabel.ConditionLabel.ElseLabel)
typealias Marking = HashMap<ProcessName, Boolean>
typealias Hash = Int

/**
 * services are allowed to be livelocked
 */
class Extraction(private val strategy: ExtractionStrategy, private val services: Set<String>) {
    private enum class BuildGraphResult {
        OK,
        BADLOOP,
        FAIL
    }
    
    companion object {
        fun extractChoreography(n: String, strategy: ExtractionStrategy = ExtractionStrategy.Default, services: List<String> = arrayListOf()): Program {
            val inputNetwork = NetworkUtils.purgeNetwork(ParseUtils.stringToNetwork(n))
            if ( !WellFormedness.compute(inputNetwork) ) {
                return Program(listOf(null), emptyList())
            }

//            val r = Extraction(strategy, HashSet(services)).extract(inputNetwork)
//            return Program(listOf(r.first), listOf(r.second))

            val parallelNetworks = splitNetwork(inputNetwork)

            if (parallelNetworks == null) {
                return Program(emptyList(), emptyList())
            }

            val results =
                    parallelNetworks.parallelStream().map { network ->
                        arrayListOf(Extraction(strategy, HashSet(services)).extract(network))
                    }.reduce(ArrayList()) { list1, list2 ->
                        val ret = ArrayList(list1)
                        ret.addAll(list2)
                        ret
                    }

            results.sortBy { pair -> pair.first.toString() }

            return Program(results.map { it.first }, results.map { it.second })
        }

        private fun splitNetwork(network: Network): HashSet<Network>? {
            val processSets = getProcessSets(network)
            if ( processSets == null ) return null
            val networks = hashSetOf<Network>()

            for (processSet in processSets) {
                val processes = hashMapOf<ProcessName, ProcessTerm>()
                processSet.forEach { process -> processes[process] = network.processes[process]!! }
                networks.add(Network(processes))
            }

            return networks
        }

        private fun getProcessSets(network: Network): ArrayList<HashSet<String>>? {
            val map = HashMap<ProcessName, HashSet<ProcessName>>()
            network.processes.forEach { (processName, processTerm) ->
                map[processName] = NetworkUsedProcesses.compute(processTerm)
            }

            val processSets = ArrayList<HashSet<ProcessName>>()
            val unprocessed = HashSet<ProcessName>(network.processes.keys)
            while (unprocessed.isNotEmpty()) {
                val component = HashSet<ProcessName>()
                val processName = unprocessed.first()
                component.add(processName)
                unprocessed.remove(processName)

                val visible = map[processName]!!
                visible.add(processName)
                while (visible != component) {
                    val intersection = (visible - component) //.sorted()
                    try {
                        intersection.forEach { component.add(it); unprocessed.remove(it); visible.addAll(map[it]!!) }
                    } catch( e:NullPointerException ) {
                        return null
                    }
                }

                processSets.add(component)
            }

            return processSets
        }
    }

    private var nodeIdCounter = 0
    private val nodeHashes = HashMap<Hash, ArrayList<ConcreteNode>>()
    private var choicePaths = HashMap<String, ArrayList<ConcreteNode>>() //global map of processes used in badNodes loop calculations
    private var badLoopCounter = 0
    private val graph: DirectedPseudograph<Node, ExtractionLabel> = DirectedPseudograph(ExtractionLabel::class.java)

    /**
     * 1. build graph with nodes as networks and edges as body actions
     * 2. remove cycles from the graph
     * 3. traverse the graph reading body actions
     * @param n the network to extract from
     * @return the extracted choreography
     */
    @Suppress("UNCHECKED_CAST")
    private fun extract(n: Network): Pair<Choreography?, GraphStatistics> {
        val marking = HashMap<ProcessName, Boolean>()

        //we mark as visited processes which has no active actions in the main name or which are in the list of livelocked processes
        n.processes.forEach { (name, term) -> marking[name] = term.main is TerminationSP || services.contains(name) }

        val node = ConcreteNode(network = n, choicePath = "0", id = nextNodeId(), badNodes = HashSet(), marking = marking)
        graph.addVertex(node)
        addToChoicePathMap(node)
        addToHashMap(node)

        return if (buildGraph(node) == BuildGraphResult.OK) {
            val procedureNodes = unrollGraph(node)
            Pair(buildChoreography(node, procedureNodes), GraphStatistics(graph.vertexSet().size, badLoopCounter))
        } else {
            Pair(null, GraphStatistics(graph.vertexSet().size, badLoopCounter))
        }
    }

    private fun fold(unfoldedProcesses: HashSet<String>, targetNetwork: Network, currentNode: ConcreteNode) = unfoldedProcesses.forEach { targetNetwork.processes[it]?.main = currentNode.network.processes[it]?.main?.copy()!! }

    private fun buildGraph(currentNode: ConcreteNode): BuildGraphResult {
        val unfoldedProcesses = HashSet<String>()
        val processes = copyAndSortProcesses(currentNode)

        // If the process is calling a procedure, try to unfold it
        processes.forEach { (processName, processTerm) ->
            if (unfold(processName, processTerm))
                unfoldedProcesses.add(processName)
        }

        // Try to find an interaction or a conditional
        for ((processName, processTerm) in processes) {
            val unfoldedProcessesCopy = HashSet(unfoldedProcesses)

            // Try to find an interaction
            val communication = findCommunication(processes, processName, processTerm)
            if (communication != null) {
                val (targetNetwork, label) = communication
                // revert unfolding all processes not involved in the communication
                unfoldedProcessesCopy.removeAll(arrayOf(label.sender, label.receiver))
                fold(unfoldedProcessesCopy, targetNetwork, currentNode)

                when ( buildCommunication(targetNetwork, label, currentNode) ) {
                    BuildGraphResult.OK -> return BuildGraphResult.OK
                    BuildGraphResult.FAIL -> return BuildGraphResult.FAIL
                }
                continue
            }

            // Try to find a conditional
            val conditional = findConditional(processes, processName, processTerm)
            if (conditional != null) {
                val (thenNetwork, thenLabel, elseNetwork, elseLabel) = conditional
                unfoldedProcessesCopy.remove(thenLabel.process)
                fold(unfoldedProcessesCopy, thenNetwork, currentNode)
                fold(unfoldedProcessesCopy, elseNetwork, currentNode)

                when ( buildConditional(thenNetwork, thenLabel, elseNetwork, elseLabel, currentNode) ) {
                    BuildGraphResult.OK -> return BuildGraphResult.OK
                    BuildGraphResult.FAIL -> return BuildGraphResult.FAIL
                }
                continue
            }
        }

        // Check if there are no possible actions left
        // This is after the previous loop because we might need to unfold processes to realise this
        if (allTerminated(processes)) return BuildGraphResult.OK

        //try to find a multi-action communication
//        for ((processName, _) in processes) {
//            //TODO check this
//            val interactionLabel = createInteractionLabel(processName, processes)
//            if (interactionLabel != null) {
//                val actions = ArrayList<ExtractionLabel.InteractionLabel>()
//                val waiting = ArrayList<ExtractionLabel.InteractionLabel>()
//                waiting.add(interactionLabel)
//                collectMulticomActions(waiting, actions, processes, currentNode)
//
//                //if we managed to collect some actions for a multicom
//                if ( actions.isEmpty() ) continue
//                else when (buildMulticom(actions, currentNode, processes, unfoldedProcesses)) {
//                    BuildGraphResult.OK -> return BuildGraphResult.OK
//                    BuildGraphResult.FAIL -> return BuildGraphResult.FAIL
//                }
//                continue
//            }
//        }

        //System.err.println("No possible actions at the node $currentNode")
        return BuildGraphResult.FAIL
    }

    private fun buildCommunication(targetNetwork: Network, label: ExtractionLabel.InteractionLabel, currentNode: ConcreteNode): BuildGraphResult {
        val targetMarking = Marking(currentNode.marking)

        targetMarking[label.sender] = true
        targetMarking[label.receiver] = true

        // if all processes reduced, reset the marking
        if (targetMarking.values.all { it })
            flipAndResetMarking(label, targetMarking, targetNetwork)

        // check if a node with same network and marking is already in the graph
        val node = findNodeInGraph(targetNetwork, targetMarking, currentNode)

        if (node == null) { /* case 1 */
            val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)

            return if (addNodeAndEdgeToGraph(currentNode, newNode, label)) { // TODO: This test is actually unnecessary (the side effects are not)
                when(val res = buildGraph(newNode)) {
                    BuildGraphResult.OK -> BuildGraphResult.OK
                    else -> {
                        removeNodeFromGraph(newNode)
                        res
                    }
                }
            } else {
                BuildGraphResult.FAIL
            }
        } else { /* case 2 */
            /*
            If targetNode already exists, then the whole subgraph from that node is already built.
            So we don't need to update any badNodes list.
            */
            return if( addEdgeToGraph(currentNode, node, label) ) {
                BuildGraphResult.OK
            } else {
                BuildGraphResult.BADLOOP
            }
        }
    }

    private fun buildConditional(thenNetwork: Network, thenLabel: ExtractionLabel.ConditionLabel.ThenLabel, elseNetwork: Network, elseLabel: ExtractionLabel.ConditionLabel.ElseLabel, currentNode: ConcreteNode): BuildGraphResult {
        val targetMarking = Marking(currentNode.marking)

        targetMarking[thenLabel.process] = true

        if (targetMarking.values.all { it }) {
            flipAndResetMarking(thenLabel, targetMarking, thenNetwork)
            flipAndResetMarking(elseLabel, targetMarking, elseNetwork)
        }

        //check if the then node with the same network and markings already exists in the graph
        val thenNode = findNodeInGraph(thenNetwork, targetMarking, currentNode)
        val newThenNode: ConcreteNode

        /* case 4 */
        if (thenNode == null) {
            newThenNode = createNewNode(thenNetwork, thenLabel, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newThenNode, thenLabel)

            when( val res = buildGraph(newThenNode) ) {
                BuildGraphResult.BADLOOP, BuildGraphResult.FAIL -> {
                    removeNodeFromGraph(newThenNode)
                    return res
                }
            }
        }
        /* case 5 */
        else {
            newThenNode = thenNode
            if (!addEdgeToGraph(currentNode, thenNode, thenLabel))
                return BuildGraphResult.BADLOOP
        }

        //check if the else node with the same network and markings already exists in the graph
        val elseNode = findNodeInGraph(elseNetwork, targetMarking, currentNode)
        val newElseNode: ConcreteNode

        val garbageCollectThen = {
            if (thenNode == null) {
                removeNodesFromGraph(newThenNode.choicePath)
            } else {
                graph.removeEdge(currentNode, newThenNode)
            }
        }

        /* case 7 */
        if (elseNode == null) {
            newElseNode = createNewNode(elseNetwork, elseLabel, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newElseNode, elseLabel)

            when( val res = buildGraph(newElseNode) ) {
                BuildGraphResult.BADLOOP, BuildGraphResult.FAIL -> {
                    garbageCollectThen()
                    removeNodeFromGraph(newElseNode)
                    return res
                }
            }
        }
        /* case 8 */
        else {
            newElseNode = elseNode
            if (!addEdgeToGraph(currentNode, newElseNode, elseLabel)) {
                garbageCollectThen()
                return BuildGraphResult.BADLOOP
            }
        }

//        relabel(newThenNode)
//        relabel(newElseNode)
        return BuildGraphResult.OK
    }

    private fun collectMulticomActions(waiting: ArrayList<ExtractionLabel.InteractionLabel>, actions: ArrayList<ExtractionLabel.InteractionLabel>, processes: HashMap<String, ProcessTerm>, currentNode: ConcreteNode):Boolean {
        val receivers = ArrayList<String>()
        while (waiting.isNotEmpty()) {
            val label = waiting.first()
            waiting.remove(label)

            //multicom can't contain actions with the same receiver
            if (receivers.contains(label.receiver)) {
                return false
//                throw MulticomException("multicom can't contain actions with the same receiver")
            }
            actions.add(label)
            receivers.add(label.receiver)

            val receiver = label.receiver
            val sender = label.sender

            val receiverProcessTerm = processes[receiver]

            @Suppress("UNCHECKED_CAST")
            val marking = currentNode.marking.clone() as HashMap<ProcessName, Boolean>

            //fill the list of waiting actions with sending/selection actions from the receiverTerm process behaviour if it is in correct format
            //return the new receiverTerm pb if success and null otherwise. waiting list is populated implicitly
            //TODO("potential bug here - waiting might be populated even if fillWaiting returns null")
            val newReceiverBehaviour = fillWaiting(waiting, actions, label, receiverProcessTerm!!.main, processes[receiver]!!.procedures, marking)
            if (newReceiverBehaviour != null) {
                val senderProcessTerm = processes[sender]
                val senderBehaviour = senderProcessTerm!!.main

                processes.replace(receiver, ProcessTerm(receiverProcessTerm.procedures, newReceiverBehaviour))
                val senderBehaviourContinuation = (senderBehaviour as? SendSP)?.continuation
                        ?: ((senderBehaviour as? SelectionSP)?.continuation
                                ?: throw UnsupportedOperationException())

                processes.replace(sender, ProcessTerm(senderProcessTerm.procedures, senderBehaviourContinuation))
            }
        }

        return true
    }

    private fun buildMulticom(actions: ArrayList<ExtractionLabel.InteractionLabel>, currentNode: ConcreteNode, processesCopy: HashMap<String, ProcessTerm>, unfoldedProcesses: HashSet<String>): BuildGraphResult {
        val label = ExtractionLabel.MulticomLabel(actions)

        val targetMarking = Marking(currentNode.marking)
        val targetNetwork = Network(processesCopy)

        //fold back unfoldedProcesses procedures that were not participating in communication
        label.labels.forEach {
            targetMarking[it.sender] = true
            targetMarking[it.receiver] = true
            unfoldedProcesses.removeAll(arrayOf(it.sender, it.receiver))
        }

        fold(unfoldedProcesses, targetNetwork, currentNode)

        //if all procedures were visited, flip all markings
        if (targetMarking.values.all { it }) flipAndResetMarking(label, targetMarking, targetNetwork)

        //check if the eta with the same network and markings already exists in the graph
        val existingNode = findNodeInGraph(targetNetwork, targetMarking, currentNode)

        if (existingNode == null) {
            val newNode = createNewNode(targetNetwork, label, currentNode, targetMarking)
            addNodeAndEdgeToGraph(currentNode, newNode, label)
            return buildGraph(newNode)
        } else {
            if (addEdgeToGraph(currentNode, existingNode, label)) return BuildGraphResult.OK
        }

        return BuildGraphResult.FAIL
    }

    // We look for a node with same content in the same choice path.
    // There is at most one such node, since we never add it if it's there and closing the loop would make a bad loop.
    private fun findNodeInGraph(targetNetwork: Network, targetMarking: Marking, currentNode: ConcreteNode): ConcreteNode? {
        val existingNodes = nodeHashes[hash(targetNetwork, targetMarking)]?.filter { currentNode.choicePath.startsWith(it.choicePath) }
        return existingNodes?.firstOrNull { it.network == targetNetwork && it.marking == targetMarking }
    }

    /***
     * if all processes in the target node were already exploited we need to set it to the initial state
     * we mark the label between the source and target node as it was "flipped" and
     * we wash all previous markings from the target node
     */
    private fun flipAndResetMarking(label: ExtractionLabel, marking: Marking, network: Network) {
        label.flipped = true
        for (key in marking.keys) {
            marking[key] = isTerminated( network.processes[key]!!.main, network.processes[key]!!.procedures ) || services.contains(key)
        }
    }

    private fun isTerminated( b:Behaviour, procedures:Map<String, Behaviour>):Boolean {
        return when( b ) {
            is TerminationSP -> true
            is ProcedureInvocationSP -> isTerminated( procedures[b.procedure]!!, procedures )
            else -> false
        }
    }

    private fun unrollGraph(root: ConcreteNode): ArrayList<InvocationNode> {
        val invocations = ArrayList<InvocationNode>()

        var count = 1
        val recursiveNodes = HashMap<String, ConcreteNode>()

        if (graph.incomingEdgesOf(root).size == 1)
            recursiveNodes["X${count++}"] = root

        graph.vertexSet().forEach {
            if (it is ConcreteNode && graph.incomingEdgesOf(it).size > 1)
                recursiveNodes["X${count++}"] = it
        }

        recursiveNodes.forEach { pair ->
            val invocationNode = InvocationNode(pair.key, pair.value)
            graph.addVertex(invocationNode)
            invocations.add(invocationNode)

            val incomingEdges = HashSet(graph.incomingEdgesOf(pair.value))
            incomingEdges.forEach {
                val source = graph.getEdgeSource(it)
                graph.removeEdge(it)
                graph.addEdge(source, invocationNode, it)
            }
        }

        return invocations
    }

    private fun buildChoreography(root: Node, invocationNodes: ArrayList<InvocationNode>): Choreography {
        val mainInvokers = invocationNodes.filter { it.node === root }

        val main =
                if (mainInvokers.isNotEmpty()) {
                    ProcedureInvocation(mainInvokers.first().procedureName)
                } else {
                    buildChoreographyBody(root)
                }

        val procedures = ArrayList<ProcedureDefinition>()
        for (procedureNode in invocationNodes) {
            procedures.add(ProcedureDefinition(procedureNode.procedureName, buildChoreographyBody(procedureNode.node), HashSet()))
        }

        return Choreography(main, procedures)
    }

    private fun buildChoreographyBody(node: Node): ChoreographyBody {
        val edges = graph.outgoingEdgesOf(node)

        when (edges.size) {
            0 -> {
                return when (node) {
                    is ConcreteNode -> {
                        if (node.network.processes.all { isTerminated( it.value.main, it.value.procedures ) }) {
                            Termination()
                        } else {
                            throw IllegalStateException("Bad graph. No more edges found, but not all processes were terminated.")
                        }
                    }
                    is InvocationNode -> ProcedureInvocation(node.procedureName)
                    else -> throw IllegalStateException("Ill-formed graph: unknown node type")
                }
            }
            1 -> {
                return when (val edge = edges.first()) {
                    is ExtractionLabel.InteractionLabel.CommunicationLabel -> CommunicationSelection(Communication(edge.sender, edge.receiver, edge.expression), buildChoreographyBody(graph.getEdgeTarget(edge)))
                    is ExtractionLabel.InteractionLabel.SelectionLabel -> CommunicationSelection(Selection(edge.receiver, edge.sender, edge.label), buildChoreographyBody(graph.getEdgeTarget(edge)))

                    is ExtractionLabel.MulticomLabel -> {
                        val actions = ArrayList<Interaction>()
                        for (label in edge.labels) {
                            when (label) {
                                is ExtractionLabel.InteractionLabel.SelectionLabel -> actions.add(Selection(label.sender, label.receiver, label.label))
                                is ExtractionLabel.InteractionLabel.CommunicationLabel -> actions.add(Communication(label.sender, label.receiver, label.expression))

                            }
                        }
                        Multicom(actions, buildChoreographyBody(graph.getEdgeTarget(edge)))
                    }

                    is ExtractionLabel.ConditionLabel -> {
                        throw IllegalStateException("Unary condition label in graph")
                    }
                }
            }
            2 -> {
                val thenLabel = edges.filterIsInstance<ExtractionLabel.ConditionLabel.ThenLabel>().first()
                val elseLabel = edges.filterIsInstance<ExtractionLabel.ConditionLabel.ElseLabel>().first()

                return Condition(thenLabel.process, thenLabel.expression, buildChoreographyBody(graph.getEdgeTarget(thenLabel)), buildChoreographyBody(graph.getEdgeTarget(elseLabel)))
            }
            else -> throw IllegalStateException("Bad graph. A node has more than 2 outgoing edges.")
        }
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
    private fun findCommunication(processes: ProcessMap, processName: String, processTerm: ProcessTerm): CommunicationEdgeTo? {
        when (val behaviour = processTerm.main) {
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

    private fun consumeCommunication(processes: ProcessMap, senderTerm: ProcessTerm, receiverTerm: ProcessTerm): CommunicationEdgeTo {
        val processCopy = copyProcesses(processes)
        val receiverName = (senderTerm.main as SendSP).receiver
        val senderName = (receiverTerm.main as ReceiveSP).sender

        processCopy.replace(senderName, ProcessTerm(senderTerm.procedures, (senderTerm.main as SendSP).continuation))
        processCopy.replace(receiverName, ProcessTerm(receiverTerm.procedures, (receiverTerm.main as ReceiveSP).continuation))

        val label = ExtractionLabel.InteractionLabel.CommunicationLabel(senderName, receiverName, (senderTerm.main as SendSP).expression)

        return CommunicationEdgeTo(Network(processCopy), label)
    }

    private fun consumeSelection(processes: ProcessMap, offerTerm: ProcessTerm, selectTerm: ProcessTerm): CommunicationEdgeTo? {
        val processCopy = copyProcesses(processes)

        val selectionProcess = (offerTerm.main as OfferingSP).process
        val offeringProcess = (selectTerm.main as SelectionSP).process

        val offeringBehavior = (offerTerm.main as OfferingSP).branches[(selectTerm.main as SelectionSP).label] ?: return null

        processCopy.replace(offeringProcess, ProcessTerm(offerTerm.procedures, offeringBehavior))
        processCopy.replace(selectionProcess, ProcessTerm(selectTerm.procedures, (selectTerm.main as SelectionSP).continuation))

        val label = ExtractionLabel.InteractionLabel.SelectionLabel(offeringProcess, selectionProcess, (selectTerm.main as SelectionSP).label)

        return CommunicationEdgeTo(Network(processCopy), label)
    }

    private fun findConditional(processes: ProcessMap, processName: String, processTerm: ProcessTerm): ConditionEdgesTo? {
        if (processTerm.main !is ConditionSP)
            return null

        val conditional = processTerm.main as ConditionSP
        val thenProcessesMap = HashMap<String, ProcessTerm>(processes)
        val elseProcessesMap = HashMap<String, ProcessTerm>(processes)

        thenProcessesMap.replace(processName, ProcessTerm(processTerm.procedures, conditional.thenBehaviour))
        elseProcessesMap.replace(processName, ProcessTerm(processTerm.procedures, conditional.elseBehaviour))

        return ConditionEdgesTo(Network(thenProcessesMap), ExtractionLabel.ConditionLabel.ThenLabel(processName, conditional.expression), Network(elseProcessesMap), ExtractionLabel.ConditionLabel.ElseLabel(processName, conditional.expression))
    }
    //endregion

    //region Manipulations with nodes in the graph and auxiliary data structures
    private fun createNewNode(targetNetwork: Network, label: ExtractionLabel, predecessorNode: ConcreteNode, marking: HashMap<ProcessName, Boolean>): ConcreteNode {
        val choicePath = when (label) {
            is ExtractionLabel.ConditionLabel.ThenLabel -> "${predecessorNode.choicePath}0"
            is ExtractionLabel.ConditionLabel.ElseLabel -> "${predecessorNode.choicePath}1"
            else -> predecessorNode.choicePath
        }

        val badNodes = HashSet<Int>()
        if (!label.flipped) {
            badNodes.addAll(predecessorNode.badNodes)
            badNodes.add(predecessorNode.id)
        }

        return ConcreteNode(targetNetwork, choicePath, nextNodeId(), badNodes, marking)
    }

    private fun addToHashMap(newNode: ConcreteNode) {
        val hash = hash(newNode.network, newNode.marking)
        nodeHashes.compute(hash) { _, value ->
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

    private fun removeFromHashMap(newNode: ConcreteNode) = nodeHashes.remove(hash(newNode.network, newNode.marking))


    private fun addNodeAndEdgeToGraph(currentNode: ConcreteNode, newNode: ConcreteNode, label: ExtractionLabel): Boolean {
        if (graph.addVertex(newNode)) {
            if (graph.addEdge(currentNode, newNode, label)) {
                addToChoicePathMap(newNode)
                addToHashMap(newNode)
                return true
            } else graph.removeVertex(newNode)
        }
        return false
    }

    private fun removeNodesFromGraph(choicePath: String) {
        for ((path, nodes) in choicePaths) {
            if (path.startsWith(choicePath)) {
                nodes.forEach {
                    graph.removeVertex(it)
                    removeFromHashMap(it)
                }
                nodes.clear()
            }
        }
    }

    private fun removeNodeFromGraph(n: ConcreteNode) {
        graph.removeVertex(n)
        removeFromHashMap(n)
        removeFromChoicePathMap(n)
    }

    private fun addEdgeToGraph(sourceNode: ConcreteNode, targetNode: ConcreteNode, label: ExtractionLabel): Boolean {
//        if (checkPrefix(targetNode) && checkLoop(sourceNode, targetNode, label)) {
//            return graph.addEdge(sourceNode, targetNode, label)
//        }

        if (checkLoop(sourceNode, targetNode, label)) {
            return graph.addEdge(sourceNode, targetNode, label)
        }

        badLoopCounter++
        return false
    }
    //endregion

    //region Checkloop and choicePaths manipulations
    private fun checkLoop(sourceNode: ConcreteNode, targetNode: ConcreteNode, label: ExtractionLabel): Boolean {
        if (label.flipped) return true
        if (targetNode === sourceNode) return false
        return !sourceNode.badNodes.contains(targetNode.id)
    }

//    private fun relabel(node: ConcreteNode) {
//        val key = node.choicePath.dropLast(1)
//        addToChoicePathMap(ConcreteNode(node.network, key, node.id, node.badNodes, node.marking))
//        removeFromChoicePathMap(node)
//    }

//    private fun checkPrefix(n: ConcreteNode): Boolean {
//        for ((path, nodes) in choicePaths) {
//            if (path.startsWith(n.choicePath) && nodes.isNotEmpty())
//                return true
//        }
//        println("Ping!")
//        return false
//    }

    private fun removeFromChoicePathMap(node: ConcreteNode) = choicePaths[node.choicePath]?.remove(node)

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

    class MulticomException(override val message: String) : Exception(message)
    //endregion

    //region Data classes and interfaces
    interface Node

    data class ConcreteNode(val network: Network, val choicePath: String, val id: Int, val badNodes: HashSet<Int>, val marking: HashMap<ProcessName, Boolean>) : Node {
        fun equals(other: ConcreteNode): Boolean = id == other.id

        override fun hashCode(): Int = id
                //network.hashCode() + 31 * choicePath.hashCode() + 31 * 31 * id + 31 * 31 * 31 * badNodes.hashCode() + 31 * 31 * 31 * 31 * marking.hashCode()
    }

    data class InvocationNode(val procedureName: String, val node: Node) : Node
    //endregion

    //region Utils
    private fun copyAndSortProcesses(node: ConcreteNode): HashMap<String, ProcessTerm> {
        //val net = HashMap<String, ProcessTerm>()
        //node.network.processes.forEach { (k, v) -> net[k] = v.copy() }
        return strategy.copyAndSort(node)
    }

    private fun createInteractionLabel(processName: String, processes: HashMap<String, ProcessTerm>): ExtractionLabel.InteractionLabel? {
        return when (val processTerm = processes[processName]?.main) {
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

    private fun hash(network: Network, marking: Marking): Int = network.hashCode() + 31 * marking.hashCode()

    private fun copyProcesses(processes: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
        val copy = HashMap<String, ProcessTerm>()
        processes.forEach { (t, u) -> copy[t] = u.copy() }
        return copy
    }

    private fun fillWaiting(waiting: ArrayList<ExtractionLabel.InteractionLabel>, actions: ArrayList<ExtractionLabel.InteractionLabel>, label: ExtractionLabel.InteractionLabel, receiverProcessTermMain: Behaviour, receiverProcesses: HashMap<String, Behaviour>, marking: HashMap<ProcessName, Boolean>)
            : Behaviour? {
        when (receiverProcessTermMain) {
            is SendSP -> {
                //look into continuation to be sure that it is in the form a1;...;ak;r?;B where each ai is either the sending of a value or a label selection
                val sendingContinuation = fillWaiting(waiting, actions, label, receiverProcessTermMain.continuation, receiverProcesses, marking)

                if (sendingContinuation != null) {
                    val newLabel = ExtractionLabel.InteractionLabel.CommunicationLabel(label.receiver, receiverProcessTermMain.receiver, receiverProcessTermMain.expression)
                    if (!actions.contains(newLabel)) waiting.add(newLabel)
                    return SendSP(sendingContinuation, label.receiver, label.expression)
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