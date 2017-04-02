// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ChoreographyParser}.
 */
public interface ChoreographyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#choreography}.
	 * @param ctx the parse tree
	 */
	void enterChoreography(ChoreographyParser.ChoreographyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#choreography}.
	 * @param ctx the parse tree
	 */
	void exitChoreography(ChoreographyParser.ChoreographyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(ChoreographyParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(ChoreographyParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#procedureDefinition}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#procedureDefinition}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void enterProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void exitProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#internal_choreography}.
	 * @param ctx the parse tree
	 */
	void enterInternal_choreography(ChoreographyParser.Internal_choreographyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#internal_choreography}.
	 * @param ctx the parse tree
	 */
	void exitInternal_choreography(ChoreographyParser.Internal_choreographyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#external_choreography}.
	 * @param ctx the parse tree
	 */
	void enterExternal_choreography(ChoreographyParser.External_choreographyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#external_choreography}.
	 * @param ctx the parse tree
	 */
	void exitExternal_choreography(ChoreographyParser.External_choreographyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#communication}.
	 * @param ctx the parse tree
	 */
	void enterCommunication(ChoreographyParser.CommunicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#communication}.
	 * @param ctx the parse tree
	 */
	void exitCommunication(ChoreographyParser.CommunicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#send}.
	 * @param ctx the parse tree
	 */
	void enterSend(ChoreographyParser.SendContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#send}.
	 * @param ctx the parse tree
	 */
	void exitSend(ChoreographyParser.SendContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#choose}.
	 * @param ctx the parse tree
	 */
	void enterChoose(ChoreographyParser.ChooseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#choose}.
	 * @param ctx the parse tree
	 */
	void exitChoose(ChoreographyParser.ChooseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#sendingProcess}.
	 * @param ctx the parse tree
	 */
	void enterSendingProcess(ChoreographyParser.SendingProcessContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#sendingProcess}.
	 * @param ctx the parse tree
	 */
	void exitSendingProcess(ChoreographyParser.SendingProcessContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#receivingProcess}.
	 * @param ctx the parse tree
	 */
	void enterReceivingProcess(ChoreographyParser.ReceivingProcessContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#receivingProcess}.
	 * @param ctx the parse tree
	 */
	void exitReceivingProcess(ChoreographyParser.ReceivingProcessContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#firstExpression}.
	 * @param ctx the parse tree
	 */
	void enterFirstExpression(ChoreographyParser.FirstExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#firstExpression}.
	 * @param ctx the parse tree
	 */
	void exitFirstExpression(ChoreographyParser.FirstExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#secondExpression}.
	 * @param ctx the parse tree
	 */
	void enterSecondExpression(ChoreographyParser.SecondExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#secondExpression}.
	 * @param ctx the parse tree
	 */
	void exitSecondExpression(ChoreographyParser.SecondExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ChoreographyParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ChoreographyParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(ChoreographyParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(ChoreographyParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#procedure}.
	 * @param ctx the parse tree
	 */
	void enterProcedure(ChoreographyParser.ProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#procedure}.
	 * @param ctx the parse tree
	 */
	void exitProcedure(ChoreographyParser.ProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#process}.
	 * @param ctx the parse tree
	 */
	void enterProcess(ChoreographyParser.ProcessContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#process}.
	 * @param ctx the parse tree
	 */
	void exitProcess(ChoreographyParser.ProcessContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(ChoreographyParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(ChoreographyParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ChoreographyParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ChoreographyParser.ValueContext ctx);
}