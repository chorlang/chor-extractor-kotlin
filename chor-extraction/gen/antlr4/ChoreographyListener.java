// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ChoreographyParser}.
 */
public interface ChoreographyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ChoreographyParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ChoreographyParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ChoreographyParser.ProgramContext ctx);
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
	 * Enter a parse tree produced by {@link ChoreographyParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(ChoreographyParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(ChoreographyParser.MainContext ctx);
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
	 * Enter a parse tree produced by {@link ChoreographyParser#interaction}.
	 * @param ctx the parse tree
	 */
	void enterInteraction(ChoreographyParser.InteractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#interaction}.
	 * @param ctx the parse tree
	 */
	void exitInteraction(ChoreographyParser.InteractionContext ctx);
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
	 * Enter a parse tree produced by {@link ChoreographyParser#selection}.
	 * @param ctx the parse tree
	 */
	void enterSelection(ChoreographyParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ChoreographyParser#selection}.
	 * @param ctx the parse tree
	 */
	void exitSelection(ChoreographyParser.SelectionContext ctx);
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