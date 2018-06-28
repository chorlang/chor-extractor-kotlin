// Generated from Network.g4 by ANTLR 4.7.1

    package antlrgen;

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
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		TERMINATE=18, Identifier=19, BooleanLiteral=20, StringLiteral=21, Wildcard=22, 
		WS=23, INT=24;
	public static final int
		RULE_parallelNetworks = 0, RULE_network = 1, RULE_processBehaviour = 2, 
		RULE_procedureDefinition = 3, RULE_behaviour = 4, RULE_interaction = 5, 
		RULE_sending = 6, RULE_receiving = 7, RULE_selection = 8, RULE_offering = 9, 
		RULE_labeledBehaviour = 10, RULE_condition = 11, RULE_procedureInvocation = 12, 
		RULE_expression = 13, RULE_process = 14, RULE_procedure = 15;
	public static final String[] ruleNames = {
		"parallelNetworks", "network", "processBehaviour", "procedureDefinition", 
		"behaviour", "interaction", "sending", "receiving", "selection", "offering", 
		"labeledBehaviour", "condition", "procedureInvocation", "expression", 
		"process", "procedure"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'||'", "'|'", "'{'", "'def'", "'main'", "'}'", "'!<'", "'>;'", 
		"'?;'", "'+'", "';'", "'&{'", "','", "':'", "'if'", "'then'", "'else'", 
		"'stop'", null, null, null, "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "TERMINATE", "Identifier", "BooleanLiteral", 
		"StringLiteral", "Wildcard", "WS", "INT"
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
	public static class ParallelNetworksContext extends ParserRuleContext {
		public List<NetworkContext> network() {
			return getRuleContexts(NetworkContext.class);
		}
		public NetworkContext network(int i) {
			return getRuleContext(NetworkContext.class,i);
		}
		public ParallelNetworksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parallelNetworks; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterParallelNetworks(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitParallelNetworks(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitParallelNetworks(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParallelNetworksContext parallelNetworks() throws RecognitionException {
		ParallelNetworksContext _localctx = new ParallelNetworksContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parallelNetworks);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			network();
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(33);
				match(T__0);
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
		public List<ProcessContext> process() {
			return getRuleContexts(ProcessContext.class);
		}
		public ProcessContext process(int i) {
			return getRuleContext(ProcessContext.class,i);
		}
		public List<ProcessBehaviourContext> processBehaviour() {
			return getRuleContexts(ProcessBehaviourContext.class);
		}
		public ProcessBehaviourContext processBehaviour(int i) {
			return getRuleContext(ProcessBehaviourContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			process();
			setState(41);
			processBehaviour();
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(42);
				match(T__1);
				setState(43);
				process();
				setState(44);
				processBehaviour();
				}
				}
				setState(50);
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
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public List<ProcedureContext> procedure() {
			return getRuleContexts(ProcedureContext.class);
		}
		public ProcedureContext procedure(int i) {
			return getRuleContext(ProcedureContext.class,i);
		}
		public List<ProcedureDefinitionContext> procedureDefinition() {
			return getRuleContexts(ProcedureDefinitionContext.class);
		}
		public ProcedureDefinitionContext procedureDefinition(int i) {
			return getRuleContext(ProcedureDefinitionContext.class,i);
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
		enterRule(_localctx, 4, RULE_processBehaviour);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__2);
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(52);
				match(T__3);
				setState(53);
				procedure();
				setState(54);
				procedureDefinition();
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			match(T__4);
			setState(62);
			match(T__2);
			setState(63);
			behaviour();
			setState(64);
			match(T__5);
			setState(65);
			match(T__5);
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
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
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
		enterRule(_localctx, 6, RULE_procedureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__2);
			setState(68);
			behaviour();
			setState(69);
			match(T__5);
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
		enterRule(_localctx, 8, RULE_behaviour);
		try {
			setState(76);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				interaction();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				offering();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(73);
				condition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(74);
				procedureInvocation();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(75);
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
		enterRule(_localctx, 10, RULE_interaction);
		try {
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				sending();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				receiving();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
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
		enterRule(_localctx, 12, RULE_sending);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			process();
			setState(84);
			match(T__6);
			setState(85);
			expression();
			setState(86);
			match(T__7);
			setState(87);
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
		enterRule(_localctx, 14, RULE_receiving);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			process();
			setState(90);
			match(T__8);
			setState(91);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		enterRule(_localctx, 16, RULE_selection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			process();
			setState(94);
			match(T__9);
			setState(95);
			expression();
			setState(96);
			match(T__10);
			setState(97);
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
		enterRule(_localctx, 18, RULE_offering);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			process();
			setState(100);
			match(T__11);
			{
			setState(101);
			labeledBehaviour();
			}
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__12) {
				{
				{
				setState(102);
				match(T__12);
				setState(103);
				labeledBehaviour();
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(109);
			match(T__5);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
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
		enterRule(_localctx, 20, RULE_labeledBehaviour);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			expression();
			setState(112);
			match(T__13);
			setState(113);
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
		enterRule(_localctx, 22, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(T__14);
			setState(116);
			expression();
			setState(117);
			match(T__15);
			setState(118);
			behaviour();
			setState(119);
			match(T__16);
			setState(120);
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
		enterRule(_localctx, 24, RULE_procedureInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
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
		enterRule(_localctx, 26, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Identifier) | (1L << BooleanLiteral) | (1L << Wildcard) | (1L << INT))) != 0)) ) {
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
		enterRule(_localctx, 28, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
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
		enterRule(_localctx, 30, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u0085\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2"+
		"\3\2\7\2&\n\2\f\2\16\2)\13\2\3\3\3\3\3\3\3\3\3\3\3\3\7\3\61\n\3\f\3\16"+
		"\3\64\13\3\3\4\3\4\3\4\3\4\3\4\7\4;\n\4\f\4\16\4>\13\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\5\6O\n\6\3\7\3\7\3\7\5\7"+
		"T\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\13\7\13k\n\13\f\13\16\13n\13\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\21\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\3\5\2\25"+
		"\26\30\30\32\32\2~\2\"\3\2\2\2\4*\3\2\2\2\6\65\3\2\2\2\bE\3\2\2\2\nN\3"+
		"\2\2\2\fS\3\2\2\2\16U\3\2\2\2\20[\3\2\2\2\22_\3\2\2\2\24e\3\2\2\2\26q"+
		"\3\2\2\2\30u\3\2\2\2\32|\3\2\2\2\34~\3\2\2\2\36\u0080\3\2\2\2 \u0082\3"+
		"\2\2\2\"\'\5\4\3\2#$\7\3\2\2$&\5\4\3\2%#\3\2\2\2&)\3\2\2\2\'%\3\2\2\2"+
		"\'(\3\2\2\2(\3\3\2\2\2)\'\3\2\2\2*+\5\36\20\2+\62\5\6\4\2,-\7\4\2\2-."+
		"\5\36\20\2./\5\6\4\2/\61\3\2\2\2\60,\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2"+
		"\2\62\63\3\2\2\2\63\5\3\2\2\2\64\62\3\2\2\2\65<\7\5\2\2\66\67\7\6\2\2"+
		"\678\5 \21\289\5\b\5\29;\3\2\2\2:\66\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2"+
		"\2\2=?\3\2\2\2><\3\2\2\2?@\7\7\2\2@A\7\5\2\2AB\5\n\6\2BC\7\b\2\2CD\7\b"+
		"\2\2D\7\3\2\2\2EF\7\5\2\2FG\5\n\6\2GH\7\b\2\2H\t\3\2\2\2IO\5\f\7\2JO\5"+
		"\24\13\2KO\5\30\r\2LO\5\32\16\2MO\7\24\2\2NI\3\2\2\2NJ\3\2\2\2NK\3\2\2"+
		"\2NL\3\2\2\2NM\3\2\2\2O\13\3\2\2\2PT\5\16\b\2QT\5\20\t\2RT\5\22\n\2SP"+
		"\3\2\2\2SQ\3\2\2\2SR\3\2\2\2T\r\3\2\2\2UV\5\36\20\2VW\7\t\2\2WX\5\34\17"+
		"\2XY\7\n\2\2YZ\5\n\6\2Z\17\3\2\2\2[\\\5\36\20\2\\]\7\13\2\2]^\5\n\6\2"+
		"^\21\3\2\2\2_`\5\36\20\2`a\7\f\2\2ab\5\34\17\2bc\7\r\2\2cd\5\n\6\2d\23"+
		"\3\2\2\2ef\5\36\20\2fg\7\16\2\2gl\5\26\f\2hi\7\17\2\2ik\5\26\f\2jh\3\2"+
		"\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2mo\3\2\2\2nl\3\2\2\2op\7\b\2\2p\25\3"+
		"\2\2\2qr\5\34\17\2rs\7\20\2\2st\5\n\6\2t\27\3\2\2\2uv\7\21\2\2vw\5\34"+
		"\17\2wx\7\22\2\2xy\5\n\6\2yz\7\23\2\2z{\5\n\6\2{\31\3\2\2\2|}\5 \21\2"+
		"}\33\3\2\2\2~\177\t\2\2\2\177\35\3\2\2\2\u0080\u0081\7\25\2\2\u0081\37"+
		"\3\2\2\2\u0082\u0083\7\25\2\2\u0083!\3\2\2\2\b\'\62<NSl";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}