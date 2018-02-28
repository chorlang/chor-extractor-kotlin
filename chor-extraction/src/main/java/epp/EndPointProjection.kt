package epp

import gen.ChoreographyLexer
import gen.ChoreographyParser
import ast.cc.interfaces.CCNode
import ast.sp.nodes.Network
import ast.sp.nodes.ProcessBehaviour
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import sun.invoke.empty.Empty

import java.util.HashSet
import java.util.TreeMap

class EndPointProjection {
    private fun parse(grammar: String): ChoreographyParser.ProgramContext? {
        try {
            val stream = ANTLRInputStream(grammar)
            val lexer = ChoreographyLexer(stream)
            val parser = ChoreographyParser(CommonTokenStream(lexer!!))
            val tree = parser!!.program()
            return tree
        } catch (e: Exception) {
            println(e.message)
        }
        return null //Need to fix this
    }

    @Throws(MergingException::class)
    fun project(choreography: String): Network {
        val tree = this.parse(choreography)
        val network = TreeMap<String,ProcessBehaviour>()

        if (tree != null) {
            val choreographyVisitor = ChoreographyVisitor()
            val ccast = choreographyVisitor.getCCAST(tree)
            val processes = choreographyVisitor.processes

            val behaviourProjection = BehaviourProjection()
            for (process in processes) {
                network.put(process, behaviourProjection.getSPAST(ccast, process) as ProcessBehaviour)
            }
        }
        return Network(network)
    }
}
