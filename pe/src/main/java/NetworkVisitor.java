// Generated from Network.g4 by ANTLR 4.7
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
	 * Visit a parse tree produced by {@link NetworkParser#network}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNetwork(NetworkParser.NetworkContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#processBehaviour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcessBehaviour(NetworkParser.ProcessBehaviourContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#procedureDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDefinition(NetworkParser.ProcedureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#behaviour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBehaviour(NetworkParser.BehaviourContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#interaction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteraction(NetworkParser.InteractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#sending}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSending(NetworkParser.SendingContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#receiving}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReceiving(NetworkParser.ReceivingContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(NetworkParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#offering}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOffering(NetworkParser.OfferingContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#labeledBehaviour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledBehaviour(NetworkParser.LabeledBehaviourContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(NetworkParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#procedureInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureInvocation(NetworkParser.ProcedureInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(NetworkParser.ExpressionContext ctx);
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
	 * Visit a parse tree produced by {@link NetworkParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(NetworkParser.ValueContext ctx);
}