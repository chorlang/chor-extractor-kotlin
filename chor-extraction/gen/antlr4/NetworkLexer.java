// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Network.g4 by ANTLR 4.6
package antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NetworkLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "INT", "TERMINATE", 
		"IntegerLiteral", "DecimalIntegerLiteral", "IntegerTypeSuffix", "DecimalNumeral", 
		"Digits", "Digit", "NonZeroDigit", "DigitOrUnderscore", "Underscores", 
		"FloatingPointLiteral", "DecimalFloatingPointLiteral", "ExponentPart", 
		"ExponentIndicator", "SignedInteger", "Sign", "FloatTypeSuffix", "BooleanLiteral", 
		"CharacterLiteral", "SingleCharacter", "StringLiteral", "StringCharacters", 
		"StringCharacter", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", 
		"MUL", "DIV", "BITAND", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", 
		"DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "JavaLetter", "JavaLetterOrDigit", 
		"AT", "ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "Parallel", "Wildcard", 
		"Arrow"
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


	public NetworkLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Network.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 73:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 74:
			return JavaLetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean JavaLetter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean JavaLetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2B\u01f4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\6\t\u00c4\n\t\r\t\16\t\u00c5\3\n\3"+
		"\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\5\f\u00d1\n\f\3\r\3\r\3\16\3\16\3\16"+
		"\5\16\u00d8\n\16\3\16\3\16\3\16\5\16\u00dd\n\16\5\16\u00df\n\16\3\17\3"+
		"\17\7\17\u00e3\n\17\f\17\16\17\u00e6\13\17\3\17\5\17\u00e9\n\17\3\20\3"+
		"\20\5\20\u00ed\n\20\3\21\3\21\3\22\3\22\5\22\u00f3\n\22\3\23\6\23\u00f6"+
		"\n\23\r\23\16\23\u00f7\3\24\3\24\3\25\3\25\3\25\5\25\u00ff\n\25\3\25\5"+
		"\25\u0102\n\25\3\25\5\25\u0105\n\25\3\25\3\25\3\25\5\25\u010a\n\25\3\25"+
		"\5\25\u010d\n\25\3\25\3\25\3\25\5\25\u0112\n\25\3\25\3\25\3\25\5\25\u0117"+
		"\n\25\3\26\3\26\3\26\3\27\3\27\3\30\5\30\u011f\n\30\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0130\n\33"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\5\36\u013a\n\36\3\36\3\36\3\37"+
		"\6\37\u013f\n\37\r\37\16\37\u0140\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3"+
		"%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3"+
		"\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3"+
		"<\3<\3=\3=\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\3C\3D\3D\3"+
		"D\3E\3E\3E\3F\3F\3F\3G\3G\3G\3G\3H\3H\3H\3H\3I\3I\3I\3I\3I\3J\3J\7J\u01b0"+
		"\nJ\fJ\16J\u01b3\13J\3K\3K\3K\3K\3K\3K\5K\u01bb\nK\3L\3L\3L\3L\3L\3L\5"+
		"L\u01c3\nL\3M\3M\3N\3N\3N\3N\3O\6O\u01cc\nO\rO\16O\u01cd\3O\3O\3P\3P\3"+
		"P\3P\7P\u01d6\nP\fP\16P\u01d9\13P\3P\3P\3P\3P\3P\3Q\3Q\3Q\3Q\7Q\u01e4"+
		"\nQ\fQ\16Q\u01e7\13Q\3Q\3Q\3R\3R\3S\3S\3S\3S\3S\3T\3T\3T\3\u01d7\2U\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31\2\33\2\35\2\37\2"+
		"!\2#\2%\2\'\r)\2+\2-\2/\2\61\2\63\2\65\16\67\179\2;\20=\2?\2A\21C\22E"+
		"\23G\24I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c\"e#g$i%k&m\'"+
		"o(q)s*u+w,y-{.}/\177\60\u0081\61\u0083\62\u0085\63\u0087\64\u0089\65\u008b"+
		"\66\u008d\67\u008f8\u00919\u0093:\u0095\2\u0097\2\u0099;\u009b<\u009d"+
		"=\u009f>\u00a1?\u00a3@\u00a5A\u00a7B\3\2\21\3\2\62;\4\2NNnn\3\2\63;\4"+
		"\2GGgg\4\2--//\6\2FFHHffhh\4\2))^^\4\2$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802"+
		"\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17"+
		"\"\"\4\2\f\f\17\17\u01ff\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\'\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\2;\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2"+
		"\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2"+
		"i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3"+
		"\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2"+
		"\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093"+
		"\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2"+
		"\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\3\u00a9"+
		"\3\2\2\2\5\u00ac\3\2\2\2\7\u00ae\3\2\2\2\t\u00b1\3\2\2\2\13\u00b6\3\2"+
		"\2\2\r\u00bb\3\2\2\2\17\u00bf\3\2\2\2\21\u00c3\3\2\2\2\23\u00c7\3\2\2"+
		"\2\25\u00cc\3\2\2\2\27\u00ce\3\2\2\2\31\u00d2\3\2\2\2\33\u00de\3\2\2\2"+
		"\35\u00e0\3\2\2\2\37\u00ec\3\2\2\2!\u00ee\3\2\2\2#\u00f2\3\2\2\2%\u00f5"+
		"\3\2\2\2\'\u00f9\3\2\2\2)\u0116\3\2\2\2+\u0118\3\2\2\2-\u011b\3\2\2\2"+
		"/\u011e\3\2\2\2\61\u0122\3\2\2\2\63\u0124\3\2\2\2\65\u012f\3\2\2\2\67"+
		"\u0131\3\2\2\29\u0135\3\2\2\2;\u0137\3\2\2\2=\u013e\3\2\2\2?\u0142\3\2"+
		"\2\2A\u0144\3\2\2\2C\u0146\3\2\2\2E\u0148\3\2\2\2G\u014a\3\2\2\2I\u014c"+
		"\3\2\2\2K\u014e\3\2\2\2M\u0150\3\2\2\2O\u0152\3\2\2\2Q\u0154\3\2\2\2S"+
		"\u0156\3\2\2\2U\u0158\3\2\2\2W\u015a\3\2\2\2Y\u015c\3\2\2\2[\u015e\3\2"+
		"\2\2]\u0160\3\2\2\2_\u0162\3\2\2\2a\u0165\3\2\2\2c\u0168\3\2\2\2e\u016b"+
		"\3\2\2\2g\u016e\3\2\2\2i\u0171\3\2\2\2k\u0174\3\2\2\2m\u0177\3\2\2\2o"+
		"\u017a\3\2\2\2q\u017c\3\2\2\2s\u017e\3\2\2\2u\u0180\3\2\2\2w\u0182\3\2"+
		"\2\2y\u0184\3\2\2\2{\u0186\3\2\2\2}\u0188\3\2\2\2\177\u018b\3\2\2\2\u0081"+
		"\u018e\3\2\2\2\u0083\u0191\3\2\2\2\u0085\u0194\3\2\2\2\u0087\u0197\3\2"+
		"\2\2\u0089\u019a\3\2\2\2\u008b\u019d\3\2\2\2\u008d\u01a0\3\2\2\2\u008f"+
		"\u01a4\3\2\2\2\u0091\u01a8\3\2\2\2\u0093\u01ad\3\2\2\2\u0095\u01ba\3\2"+
		"\2\2\u0097\u01c2\3\2\2\2\u0099\u01c4\3\2\2\2\u009b\u01c6\3\2\2\2\u009d"+
		"\u01cb\3\2\2\2\u009f\u01d1\3\2\2\2\u00a1\u01df\3\2\2\2\u00a3\u01ea\3\2"+
		"\2\2\u00a5\u01ec\3\2\2\2\u00a7\u01f1\3\2\2\2\u00a9\u00aa\7k\2\2\u00aa"+
		"\u00ab\7u\2\2\u00ab\4\3\2\2\2\u00ac\u00ad\7=\2\2\u00ad\6\3\2\2\2\u00ae"+
		"\u00af\7k\2\2\u00af\u00b0\7h\2\2\u00b0\b\3\2\2\2\u00b1\u00b2\7v\2\2\u00b2"+
		"\u00b3\7j\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7p\2\2\u00b5\n\3\2\2\2\u00b6"+
		"\u00b7\7g\2\2\u00b7\u00b8\7n\2\2\u00b8\u00b9\7u\2\2\u00b9\u00ba\7g\2\2"+
		"\u00ba\f\3\2\2\2\u00bb\u00bc\7f\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7h"+
		"\2\2\u00be\16\3\2\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7p\2\2\u00c1\20\3"+
		"\2\2\2\u00c2\u00c4\t\2\2\2\u00c3\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\22\3\2\2\2\u00c7\u00c8\7u\2\2"+
		"\u00c8\u00c9\7v\2\2\u00c9\u00ca\7q\2\2\u00ca\u00cb\7r\2\2\u00cb\24\3\2"+
		"\2\2\u00cc\u00cd\5\27\f\2\u00cd\26\3\2\2\2\u00ce\u00d0\5\33\16\2\u00cf"+
		"\u00d1\5\31\r\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\30\3\2\2"+
		"\2\u00d2\u00d3\t\3\2\2\u00d3\32\3\2\2\2\u00d4\u00df\7\62\2\2\u00d5\u00dc"+
		"\5!\21\2\u00d6\u00d8\5\35\17\2\u00d7\u00d6\3\2\2\2\u00d7\u00d8\3\2\2\2"+
		"\u00d8\u00dd\3\2\2\2\u00d9\u00da\5%\23\2\u00da\u00db\5\35\17\2\u00db\u00dd"+
		"\3\2\2\2\u00dc\u00d7\3\2\2\2\u00dc\u00d9\3\2\2\2\u00dd\u00df\3\2\2\2\u00de"+
		"\u00d4\3\2\2\2\u00de\u00d5\3\2\2\2\u00df\34\3\2\2\2\u00e0\u00e8\5\37\20"+
		"\2\u00e1\u00e3\5#\22\2\u00e2\u00e1\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2"+
		"\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7"+
		"\u00e9\5\37\20\2\u00e8\u00e4\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\36\3\2"+
		"\2\2\u00ea\u00ed\7\62\2\2\u00eb\u00ed\5!\21\2\u00ec\u00ea\3\2\2\2\u00ec"+
		"\u00eb\3\2\2\2\u00ed \3\2\2\2\u00ee\u00ef\t\4\2\2\u00ef\"\3\2\2\2\u00f0"+
		"\u00f3\5\37\20\2\u00f1\u00f3\7a\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f1\3"+
		"\2\2\2\u00f3$\3\2\2\2\u00f4\u00f6\7a\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f7"+
		"\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8&\3\2\2\2\u00f9"+
		"\u00fa\5)\25\2\u00fa(\3\2\2\2\u00fb\u00fc\5\35\17\2\u00fc\u00fe\7\60\2"+
		"\2\u00fd\u00ff\5\35\17\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff"+
		"\u0101\3\2\2\2\u0100\u0102\5+\26\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2"+
		"\2\2\u0102\u0104\3\2\2\2\u0103\u0105\5\63\32\2\u0104\u0103\3\2\2\2\u0104"+
		"\u0105\3\2\2\2\u0105\u0117\3\2\2\2\u0106\u0107\7\60\2\2\u0107\u0109\5"+
		"\35\17\2\u0108\u010a\5+\26\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u010c\3\2\2\2\u010b\u010d\5\63\32\2\u010c\u010b\3\2\2\2\u010c\u010d\3"+
		"\2\2\2\u010d\u0117\3\2\2\2\u010e\u010f\5\35\17\2\u010f\u0111\5+\26\2\u0110"+
		"\u0112\5\63\32\2\u0111\u0110\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0117\3"+
		"\2\2\2\u0113\u0114\5\35\17\2\u0114\u0115\5\63\32\2\u0115\u0117\3\2\2\2"+
		"\u0116\u00fb\3\2\2\2\u0116\u0106\3\2\2\2\u0116\u010e\3\2\2\2\u0116\u0113"+
		"\3\2\2\2\u0117*\3\2\2\2\u0118\u0119\5-\27\2\u0119\u011a\5/\30\2\u011a"+
		",\3\2\2\2\u011b\u011c\t\5\2\2\u011c.\3\2\2\2\u011d\u011f\5\61\31\2\u011e"+
		"\u011d\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\5\35"+
		"\17\2\u0121\60\3\2\2\2\u0122\u0123\t\6\2\2\u0123\62\3\2\2\2\u0124\u0125"+
		"\t\7\2\2\u0125\64\3\2\2\2\u0126\u0127\7v\2\2\u0127\u0128\7t\2\2\u0128"+
		"\u0129\7w\2\2\u0129\u0130\7g\2\2\u012a\u012b\7h\2\2\u012b\u012c\7c\2\2"+
		"\u012c\u012d\7n\2\2\u012d\u012e\7u\2\2\u012e\u0130\7g\2\2\u012f\u0126"+
		"\3\2\2\2\u012f\u012a\3\2\2\2\u0130\66\3\2\2\2\u0131\u0132\7)\2\2\u0132"+
		"\u0133\59\35\2\u0133\u0134\7)\2\2\u01348\3\2\2\2\u0135\u0136\n\b\2\2\u0136"+
		":\3\2\2\2\u0137\u0139\7$\2\2\u0138\u013a\5=\37\2\u0139\u0138\3\2\2\2\u0139"+
		"\u013a\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c\7$\2\2\u013c<\3\2\2\2\u013d"+
		"\u013f\5? \2\u013e\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u013e\3\2\2"+
		"\2\u0140\u0141\3\2\2\2\u0141>\3\2\2\2\u0142\u0143\n\t\2\2\u0143@\3\2\2"+
		"\2\u0144\u0145\7*\2\2\u0145B\3\2\2\2\u0146\u0147\7+\2\2\u0147D\3\2\2\2"+
		"\u0148\u0149\7}\2\2\u0149F\3\2\2\2\u014a\u014b\7\177\2\2\u014bH\3\2\2"+
		"\2\u014c\u014d\7]\2\2\u014dJ\3\2\2\2\u014e\u014f\7_\2\2\u014fL\3\2\2\2"+
		"\u0150\u0151\7.\2\2\u0151N\3\2\2\2\u0152\u0153\7\60\2\2\u0153P\3\2\2\2"+
		"\u0154\u0155\7?\2\2\u0155R\3\2\2\2\u0156\u0157\7@\2\2\u0157T\3\2\2\2\u0158"+
		"\u0159\7>\2\2\u0159V\3\2\2\2\u015a\u015b\7#\2\2\u015bX\3\2\2\2\u015c\u015d"+
		"\7\u0080\2\2\u015dZ\3\2\2\2\u015e\u015f\7A\2\2\u015f\\\3\2\2\2\u0160\u0161"+
		"\7<\2\2\u0161^\3\2\2\2\u0162\u0163\7?\2\2\u0163\u0164\7?\2\2\u0164`\3"+
		"\2\2\2\u0165\u0166\7>\2\2\u0166\u0167\7?\2\2\u0167b\3\2\2\2\u0168\u0169"+
		"\7@\2\2\u0169\u016a\7?\2\2\u016ad\3\2\2\2\u016b\u016c\7#\2\2\u016c\u016d"+
		"\7?\2\2\u016df\3\2\2\2\u016e\u016f\7(\2\2\u016f\u0170\7(\2\2\u0170h\3"+
		"\2\2\2\u0171\u0172\7~\2\2\u0172\u0173\7~\2\2\u0173j\3\2\2\2\u0174\u0175"+
		"\7-\2\2\u0175\u0176\7-\2\2\u0176l\3\2\2\2\u0177\u0178\7/\2\2\u0178\u0179"+
		"\7/\2\2\u0179n\3\2\2\2\u017a\u017b\7-\2\2\u017bp\3\2\2\2\u017c\u017d\7"+
		"/\2\2\u017dr\3\2\2\2\u017e\u017f\7,\2\2\u017ft\3\2\2\2\u0180\u0181\7\61"+
		"\2\2\u0181v\3\2\2\2\u0182\u0183\7(\2\2\u0183x\3\2\2\2\u0184\u0185\7`\2"+
		"\2\u0185z\3\2\2\2\u0186\u0187\7\'\2\2\u0187|\3\2\2\2\u0188\u0189\7-\2"+
		"\2\u0189\u018a\7?\2\2\u018a~\3\2\2\2\u018b\u018c\7/\2\2\u018c\u018d\7"+
		"?\2\2\u018d\u0080\3\2\2\2\u018e\u018f\7,\2\2\u018f\u0190\7?\2\2\u0190"+
		"\u0082\3\2\2\2\u0191\u0192\7\61\2\2\u0192\u0193\7?\2\2\u0193\u0084\3\2"+
		"\2\2\u0194\u0195\7(\2\2\u0195\u0196\7?\2\2\u0196\u0086\3\2\2\2\u0197\u0198"+
		"\7~\2\2\u0198\u0199\7?\2\2\u0199\u0088\3\2\2\2\u019a\u019b\7`\2\2\u019b"+
		"\u019c\7?\2\2\u019c\u008a\3\2\2\2\u019d\u019e\7\'\2\2\u019e\u019f\7?\2"+
		"\2\u019f\u008c\3\2\2\2\u01a0\u01a1\7>\2\2\u01a1\u01a2\7>\2\2\u01a2\u01a3"+
		"\7?\2\2\u01a3\u008e\3\2\2\2\u01a4\u01a5\7@\2\2\u01a5\u01a6\7@\2\2\u01a6"+
		"\u01a7\7?\2\2\u01a7\u0090\3\2\2\2\u01a8\u01a9\7@\2\2\u01a9\u01aa\7@\2"+
		"\2\u01aa\u01ab\7@\2\2\u01ab\u01ac\7?\2\2\u01ac\u0092\3\2\2\2\u01ad\u01b1"+
		"\5\u0095K\2\u01ae\u01b0\5\u0097L\2\u01af\u01ae\3\2\2\2\u01b0\u01b3\3\2"+
		"\2\2\u01b1\u01af\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2\u0094\3\2\2\2\u01b3"+
		"\u01b1\3\2\2\2\u01b4\u01bb\t\n\2\2\u01b5\u01b6\n\13\2\2\u01b6\u01bb\6"+
		"K\2\2\u01b7\u01b8\t\f\2\2\u01b8\u01b9\t\r\2\2\u01b9\u01bb\6K\3\2\u01ba"+
		"\u01b4\3\2\2\2\u01ba\u01b5\3\2\2\2\u01ba\u01b7\3\2\2\2\u01bb\u0096\3\2"+
		"\2\2\u01bc\u01c3\t\16\2\2\u01bd\u01be\n\13\2\2\u01be\u01c3\6L\4\2\u01bf"+
		"\u01c0\t\f\2\2\u01c0\u01c1\t\r\2\2\u01c1\u01c3\6L\5\2\u01c2\u01bc\3\2"+
		"\2\2\u01c2\u01bd\3\2\2\2\u01c2\u01bf\3\2\2\2\u01c3\u0098\3\2\2\2\u01c4"+
		"\u01c5\7B\2\2\u01c5\u009a\3\2\2\2\u01c6\u01c7\7\60\2\2\u01c7\u01c8\7\60"+
		"\2\2\u01c8\u01c9\7\60\2\2\u01c9\u009c\3\2\2\2\u01ca\u01cc\t\17\2\2\u01cb"+
		"\u01ca\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cb\3\2\2\2\u01cd\u01ce\3\2"+
		"\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0\bO\2\2\u01d0\u009e\3\2\2\2\u01d1"+
		"\u01d2\7\61\2\2\u01d2\u01d3\7,\2\2\u01d3\u01d7\3\2\2\2\u01d4\u01d6\13"+
		"\2\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d9\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d7"+
		"\u01d5\3\2\2\2\u01d8\u01da\3\2\2\2\u01d9\u01d7\3\2\2\2\u01da\u01db\7,"+
		"\2\2\u01db\u01dc\7\61\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\bP\2\2\u01de"+
		"\u00a0\3\2\2\2\u01df\u01e0\7\61\2\2\u01e0\u01e1\7\61\2\2\u01e1\u01e5\3"+
		"\2\2\2\u01e2\u01e4\n\20\2\2\u01e3\u01e2\3\2\2\2\u01e4\u01e7\3\2\2\2\u01e5"+
		"\u01e3\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e5\3\2"+
		"\2\2\u01e8\u01e9\bQ\2\2\u01e9\u00a2\3\2\2\2\u01ea\u01eb\7~\2\2\u01eb\u00a4"+
		"\3\2\2\2\u01ec\u01ed\7v\2\2\u01ed\u01ee\7j\2\2\u01ee\u01ef\7k\2\2\u01ef"+
		"\u01f0\7u\2\2\u01f0\u00a6\3\2\2\2\u01f1\u01f2\7/\2\2\u01f2\u01f3\7@\2"+
		"\2\u01f3\u00a8\3\2\2\2\36\2\u00c5\u00d0\u00d7\u00dc\u00de\u00e4\u00e8"+
		"\u00ec\u00f2\u00f7\u00fe\u0101\u0104\u0109\u010c\u0111\u0116\u011e\u012f"+
		"\u0139\u0140\u01b1\u01ba\u01c2\u01cd\u01d7\u01e5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}