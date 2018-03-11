package base

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.ExtractionLabel
import ast.sp.nodes.*
import extraction.ChoreographyExtraction
import extraction.NetworkExtraction
import extraction.NetworkExtraction.ConcreteNode
import extraction.NetworkExtraction.Node
import org.jgrapht.graph.DefaultDirectedGraph
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class DeepCopyTest : Assert() {
    @Test
    fun test() {
        val grammar = "p {def X {q!<e>;X} main {X}} | q {def Y{p?; Y} main {Y}} | r { main {stop}}"
        val ce = ChoreographyExtraction(grammar)

        val a = ce.network
        val b = ce.network.copy()

        val ne = NetworkExtraction()
        for (p in a.network) ne.unfold(p.key, a.network)

        assert(!a.equals(b))
    }
}