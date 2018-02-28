package extraction

import ast.cc.interfaces.CCNode
import ast.cc.nodes.Termination
import ast.sp.nodes.Network
import gen.NetworkLexer
import gen.NetworkParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

class ChoreographyExtraction {

    @Throws(Exception::class)
    fun extractChoreography(grammar: String): CCNode {
        try {
            val stream = ANTLRInputStream(grammar)
            val lexer = NetworkLexer(stream)
            val parser = NetworkParser(CommonTokenStream(lexer))
            val tree = parser.network()
            val networkVisitor = NetworkVisitor()
            val sp = networkVisitor.visitNetwork(tree) as Network
            val np = NetworkExtraction(sp).extract()
        } catch (e: Exception) {
            println(e.message)
        }

        return Termination()
    }
}
