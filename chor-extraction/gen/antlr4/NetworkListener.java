// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NetworkParser}.
 */
public interface NetworkListener extends ParseTreeListener {
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
	 * Enter a parse tree produced by {@link NetworkParser#processBehaviour}.
	 * @param ctx the parse tree
	 */
	void enterProcessBehaviour(NetworkParser.ProcessBehaviourContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#processBehaviour}.
	 * @param ctx the parse tree
	 */
	void exitProcessBehaviour(NetworkParser.ProcessBehaviourContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#procedureDefinition}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDefinition(NetworkParser.ProcedureDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#procedureDefinition}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDefinition(NetworkParser.ProcedureDefinitionContext ctx);
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
	 * Enter a parse tree produced by {@link NetworkParser#interaction}.
	 * @param ctx the parse tree
	 */
	void enterInteraction(NetworkParser.InteractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#interaction}.
	 * @param ctx the parse tree
	 */
	void exitInteraction(NetworkParser.InteractionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#sending}.
	 * @param ctx the parse tree
	 */
	void enterSending(NetworkParser.SendingContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#sending}.
	 * @param ctx the parse tree
	 */
	void exitSending(NetworkParser.SendingContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#receiving}.
	 * @param ctx the parse tree
	 */
	void enterReceiving(NetworkParser.ReceivingContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#receiving}.
	 * @param ctx the parse tree
	 */
	void exitReceiving(NetworkParser.ReceivingContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#selection}.
	 * @param ctx the parse tree
	 */
	void enterSelection(NetworkParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#selection}.
	 * @param ctx the parse tree
	 */
	void exitSelection(NetworkParser.SelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#offering}.
	 * @param ctx the parse tree
	 */
	void enterOffering(NetworkParser.OfferingContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#offering}.
	 * @param ctx the parse tree
	 */
	void exitOffering(NetworkParser.OfferingContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#labeledBehaviour}.
	 * @param ctx the parse tree
	 */
	void enterLabeledBehaviour(NetworkParser.LabeledBehaviourContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#labeledBehaviour}.
	 * @param ctx the parse tree
	 */
	void exitLabeledBehaviour(NetworkParser.LabeledBehaviourContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(NetworkParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(NetworkParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void enterProcedureInvocation(NetworkParser.ProcedureInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void exitProcedureInvocation(NetworkParser.ProcedureInvocationContext ctx);
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
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(NetworkParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(NetworkParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(NetworkParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(NetworkParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(NetworkParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(NetworkParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code refExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRefExpr(NetworkParser.RefExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code refExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRefExpr(NetworkParser.RefExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(NetworkParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link NetworkParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(NetworkParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link NetworkParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(NetworkParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link NetworkParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(NetworkParser.OperandContext ctx);
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
}