// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ChoreographyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, INT=7, TERMINATE=8, IntegerLiteral=9, 
		FloatingPointLiteral=10, BooleanLiteral=11, CharacterLiteral=12, StringLiteral=13, 
		LPAREN=14, RPAREN=15, LBRACE=16, RBRACE=17, LBRACK=18, RBRACK=19, COMMA=20, 
		DOT=21, ASSIGN=22, GT=23, LT=24, TILDE=25, COLON=26, EQUAL=27, LE=28, 
		GE=29, NOTEQUAL=30, AND=31, OR=32, INC=33, DEC=34, SUB=35, DIV=36, CARET=37, 
		MOD=38, ADD_ASSIGN=39, SUB_ASSIGN=40, MUL_ASSIGN=41, DIV_ASSIGN=42, AND_ASSIGN=43, 
		OR_ASSIGN=44, XOR_ASSIGN=45, MOD_ASSIGN=46, LSHIFT_ASSIGN=47, RSHIFT_ASSIGN=48, 
		URSHIFT_ASSIGN=49, Identifier=50, AT=51, ELLIPSIS=52, WS=53, COMMENT=54, 
		LINE_COMMENT=55, Parallel=56, Wildcard=57, Arrow=58, Send=59, Receive=60, 
		Select=61, Choose=62, Continue=63;
	public static final int
		RULE_choreography = 0, RULE_condition = 1, RULE_procedureDefinition = 2, 
		RULE_procedureInvocation = 3, RULE_internal_choreography = 4, RULE_external_choreography = 5, 
		RULE_communication = 6, RULE_send = 7, RULE_choose = 8, RULE_firstExpression = 9, 
		RULE_secondExpression = 10, RULE_expression = 11, RULE_expr = 12, RULE_procedure = 13, 
		RULE_process = 14, RULE_label = 15, RULE_value = 16;
	public static final String[] ruleNames = {
		"choreography", "condition", "procedureDefinition", "procedureInvocation", 
		"internal_choreography", "external_choreography", "communication", "send", 
		"choose", "firstExpression", "secondExpression", "expression", "expr", 
		"procedure", "process", "label", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'if'", "'then'", "'else'", "'def'", "'in'", "'*'", null, "'stop'", 
		null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"','", "'.'", "'='", "'>'", "'<'", "'~'", "':'", "'=='", "'<='", "'>='", 
		"'!='", "'&&'", "'||'", "'++'", "'--'", "'-'", "'/'", "'^'", "'%'", "'+='", 
		"'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", 
		"'>>>='", null, "'@'", "'...'", null, null, null, "'|'", "'this'", "'->'", 
		"'!'", "'?'", "'+'", "'&'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "INT", "TERMINATE", "IntegerLiteral", 
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
			setState(42);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				communication();
				setState(35);
				match(Continue);
				setState(36);
				choreography();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(38);
				condition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(39);
				procedureInvocation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(40);
				procedureDefinition();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(41);
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
		public FirstExpressionContext firstExpression() {
			return getRuleContext(FirstExpressionContext.class,0);
		}
		public SecondExpressionContext secondExpression() {
			return getRuleContext(SecondExpressionContext.class,0);
		}
		public Internal_choreographyContext internal_choreography() {
			return getRuleContext(Internal_choreographyContext.class,0);
		}
		public External_choreographyContext external_choreography() {
			return getRuleContext(External_choreographyContext.class,0);
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
			setState(44);
			match(T__0);
			setState(45);
			firstExpression();
			setState(46);
			match(ASSIGN);
			setState(47);
			secondExpression();
			setState(48);
			match(T__1);
			setState(49);
			internal_choreography();
			setState(50);
			match(T__2);
			setState(51);
			external_choreography();
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
		public External_choreographyContext external_choreography() {
			return getRuleContext(External_choreographyContext.class,0);
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
			setState(53);
			match(T__3);
			setState(54);
			procedure();
			setState(55);
			match(ASSIGN);
			setState(56);
			internal_choreography();
			setState(57);
			match(T__4);
			setState(58);
			external_choreography();
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
			setState(60);
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
			setState(62);
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

	public static class External_choreographyContext extends ParserRuleContext {
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public External_choreographyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_external_choreography; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterExternal_choreography(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitExternal_choreography(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitExternal_choreography(this);
			else return visitor.visitChildren(this);
		}
	}

	public final External_choreographyContext external_choreography() throws RecognitionException {
		External_choreographyContext _localctx = new External_choreographyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_external_choreography);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
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
		enterRule(_localctx, 12, RULE_communication);
		try {
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				send();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
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
		public List<ProcessContext> process() {
			return getRuleContexts(ProcessContext.class);
		}
		public ProcessContext process(int i) {
			return getRuleContext(ProcessContext.class,i);
		}
		public TerminalNode DOT() { return getToken(ChoreographyParser.DOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Arrow() { return getToken(ChoreographyParser.Arrow, 0); }
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
		enterRule(_localctx, 14, RULE_send);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			process();
			setState(71);
			match(DOT);
			setState(72);
			expression();
			setState(73);
			match(Arrow);
			setState(74);
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

	public static class ChooseContext extends ParserRuleContext {
		public List<ProcessContext> process() {
			return getRuleContexts(ProcessContext.class);
		}
		public ProcessContext process(int i) {
			return getRuleContext(ProcessContext.class,i);
		}
		public TerminalNode Arrow() { return getToken(ChoreographyParser.Arrow, 0); }
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
		enterRule(_localctx, 16, RULE_choose);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			process();
			setState(77);
			match(Arrow);
			setState(78);
			process();
			setState(79);
			match(LBRACK);
			setState(80);
			label();
			setState(81);
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

	public static class FirstExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FirstExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_firstExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterFirstExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitFirstExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitFirstExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FirstExpressionContext firstExpression() throws RecognitionException {
		FirstExpressionContext _localctx = new FirstExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_firstExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			expression();
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

	public static class SecondExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SecondExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_secondExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterSecondExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitSecondExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitSecondExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SecondExpressionContext secondExpression() throws RecognitionException {
		SecondExpressionContext _localctx = new SecondExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_secondExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			expression();
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
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ChoreographyParser.BooleanLiteral, 0); }
		public TerminalNode Wildcard() { return getToken(ChoreographyParser.Wildcard, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expression);
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				match(Identifier);
				}
				break;
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				match(BooleanLiteral);
				}
				break;
			case Wildcard:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				match(Wildcard);
				}
				break;
			case INT:
			case LPAREN:
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				expr(0);
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

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(ChoreographyParser.INT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
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
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				{
				setState(94);
				match(INT);
				}
				break;
			case LPAREN:
				{
				setState(95);
				match(LPAREN);
				setState(96);
				expr(0);
				setState(97);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(109);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(107);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(101);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(102);
						_la = _input.LA(1);
						if ( !(_la==T__5 || _la==DIV) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(103);
						expr(5);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(104);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(105);
						_la = _input.LA(1);
						if ( !(_la==SUB || _la==Select) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(106);
						expr(4);
						}
						break;
					}
					} 
				}
				setState(111);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
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
		enterRule(_localctx, 26, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
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
		enterRule(_localctx, 28, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
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
		enterRule(_localctx, 30, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
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
		enterRule(_localctx, 32, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 12:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3A{\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\5\2-\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\5\bG\n\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\r\3\r\5\r^\n\r\3\16\3\16\3\16\3\16\3\16\3\16\5\16f\n\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\7\16n\n\16\f\16\16\16q\13\16\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\22\2\3\32\23\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"\2\5\4\2\b\b&&\4\2%%??\4\2\t\t\r\17t\2,\3\2\2\2\4.\3\2\2\2"+
		"\6\67\3\2\2\2\b>\3\2\2\2\n@\3\2\2\2\fB\3\2\2\2\16F\3\2\2\2\20H\3\2\2\2"+
		"\22N\3\2\2\2\24U\3\2\2\2\26W\3\2\2\2\30]\3\2\2\2\32e\3\2\2\2\34r\3\2\2"+
		"\2\36t\3\2\2\2 v\3\2\2\2\"x\3\2\2\2$%\5\16\b\2%&\7A\2\2&\'\5\2\2\2\'-"+
		"\3\2\2\2(-\5\4\3\2)-\5\b\5\2*-\5\6\4\2+-\7\n\2\2,$\3\2\2\2,(\3\2\2\2,"+
		")\3\2\2\2,*\3\2\2\2,+\3\2\2\2-\3\3\2\2\2./\7\3\2\2/\60\5\24\13\2\60\61"+
		"\7\30\2\2\61\62\5\26\f\2\62\63\7\4\2\2\63\64\5\n\6\2\64\65\7\5\2\2\65"+
		"\66\5\f\7\2\66\5\3\2\2\2\678\7\6\2\289\5\34\17\29:\7\30\2\2:;\5\n\6\2"+
		";<\7\7\2\2<=\5\f\7\2=\7\3\2\2\2>?\5\34\17\2?\t\3\2\2\2@A\5\2\2\2A\13\3"+
		"\2\2\2BC\5\2\2\2C\r\3\2\2\2DG\5\20\t\2EG\5\22\n\2FD\3\2\2\2FE\3\2\2\2"+
		"G\17\3\2\2\2HI\5\36\20\2IJ\7\27\2\2JK\5\30\r\2KL\7<\2\2LM\5\36\20\2M\21"+
		"\3\2\2\2NO\5\36\20\2OP\7<\2\2PQ\5\36\20\2QR\7\24\2\2RS\5 \21\2ST\7\25"+
		"\2\2T\23\3\2\2\2UV\5\30\r\2V\25\3\2\2\2WX\5\30\r\2X\27\3\2\2\2Y^\7\64"+
		"\2\2Z^\7\r\2\2[^\7;\2\2\\^\5\32\16\2]Y\3\2\2\2]Z\3\2\2\2][\3\2\2\2]\\"+
		"\3\2\2\2^\31\3\2\2\2_`\b\16\1\2`f\7\t\2\2ab\7\20\2\2bc\5\32\16\2cd\7\21"+
		"\2\2df\3\2\2\2e_\3\2\2\2ea\3\2\2\2fo\3\2\2\2gh\f\6\2\2hi\t\2\2\2in\5\32"+
		"\16\7jk\f\5\2\2kl\t\3\2\2ln\5\32\16\6mg\3\2\2\2mj\3\2\2\2nq\3\2\2\2om"+
		"\3\2\2\2op\3\2\2\2p\33\3\2\2\2qo\3\2\2\2rs\7\64\2\2s\35\3\2\2\2tu\7\64"+
		"\2\2u\37\3\2\2\2vw\7\64\2\2w!\3\2\2\2xy\t\4\2\2y#\3\2\2\2\b,F]emo";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}