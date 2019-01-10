package util

import antlrgen.ChoreographyLexer
import antlrgen.ChoreographyParser
import ast.cc.nodes.Program
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
}