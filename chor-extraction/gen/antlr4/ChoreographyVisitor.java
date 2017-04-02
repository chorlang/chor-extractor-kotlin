// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ChoreographyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ChoreographyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#choreography}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoreography(ChoreographyParser.ChoreographyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ChoreographyParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#procedureDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#procedureInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#internal_choreography}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInternal_choreography(ChoreographyParser.Internal_choreographyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#external_choreography}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternal_choreography(ChoreographyParser.External_choreographyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#communication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommunication(ChoreographyParser.CommunicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#send}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSend(ChoreographyParser.SendContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#choose}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoose(ChoreographyParser.ChooseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#sendingProcess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSendingProcess(ChoreographyParser.SendingProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#receivingProcess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReceivingProcess(ChoreographyParser.ReceivingProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#firstExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFirstExpression(ChoreographyParser.FirstExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#secondExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecondExpression(ChoreographyParser.SecondExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ChoreographyParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(ChoreographyParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(ChoreographyParser.ProcedureContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#process}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcess(ChoreographyParser.ProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(ChoreographyParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ChoreographyParser.ValueContext ctx);
}