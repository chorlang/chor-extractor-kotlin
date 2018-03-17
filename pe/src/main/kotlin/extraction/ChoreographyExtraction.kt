package extraction

import ast.cc.nodes.Program
import ast.sp.nodes.Network
import NetworkLexer
import NetworkParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

class ChoreographyExtraction @Throws(Exception::class) constructor(grammar: String) {
    var network: Network

    init {
        val stream = ANTLRInputStream(grammar)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        val tree = parser.network()
        val networkVisitor = NetworkVisitor()
        network = networkVisitor.visitNetwork(tree) as Network
    }

    fun extractChoreography(): Program {
        return NetworkExtraction().extract(network)
    }

}
