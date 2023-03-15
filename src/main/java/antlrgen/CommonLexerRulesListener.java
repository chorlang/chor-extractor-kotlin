// Generated from CommonLexerRules.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CommonLexerRulesParser}.
 */
public interface CommonLexerRulesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CommonLexerRulesParser#process}.
	 * @param ctx the parse tree
	 */
	void enterProcess(CommonLexerRulesParser.ProcessContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommonLexerRulesParser#process}.
	 * @param ctx the parse tree
	 */
	void exitProcess(CommonLexerRulesParser.ProcessContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommonLexerRulesParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(CommonLexerRulesParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommonLexerRulesParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(CommonLexerRulesParser.ProcedureContext ctx);
}