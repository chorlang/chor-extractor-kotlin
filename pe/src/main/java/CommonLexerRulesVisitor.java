// Generated from CommonLexerRules.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CommonLexerRulesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CommonLexerRulesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CommonLexerRulesParser#process}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcess(CommonLexerRulesParser.ProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonLexerRulesParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(CommonLexerRulesParser.ProcedureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonLexerRulesParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CommonLexerRulesParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommonLexerRulesParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(CommonLexerRulesParser.ValueContext ctx);
}