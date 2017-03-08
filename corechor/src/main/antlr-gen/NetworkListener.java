// Generated from Network.g4 by ANTLR 4.5
import org.antlr.v4.runtime.misc.NotNull;
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
}