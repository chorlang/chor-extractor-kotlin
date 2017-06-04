package extraction

import ast.sp.interfaces.ExtractionLabel
import ast.sp.labels.Communication
import ast.sp.labels.Else
import ast.sp.labels.Selection
import ast.sp.labels.Then
import ast.sp.nodes.*
import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class NetworkExtraction @Throws(Exception::class)
constructor(network: Network) {
    private val graph: DirectedGraph<Network, ExtractionLabel>

    init {
        graph = DefaultDirectedGraph<Network, ExtractionLabel>(ExtractionLabel::class.java)
        graph.addVertex(network)

        val root = Network(ArrayList<ProcessBehaviour>())
        val tmp = Network(ArrayList<ProcessBehaviour>())
        root.network.addAll(network.network)
        tmp.network.addAll(network.network)

        buildGraph(root, tmp)
    }


    private fun buildGraph(n: Network, tmp: Network) {
        val findComm = findCommunication(tmp)
        if (findComm.sending.isPresent || findComm.selection.isPresent) {
            val (tmp, lbl) = getCommunication(tmp, findComm)
            commit(n, lbl, tmp)

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

    data class GetCommunication(val sending: Optional<ProcessBehaviour>, val selection: Optional<ProcessBehaviour>)

    private fun findCommunication(n: Network): GetCommunication {
        val sendreceive = n.network.stream().filter { i -> i.main is Sending &&
                n.network.stream().filter { j -> j.main is Receiving && i.activeProcess == (j.main as Receiving).process}.findFirst().isPresent}.
                findAny()

        val offerselect = n.network.stream().filter { i -> i.main is SelectionSP &&
                n.network.stream().filter { j -> j.main is Offering && i.activeProcess == (j.main as Offering).process}.findFirst().isPresent}.
                findAny()

        return  GetCommunication(sendreceive, offerselect)

    }

    private fun getCommunication(n: Network, findComm: GetCommunication): Pair<Network, ExtractionLabel> {
        if (findComm.sending.isPresent){
            val sending = findComm.sending.get()
            val sendingMain = sending.main as Sending
            val receivingList = n.network.stream().filter { i -> i.main is Receiving && n.network.stream().filter { i.activeProcess == sendingMain.process }.findFirst().isPresent }
            val receiving = receivingList.findAny().get()

            val pbl: MutableList<ProcessBehaviour> = n.network.stream().filter { i -> i != sending && i != receiving }.collect(Collectors.toList())
            pbl.add(ProcessBehaviour(sending.activeProcess, sending.procedures, sendingMain.continuation))
            pbl.add(ProcessBehaviour(receiving.activeProcess, receiving.procedures, (receiving.main as Receiving).continuation))

            val label = Communication(sending.activeProcess, receiving.activeProcess, sendingMain.expression)

            return Pair(Network(pbl), label)

        } else if (findComm.selection.isPresent) {
            val selection = findComm.selection.get()
            val selectionMain = selection.main as SelectionSP
            val offering = n.network.stream().
                    filter { i -> i.main is Offering && n.network.stream().
                            filter { i.activeProcess == selectionMain.process }.findFirst().isPresent}.
                    findFirst().get()

            val pbl: MutableList<ProcessBehaviour> = n.network.stream().filter { n -> n != selection && n != offering }.collect(Collectors.toList())
            val offeringMain = offering.main as Offering
            pbl.add(ProcessBehaviour(offering.activeProcess, offering.procedures, offeringMain.labels.get(selectionMain.expression)))
            pbl.add(ProcessBehaviour(selection.activeProcess, selection.procedures, selectionMain.continuation))

            val label = Selection(offering.activeProcess, offering.activeProcess, selectionMain.expression)

            return Pair(Network(pbl), label)

        } else throw IllegalArgumentException("getCommunication invoked, but no communication found")
    }

    private fun findCondition(n: Network): Boolean {
        return n.network.stream().filter { i -> i.main is ConditionSP }.findFirst().isPresent
    }

    private fun getCondition(n: Network):ResultCondition {
        val conditionPB = n.network.stream().filter { i -> i.main is ConditionSP }.findFirst().get()
        val conditionMain = conditionPB.main as ConditionSP

        val listOfPBT: MutableList<ProcessBehaviour> = n.network.stream().filter { n -> n != conditionPB }.collect(Collectors.toList())
        val listOfPBE = ArrayList<ProcessBehaviour>(listOfPBT);

        listOfPBT.add(ProcessBehaviour(conditionPB.activeProcess, conditionPB.procedures, conditionMain.thenBehaviour))
        listOfPBE.add(ProcessBehaviour(conditionPB.activeProcess, conditionPB.procedures, conditionMain.elseBehaviour))

        return ResultCondition(Network(listOfPBT), Then(conditionMain.process, conditionMain.expression), Network(listOfPBE), Else(conditionMain.process, conditionMain.expression))
    }

    private fun canUnfold(n: Network): Boolean {
        return n.network.stream().filter { i -> i.main is ProcedureInvocationSP}.findFirst().isPresent
    }

    private fun notTerminated(n: Network): Boolean {
        return n.network.stream().filter { i -> i.main !is TerminationSP}.findFirst().isPresent
    }

    data class ResultCondition(val tmp1: Network, val lb1: ExtractionLabel, val tmp2: Network, val lbl2: ExtractionLabel)

    private fun commit(n1: Network, label: ExtractionLabel, n2: Network){
        val source = graph.vertexSet().stream().filter { i -> i.toString() == n1.toString() }.findAny()
        val target = graph.vertexSet().stream().filter { i -> i.toString() == n2.toString() }.findAny()

        if (!target.isPresent){
            graph.addVertex(n2);
            graph.addEdge(source.get(), n2, label);
            buildGraph(n2, Network(object : ArrayList<ProcessBehaviour>() {init { addAll(n2.network) } }))
        } else {
            graph.addEdge(source.get(), target.get(), label);
        }
    }

    private fun unfold(n: Network): Network {
        val list = n.network.stream().filter { i -> i.main is ProcedureInvocationSP }.collect(Collectors.toList())
        n.network.removeAll(list)

        list.forEach { i ->  run {
            val findFirst = i.procedures.stream().filter { j -> j.procedure == (i.main as ProcedureInvocationSP).procedure }
                .findFirst()?.get()
            val pb = ProcessBehaviour(i.activeProcess, i.procedures, findFirst?.behaviour)
            pb.setVisitedProcedures(findFirst?.procedure)
            n.network.add(pb)}
        }

        return n
    }

    private fun wash(n: Network): Network {
        val numberOfUnfolfedPD = n.network.stream().filter { i -> i.visitedProcedures.size == i.procedures.size }.count()
        if (n.network.size.equals(numberOfUnfolfedPD.toInt())){
            n.network.stream().forEach { i -> i.clearVisitedProcedures() }
        }
        return n
    }
}
