package util

import antlrgen.ChoreographyLexer
import antlrgen.ChoreographyParser
import antlrgen.NetworkLexer
import antlrgen.NetworkParser
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

object ParseUtils {
    fun parseChoreography(choreography: String): ChoreographyParser.ProgramContext
    {
        val stream = ANTLRInputStream(choreography)
        val lexer = ChoreographyLexer(stream)
        val parser = ChoreographyParser(CommonTokenStream(lexer))
        return parser.program()
    }

    fun stringToProgram(choreography: String): Program = ChoreographyASTToProgram.toProgram(parseChoreography(choreography))

    fun parseNetwork(network: String): NetworkParser.NetworkContext {
        val stream = ANTLRInputStream(network)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        return parser.network()
    }

    fun stringToNetwork(network: String): Network = NetworkASTToNetwork.toNetwork(parseNetwork(network))
}