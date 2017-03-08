// Generated from Network.g4 by ANTLR 4.5
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
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Network=1, R=2, Behaviour=3, IntegerLiteral=4, FloatingPointLiteral=5, 
		BooleanLiteral=6, CharacterLiteral=7, StringLiteral=8, LPAREN=9, RPAREN=10, 
		LBRACE=11, RBRACE=12, LBRACK=13, RBRACK=14, COMMA=15, DOT=16, ASSIGN=17, 
		GT=18, LT=19, TILDE=20, EQUAL=21, LE=22, GE=23, NOTEQUAL=24, AND=25, OR=26, 
		INC=27, DEC=28, SUB=29, DIV=30, CARET=31, MOD=32, ADD_ASSIGN=33, SUB_ASSIGN=34, 
		MUL_ASSIGN=35, DIV_ASSIGN=36, AND_ASSIGN=37, OR_ASSIGN=38, XOR_ASSIGN=39, 
		MOD_ASSIGN=40, LSHIFT_ASSIGN=41, RSHIFT_ASSIGN=42, URSHIFT_ASSIGN=43, 
		Identifier=44, AT=45, ELLIPSIS=46, WS=47, COMMENT=48, LINE_COMMENT=49, 
		IF=50, THEN=51, ELSE=52, DEF=53, IN=54, Expression=55, Process=56, Procedure=57, 
		Label=58, Value=59, INT=60, Terminate=61, Parallel=62, Wildcard=63, Arrow=64, 
		Send=65, Receive=66, Select=67, Choose=68, Has=69, Continue=70;
	public static final int
		RULE_prog = 0;
	public static final String[] ruleNames = {
		"prog"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, "'('", "')'", "'{'", 
		"'}'", "'['", "']'", "','", "'.'", "'='", "'>'", "'<'", "'~'", "'=='", 
		"'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'-'", "'/'", 
		"'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", 
		"'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", "'...'", null, null, 
		null, "'if'", "'then'", "'else'", "'def'", "'in'", null, null, null, null, 
		null, null, "'stop'", "'|'", "'this'", "'->'", "'!'", "'?'", "'+'", "'&'", 
		"':'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Network", "R", "Behaviour", "IntegerLiteral", "FloatingPointLiteral", 
		"BooleanLiteral", "CharacterLiteral", "StringLiteral", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "ASSIGN", "GT", 
		"LT", "TILDE", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", 
		"SUB", "DIV", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", 
		"DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "AT", "ELLIPSIS", "WS", 
		"COMMENT", "LINE_COMMENT", "IF", "THEN", "ELSE", "DEF", "IN", "Expression", 
		"Process", "Procedure", "Label", "Value", "INT", "Terminate", "Parallel", 
		"Wildcard", "Arrow", "Send", "Receive", "Select", "Choose", "Has", "Continue"
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
		public TerminalNode Network() { return getToken(NetworkParser.Network, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2);
			match(Network);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3H\7\4\2\t\2\3\2\3"+
		"\2\3\2\2\2\3\2\2\2\5\2\4\3\2\2\2\4\5\7\3\2\2\5\3\3\2\2\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}