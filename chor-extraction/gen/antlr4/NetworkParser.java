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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, INT=8, TERMINATE=9, 
		IntegerLiteral=10, FloatingPointLiteral=11, BooleanLiteral=12, CharacterLiteral=13, 
		StringLiteral=14, LPAREN=15, RPAREN=16, LBRACE=17, RBRACE=18, LBRACK=19, 
		RBRACK=20, COMMA=21, DOT=22, ASSIGN=23, GT=24, LT=25, BANG=26, TILDE=27, 
		QUESTION=28, COLON=29, EQUAL=30, LE=31, GE=32, NOTEQUAL=33, AND=34, OR=35, 
		INC=36, DEC=37, ADD=38, SUB=39, MUL=40, DIV=41, BITAND=42, CARET=43, MOD=44, 
		ADD_ASSIGN=45, SUB_ASSIGN=46, MUL_ASSIGN=47, DIV_ASSIGN=48, AND_ASSIGN=49, 
		OR_ASSIGN=50, XOR_ASSIGN=51, MOD_ASSIGN=52, LSHIFT_ASSIGN=53, RSHIFT_ASSIGN=54, 
		URSHIFT_ASSIGN=55, Identifier=56, AT=57, ELLIPSIS=58, WS=59, COMMENT=60, 
		LINE_COMMENT=61, Parallel=62, Wildcard=63, Arrow=64;
	public static final int
		RULE_network = 0, RULE_processBehaviour = 1, RULE_behaviour = 2, RULE_interaction = 3, 
		RULE_sending = 4, RULE_receiving = 5, RULE_selection = 6, RULE_offering = 7, 
		RULE_labeledBehaviour = 8, RULE_condition = 9, RULE_procedureDefinition = 10, 
		RULE_procedureInvocation = 11, RULE_expression = 12, RULE_process = 13, 
		RULE_procedure = 14, RULE_label = 15, RULE_value = 16;
	public static final String[] ruleNames = {
		"network", "processBehaviour", "behaviour", "interaction", "sending", 
		"receiving", "selection", "offering", "labeledBehaviour", "condition", 
		"procedureDefinition", "procedureInvocation", "expression", "process", 
		"procedure", "label", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'is'", "';'", "'if'", "'then'", "'else'", "'def'", "'in'", null, 
		"'stop'", null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", 
		"']'", "','", "'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", 
		"'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", 
		"'-'", "'*'", "'/'", "'&'", "'^'", "'%'", "'+='", "'-='", "'*='", "'/='", 
		"'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", 
		"'...'", null, null, null, "'|'", "'this'", "'->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "INT", "TERMINATE", "IntegerLiteral", 
		"FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", "StringLiteral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", 
		"ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", "LE", 
		"GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", 
		"BITAND", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", 
		"AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
		"COMMENT", "LINE_COMMENT", "Parallel", "Wildcard", "Arrow"
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
	public static class NetworkContext extends ParserRuleContext {
		public List<ProcessBehaviourContext> processBehaviour() {
			return getRuleContexts(ProcessBehaviourContext.class);
		}
		public ProcessBehaviourContext processBehaviour(int i) {
			return getRuleContext(ProcessBehaviourContext.class,i);
		}
		public List<TerminalNode> Parallel() { return getTokens(NetworkParser.Parallel); }
		public TerminalNode Parallel(int i) {
			return getToken(NetworkParser.Parallel, i);
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
		enterRule(_localctx, 0, RULE_network);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			processBehaviour();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Parallel) {
				{
				{
				setState(35);
				match(Parallel);
				setState(36);
				processBehaviour();
				}
				}
				setState(41);
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

	public static class ProcessBehaviourContext extends ParserRuleContext {
		public TerminalNode TERMINATE() { return getToken(NetworkParser.TERMINATE, 0); }
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public ProcessBehaviourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processBehaviour; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterProcessBehaviour(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitProcessBehaviour(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitProcessBehaviour(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessBehaviourContext processBehaviour() throws RecognitionException {
		ProcessBehaviourContext _localctx = new ProcessBehaviourContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_processBehaviour);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TERMINATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				match(TERMINATE);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				process();
				setState(44);
				match(T__0);
				setState(45);
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
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				interaction();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				offering();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(51);
				condition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(52);
				procedureDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(53);
				procedureInvocation();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(54);
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
			setState(60);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				sending();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				receiving();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(59);
				selection();
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
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
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
			setState(62);
			process();
			setState(63);
			match(BANG);
			setState(64);
			match(LT);
			setState(65);
			expression();
			setState(66);
			match(GT);
			setState(67);
			match(T__1);
			setState(68);
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

	public static class ReceivingContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(NetworkParser.QUESTION, 0); }
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
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
			setState(70);
			process();
			setState(71);
			match(QUESTION);
			setState(72);
			match(T__1);
			setState(73);
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

	public static class SelectionContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode ADD() { return getToken(NetworkParser.ADD, 0); }
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
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
			setState(75);
			process();
			setState(76);
			match(ADD);
			setState(77);
			label();
			setState(78);
			match(T__1);
			setState(79);
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

	public static class OfferingContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode LBRACE() { return getToken(NetworkParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(NetworkParser.RBRACE, 0); }
		public List<LabeledBehaviourContext> labeledBehaviour() {
			return getRuleContexts(LabeledBehaviourContext.class);
		}
		public LabeledBehaviourContext labeledBehaviour(int i) {
			return getRuleContext(LabeledBehaviourContext.class,i);
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
			setState(81);
			process();
			setState(82);
			match(BITAND);
			setState(83);
			match(LBRACE);
			{
			setState(84);
			labeledBehaviour();
			}
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(85);
				match(COMMA);
				setState(86);
				labeledBehaviour();
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(92);
			match(RBRACE);
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

	public static class LabeledBehaviourContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public TerminalNode COLON() { return getToken(NetworkParser.COLON, 0); }
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public LabeledBehaviourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledBehaviour; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterLabeledBehaviour(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitLabeledBehaviour(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitLabeledBehaviour(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabeledBehaviourContext labeledBehaviour() throws RecognitionException {
		LabeledBehaviourContext _localctx = new LabeledBehaviourContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_labeledBehaviour);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			label();
			setState(95);
			match(COLON);
			setState(96);
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
		enterRule(_localctx, 18, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__2);
			setState(99);
			process();
			setState(100);
			match(DOT);
			setState(101);
			expression();
			setState(102);
			match(T__3);
			setState(103);
			behaviour();
			setState(104);
			match(T__4);
			setState(105);
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
		enterRule(_localctx, 20, RULE_procedureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(T__5);
			setState(108);
			procedure();
			setState(109);
			match(ASSIGN);
			setState(110);
			behaviour();
			setState(111);
			match(T__6);
			setState(112);
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
		enterRule(_localctx, 22, RULE_procedureInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
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
		enterRule(_localctx, 24, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
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
		enterRule(_localctx, 26, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
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
		enterRule(_localctx, 28, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
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
		enterRule(_localctx, 30, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
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
		enterRule(_localctx, 32, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3B\u0081\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\7\2(\n\2\f\2\16\2+\13\2\3\3\3\3\3\3\3\3\3\3\5\3\62\n\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\5\4:\n\4\3\5\3\5\3\5\5\5?\n\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\7\tZ\n\t\f\t\16\t]\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3"+
		"\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\2\2\23\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"\2\4\6\2\n\n\16\16::AA\4\2\n\n\16"+
		"\20y\2$\3\2\2\2\4\61\3\2\2\2\69\3\2\2\2\b>\3\2\2\2\n@\3\2\2\2\fH\3\2\2"+
		"\2\16M\3\2\2\2\20S\3\2\2\2\22`\3\2\2\2\24d\3\2\2\2\26m\3\2\2\2\30t\3\2"+
		"\2\2\32v\3\2\2\2\34x\3\2\2\2\36z\3\2\2\2 |\3\2\2\2\"~\3\2\2\2$)\5\4\3"+
		"\2%&\7@\2\2&(\5\4\3\2\'%\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\3\3\2"+
		"\2\2+)\3\2\2\2,\62\7\13\2\2-.\5\34\17\2./\7\3\2\2/\60\5\6\4\2\60\62\3"+
		"\2\2\2\61,\3\2\2\2\61-\3\2\2\2\62\5\3\2\2\2\63:\5\b\5\2\64:\5\20\t\2\65"+
		":\5\24\13\2\66:\5\26\f\2\67:\5\30\r\28:\7\13\2\29\63\3\2\2\29\64\3\2\2"+
		"\29\65\3\2\2\29\66\3\2\2\29\67\3\2\2\298\3\2\2\2:\7\3\2\2\2;?\5\n\6\2"+
		"<?\5\f\7\2=?\5\16\b\2>;\3\2\2\2><\3\2\2\2>=\3\2\2\2?\t\3\2\2\2@A\5\34"+
		"\17\2AB\7\34\2\2BC\7\33\2\2CD\5\32\16\2DE\7\32\2\2EF\7\4\2\2FG\5\6\4\2"+
		"G\13\3\2\2\2HI\5\34\17\2IJ\7\36\2\2JK\7\4\2\2KL\5\6\4\2L\r\3\2\2\2MN\5"+
		"\34\17\2NO\7(\2\2OP\5 \21\2PQ\7\4\2\2QR\5\6\4\2R\17\3\2\2\2ST\5\34\17"+
		"\2TU\7,\2\2UV\7\23\2\2V[\5\22\n\2WX\7\27\2\2XZ\5\22\n\2YW\3\2\2\2Z]\3"+
		"\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\^\3\2\2\2][\3\2\2\2^_\7\24\2\2_\21\3\2\2"+
		"\2`a\5 \21\2ab\7\37\2\2bc\5\6\4\2c\23\3\2\2\2de\7\5\2\2ef\5\34\17\2fg"+
		"\7\30\2\2gh\5\32\16\2hi\7\6\2\2ij\5\6\4\2jk\7\7\2\2kl\5\6\4\2l\25\3\2"+
		"\2\2mn\7\b\2\2no\5\36\20\2op\7\31\2\2pq\5\6\4\2qr\7\t\2\2rs\5\6\4\2s\27"+
		"\3\2\2\2tu\5\36\20\2u\31\3\2\2\2vw\t\2\2\2w\33\3\2\2\2xy\7:\2\2y\35\3"+
		"\2\2\2z{\7:\2\2{\37\3\2\2\2|}\7:\2\2}!\3\2\2\2~\177\t\3\2\2\177#\3\2\2"+
		"\2\7)\619>[";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}