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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "INT", "TERMINATE", "IntegerLiteral", 
		"DecimalIntegerLiteral", "IntegerTypeSuffix", "DecimalNumeral", "Digits", 
		"Digit", "NonZeroDigit", "DigitOrUnderscore", "Underscores", "FloatingPointLiteral", 
		"DecimalFloatingPointLiteral", "ExponentPart", "ExponentIndicator", "SignedInteger", 
		"Sign", "FloatTypeSuffix", "BooleanLiteral", "CharacterLiteral", "SingleCharacter", 
		"StringLiteral", "StringCharacters", "StringCharacter", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "ASSIGN", "GT", 
		"LT", "BANG", "TILDE", "QUESTION", "COLON", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "INC", "DEC", "ADD", "SUB", "MUL", "DIV", "BITAND", "CARET", 
		"MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", 
		"OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", 
		"URSHIFT_ASSIGN", "Identifier", "JavaLetter", "JavaLetterOrDigit", "AT", 
		"ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "Parallel", "Wildcard", "Arrow"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'main'", "'def'", "';'", "'if'", "'then'", "'else'", null, "'stop'", 
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
		case 72:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 73:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2A\u01f1\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\3\2\3"+
		"\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\b\6\b\u00c1\n\b\r\b\16\b\u00c2\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\13\3\13\5\13\u00ce\n\13\3\f\3\f\3\r\3\r\3\r\5\r\u00d5\n"+
		"\r\3\r\3\r\3\r\5\r\u00da\n\r\5\r\u00dc\n\r\3\16\3\16\7\16\u00e0\n\16\f"+
		"\16\16\16\u00e3\13\16\3\16\5\16\u00e6\n\16\3\17\3\17\5\17\u00ea\n\17\3"+
		"\20\3\20\3\21\3\21\5\21\u00f0\n\21\3\22\6\22\u00f3\n\22\r\22\16\22\u00f4"+
		"\3\23\3\23\3\24\3\24\3\24\5\24\u00fc\n\24\3\24\5\24\u00ff\n\24\3\24\5"+
		"\24\u0102\n\24\3\24\3\24\3\24\5\24\u0107\n\24\3\24\5\24\u010a\n\24\3\24"+
		"\3\24\3\24\5\24\u010f\n\24\3\24\3\24\3\24\5\24\u0114\n\24\3\25\3\25\3"+
		"\25\3\26\3\26\3\27\5\27\u011c\n\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u012d\n\32\3\33\3\33\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\5\35\u0137\n\35\3\35\3\35\3\36\6\36\u013c\n"+
		"\36\r\36\16\36\u013d\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3"+
		"&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3/\3\60\3"+
		"\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3"+
		"\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3<\3"+
		"=\3=\3>\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\3C\3D\3D\3D\3"+
		"E\3E\3E\3F\3F\3F\3F\3G\3G\3G\3G\3H\3H\3H\3H\3H\3I\3I\7I\u01ad\nI\fI\16"+
		"I\u01b0\13I\3J\3J\3J\3J\3J\3J\5J\u01b8\nJ\3K\3K\3K\3K\3K\3K\5K\u01c0\n"+
		"K\3L\3L\3M\3M\3M\3M\3N\6N\u01c9\nN\rN\16N\u01ca\3N\3N\3O\3O\3O\3O\7O\u01d3"+
		"\nO\fO\16O\u01d6\13O\3O\3O\3O\3O\3O\3P\3P\3P\3P\7P\u01e1\nP\fP\16P\u01e4"+
		"\13P\3P\3P\3Q\3Q\3R\3R\3R\3R\3R\3S\3S\3S\3\u01d4\2T\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\2\27\2\31\2\33\2\35\2\37\2!\2#\2%\f\'\2)\2+"+
		"\2-\2/\2\61\2\63\r\65\16\67\29\17;\2=\2?\20A\21C\22E\23G\24I\25K\26M\27"+
		"O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c\"e#g$i%k&m\'o(q)s*u+w,y-{.}/\177"+
		"\60\u0081\61\u0083\62\u0085\63\u0087\64\u0089\65\u008b\66\u008d\67\u008f"+
		"8\u00919\u0093\2\u0095\2\u0097:\u0099;\u009b<\u009d=\u009f>\u00a1?\u00a3"+
		"@\u00a5A\3\2\21\3\2\62;\4\2NNnn\3\2\63;\4\2GGgg\4\2--//\6\2FFHHffhh\4"+
		"\2))^^\4\2$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3"+
		"\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\u01fc"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2%\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\29\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G"+
		"\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2"+
		"\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2"+
		"\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m"+
		"\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2"+
		"\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2"+
		"\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d"+
		"\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2"+
		"\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2\2\2\u00a3"+
		"\3\2\2\2\2\u00a5\3\2\2\2\3\u00a7\3\2\2\2\5\u00ac\3\2\2\2\7\u00b0\3\2\2"+
		"\2\t\u00b2\3\2\2\2\13\u00b5\3\2\2\2\r\u00ba\3\2\2\2\17\u00c0\3\2\2\2\21"+
		"\u00c4\3\2\2\2\23\u00c9\3\2\2\2\25\u00cb\3\2\2\2\27\u00cf\3\2\2\2\31\u00db"+
		"\3\2\2\2\33\u00dd\3\2\2\2\35\u00e9\3\2\2\2\37\u00eb\3\2\2\2!\u00ef\3\2"+
		"\2\2#\u00f2\3\2\2\2%\u00f6\3\2\2\2\'\u0113\3\2\2\2)\u0115\3\2\2\2+\u0118"+
		"\3\2\2\2-\u011b\3\2\2\2/\u011f\3\2\2\2\61\u0121\3\2\2\2\63\u012c\3\2\2"+
		"\2\65\u012e\3\2\2\2\67\u0132\3\2\2\29\u0134\3\2\2\2;\u013b\3\2\2\2=\u013f"+
		"\3\2\2\2?\u0141\3\2\2\2A\u0143\3\2\2\2C\u0145\3\2\2\2E\u0147\3\2\2\2G"+
		"\u0149\3\2\2\2I\u014b\3\2\2\2K\u014d\3\2\2\2M\u014f\3\2\2\2O\u0151\3\2"+
		"\2\2Q\u0153\3\2\2\2S\u0155\3\2\2\2U\u0157\3\2\2\2W\u0159\3\2\2\2Y\u015b"+
		"\3\2\2\2[\u015d\3\2\2\2]\u015f\3\2\2\2_\u0162\3\2\2\2a\u0165\3\2\2\2c"+
		"\u0168\3\2\2\2e\u016b\3\2\2\2g\u016e\3\2\2\2i\u0171\3\2\2\2k\u0174\3\2"+
		"\2\2m\u0177\3\2\2\2o\u0179\3\2\2\2q\u017b\3\2\2\2s\u017d\3\2\2\2u\u017f"+
		"\3\2\2\2w\u0181\3\2\2\2y\u0183\3\2\2\2{\u0185\3\2\2\2}\u0188\3\2\2\2\177"+
		"\u018b\3\2\2\2\u0081\u018e\3\2\2\2\u0083\u0191\3\2\2\2\u0085\u0194\3\2"+
		"\2\2\u0087\u0197\3\2\2\2\u0089\u019a\3\2\2\2\u008b\u019d\3\2\2\2\u008d"+
		"\u01a1\3\2\2\2\u008f\u01a5\3\2\2\2\u0091\u01aa\3\2\2\2\u0093\u01b7\3\2"+
		"\2\2\u0095\u01bf\3\2\2\2\u0097\u01c1\3\2\2\2\u0099\u01c3\3\2\2\2\u009b"+
		"\u01c8\3\2\2\2\u009d\u01ce\3\2\2\2\u009f\u01dc\3\2\2\2\u00a1\u01e7\3\2"+
		"\2\2\u00a3\u01e9\3\2\2\2\u00a5\u01ee\3\2\2\2\u00a7\u00a8\7o\2\2\u00a8"+
		"\u00a9\7c\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7p\2\2\u00ab\4\3\2\2\2\u00ac"+
		"\u00ad\7f\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af\7h\2\2\u00af\6\3\2\2\2\u00b0"+
		"\u00b1\7=\2\2\u00b1\b\3\2\2\2\u00b2\u00b3\7k\2\2\u00b3\u00b4\7h\2\2\u00b4"+
		"\n\3\2\2\2\u00b5\u00b6\7v\2\2\u00b6\u00b7\7j\2\2\u00b7\u00b8\7g\2\2\u00b8"+
		"\u00b9\7p\2\2\u00b9\f\3\2\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc"+
		"\u00bd\7u\2\2\u00bd\u00be\7g\2\2\u00be\16\3\2\2\2\u00bf\u00c1\t\2\2\2"+
		"\u00c0\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3"+
		"\3\2\2\2\u00c3\20\3\2\2\2\u00c4\u00c5\7u\2\2\u00c5\u00c6\7v\2\2\u00c6"+
		"\u00c7\7q\2\2\u00c7\u00c8\7r\2\2\u00c8\22\3\2\2\2\u00c9\u00ca\5\25\13"+
		"\2\u00ca\24\3\2\2\2\u00cb\u00cd\5\31\r\2\u00cc\u00ce\5\27\f\2\u00cd\u00cc"+
		"\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\26\3\2\2\2\u00cf\u00d0\t\3\2\2\u00d0"+
		"\30\3\2\2\2\u00d1\u00dc\7\62\2\2\u00d2\u00d9\5\37\20\2\u00d3\u00d5\5\33"+
		"\16\2\u00d4\u00d3\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00da\3\2\2\2\u00d6"+
		"\u00d7\5#\22\2\u00d7\u00d8\5\33\16\2\u00d8\u00da\3\2\2\2\u00d9\u00d4\3"+
		"\2\2\2\u00d9\u00d6\3\2\2\2\u00da\u00dc\3\2\2\2\u00db\u00d1\3\2\2\2\u00db"+
		"\u00d2\3\2\2\2\u00dc\32\3\2\2\2\u00dd\u00e5\5\35\17\2\u00de\u00e0\5!\21"+
		"\2\u00df\u00de\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2"+
		"\3\2\2\2\u00e2\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e6\5\35\17\2"+
		"\u00e5\u00e1\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\34\3\2\2\2\u00e7\u00ea"+
		"\7\62\2\2\u00e8\u00ea\5\37\20\2\u00e9\u00e7\3\2\2\2\u00e9\u00e8\3\2\2"+
		"\2\u00ea\36\3\2\2\2\u00eb\u00ec\t\4\2\2\u00ec \3\2\2\2\u00ed\u00f0\5\35"+
		"\17\2\u00ee\u00f0\7a\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00ee\3\2\2\2\u00f0"+
		"\"\3\2\2\2\u00f1\u00f3\7a\2\2\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2"+
		"\u00f4\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5$\3\2\2\2\u00f6\u00f7\5"+
		"\'\24\2\u00f7&\3\2\2\2\u00f8\u00f9\5\33\16\2\u00f9\u00fb\7\60\2\2\u00fa"+
		"\u00fc\5\33\16\2\u00fb\u00fa\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3"+
		"\2\2\2\u00fd\u00ff\5)\25\2\u00fe\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff"+
		"\u0101\3\2\2\2\u0100\u0102\5\61\31\2\u0101\u0100\3\2\2\2\u0101\u0102\3"+
		"\2\2\2\u0102\u0114\3\2\2\2\u0103\u0104\7\60\2\2\u0104\u0106\5\33\16\2"+
		"\u0105\u0107\5)\25\2\u0106\u0105\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109"+
		"\3\2\2\2\u0108\u010a\5\61\31\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2"+
		"\u010a\u0114\3\2\2\2\u010b\u010c\5\33\16\2\u010c\u010e\5)\25\2\u010d\u010f"+
		"\5\61\31\2\u010e\u010d\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0114\3\2\2\2"+
		"\u0110\u0111\5\33\16\2\u0111\u0112\5\61\31\2\u0112\u0114\3\2\2\2\u0113"+
		"\u00f8\3\2\2\2\u0113\u0103\3\2\2\2\u0113\u010b\3\2\2\2\u0113\u0110\3\2"+
		"\2\2\u0114(\3\2\2\2\u0115\u0116\5+\26\2\u0116\u0117\5-\27\2\u0117*\3\2"+
		"\2\2\u0118\u0119\t\5\2\2\u0119,\3\2\2\2\u011a\u011c\5/\30\2\u011b\u011a"+
		"\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e\5\33\16\2"+
		"\u011e.\3\2\2\2\u011f\u0120\t\6\2\2\u0120\60\3\2\2\2\u0121\u0122\t\7\2"+
		"\2\u0122\62\3\2\2\2\u0123\u0124\7v\2\2\u0124\u0125\7t\2\2\u0125\u0126"+
		"\7w\2\2\u0126\u012d\7g\2\2\u0127\u0128\7h\2\2\u0128\u0129\7c\2\2\u0129"+
		"\u012a\7n\2\2\u012a\u012b\7u\2\2\u012b\u012d\7g\2\2\u012c\u0123\3\2\2"+
		"\2\u012c\u0127\3\2\2\2\u012d\64\3\2\2\2\u012e\u012f\7)\2\2\u012f\u0130"+
		"\5\67\34\2\u0130\u0131\7)\2\2\u0131\66\3\2\2\2\u0132\u0133\n\b\2\2\u0133"+
		"8\3\2\2\2\u0134\u0136\7$\2\2\u0135\u0137\5;\36\2\u0136\u0135\3\2\2\2\u0136"+
		"\u0137\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\7$\2\2\u0139:\3\2\2\2\u013a"+
		"\u013c\5=\37\2\u013b\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u013b\3\2"+
		"\2\2\u013d\u013e\3\2\2\2\u013e<\3\2\2\2\u013f\u0140\n\t\2\2\u0140>\3\2"+
		"\2\2\u0141\u0142\7*\2\2\u0142@\3\2\2\2\u0143\u0144\7+\2\2\u0144B\3\2\2"+
		"\2\u0145\u0146\7}\2\2\u0146D\3\2\2\2\u0147\u0148\7\177\2\2\u0148F\3\2"+
		"\2\2\u0149\u014a\7]\2\2\u014aH\3\2\2\2\u014b\u014c\7_\2\2\u014cJ\3\2\2"+
		"\2\u014d\u014e\7.\2\2\u014eL\3\2\2\2\u014f\u0150\7\60\2\2\u0150N\3\2\2"+
		"\2\u0151\u0152\7?\2\2\u0152P\3\2\2\2\u0153\u0154\7@\2\2\u0154R\3\2\2\2"+
		"\u0155\u0156\7>\2\2\u0156T\3\2\2\2\u0157\u0158\7#\2\2\u0158V\3\2\2\2\u0159"+
		"\u015a\7\u0080\2\2\u015aX\3\2\2\2\u015b\u015c\7A\2\2\u015cZ\3\2\2\2\u015d"+
		"\u015e\7<\2\2\u015e\\\3\2\2\2\u015f\u0160\7?\2\2\u0160\u0161\7?\2\2\u0161"+
		"^\3\2\2\2\u0162\u0163\7>\2\2\u0163\u0164\7?\2\2\u0164`\3\2\2\2\u0165\u0166"+
		"\7@\2\2\u0166\u0167\7?\2\2\u0167b\3\2\2\2\u0168\u0169\7#\2\2\u0169\u016a"+
		"\7?\2\2\u016ad\3\2\2\2\u016b\u016c\7(\2\2\u016c\u016d\7(\2\2\u016df\3"+
		"\2\2\2\u016e\u016f\7~\2\2\u016f\u0170\7~\2\2\u0170h\3\2\2\2\u0171\u0172"+
		"\7-\2\2\u0172\u0173\7-\2\2\u0173j\3\2\2\2\u0174\u0175\7/\2\2\u0175\u0176"+
		"\7/\2\2\u0176l\3\2\2\2\u0177\u0178\7-\2\2\u0178n\3\2\2\2\u0179\u017a\7"+
		"/\2\2\u017ap\3\2\2\2\u017b\u017c\7,\2\2\u017cr\3\2\2\2\u017d\u017e\7\61"+
		"\2\2\u017et\3\2\2\2\u017f\u0180\7(\2\2\u0180v\3\2\2\2\u0181\u0182\7`\2"+
		"\2\u0182x\3\2\2\2\u0183\u0184\7\'\2\2\u0184z\3\2\2\2\u0185\u0186\7-\2"+
		"\2\u0186\u0187\7?\2\2\u0187|\3\2\2\2\u0188\u0189\7/\2\2\u0189\u018a\7"+
		"?\2\2\u018a~\3\2\2\2\u018b\u018c\7,\2\2\u018c\u018d\7?\2\2\u018d\u0080"+
		"\3\2\2\2\u018e\u018f\7\61\2\2\u018f\u0190\7?\2\2\u0190\u0082\3\2\2\2\u0191"+
		"\u0192\7(\2\2\u0192\u0193\7?\2\2\u0193\u0084\3\2\2\2\u0194\u0195\7~\2"+
		"\2\u0195\u0196\7?\2\2\u0196\u0086\3\2\2\2\u0197\u0198\7`\2\2\u0198\u0199"+
		"\7?\2\2\u0199\u0088\3\2\2\2\u019a\u019b\7\'\2\2\u019b\u019c\7?\2\2\u019c"+
		"\u008a\3\2\2\2\u019d\u019e\7>\2\2\u019e\u019f\7>\2\2\u019f\u01a0\7?\2"+
		"\2\u01a0\u008c\3\2\2\2\u01a1\u01a2\7@\2\2\u01a2\u01a3\7@\2\2\u01a3\u01a4"+
		"\7?\2\2\u01a4\u008e\3\2\2\2\u01a5\u01a6\7@\2\2\u01a6\u01a7\7@\2\2\u01a7"+
		"\u01a8\7@\2\2\u01a8\u01a9\7?\2\2\u01a9\u0090\3\2\2\2\u01aa\u01ae\5\u0093"+
		"J\2\u01ab\u01ad\5\u0095K\2\u01ac\u01ab\3\2\2\2\u01ad\u01b0\3\2\2\2\u01ae"+
		"\u01ac\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u0092\3\2\2\2\u01b0\u01ae\3\2"+
		"\2\2\u01b1\u01b8\t\n\2\2\u01b2\u01b3\n\13\2\2\u01b3\u01b8\6J\2\2\u01b4"+
		"\u01b5\t\f\2\2\u01b5\u01b6\t\r\2\2\u01b6\u01b8\6J\3\2\u01b7\u01b1\3\2"+
		"\2\2\u01b7\u01b2\3\2\2\2\u01b7\u01b4\3\2\2\2\u01b8\u0094\3\2\2\2\u01b9"+
		"\u01c0\t\16\2\2\u01ba\u01bb\n\13\2\2\u01bb\u01c0\6K\4\2\u01bc\u01bd\t"+
		"\f\2\2\u01bd\u01be\t\r\2\2\u01be\u01c0\6K\5\2\u01bf\u01b9\3\2\2\2\u01bf"+
		"\u01ba\3\2\2\2\u01bf\u01bc\3\2\2\2\u01c0\u0096\3\2\2\2\u01c1\u01c2\7B"+
		"\2\2\u01c2\u0098\3\2\2\2\u01c3\u01c4\7\60\2\2\u01c4\u01c5\7\60\2\2\u01c5"+
		"\u01c6\7\60\2\2\u01c6\u009a\3\2\2\2\u01c7\u01c9\t\17\2\2\u01c8\u01c7\3"+
		"\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01c8\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb"+
		"\u01cc\3\2\2\2\u01cc\u01cd\bN\2\2\u01cd\u009c\3\2\2\2\u01ce\u01cf\7\61"+
		"\2\2\u01cf\u01d0\7,\2\2\u01d0\u01d4\3\2\2\2\u01d1\u01d3\13\2\2\2\u01d2"+
		"\u01d1\3\2\2\2\u01d3\u01d6\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d4\u01d2\3\2"+
		"\2\2\u01d5\u01d7\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d7\u01d8\7,\2\2\u01d8"+
		"\u01d9\7\61\2\2\u01d9\u01da\3\2\2\2\u01da\u01db\bO\2\2\u01db\u009e\3\2"+
		"\2\2\u01dc\u01dd\7\61\2\2\u01dd\u01de\7\61\2\2\u01de\u01e2\3\2\2\2\u01df"+
		"\u01e1\n\20\2\2\u01e0\u01df\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e0\3"+
		"\2\2\2\u01e2\u01e3\3\2\2\2\u01e3\u01e5\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5"+
		"\u01e6\bP\2\2\u01e6\u00a0\3\2\2\2\u01e7\u01e8\7~\2\2\u01e8\u00a2\3\2\2"+
		"\2\u01e9\u01ea\7v\2\2\u01ea\u01eb\7j\2\2\u01eb\u01ec\7k\2\2\u01ec\u01ed"+
		"\7u\2\2\u01ed\u00a4\3\2\2\2\u01ee\u01ef\7/\2\2\u01ef\u01f0\7@\2\2\u01f0"+
		"\u00a6\3\2\2\2\36\2\u00c2\u00cd\u00d4\u00d9\u00db\u00e1\u00e5\u00e9\u00ef"+
		"\u00f4\u00fb\u00fe\u0101\u0106\u0109\u010e\u0113\u011b\u012c\u0136\u013d"+
		"\u01ae\u01b7\u01bf\u01ca\u01d4\u01e2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}