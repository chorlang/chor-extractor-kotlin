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
		DOT=21, ASSIGN=22, GT=23, LT=24, BANG=25, TILDE=26, QUESTION=27, COLON=28, 
		EQUAL=29, LE=30, GE=31, NOTEQUAL=32, AND=33, OR=34, INC=35, DEC=36, ADD=37, 
		SUB=38, MUL=39, DIV=40, BITAND=41, CARET=42, MOD=43, ADD_ASSIGN=44, SUB_ASSIGN=45, 
		MUL_ASSIGN=46, DIV_ASSIGN=47, AND_ASSIGN=48, OR_ASSIGN=49, XOR_ASSIGN=50, 
		MOD_ASSIGN=51, LSHIFT_ASSIGN=52, RSHIFT_ASSIGN=53, URSHIFT_ASSIGN=54, 
		Identifier=55, AT=56, ELLIPSIS=57, WS=58, COMMENT=59, LINE_COMMENT=60, 
		Parallel=61, Wildcard=62, Arrow=63;
	public static final int
		RULE_program = 0, RULE_procedureDefinition = 1, RULE_main = 2, RULE_choreography = 3, 
		RULE_condition = 4, RULE_procedureInvocation = 5, RULE_interaction = 6, 
		RULE_communication = 7, RULE_selection = 8, RULE_expression = 9, RULE_process = 10, 
		RULE_procedure = 11, RULE_value = 12;
	public static final String[] ruleNames = {
		"program", "procedureDefinition", "main", "choreography", "condition", 
		"procedureInvocation", "interaction", "communication", "selectoffer", "expression",
		"process", "procedure", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'def'", "'main {'", "'if'", "'then'", "'else'", "';'", null, "'stop'", 
		null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"','", "'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", "'=='", 
		"'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", 
		"'*'", "'/'", "'&'", "'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", 
		"'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", "'...'", 
		null, null, null, "'|'", "'this'", "'->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "INT", "TERMINATE", "IntegerLiteral", 
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
	public static class ProgramContext extends ParserRuleContext {
		public MainContext main() {
			return getRuleContext(MainContext.class,0);
		}
		public List<ProcedureDefinitionContext> procedureDefinition() {
			return getRuleContexts(ProcedureDefinitionContext.class);
		}
		public ProcedureDefinitionContext procedureDefinition(int i) {
			return getRuleContext(ProcedureDefinitionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(26);
				procedureDefinition();
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			main();
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
		enterRule(_localctx, 2, RULE_procedureDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__0);
			setState(35);
			procedure();
			setState(36);
			match(LBRACE);
			setState(37);
			choreography();
			setState(38);
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

	public static class MainContext extends ParserRuleContext {
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitMain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_main);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(T__1);
			setState(41);
			choreography();
			setState(42);
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

	public static class ChoreographyContext extends ParserRuleContext {
		public InteractionContext interaction() {
			return getRuleContext(InteractionContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ProcedureInvocationContext procedureInvocation() {
			return getRuleContext(ProcedureInvocationContext.class,0);
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
		enterRule(_localctx, 6, RULE_choreography);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				interaction();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				condition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				procedureInvocation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(47);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		enterRule(_localctx, 8, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(T__2);
			setState(51);
			process();
			setState(52);
			match(DOT);
			setState(53);
			expression();
			setState(54);
			match(T__3);
			setState(55);
			choreography();
			setState(56);
			match(T__4);
			setState(57);
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
		enterRule(_localctx, 10, RULE_procedureInvocation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
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

	public static class InteractionContext extends ParserRuleContext {
		public CommunicationContext communication() {
			return getRuleContext(CommunicationContext.class,0);
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
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterInteraction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitInteraction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitInteraction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InteractionContext interaction() throws RecognitionException {
		InteractionContext _localctx = new InteractionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_interaction);
		try {
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				communication();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
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

	public static class CommunicationContext extends ParserRuleContext {
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
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
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
		enterRule(_localctx, 14, RULE_communication);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			process();
			setState(66);
			match(DOT);
			setState(67);
			expression();
			setState(68);
			match(Arrow);
			setState(69);
			process();
			setState(70);
			match(T__5);
			setState(71);
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

	public static class SelectionContext extends ParserRuleContext {
		public List<ProcessContext> process() {
			return getRuleContexts(ProcessContext.class);
		}
		public ProcessContext process(int i) {
			return getRuleContext(ProcessContext.class,i);
		}
		public TerminalNode Arrow() { return getToken(ChoreographyParser.Arrow, 0); }
		public TerminalNode LBRACK() { return getToken(ChoreographyParser.LBRACK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(ChoreographyParser.RBRACK, 0); }
		public ChoreographyContext choreography() {
			return getRuleContext(ChoreographyContext.class,0);
		}
		public SelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).enterSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ChoreographyListener ) ((ChoreographyListener)listener).exitSelection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ChoreographyVisitor ) return ((ChoreographyVisitor<? extends T>)visitor).visitSelection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionContext selection() throws RecognitionException {
		SelectionContext _localctx = new SelectionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_selection);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			process();
			setState(74);
			match(Arrow);
			setState(75);
			process();
			setState(76);
			match(LBRACK);
			setState(77);
			expression();
			setState(78);
			match(RBRACK);
			setState(79);
			match(T__5);
			setState(80);
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

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(ChoreographyParser.Identifier, 0); }
		public TerminalNode BooleanLiteral() { return getToken(ChoreographyParser.BooleanLiteral, 0); }
		public TerminalNode Wildcard() { return getToken(ChoreographyParser.Wildcard, 0); }
		public TerminalNode INT() { return getToken(ChoreographyParser.INT, 0); }
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
		enterRule(_localctx, 18, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
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
		enterRule(_localctx, 20, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
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
		enterRule(_localctx, 22, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
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
		enterRule(_localctx, 24, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3A]\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\4\16\t\16\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5\63\n\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\5\bB\n\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\4\6\2\t"+
		"\t\r\r99@@\4\2\t\t\r\17T\2\37\3\2\2\2\4$\3\2\2\2\6*\3\2\2\2\b\62\3\2\2"+
		"\2\n\64\3\2\2\2\f=\3\2\2\2\16A\3\2\2\2\20C\3\2\2\2\22K\3\2\2\2\24T\3\2"+
		"\2\2\26V\3\2\2\2\30X\3\2\2\2\32Z\3\2\2\2\34\36\5\4\3\2\35\34\3\2\2\2\36"+
		"!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \"\3\2\2\2!\37\3\2\2\2\"#\5\6\4\2"+
		"#\3\3\2\2\2$%\7\3\2\2%&\5\30\r\2&\'\7\22\2\2\'(\5\b\5\2()\7\23\2\2)\5"+
		"\3\2\2\2*+\7\4\2\2+,\5\b\5\2,-\7\23\2\2-\7\3\2\2\2.\63\5\16\b\2/\63\5"+
		"\n\6\2\60\63\5\f\7\2\61\63\7\n\2\2\62.\3\2\2\2\62/\3\2\2\2\62\60\3\2\2"+
		"\2\62\61\3\2\2\2\63\t\3\2\2\2\64\65\7\5\2\2\65\66\5\26\f\2\66\67\7\27"+
		"\2\2\678\5\24\13\289\7\6\2\29:\5\b\5\2:;\7\7\2\2;<\5\b\5\2<\13\3\2\2\2"+
		"=>\5\30\r\2>\r\3\2\2\2?B\5\20\t\2@B\5\22\n\2A?\3\2\2\2A@\3\2\2\2B\17\3"+
		"\2\2\2CD\5\26\f\2DE\7\27\2\2EF\5\24\13\2FG\7A\2\2GH\5\26\f\2HI\7\b\2\2"+
		"IJ\5\b\5\2J\21\3\2\2\2KL\5\26\f\2LM\7A\2\2MN\5\26\f\2NO\7\24\2\2OP\5\24"+
		"\13\2PQ\7\25\2\2QR\7\b\2\2RS\5\b\5\2S\23\3\2\2\2TU\t\2\2\2U\25\3\2\2\2"+
		"VW\79\2\2W\27\3\2\2\2XY\79\2\2Y\31\3\2\2\2Z[\t\3\2\2[\33\3\2\2\2\5\37"+
		"\62A";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}