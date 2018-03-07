package base


import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ExtractionLabel
import ast.sp.nodes.*
import extraction.NetworkExtraction
import org.jgrapht.graph.DefaultDirectedGraph
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class EqualityTest : Assert() {
    @Test
    fun test() {

        /* creating offering object */
        val labels = HashMap<String, Behaviour>()
        labels.put("R", TerminationSP())
        labels.put("L", TerminationSP())
        val off = Offering("p", labels)
        val network = TreeMap<String, ProcessBehaviour>()
        network.put("q", ProcessBehaviour(HashMap<String, ProcedureDefinitionSP>(), off))
        val node = Network(network)

        /* put node to the graph */
        val graph = DefaultDirectedGraph<NetworkExtraction.Node, ExtractionLabel>(ExtractionLabel::class.java)
        graph.addVertex(NetworkExtraction.Node(node, "0", HashSet<NetworkExtraction.Node>()))

        val copy = node.copy()
        val result = graph.vertexSet().stream().filter { i -> i.nodenet.equals(copy) }.findAny()
        assert(result.isPresent)
    }
}