// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NetworkParser}.
 */
public interface NetworkListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NetworkParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(NetworkParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(NetworkParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#network}.
	 * @param ctx the parse tree
	 */
	void enterNetwork(NetworkParser.NetworkContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#network}.
	 * @param ctx the parse tree
	 */
	void exitNetwork(NetworkParser.NetworkContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#behaviour}.
	 * @param ctx the parse tree
	 */
	void enterBehaviour(NetworkParser.BehaviourContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#behaviour}.
	 * @param ctx the parse tree
	 */
	void exitBehaviour(NetworkParser.BehaviourContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(NetworkParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(NetworkParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(NetworkParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(NetworkParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#process}.
	 * @param ctx the parse tree
	 */
	void enterProcess(NetworkParser.ProcessContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#process}.
	 * @param ctx the parse tree
	 */
	void exitProcess(NetworkParser.ProcessContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(NetworkParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(NetworkParser.ProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(NetworkParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(NetworkParser.LabelContext ctx);
}