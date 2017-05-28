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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, NOT=7, INT=8, TERMINATE=9, 
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
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "NOT", "INT", "TERMINATE", 
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
		null, "'main'", "'def'", "';'", "'if'", "'then'", "'else'", null, null, 
		"'stop'", null, null, null, null, null, "'('", "')'", "'{'", "'}'", "'['", 
		"']'", "','", "'.'", "'='", "'>'", "'<'", "'!'", null, "'?'", "':'", "'=='", 
		"'<='", "'>='", "'!='", "'&&'", "'||'", "'++'", "'--'", "'+'", "'-'", 
		"'*'", "'/'", "'&'", "'^'", "'%'", "'+='", "'-='", "'*='", "'/='", "'&='", 
		"'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", null, "'@'", "'...'", 
		null, null, null, "'|'", "'this'", "'->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "NOT", "INT", "TERMINATE", "IntegerLiteral", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2B\u01f5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\6\t\u00c5\n\t\r\t\16\t\u00c6\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\5\f\u00d2\n\f\3\r\3\r\3\16\3\16\3"+
		"\16\5\16\u00d9\n\16\3\16\3\16\3\16\5\16\u00de\n\16\5\16\u00e0\n\16\3\17"+
		"\3\17\7\17\u00e4\n\17\f\17\16\17\u00e7\13\17\3\17\5\17\u00ea\n\17\3\20"+
		"\3\20\5\20\u00ee\n\20\3\21\3\21\3\22\3\22\5\22\u00f4\n\22\3\23\6\23\u00f7"+
		"\n\23\r\23\16\23\u00f8\3\24\3\24\3\25\3\25\3\25\5\25\u0100\n\25\3\25\5"+
		"\25\u0103\n\25\3\25\5\25\u0106\n\25\3\25\3\25\3\25\5\25\u010b\n\25\3\25"+
		"\5\25\u010e\n\25\3\25\3\25\3\25\5\25\u0113\n\25\3\25\3\25\3\25\5\25\u0118"+
		"\n\25\3\26\3\26\3\26\3\27\3\27\3\30\5\30\u0120\n\30\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0131\n\33"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\5\36\u013b\n\36\3\36\3\36\3\37"+
		"\6\37\u0140\n\37\r\37\16\37\u0141\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3"+
		"%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3"+
		"\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3"+
		"<\3<\3=\3=\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\3C\3D\3D\3"+
		"D\3E\3E\3E\3F\3F\3F\3G\3G\3G\3G\3H\3H\3H\3H\3I\3I\3I\3I\3I\3J\3J\7J\u01b1"+
		"\nJ\fJ\16J\u01b4\13J\3K\3K\3K\3K\3K\3K\5K\u01bc\nK\3L\3L\3L\3L\3L\3L\5"+
		"L\u01c4\nL\3M\3M\3N\3N\3N\3N\3O\6O\u01cd\nO\rO\16O\u01ce\3O\3O\3P\3P\3"+
		"P\3P\7P\u01d7\nP\fP\16P\u01da\13P\3P\3P\3P\3P\3P\3Q\3Q\3Q\3Q\7Q\u01e5"+
		"\nQ\fQ\16Q\u01e8\13Q\3Q\3Q\3R\3R\3S\3S\3S\3S\3S\3T\3T\3T\3\u01d8\2U\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31\2\33\2\35\2\37\2"+
		"!\2#\2%\2\'\r)\2+\2-\2/\2\61\2\63\2\65\16\67\179\2;\20=\2?\2A\21C\22E"+
		"\23G\24I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c\"e#g$i%k&m\'"+
		"o(q)s*u+w,y-{.}/\177\60\u0081\61\u0083\62\u0085\63\u0087\64\u0089\65\u008b"+
		"\66\u008d\67\u008f8\u00919\u0093:\u0095\2\u0097\2\u0099;\u009b<\u009d"+
		"=\u009f>\u00a1?\u00a3@\u00a5A\u00a7B\3\2\21\3\2\62;\4\2NNnn\3\2\63;\4"+
		"\2GGgg\4\2--//\6\2FFHHffhh\4\2))^^\4\2$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802"+
		"\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17"+
		"\"\"\4\2\f\f\17\17\u0200\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
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
		"\3\2\2\2\5\u00ae\3\2\2\2\7\u00b2\3\2\2\2\t\u00b4\3\2\2\2\13\u00b7\3\2"+
		"\2\2\r\u00bc\3\2\2\2\17\u00c1\3\2\2\2\21\u00c4\3\2\2\2\23\u00c8\3\2\2"+
		"\2\25\u00cd\3\2\2\2\27\u00cf\3\2\2\2\31\u00d3\3\2\2\2\33\u00df\3\2\2\2"+
		"\35\u00e1\3\2\2\2\37\u00ed\3\2\2\2!\u00ef\3\2\2\2#\u00f3\3\2\2\2%\u00f6"+
		"\3\2\2\2\'\u00fa\3\2\2\2)\u0117\3\2\2\2+\u0119\3\2\2\2-\u011c\3\2\2\2"+
		"/\u011f\3\2\2\2\61\u0123\3\2\2\2\63\u0125\3\2\2\2\65\u0130\3\2\2\2\67"+
		"\u0132\3\2\2\29\u0136\3\2\2\2;\u0138\3\2\2\2=\u013f\3\2\2\2?\u0143\3\2"+
		"\2\2A\u0145\3\2\2\2C\u0147\3\2\2\2E\u0149\3\2\2\2G\u014b\3\2\2\2I\u014d"+
		"\3\2\2\2K\u014f\3\2\2\2M\u0151\3\2\2\2O\u0153\3\2\2\2Q\u0155\3\2\2\2S"+
		"\u0157\3\2\2\2U\u0159\3\2\2\2W\u015b\3\2\2\2Y\u015d\3\2\2\2[\u015f\3\2"+
		"\2\2]\u0161\3\2\2\2_\u0163\3\2\2\2a\u0166\3\2\2\2c\u0169\3\2\2\2e\u016c"+
		"\3\2\2\2g\u016f\3\2\2\2i\u0172\3\2\2\2k\u0175\3\2\2\2m\u0178\3\2\2\2o"+
		"\u017b\3\2\2\2q\u017d\3\2\2\2s\u017f\3\2\2\2u\u0181\3\2\2\2w\u0183\3\2"+
		"\2\2y\u0185\3\2\2\2{\u0187\3\2\2\2}\u0189\3\2\2\2\177\u018c\3\2\2\2\u0081"+
		"\u018f\3\2\2\2\u0083\u0192\3\2\2\2\u0085\u0195\3\2\2\2\u0087\u0198\3\2"+
		"\2\2\u0089\u019b\3\2\2\2\u008b\u019e\3\2\2\2\u008d\u01a1\3\2\2\2\u008f"+
		"\u01a5\3\2\2\2\u0091\u01a9\3\2\2\2\u0093\u01ae\3\2\2\2\u0095\u01bb\3\2"+
		"\2\2\u0097\u01c3\3\2\2\2\u0099\u01c5\3\2\2\2\u009b\u01c7\3\2\2\2\u009d"+
		"\u01cc\3\2\2\2\u009f\u01d2\3\2\2\2\u00a1\u01e0\3\2\2\2\u00a3\u01eb\3\2"+
		"\2\2\u00a5\u01ed\3\2\2\2\u00a7\u01f2\3\2\2\2\u00a9\u00aa\7o\2\2\u00aa"+
		"\u00ab\7c\2\2\u00ab\u00ac\7k\2\2\u00ac\u00ad\7p\2\2\u00ad\4\3\2\2\2\u00ae"+
		"\u00af\7f\2\2\u00af\u00b0\7g\2\2\u00b0\u00b1\7h\2\2\u00b1\6\3\2\2\2\u00b2"+
		"\u00b3\7=\2\2\u00b3\b\3\2\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6\7h\2\2\u00b6"+
		"\n\3\2\2\2\u00b7\u00b8\7v\2\2\u00b8\u00b9\7j\2\2\u00b9\u00ba\7g\2\2\u00ba"+
		"\u00bb\7p\2\2\u00bb\f\3\2\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7n\2\2\u00be"+
		"\u00bf\7u\2\2\u00bf\u00c0\7g\2\2\u00c0\16\3\2\2\2\u00c1\u00c2\7\u0080"+
		"\2\2\u00c2\20\3\2\2\2\u00c3\u00c5\t\2\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c6"+
		"\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\22\3\2\2\2\u00c8"+
		"\u00c9\7u\2\2\u00c9\u00ca\7v\2\2\u00ca\u00cb\7q\2\2\u00cb\u00cc\7r\2\2"+
		"\u00cc\24\3\2\2\2\u00cd\u00ce\5\27\f\2\u00ce\26\3\2\2\2\u00cf\u00d1\5"+
		"\33\16\2\u00d0\u00d2\5\31\r\2\u00d1\u00d0\3\2\2\2\u00d1\u00d2\3\2\2\2"+
		"\u00d2\30\3\2\2\2\u00d3\u00d4\t\3\2\2\u00d4\32\3\2\2\2\u00d5\u00e0\7\62"+
		"\2\2\u00d6\u00dd\5!\21\2\u00d7\u00d9\5\35\17\2\u00d8\u00d7\3\2\2\2\u00d8"+
		"\u00d9\3\2\2\2\u00d9\u00de\3\2\2\2\u00da\u00db\5%\23\2\u00db\u00dc\5\35"+
		"\17\2\u00dc\u00de\3\2\2\2\u00dd\u00d8\3\2\2\2\u00dd\u00da\3\2\2\2\u00de"+
		"\u00e0\3\2\2\2\u00df\u00d5\3\2\2\2\u00df\u00d6\3\2\2\2\u00e0\34\3\2\2"+
		"\2\u00e1\u00e9\5\37\20\2\u00e2\u00e4\5#\22\2\u00e3\u00e2\3\2\2\2\u00e4"+
		"\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2"+
		"\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00ea\5\37\20\2\u00e9\u00e5\3\2\2\2\u00e9"+
		"\u00ea\3\2\2\2\u00ea\36\3\2\2\2\u00eb\u00ee\7\62\2\2\u00ec\u00ee\5!\21"+
		"\2\u00ed\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee \3\2\2\2\u00ef\u00f0"+
		"\t\4\2\2\u00f0\"\3\2\2\2\u00f1\u00f4\5\37\20\2\u00f2\u00f4\7a\2\2\u00f3"+
		"\u00f1\3\2\2\2\u00f3\u00f2\3\2\2\2\u00f4$\3\2\2\2\u00f5\u00f7\7a\2\2\u00f6"+
		"\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2"+
		"\2\2\u00f9&\3\2\2\2\u00fa\u00fb\5)\25\2\u00fb(\3\2\2\2\u00fc\u00fd\5\35"+
		"\17\2\u00fd\u00ff\7\60\2\2\u00fe\u0100\5\35\17\2\u00ff\u00fe\3\2\2\2\u00ff"+
		"\u0100\3\2\2\2\u0100\u0102\3\2\2\2\u0101\u0103\5+\26\2\u0102\u0101\3\2"+
		"\2\2\u0102\u0103\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u0106\5\63\32\2\u0105"+
		"\u0104\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0118\3\2\2\2\u0107\u0108\7\60"+
		"\2\2\u0108\u010a\5\35\17\2\u0109\u010b\5+\26\2\u010a\u0109\3\2\2\2\u010a"+
		"\u010b\3\2\2\2\u010b\u010d\3\2\2\2\u010c\u010e\5\63\32\2\u010d\u010c\3"+
		"\2\2\2\u010d\u010e\3\2\2\2\u010e\u0118\3\2\2\2\u010f\u0110\5\35\17\2\u0110"+
		"\u0112\5+\26\2\u0111\u0113\5\63\32\2\u0112\u0111\3\2\2\2\u0112\u0113\3"+
		"\2\2\2\u0113\u0118\3\2\2\2\u0114\u0115\5\35\17\2\u0115\u0116\5\63\32\2"+
		"\u0116\u0118\3\2\2\2\u0117\u00fc\3\2\2\2\u0117\u0107\3\2\2\2\u0117\u010f"+
		"\3\2\2\2\u0117\u0114\3\2\2\2\u0118*\3\2\2\2\u0119\u011a\5-\27\2\u011a"+
		"\u011b\5/\30\2\u011b,\3\2\2\2\u011c\u011d\t\5\2\2\u011d.\3\2\2\2\u011e"+
		"\u0120\5\61\31\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\3"+
		"\2\2\2\u0121\u0122\5\35\17\2\u0122\60\3\2\2\2\u0123\u0124\t\6\2\2\u0124"+
		"\62\3\2\2\2\u0125\u0126\t\7\2\2\u0126\64\3\2\2\2\u0127\u0128\7v\2\2\u0128"+
		"\u0129\7t\2\2\u0129\u012a\7w\2\2\u012a\u0131\7g\2\2\u012b\u012c\7h\2\2"+
		"\u012c\u012d\7c\2\2\u012d\u012e\7n\2\2\u012e\u012f\7u\2\2\u012f\u0131"+
		"\7g\2\2\u0130\u0127\3\2\2\2\u0130\u012b\3\2\2\2\u0131\66\3\2\2\2\u0132"+
		"\u0133\7)\2\2\u0133\u0134\59\35\2\u0134\u0135\7)\2\2\u01358\3\2\2\2\u0136"+
		"\u0137\n\b\2\2\u0137:\3\2\2\2\u0138\u013a\7$\2\2\u0139\u013b\5=\37\2\u013a"+
		"\u0139\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013d\7$"+
		"\2\2\u013d<\3\2\2\2\u013e\u0140\5? \2\u013f\u013e\3\2\2\2\u0140\u0141"+
		"\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142>\3\2\2\2\u0143"+
		"\u0144\n\t\2\2\u0144@\3\2\2\2\u0145\u0146\7*\2\2\u0146B\3\2\2\2\u0147"+
		"\u0148\7+\2\2\u0148D\3\2\2\2\u0149\u014a\7}\2\2\u014aF\3\2\2\2\u014b\u014c"+
		"\7\177\2\2\u014cH\3\2\2\2\u014d\u014e\7]\2\2\u014eJ\3\2\2\2\u014f\u0150"+
		"\7_\2\2\u0150L\3\2\2\2\u0151\u0152\7.\2\2\u0152N\3\2\2\2\u0153\u0154\7"+
		"\60\2\2\u0154P\3\2\2\2\u0155\u0156\7?\2\2\u0156R\3\2\2\2\u0157\u0158\7"+
		"@\2\2\u0158T\3\2\2\2\u0159\u015a\7>\2\2\u015aV\3\2\2\2\u015b\u015c\7#"+
		"\2\2\u015cX\3\2\2\2\u015d\u015e\7\u0080\2\2\u015eZ\3\2\2\2\u015f\u0160"+
		"\7A\2\2\u0160\\\3\2\2\2\u0161\u0162\7<\2\2\u0162^\3\2\2\2\u0163\u0164"+
		"\7?\2\2\u0164\u0165\7?\2\2\u0165`\3\2\2\2\u0166\u0167\7>\2\2\u0167\u0168"+
		"\7?\2\2\u0168b\3\2\2\2\u0169\u016a\7@\2\2\u016a\u016b\7?\2\2\u016bd\3"+
		"\2\2\2\u016c\u016d\7#\2\2\u016d\u016e\7?\2\2\u016ef\3\2\2\2\u016f\u0170"+
		"\7(\2\2\u0170\u0171\7(\2\2\u0171h\3\2\2\2\u0172\u0173\7~\2\2\u0173\u0174"+
		"\7~\2\2\u0174j\3\2\2\2\u0175\u0176\7-\2\2\u0176\u0177\7-\2\2\u0177l\3"+
		"\2\2\2\u0178\u0179\7/\2\2\u0179\u017a\7/\2\2\u017an\3\2\2\2\u017b\u017c"+
		"\7-\2\2\u017cp\3\2\2\2\u017d\u017e\7/\2\2\u017er\3\2\2\2\u017f\u0180\7"+
		",\2\2\u0180t\3\2\2\2\u0181\u0182\7\61\2\2\u0182v\3\2\2\2\u0183\u0184\7"+
		"(\2\2\u0184x\3\2\2\2\u0185\u0186\7`\2\2\u0186z\3\2\2\2\u0187\u0188\7\'"+
		"\2\2\u0188|\3\2\2\2\u0189\u018a\7-\2\2\u018a\u018b\7?\2\2\u018b~\3\2\2"+
		"\2\u018c\u018d\7/\2\2\u018d\u018e\7?\2\2\u018e\u0080\3\2\2\2\u018f\u0190"+
		"\7,\2\2\u0190\u0191\7?\2\2\u0191\u0082\3\2\2\2\u0192\u0193\7\61\2\2\u0193"+
		"\u0194\7?\2\2\u0194\u0084\3\2\2\2\u0195\u0196\7(\2\2\u0196\u0197\7?\2"+
		"\2\u0197\u0086\3\2\2\2\u0198\u0199\7~\2\2\u0199\u019a\7?\2\2\u019a\u0088"+
		"\3\2\2\2\u019b\u019c\7`\2\2\u019c\u019d\7?\2\2\u019d\u008a\3\2\2\2\u019e"+
		"\u019f\7\'\2\2\u019f\u01a0\7?\2\2\u01a0\u008c\3\2\2\2\u01a1\u01a2\7>\2"+
		"\2\u01a2\u01a3\7>\2\2\u01a3\u01a4\7?\2\2\u01a4\u008e\3\2\2\2\u01a5\u01a6"+
		"\7@\2\2\u01a6\u01a7\7@\2\2\u01a7\u01a8\7?\2\2\u01a8\u0090\3\2\2\2\u01a9"+
		"\u01aa\7@\2\2\u01aa\u01ab\7@\2\2\u01ab\u01ac\7@\2\2\u01ac\u01ad\7?\2\2"+
		"\u01ad\u0092\3\2\2\2\u01ae\u01b2\5\u0095K\2\u01af\u01b1\5\u0097L\2\u01b0"+
		"\u01af\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2"+
		"\2\2\u01b3\u0094\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01bc\t\n\2\2\u01b6"+
		"\u01b7\n\13\2\2\u01b7\u01bc\6K\2\2\u01b8\u01b9\t\f\2\2\u01b9\u01ba\t\r"+
		"\2\2\u01ba\u01bc\6K\3\2\u01bb\u01b5\3\2\2\2\u01bb\u01b6\3\2\2\2\u01bb"+
		"\u01b8\3\2\2\2\u01bc\u0096\3\2\2\2\u01bd\u01c4\t\16\2\2\u01be\u01bf\n"+
		"\13\2\2\u01bf\u01c4\6L\4\2\u01c0\u01c1\t\f\2\2\u01c1\u01c2\t\r\2\2\u01c2"+
		"\u01c4\6L\5\2\u01c3\u01bd\3\2\2\2\u01c3\u01be\3\2\2\2\u01c3\u01c0\3\2"+
		"\2\2\u01c4\u0098\3\2\2\2\u01c5\u01c6\7B\2\2\u01c6\u009a\3\2\2\2\u01c7"+
		"\u01c8\7\60\2\2\u01c8\u01c9\7\60\2\2\u01c9\u01ca\7\60\2\2\u01ca\u009c"+
		"\3\2\2\2\u01cb\u01cd\t\17\2\2\u01cc\u01cb\3\2\2\2\u01cd\u01ce\3\2\2\2"+
		"\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01d1"+
		"\bO\2\2\u01d1\u009e\3\2\2\2\u01d2\u01d3\7\61\2\2\u01d3\u01d4\7,\2\2\u01d4"+
		"\u01d8\3\2\2\2\u01d5\u01d7\13\2\2\2\u01d6\u01d5\3\2\2\2\u01d7\u01da\3"+
		"\2\2\2\u01d8\u01d9\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01db\3\2\2\2\u01da"+
		"\u01d8\3\2\2\2\u01db\u01dc\7,\2\2\u01dc\u01dd\7\61\2\2\u01dd\u01de\3\2"+
		"\2\2\u01de\u01df\bP\2\2\u01df\u00a0\3\2\2\2\u01e0\u01e1\7\61\2\2\u01e1"+
		"\u01e2\7\61\2\2\u01e2\u01e6\3\2\2\2\u01e3\u01e5\n\20\2\2\u01e4\u01e3\3"+
		"\2\2\2\u01e5\u01e8\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7"+
		"\u01e9\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e9\u01ea\bQ\2\2\u01ea\u00a2\3\2"+
		"\2\2\u01eb\u01ec\7~\2\2\u01ec\u00a4\3\2\2\2\u01ed\u01ee\7v\2\2\u01ee\u01ef"+
		"\7j\2\2\u01ef\u01f0\7k\2\2\u01f0\u01f1\7u\2\2\u01f1\u00a6\3\2\2\2\u01f2"+
		"\u01f3\7/\2\2\u01f3\u01f4\7@\2\2\u01f4\u00a8\3\2\2\2\36\2\u00c6\u00d1"+
		"\u00d8\u00dd\u00df\u00e5\u00e9\u00ed\u00f3\u00f8\u00ff\u0102\u0105\u010a"+
		"\u010d\u0112\u0117\u011f\u0130\u013a\u0141\u01b2\u01bb\u01c3\u01ce\u01d8"+
		"\u01e6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}