// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/CommonLexerRules.g4 by ANTLR 4.6
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
public class CommonLexerRulesParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IntegerLiteral=1, FloatingPointLiteral=2, BooleanLiteral=3, CharacterLiteral=4, 
		StringLiteral=5, LPAREN=6, RPAREN=7, LBRACE=8, RBRACE=9, LBRACK=10, RBRACK=11, 
		COMMA=12, DOT=13, ASSIGN=14, GT=15, LT=16, BANG=17, TILDE=18, QUESTION=19, 
		COLON=20, EQUAL=21, LE=22, GE=23, NOTEQUAL=24, AND=25, OR=26, INC=27, 
		DEC=28, ADD=29, SUB=30, DIV=31, BITAND=32, CARET=33, MOD=34, ADD_ASSIGN=35, 
		SUB_ASSIGN=36, MUL_ASSIGN=37, DIV_ASSIGN=38, AND_ASSIGN=39, OR_ASSIGN=40, 
		XOR_ASSIGN=41, MOD_ASSIGN=42, LSHIFT_ASSIGN=43, RSHIFT_ASSIGN=44, URSHIFT_ASSIGN=45, 
		Identifier=46, AT=47, ELLIPSIS=48, WS=49, COMMENT=50, LINE_COMMENT=51, 
		INT=52, TERMINATE=53, Parallel=54, Wildcard=55, Arrow=56, Continue=57;
	public static final int
		RULE_process = 0, RULE_procedure = 1, RULE_label = 2, RULE_value = 3;
	public static final String[] ruleNames = {
		"process", "procedure", "label", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", 
		"']'", "','", "'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'", "':'", 
		"'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", 
		"'-'", "'/'", "'&'", "'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", 
		"'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", "'...'", 
		null, null, null, null, "'stop'", "'|'", "'this'", "'->'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IntegerLiteral", "FloatingPointLiteral", "BooleanLiteral", "CharacterLiteral", 
		"StringLiteral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", 
		"DIV", "BITAND", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", 
		"DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
		"COMMENT", "LINE_COMMENT", "INT", "TERMINATE", "Parallel", "Wildcard", 
		"Arrow", "Continue"
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
	public String getGrammarFileName() { return "CommonLexerRules.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CommonLexerRulesParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProcessContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CommonLexerRulesParser.Identifier, 0); }
		public ProcessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_process; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).enterProcess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).exitProcess(this);
		}
	}

	public final ProcessContext process() throws RecognitionException {
		ProcessContext _localctx = new ProcessContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_process);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
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
		public TerminalNode Identifier() { return getToken(CommonLexerRulesParser.Identifier, 0); }
		public ProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).enterProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).exitProcedure(this);
		}
	}

	public final ProcedureContext procedure() throws RecognitionException {
		ProcedureContext _localctx = new ProcedureContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
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
		public TerminalNode Identifier() { return getToken(CommonLexerRulesParser.Identifier, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).exitLabel(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
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
		public TerminalNode INT() { return getToken(CommonLexerRulesParser.INT, 0); }
		public TerminalNode CharacterLiteral() { return getToken(CommonLexerRulesParser.CharacterLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(CommonLexerRulesParser.StringLiteral, 0); }
		public TerminalNode BooleanLiteral() { return getToken(CommonLexerRulesParser.BooleanLiteral, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommonLexerRulesListener ) ((CommonLexerRulesListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BooleanLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << INT))) != 0)) ) {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3;\23\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\2\2\6\2\4\6\b\2"+
		"\3\4\2\5\7\66\66\16\2\n\3\2\2\2\4\f\3\2\2\2\6\16\3\2\2\2\b\20\3\2\2\2"+
		"\n\13\7\60\2\2\13\3\3\2\2\2\f\r\7\60\2\2\r\5\3\2\2\2\16\17\7\60\2\2\17"+
		"\7\3\2\2\2\20\21\t\2\2\2\21\t\3\2\2\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}