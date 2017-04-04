// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NetworkParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, INT=6, TERMINATE=7, IntegerLiteral=8, 
		FloatingPointLiteral=9, BooleanLiteral=10, CharacterLiteral=11, StringLiteral=12, 
		LPAREN=13, RPAREN=14, LBRACE=15, RBRACE=16, LBRACK=17, RBRACK=18, COMMA=19, 
		DOT=20, ASSIGN=21, GT=22, LT=23, BANG=24, TILDE=25, QUESTION=26, COLON=27, 
		EQUAL=28, LE=29, GE=30, NOTEQUAL=31, AND=32, OR=33, INC=34, DEC=35, ADD=36, 
		SUB=37, DIV=38, BITAND=39, CARET=40, MOD=41, ADD_ASSIGN=42, SUB_ASSIGN=43, 
		MUL_ASSIGN=44, DIV_ASSIGN=45, AND_ASSIGN=46, OR_ASSIGN=47, XOR_ASSIGN=48, 
		MOD_ASSIGN=49, LSHIFT_ASSIGN=50, RSHIFT_ASSIGN=51, URSHIFT_ASSIGN=52, 
		Identifier=53, AT=54, ELLIPSIS=55, WS=56, COMMENT=57, LINE_COMMENT=58, 
		Parallel=59, Wildcard=60, Arrow=61, Continue=62;
	public static final int
		RULE_prog = 0, RULE_network = 1, RULE_behaviour = 2, RULE_interaction = 3, 
		RULE_sending = 4, RULE_receiving = 5, RULE_selection = 6, RULE_offering = 7, 
		RULE_condition = 8, RULE_procedureDefinition = 9, RULE_procedureInvocation = 10, 
		RULE_expression = 11, RULE_process = 12, RULE_procedure = 13, RULE_label = 14, 
		RULE_value = 15;
	public static final String[] ruleNames = {
		"prog", "network", "behaviour", "interaction", "sending", "receiving", 
		"selection", "offering", "condition", "procedureDefinition", "procedureInvocation", 
		"expression", "process", "procedure", "label", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'then'", "'else'", "'def'", "'in'", null, "'stop'", null, 
		null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "','", 
		"'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", "'<='", 
		"'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", "'/'", "'&'", 
		"'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", 
		"'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", "'...'", null, null, 
		null, "'|'", "'this'", "'->'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "INT", "TERMINATE", "IntegerLiteral", 
		"FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", "StringLiteral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", 
		"ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", "LE", 
		"GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "DIV", "BITAND", 
		"CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
		"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
		"COMMENT", "LINE_COMMENT", "Parallel", "Wildcard", "Arrow", "Continue"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Network.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public NetworkParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<NetworkContext> network() {
			return getRuleContexts(NetworkContext.class);
		}
		public NetworkContext network(int i) {
			return getRuleContext(NetworkContext.class,i);
		}
		public List<TerminalNode> Parallel() { return getTokens(NetworkParser.Parallel); }
		public TerminalNode Parallel(int i) {
			return getToken(NetworkParser.Parallel, i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			network();
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Parallel) {
				{
				{
				setState(33);
				match(Parallel);
				setState(34);
				network();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NetworkContext extends ParserRuleContext {
		public TerminalNode TERMINATE() { return getToken(NetworkParser.TERMINATE, 0); }
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public NetworkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_network; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterNetwork(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitNetwork(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitNetwork(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NetworkContext network() throws RecognitionException {
		NetworkContext _localctx = new NetworkContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_network);
		try {
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TERMINATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				match(TERMINATE);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				process();
				setState(42);
				match(ASSIGN);
				setState(43);
				behaviour();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BehaviourContext extends ParserRuleContext {
		public InteractionContext interaction() {
			return getRuleContext(InteractionContext.class,0);
		}
		public OfferingContext offering() {
			return getRuleContext(OfferingContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ProcedureDefinitionContext procedureDefinition() {
			return getRuleContext(ProcedureDefinitionContext.class,0);
		}
		public ProcedureInvocationContext procedureInvocation() {
			return getRuleContext(ProcedureInvocationContext.class,0);
		}
		public TerminalNode TERMINATE() { return getToken(NetworkParser.TERMINATE, 0); }
		public BehaviourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_behaviour; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterBehaviour(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitBehaviour(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitBehaviour(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BehaviourContext behaviour() throws RecognitionException {
		BehaviourContext _localctx = new BehaviourContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_behaviour);
		try {
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				interaction();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				offering();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				condition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				procedureDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(51);
				procedureInvocation();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(52);
				match(TERMINATE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InteractionContext extends ParserRuleContext {
		public SendingContext sending() {
			return getRuleContext(SendingContext.class,0);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public ReceivingContext receiving() {
			return getRuleContext(ReceivingContext.class,0);
		}
		public SelectionContext selection() {
			return getRuleContext(SelectionContext.class,0);
		}
		public InteractionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interaction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterInteraction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitInteraction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitInteraction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InteractionContext interaction() throws RecognitionException {
		InteractionContext _localctx = new InteractionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_interaction);
		try {
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				sending();
				setState(56);
				match(Continue);
				setState(57);
				behaviour();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				receiving();
				setState(60);
				match(Continue);
				setState(61);
				behaviour();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(63);
				selection();
				setState(64);
				match(Continue);
				setState(65);
				behaviour();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SendingContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode BANG() { return getToken(NetworkParser.BANG, 0); }
		public TerminalNode LT() { return getToken(NetworkParser.LT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode GT() { return getToken(NetworkParser.GT, 0); }
		public SendingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sending; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterSending(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitSending(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitSending(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendingContext sending() throws RecognitionException {
		SendingContext _localctx = new SendingContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_sending);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			process();
			setState(70);
			match(BANG);
			setState(71);
			match(LT);
			setState(72);
			expression();
			setState(73);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceivingContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(NetworkParser.QUESTION, 0); }
		public ReceivingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receiving; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterReceiving(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitReceiving(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitReceiving(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReceivingContext receiving() throws RecognitionException {
		ReceivingContext _localctx = new ReceivingContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_receiving);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			process();
			setState(76);
			match(QUESTION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode ADD() { return getToken(NetworkParser.ADD, 0); }
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public SelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitSelection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitSelection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionContext selection() throws RecognitionException {
		SelectionContext _localctx = new SelectionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_selection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			process();
			setState(79);
			match(ADD);
			setState(80);
			label();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OfferingContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(NetworkParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(NetworkParser.RBRACE, 0); }
		public TerminalNode Continue() { return getToken(NetworkParser.Continue, 0); }
		public List<BehaviourContext> behaviour() {
			return getRuleContexts(BehaviourContext.class);
		}
		public BehaviourContext behaviour(int i) {
			return getRuleContext(BehaviourContext.class,i);
		}
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(NetworkParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(NetworkParser.COLON, i);
		}
		public OfferingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_offering; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterOffering(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitOffering(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitOffering(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OfferingContext offering() throws RecognitionException {
		OfferingContext _localctx = new OfferingContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_offering);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			process();
			setState(83);
			match(BITAND);
			setState(84);
			match(LBRACE);
			{
			setState(85);
			label();
			setState(86);
			match(COLON);
			setState(87);
			behaviour();
			}
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(89);
				match(COMMA);
				setState(90);
				label();
				setState(91);
				match(COLON);
				setState(92);
				behaviour();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(99);
			match(RBRACE);
			setState(100);
			match(Continue);
			setState(101);
			behaviour();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BehaviourContext> behaviour() {
			return getRuleContexts(BehaviourContext.class);
		}
		public BehaviourContext behaviour(int i) {
			return getRuleContext(BehaviourContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__0);
			setState(104);
			process();
			setState(105);
			match(DOT);
			setState(106);
			expression();
			setState(107);
			match(T__1);
			setState(108);
			behaviour();
			setState(109);
			match(T__2);
			setState(110);
			behaviour();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureDefinitionContext extends ParserRuleContext {
		public ProcedureContext procedure() {
			return getRuleContext(ProcedureContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(NetworkParser.ASSIGN, 0); }
		public List<BehaviourContext> behaviour() {
			return getRuleContexts(BehaviourContext.class);
		}
		public BehaviourContext behaviour(int i) {
			return getRuleContext(BehaviourContext.class,i);
		}
		public ProcedureDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProcedureDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProcedureDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProcedureDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureDefinitionContext procedureDefinition() throws RecognitionException {
		ProcedureDefinitionContext _localctx = new ProcedureDefinitionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_procedureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__3);
			setState(113);
			procedure();
			setState(114);
			match(ASSIGN);
			setState(115);
			behaviour();
			setState(116);
			match(T__4);
			setState(117);
			behaviour();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureInvocationContext extends ParserRuleContext {
		public ProcedureContext procedure() {
			return getRuleContext(ProcedureContext.class,0);
		}
		public ProcedureInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProcedureInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProcedureInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProcedureInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureInvocationContext procedureInvocation() throws RecognitionException {
		ProcedureInvocationContext _localctx = new ProcedureInvocationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_procedureInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			procedure();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(NetworkParser.Identifier, 0); }
		public TerminalNode BooleanLiteral() { return getToken(NetworkParser.BooleanLiteral, 0); }
		public TerminalNode Wildcard() { return getToken(NetworkParser.Wildcard, 0); }
		public TerminalNode INT() { return getToken(NetworkParser.INT, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BooleanLiteral) | (1L << Identifier) | (1L << Wildcard))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcessContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(NetworkParser.Identifier, 0); }
		public ProcessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_process; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProcess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProcess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProcess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessContext process() throws RecognitionException {
		ProcessContext _localctx = new ProcessContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedureContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(NetworkParser.Identifier, 0); }
		public ProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProcedure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProcedure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureContext procedure() throws RecognitionException {
		ProcedureContext _localctx = new ProcedureContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(NetworkParser.Identifier, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(NetworkParser.INT, 0); }
		public TerminalNode CharacterLiteral() { return getToken(NetworkParser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(NetworkParser.StringLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(NetworkParser.BooleanLiteral, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@\u0086\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\2\7\2&\n\2\f\2\16\2)\13\2\3\3\3\3\3\3\3\3\3\3\5\3\60\n\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\5\48\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\5\5F\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\ta\n\t\f\t\16\td\13\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21"+
		"\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\4\6\2\b\b\f\f\67\67"+
		">>\4\2\b\b\f\16\177\2\"\3\2\2\2\4/\3\2\2\2\6\67\3\2\2\2\bE\3\2\2\2\nG"+
		"\3\2\2\2\fM\3\2\2\2\16P\3\2\2\2\20T\3\2\2\2\22i\3\2\2\2\24r\3\2\2\2\26"+
		"y\3\2\2\2\30{\3\2\2\2\32}\3\2\2\2\34\177\3\2\2\2\36\u0081\3\2\2\2 \u0083"+
		"\3\2\2\2\"\'\5\4\3\2#$\7=\2\2$&\5\4\3\2%#\3\2\2\2&)\3\2\2\2\'%\3\2\2\2"+
		"\'(\3\2\2\2(\3\3\2\2\2)\'\3\2\2\2*\60\7\t\2\2+,\5\32\16\2,-\7\27\2\2-"+
		".\5\6\4\2.\60\3\2\2\2/*\3\2\2\2/+\3\2\2\2\60\5\3\2\2\2\618\5\b\5\2\62"+
		"8\5\20\t\2\638\5\22\n\2\648\5\24\13\2\658\5\26\f\2\668\7\t\2\2\67\61\3"+
		"\2\2\2\67\62\3\2\2\2\67\63\3\2\2\2\67\64\3\2\2\2\67\65\3\2\2\2\67\66\3"+
		"\2\2\28\7\3\2\2\29:\5\n\6\2:;\7@\2\2;<\5\6\4\2<F\3\2\2\2=>\5\f\7\2>?\7"+
		"@\2\2?@\5\6\4\2@F\3\2\2\2AB\5\16\b\2BC\7@\2\2CD\5\6\4\2DF\3\2\2\2E9\3"+
		"\2\2\2E=\3\2\2\2EA\3\2\2\2F\t\3\2\2\2GH\5\32\16\2HI\7\32\2\2IJ\7\31\2"+
		"\2JK\5\30\r\2KL\7\30\2\2L\13\3\2\2\2MN\5\32\16\2NO\7\34\2\2O\r\3\2\2\2"+
		"PQ\5\32\16\2QR\7&\2\2RS\5\36\20\2S\17\3\2\2\2TU\5\32\16\2UV\7)\2\2VW\7"+
		"\21\2\2WX\5\36\20\2XY\7\35\2\2YZ\5\6\4\2Zb\3\2\2\2[\\\7\25\2\2\\]\5\36"+
		"\20\2]^\7\35\2\2^_\5\6\4\2_a\3\2\2\2`[\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3"+
		"\2\2\2ce\3\2\2\2db\3\2\2\2ef\7\22\2\2fg\7@\2\2gh\5\6\4\2h\21\3\2\2\2i"+
		"j\7\3\2\2jk\5\32\16\2kl\7\26\2\2lm\5\30\r\2mn\7\4\2\2no\5\6\4\2op\7\5"+
		"\2\2pq\5\6\4\2q\23\3\2\2\2rs\7\6\2\2st\5\34\17\2tu\7\27\2\2uv\5\6\4\2"+
		"vw\7\7\2\2wx\5\6\4\2x\25\3\2\2\2yz\5\34\17\2z\27\3\2\2\2{|\t\2\2\2|\31"+
		"\3\2\2\2}~\7\67\2\2~\33\3\2\2\2\177\u0080\7\67\2\2\u0080\35\3\2\2\2\u0081"+
		"\u0082\7\67\2\2\u0082\37\3\2\2\2\u0083\u0084\t\3\2\2\u0084!\3\2\2\2\7"+
		"\'/\67Eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}