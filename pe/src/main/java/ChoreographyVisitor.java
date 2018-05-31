// Generated from Choreography.g4 by ANTLR 4.7.1
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
	 * Visit a parse tree produced by {@link ChoreographyParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ChoreographyParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#procedureDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(ChoreographyParser.MainContext ctx);
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
	 * Visit a parse tree produced by {@link ChoreographyParser#procedureInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#interaction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteraction(ChoreographyParser.InteractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#communication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommunication(ChoreographyParser.CommunicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(ChoreographyParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ChoreographyParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#process}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcess(ChoreographyParser.ProcessContext ctx);
	/**
	 * Visit a parse tree produced by {@link ChoreographyParser#procedure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedure(ChoreographyParser.ProcedureContext ctx);
}