// Generated from /home/fmontesi/programs/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
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
		RULE_prog = 0, RULE_network = 1, RULE_behaviour = 2, RULE_expr = 3, RULE_value = 4, 
		RULE_process = 5, RULE_procedure = 6, RULE_label = 7;
	public static final String[] ruleNames = {
		"prog", "network", "behaviour", "expr", "value", "process", "procedure", 
		"label"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'has'", "'if'", "'then'", "'else'", "'def'", "'in'", null, "'stop'", 
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
		public TerminalNode EOF() { return getToken(NetworkParser.EOF, 0); }
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
			setState(16);
			network();
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Parallel) {
				{
				{
				setState(17);
				match(Parallel);
				setState(18);
				network();
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			match(EOF);
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
			setState(31);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TERMINATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				match(TERMINATE);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				process();
				setState(28);
				match(T__0);
				setState(29);
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
		public ProcessContext process() {
			return getRuleContext(ProcessContext.class,0);
		}
		public TerminalNode Send() { return getToken(NetworkParser.Send, 0); }
		public TerminalNode LT() { return getToken(NetworkParser.LT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode GT() { return getToken(NetworkParser.GT, 0); }
		public TerminalNode Continue() { return getToken(NetworkParser.Continue, 0); }
		public List<BehaviourContext> behaviour() {
			return getRuleContexts(BehaviourContext.class);
		}
		public BehaviourContext behaviour(int i) {
			return getRuleContext(BehaviourContext.class,i);
		}
		public TerminalNode Receive() { return getToken(NetworkParser.Receive, 0); }
		public TerminalNode Select() { return getToken(NetworkParser.Select, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public TerminalNode Choose() { return getToken(NetworkParser.Choose, 0); }
		public TerminalNode LBRACE() { return getToken(NetworkParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(NetworkParser.RBRACE, 0); }
		public List<TerminalNode> COLON() { return getTokens(NetworkParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(NetworkParser.COLON, i);
		}
		public ProcedureContext procedure() {
			return getRuleContext(ProcedureContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(NetworkParser.ASSIGN, 0); }
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
		int _la;
		try {
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				process();
				setState(34);
				match(Send);
				setState(35);
				match(LT);
				setState(36);
				expr();
				setState(37);
				match(GT);
				setState(38);
				match(Continue);
				setState(39);
				behaviour();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				process();
				setState(42);
				match(Receive);
				setState(43);
				match(Continue);
				setState(44);
				behaviour();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				process();
				setState(47);
				match(Select);
				setState(48);
				label();
				setState(49);
				match(Continue);
				setState(50);
				behaviour();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(52);
				process();
				setState(53);
				match(Choose);
				setState(54);
				match(LBRACE);
				{
				setState(55);
				label();
				setState(56);
				match(COLON);
				setState(57);
				behaviour();
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(59);
					match(COMMA);
					setState(60);
					label();
					setState(61);
					match(COLON);
					setState(62);
					behaviour();
					}
					}
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(69);
				match(RBRACE);
				setState(70);
				match(Continue);
				setState(71);
				behaviour();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(73);
				match(T__1);
				setState(74);
				expr();
				setState(75);
				match(T__2);
				setState(76);
				behaviour();
				setState(77);
				match(T__3);
				setState(78);
				behaviour();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(80);
				match(T__4);
				setState(81);
				procedure();
				setState(82);
				match(ASSIGN);
				setState(83);
				behaviour();
				setState(84);
				match(T__5);
				setState(85);
				behaviour();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(87);
				procedure();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(88);
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

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(NetworkParser.Identifier, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode Wildcard() { return getToken(NetworkParser.Wildcard, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NetworkListener ) ((NetworkListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof NetworkVisitor ) return ((NetworkVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expr);
		try {
			setState(94);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				match(Identifier);
				}
				break;
			case INT:
			case BooleanLiteral:
			case CharacterLiteral:
			case StringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				value();
				}
				break;
			case Wildcard:
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
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
		enterRule(_localctx, 8, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
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
		enterRule(_localctx, 10, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
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
		enterRule(_localctx, 12, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
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
		enterRule(_localctx, 14, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3Ak\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\7\2\26\n"+
		"\2\f\2\16\2\31\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3\"\n\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4C\n\4\f\4\16\4F\13\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\5\4\\\n\4\3\5\3\5\3\5\5\5a\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\t\2\2\n\2\4\6\b\n\f\16\20\2\3\4\2\t\t\r\17n\2\22\3\2\2\2\4!\3\2\2\2"+
		"\6[\3\2\2\2\b`\3\2\2\2\nb\3\2\2\2\fd\3\2\2\2\16f\3\2\2\2\20h\3\2\2\2\22"+
		"\27\5\4\3\2\23\24\7:\2\2\24\26\5\4\3\2\25\23\3\2\2\2\26\31\3\2\2\2\27"+
		"\25\3\2\2\2\27\30\3\2\2\2\30\32\3\2\2\2\31\27\3\2\2\2\32\33\7\2\2\3\33"+
		"\3\3\2\2\2\34\"\7\n\2\2\35\36\5\f\7\2\36\37\7\3\2\2\37 \5\6\4\2 \"\3\2"+
		"\2\2!\34\3\2\2\2!\35\3\2\2\2\"\5\3\2\2\2#$\5\f\7\2$%\7=\2\2%&\7\32\2\2"+
		"&\'\5\b\5\2\'(\7\31\2\2()\7A\2\2)*\5\6\4\2*\\\3\2\2\2+,\5\f\7\2,-\7>\2"+
		"\2-.\7A\2\2./\5\6\4\2/\\\3\2\2\2\60\61\5\f\7\2\61\62\7?\2\2\62\63\5\20"+
		"\t\2\63\64\7A\2\2\64\65\5\6\4\2\65\\\3\2\2\2\66\67\5\f\7\2\678\7@\2\2"+
		"89\7\22\2\29:\5\20\t\2:;\7\34\2\2;<\5\6\4\2<D\3\2\2\2=>\7\26\2\2>?\5\20"+
		"\t\2?@\7\34\2\2@A\5\6\4\2AC\3\2\2\2B=\3\2\2\2CF\3\2\2\2DB\3\2\2\2DE\3"+
		"\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\23\2\2HI\7A\2\2IJ\5\6\4\2J\\\3\2\2\2KL"+
		"\7\4\2\2LM\5\b\5\2MN\7\5\2\2NO\5\6\4\2OP\7\6\2\2PQ\5\6\4\2Q\\\3\2\2\2"+
		"RS\7\7\2\2ST\5\16\b\2TU\7\30\2\2UV\5\6\4\2VW\7\b\2\2WX\5\6\4\2X\\\3\2"+
		"\2\2Y\\\5\16\b\2Z\\\7\n\2\2[#\3\2\2\2[+\3\2\2\2[\60\3\2\2\2[\66\3\2\2"+
		"\2[K\3\2\2\2[R\3\2\2\2[Y\3\2\2\2[Z\3\2\2\2\\\7\3\2\2\2]a\7\64\2\2^a\5"+
		"\n\6\2_a\7;\2\2`]\3\2\2\2`^\3\2\2\2`_\3\2\2\2a\t\3\2\2\2bc\t\2\2\2c\13"+
		"\3\2\2\2de\7\64\2\2e\r\3\2\2\2fg\7\64\2\2g\17\3\2\2\2hi\7\64\2\2i\21\3"+
		"\2\2\2\7\27!D[`";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}