// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
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
public class ChoreographyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, INT=6, TERMINATE=7, IntegerLiteral=8, 
		FloatingPointLiteral=9, BooleanLiteral=10, CharacterLiteral=11, StringLiteral=12, 
		LPAREN=13, RPAREN=14, LBRACE=15, RBRACE=16, LBRACK=17, RBRACK=18, COMMA=19, 
		DOT=20, ASSIGN=21, GT=22, LT=23, TILDE=24, COLON=25, EQUAL=26, LE=27, 
		GE=28, NOTEQUAL=29, AND=30, OR=31, INC=32, DEC=33, SUB=34, DIV=35, CARET=36, 
		MOD=37, ADD_ASSIGN=38, SUB_ASSIGN=39, MUL_ASSIGN=40, DIV_ASSIGN=41, AND_ASSIGN=42, 
		OR_ASSIGN=43, XOR_ASSIGN=44, MOD_ASSIGN=45, LSHIFT_ASSIGN=46, RSHIFT_ASSIGN=47, 
		URSHIFT_ASSIGN=48, Identifier=49, AT=50, ELLIPSIS=51, WS=52, COMMENT=53, 
		LINE_COMMENT=54, Parallel=55, Wildcard=56, Arrow=57, Send=58, Receive=59, 
		Select=60, Choose=61, Continue=62;
	public static final int
		RULE_choreography = 0, RULE_condition = 1, RULE_procedureDefinition = 2, 
		RULE_procedureInvocation = 3, RULE_internal_choreography = 4, RULE_communication = 5, 
		RULE_send = 6, RULE_choose = 7, RULE_sendingProcess = 8, RULE_receivingProcess = 9, 
		RULE_expr = 10, RULE_value = 11, RULE_procedure = 12, RULE_process = 13, 
		RULE_label = 14;
	public static final String[] ruleNames = {
		"choreography", "condition", "procedureDefinition", "procedureInvocation", 
		"internal_choreography", "communication", "send", "choose", "sendingProcess", 
		"receivingProcess", "expr", "value", "procedure", "process", "label"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'then'", "'else'", "'def'", "'in'", null, "'stop'", null, 
		null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "','", 
		"'.'", "'='", "'>'", "'<'", "'~'", "':'", "'=='", "'<='", "'>='", "'!='", 
		"'&&'", "'||'", "'++'", "'--'", "'-'", "'/'", "'^'", "'%'", "'+='", "'-='", 
		"'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", 
		null, "'@'", "'...'", null, null, null, "'|'", "'this'", "'->'", "'!'", 
		"'?'", "'+'", "'&'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "INT", "TERMINATE", "IntegerLiteral", 
		"FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", "StringLiteral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", 
		"ASSIGN", "GT", "LT", "TILDE", "COLON", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "INC", "DEC", "SUB", "DIV", "CARET", "MOD", "ADD_ASSIGN", 
		"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
		"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", 
		"AT", "ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "Parallel", "Wildcard", 
		"Arrow", "Send", "Receive", "Select", "Choose", "Continue"
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
	public String getGrammarFileName() { return "Choreography.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ChoreographyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ChoreographyContext extends ParserRuleContext {
		public CommunicationContext communication() {
			return getRuleContext(CommunicationContext.class,0);
		}
		public TerminalNode Continue() { return getToken(ChoreographyParser.Continue, 0); }
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ProcedureInvocationContext procedureInvocation() {
			return getRuleContext(ProcedureInvocationContext.class,0);
		}
		public ProcedureDefinitionContext procedureDefinition() {
			return getRuleContext(ProcedureDefinitionContext.class,0);
		}
		public TerminalNode TERMINATE() { return getToken(ChoreographyParser.TERMINATE, 0); }
		public ChoreographyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choreography; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterChoreography(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitChoreography(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitChoreography(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChoreographyContext choreography() throws RecognitionException {
		ChoreographyContext _localctx = new ChoreographyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_choreography);
		try {
			setState(38);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				communication();
				setState(31);
				match(Continue);
				setState(32);
				choreography();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				condition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(35);
				procedureInvocation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(36);
				procedureDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(37);
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

	public static class ConditionContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ChoreographyParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<ChoreographyContext> choreography() {
			return getRuleContexts(ChoreographyContext.class);
		}
		public ChoreographyContext choreography(int i) {
			return getRuleContext(ChoreographyContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(T__0);
			setState(41);
			process();
			setState(42);
			match(DOT);
			setState(43);
			expr();
			setState(44);
			match(T__1);
			setState(45);
			choreography();
			setState(46);
			match(T__2);
			setState(47);
			choreography();
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
		public TerminalNode ASSIGN() { return getToken(ChoreographyParser.ASSIGN, 0); }
		public Internal_choreographyContext internal_choreography() {
			return getRuleContext(Internal_choreographyContext.class,0);
		}
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public ProcedureDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedureDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterProcedureDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitProcedureDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitProcedureDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureDefinitionContext procedureDefinition() throws RecognitionException {
		ProcedureDefinitionContext _localctx = new ProcedureDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_procedureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(T__3);
			setState(50);
			procedure();
			setState(51);
			match(ASSIGN);
			setState(52);
			internal_choreography();
			setState(53);
			match(T__4);
			setState(54);
			choreography();
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
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterProcedureInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitProcedureInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitProcedureInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureInvocationContext procedureInvocation() throws RecognitionException {
		ProcedureInvocationContext _localctx = new ProcedureInvocationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_procedureInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
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

	public static class Internal_choreographyContext extends ParserRuleContext {
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public Internal_choreographyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_internal_choreography; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterInternal_choreography(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitInternal_choreography(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitInternal_choreography(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Internal_choreographyContext internal_choreography() throws RecognitionException {
		Internal_choreographyContext _localctx = new Internal_choreographyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_internal_choreography);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			choreography();
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

	public static class CommunicationContext extends ParserRuleContext {
		public SendContext send() {
			return getRuleContext(SendContext.class,0);
		}
		public ChooseContext choose() {
			return getRuleContext(ChooseContext.class,0);
		}
		public CommunicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_communication; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterCommunication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitCommunication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitCommunication(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommunicationContext communication() throws RecognitionException {
		CommunicationContext _localctx = new CommunicationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_communication);
		try {
			setState(62);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				send();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				choose();
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

	public static class SendContext extends ParserRuleContext {
		public SendingProcessContext sendingProcess() {
			return getRuleContext(SendingProcessContext.class,0);
		}
		public TerminalNode DOT() { return getToken(ChoreographyParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Arrow() { return getToken(ChoreographyParser.Arrow, 0); }
		public ReceivingProcessContext receivingProcess() {
			return getRuleContext(ReceivingProcessContext.class,0);
		}
		public SendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_send; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterSend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitSend(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitSend(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendContext send() throws RecognitionException {
		SendContext _localctx = new SendContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_send);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			sendingProcess();
			setState(65);
			match(DOT);
			setState(66);
			expr();
			setState(67);
			match(Arrow);
			setState(68);
			receivingProcess();
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

	public static class ChooseContext extends ParserRuleContext {
		public SendingProcessContext sendingProcess() {
			return getRuleContext(SendingProcessContext.class,0);
		}
		public TerminalNode Arrow() { return getToken(ChoreographyParser.Arrow, 0); }
		public ReceivingProcessContext receivingProcess() {
			return getRuleContext(ReceivingProcessContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(ChoreographyParser.LBRACK, 0); }
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ChoreographyParser.RBRACK, 0); }
		public ChooseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choose; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterChoose(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitChoose(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitChoose(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChooseContext choose() throws RecognitionException {
		ChooseContext _localctx = new ChooseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_choose);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			sendingProcess();
			setState(71);
			match(Arrow);
			setState(72);
			receivingProcess();
			setState(73);
			match(LBRACK);
			setState(74);
			label();
			setState(75);
			match(RBRACK);
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

	public static class SendingProcessContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public SendingProcessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sendingProcess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterSendingProcess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitSendingProcess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitSendingProcess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SendingProcessContext sendingProcess() throws RecognitionException {
		SendingProcessContext _localctx = new SendingProcessContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_sendingProcess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			process();
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

	public static class ReceivingProcessContext extends ParserRuleContext {
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public ReceivingProcessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receivingProcess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterReceivingProcess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitReceivingProcess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitReceivingProcess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReceivingProcessContext receivingProcess() throws RecognitionException {
		ReceivingProcessContext _localctx = new ReceivingProcessContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_receivingProcess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			process();
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

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode Wildcard() { return getToken(ChoreographyParser.Wildcard, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expr);
		try {
			setState(84);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				match(Identifier);
				}
				break;
			case INT:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				value();
				}
				break;
			case Wildcard:
				enterOuterAlt(_localctx, 3);
				{
				setState(83);
				match(Wildcard);
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ChoreographyParser.INT, 0); }
		public TerminalNode CharacterLiteral() { return getToken(ChoreographyParser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(ChoreographyParser.StringLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ChoreographyParser.BooleanLiteral, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
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

	public static class ProcedureContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public ProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitProcedure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitProcedure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureContext procedure() throws RecognitionException {
		ProcedureContext _localctx = new ProcedureContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
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

	public static class ProcessContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public ProcessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_process; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterProcess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitProcess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitProcess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessContext process() throws RecognitionException {
		ProcessContext _localctx = new ProcessContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
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
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@a\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\5\2)\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\5\7A\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\5\fW\n\f\3\r\3"+
		"\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36\2\3\4\2\b\b\f\16X\2(\3\2\2\2\4*\3\2\2\2\6\63\3\2\2\2\b:\3"+
		"\2\2\2\n<\3\2\2\2\f@\3\2\2\2\16B\3\2\2\2\20H\3\2\2\2\22O\3\2\2\2\24Q\3"+
		"\2\2\2\26V\3\2\2\2\30X\3\2\2\2\32Z\3\2\2\2\34\\\3\2\2\2\36^\3\2\2\2 !"+
		"\5\f\7\2!\"\7@\2\2\"#\5\2\2\2#)\3\2\2\2$)\5\4\3\2%)\5\b\5\2&)\5\6\4\2"+
		"\')\7\t\2\2( \3\2\2\2($\3\2\2\2(%\3\2\2\2(&\3\2\2\2(\'\3\2\2\2)\3\3\2"+
		"\2\2*+\7\3\2\2+,\5\34\17\2,-\7\26\2\2-.\5\26\f\2./\7\4\2\2/\60\5\2\2\2"+
		"\60\61\7\5\2\2\61\62\5\2\2\2\62\5\3\2\2\2\63\64\7\6\2\2\64\65\5\32\16"+
		"\2\65\66\7\27\2\2\66\67\5\n\6\2\678\7\7\2\289\5\2\2\29\7\3\2\2\2:;\5\32"+
		"\16\2;\t\3\2\2\2<=\5\2\2\2=\13\3\2\2\2>A\5\16\b\2?A\5\20\t\2@>\3\2\2\2"+
		"@?\3\2\2\2A\r\3\2\2\2BC\5\22\n\2CD\7\26\2\2DE\5\26\f\2EF\7;\2\2FG\5\24"+
		"\13\2G\17\3\2\2\2HI\5\22\n\2IJ\7;\2\2JK\5\24\13\2KL\7\23\2\2LM\5\36\20"+
		"\2MN\7\24\2\2N\21\3\2\2\2OP\5\34\17\2P\23\3\2\2\2QR\5\34\17\2R\25\3\2"+
		"\2\2SW\7\63\2\2TW\5\30\r\2UW\7:\2\2VS\3\2\2\2VT\3\2\2\2VU\3\2\2\2W\27"+
		"\3\2\2\2XY\t\2\2\2Y\31\3\2\2\2Z[\7\63\2\2[\33\3\2\2\2\\]\7\63\2\2]\35"+
		"\3\2\2\2^_\7\63\2\2_\37\3\2\2\2\5(@V";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}