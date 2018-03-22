package epp

import ChoreographyLexer
import ChoreographyParser
import ast.sp.nodes.Network
import ast.sp.nodes.ProcessBehaviour
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import java.util.*


class EndPointProjection {
    private fun parse(grammar: String): ChoreographyParser.ProgramContext {

        val stream = ANTLRInputStream(grammar)
        val lexer = ChoreographyLexer(stream)
        val parser = ChoreographyParser(CommonTokenStream(lexer))
        val tree = parser.program()
        return tree
    }

    fun project(choreography: String): Network {
        val tree = this.parse(choreography)
        val network = HashMap<String, ProcessBehaviour>()

        val choreographyVisitor = ChoreographyVisitor()
        val ccast = choreographyVisitor.getCCAST(tree)
        val processes = choreographyVisitor.processes

        val behaviourProjection = BehaviourProjection()
        for (process in processes) {
            network.put(process, behaviourProjection.getSPAST(ccast, process) as ProcessBehaviour)
        }
        return Network(network)
    }
}