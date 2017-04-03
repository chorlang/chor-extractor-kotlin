// Generated from /home/fmontesi/programs/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link NetworkParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface NetworkVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link NetworkParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(NetworkParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#network}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNetwork(NetworkParser.NetworkContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#behaviour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBehaviour(NetworkParser.BehaviourContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(NetworkParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(NetworkParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#process}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcess(NetworkParser.ProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(NetworkParser.ProcedureContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(NetworkParser.LabelContext ctx);
}