// Generated from Choreography.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ChoreographyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

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


	public ChoreographyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Choreography.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2A\u01f3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\6\b\u00c3\n\b\r\b\16\b\u00c4\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\13\3\13\5\13\u00d0\n\13\3\f\3\f\3\r\3\r\3\r\5\r"+
		"\u00d7\n\r\3\r\3\r\3\r\5\r\u00dc\n\r\5\r\u00de\n\r\3\16\3\16\7\16\u00e2"+
		"\n\16\f\16\16\16\u00e5\13\16\3\16\5\16\u00e8\n\16\3\17\3\17\5\17\u00ec"+
		"\n\17\3\20\3\20\3\21\3\21\5\21\u00f2\n\21\3\22\6\22\u00f5\n\22\r\22\16"+
		"\22\u00f6\3\23\3\23\3\24\3\24\3\24\5\24\u00fe\n\24\3\24\5\24\u0101\n\24"+
		"\3\24\5\24\u0104\n\24\3\24\3\24\3\24\5\24\u0109\n\24\3\24\5\24\u010c\n"+
		"\24\3\24\3\24\3\24\5\24\u0111\n\24\3\24\3\24\3\24\5\24\u0116\n\24\3\25"+
		"\3\25\3\25\3\26\3\26\3\27\5\27\u011e\n\27\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u012f\n\32\3\33\3\33"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\5\35\u0139\n\35\3\35\3\35\3\36\6\36\u013e"+
		"\n\36\r\36\16\36\u013f\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3"+
		"%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3/\3\60"+
		"\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3;\3<\3"+
		"<\3=\3=\3>\3>\3>\3?\3?\3?\3@\3@\3@\3A\3A\3A\3B\3B\3B\3C\3C\3C\3D\3D\3"+
		"D\3E\3E\3E\3F\3F\3F\3F\3G\3G\3G\3G\3H\3H\3H\3H\3H\3I\3I\7I\u01af\nI\f"+
		"I\16I\u01b2\13I\3J\3J\3J\3J\3J\3J\5J\u01ba\nJ\3K\3K\3K\3K\3K\3K\5K\u01c2"+
		"\nK\3L\3L\3M\3M\3M\3M\3N\6N\u01cb\nN\rN\16N\u01cc\3N\3N\3O\3O\3O\3O\7"+
		"O\u01d5\nO\fO\16O\u01d8\13O\3O\3O\3O\3O\3O\3P\3P\3P\3P\7P\u01e3\nP\fP"+
		"\16P\u01e6\13P\3P\3P\3Q\3Q\3R\3R\3R\3R\3R\3S\3S\3S\3\u01d6\2T\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\2\27\2\31\2\33\2\35\2\37\2!\2#\2"+
		"%\f\'\2)\2+\2-\2/\2\61\2\63\r\65\16\67\29\17;\2=\2?\20A\21C\22E\23G\24"+
		"I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c\"e#g$i%k&m\'o(q)s*u"+
		"+w,y-{.}/\177\60\u0081\61\u0083\62\u0085\63\u0087\64\u0089\65\u008b\66"+
		"\u008d\67\u008f8\u00919\u0093\2\u0095\2\u0097:\u0099;\u009b<\u009d=\u009f"+
		">\u00a1?\u00a3@\u00a5A\3\2\21\3\2\62;\4\2NNnn\3\2\63;\4\2GGgg\4\2--//"+
		"\6\2FFHHffhh\4\2))^^\4\2$$^^\b\2&&C\\aaeeggu|\4\2\2\u0081\ud802\udc01"+
		"\3\2\ud802\udc01\3\2\udc02\ue001\t\2&&\62;C\\aaeeggu|\5\2\13\f\16\17\""+
		"\"\4\2\f\f\17\17\2\u01fe\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2%"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\29\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2"+
		"\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2"+
		"i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3"+
		"\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2"+
		"\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0097"+
		"\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2"+
		"\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\3\u00a7\3\2\2\2\5\u00ab"+
		"\3\2\2\2\7\u00b2\3\2\2\2\t\u00b5\3\2\2\2\13\u00ba\3\2\2\2\r\u00bf\3\2"+
		"\2\2\17\u00c2\3\2\2\2\21\u00c6\3\2\2\2\23\u00cb\3\2\2\2\25\u00cd\3\2\2"+
		"\2\27\u00d1\3\2\2\2\31\u00dd\3\2\2\2\33\u00df\3\2\2\2\35\u00eb\3\2\2\2"+
		"\37\u00ed\3\2\2\2!\u00f1\3\2\2\2#\u00f4\3\2\2\2%\u00f8\3\2\2\2\'\u0115"+
		"\3\2\2\2)\u0117\3\2\2\2+\u011a\3\2\2\2-\u011d\3\2\2\2/\u0121\3\2\2\2\61"+
		"\u0123\3\2\2\2\63\u012e\3\2\2\2\65\u0130\3\2\2\2\67\u0134\3\2\2\29\u0136"+
		"\3\2\2\2;\u013d\3\2\2\2=\u0141\3\2\2\2?\u0143\3\2\2\2A\u0145\3\2\2\2C"+
		"\u0147\3\2\2\2E\u0149\3\2\2\2G\u014b\3\2\2\2I\u014d\3\2\2\2K\u014f\3\2"+
		"\2\2M\u0151\3\2\2\2O\u0153\3\2\2\2Q\u0155\3\2\2\2S\u0157\3\2\2\2U\u0159"+
		"\3\2\2\2W\u015b\3\2\2\2Y\u015d\3\2\2\2[\u015f\3\2\2\2]\u0161\3\2\2\2_"+
		"\u0164\3\2\2\2a\u0167\3\2\2\2c\u016a\3\2\2\2e\u016d\3\2\2\2g\u0170\3\2"+
		"\2\2i\u0173\3\2\2\2k\u0176\3\2\2\2m\u0179\3\2\2\2o\u017b\3\2\2\2q\u017d"+
		"\3\2\2\2s\u017f\3\2\2\2u\u0181\3\2\2\2w\u0183\3\2\2\2y\u0185\3\2\2\2{"+
		"\u0187\3\2\2\2}\u018a\3\2\2\2\177\u018d\3\2\2\2\u0081\u0190\3\2\2\2\u0083"+
		"\u0193\3\2\2\2\u0085\u0196\3\2\2\2\u0087\u0199\3\2\2\2\u0089\u019c\3\2"+
		"\2\2\u008b\u019f\3\2\2\2\u008d\u01a3\3\2\2\2\u008f\u01a7\3\2\2\2\u0091"+
		"\u01ac\3\2\2\2\u0093\u01b9\3\2\2\2\u0095\u01c1\3\2\2\2\u0097\u01c3\3\2"+
		"\2\2\u0099\u01c5\3\2\2\2\u009b\u01ca\3\2\2\2\u009d\u01d0\3\2\2\2\u009f"+
		"\u01de\3\2\2\2\u00a1\u01e9\3\2\2\2\u00a3\u01eb\3\2\2\2\u00a5\u01f0\3\2"+
		"\2\2\u00a7\u00a8\7f\2\2\u00a8\u00a9\7g\2\2\u00a9\u00aa\7h\2\2\u00aa\4"+
		"\3\2\2\2\u00ab\u00ac\7o\2\2\u00ac\u00ad\7c\2\2\u00ad\u00ae\7k\2\2\u00ae"+
		"\u00af\7p\2\2\u00af\u00b0\7\"\2\2\u00b0\u00b1\7}\2\2\u00b1\6\3\2\2\2\u00b2"+
		"\u00b3\7k\2\2\u00b3\u00b4\7h\2\2\u00b4\b\3\2\2\2\u00b5\u00b6\7v\2\2\u00b6"+
		"\u00b7\7j\2\2\u00b7\u00b8\7g\2\2\u00b8\u00b9\7p\2\2\u00b9\n\3\2\2\2\u00ba"+
		"\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc\u00bd\7u\2\2\u00bd\u00be\7g\2\2"+
		"\u00be\f\3\2\2\2\u00bf\u00c0\7=\2\2\u00c0\16\3\2\2\2\u00c1\u00c3\t\2\2"+
		"\2\u00c2\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5"+
		"\3\2\2\2\u00c5\20\3\2\2\2\u00c6\u00c7\7u\2\2\u00c7\u00c8\7v\2\2\u00c8"+
		"\u00c9\7q\2\2\u00c9\u00ca\7r\2\2\u00ca\22\3\2\2\2\u00cb\u00cc\5\25\13"+
		"\2\u00cc\24\3\2\2\2\u00cd\u00cf\5\31\r\2\u00ce\u00d0\5\27\f\2\u00cf\u00ce"+
		"\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\26\3\2\2\2\u00d1\u00d2\t\3\2\2\u00d2"+
		"\30\3\2\2\2\u00d3\u00de\7\62\2\2\u00d4\u00db\5\37\20\2\u00d5\u00d7\5\33"+
		"\16\2\u00d6\u00d5\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00dc\3\2\2\2\u00d8"+
		"\u00d9\5#\22\2\u00d9\u00da\5\33\16\2\u00da\u00dc\3\2\2\2\u00db\u00d6\3"+
		"\2\2\2\u00db\u00d8\3\2\2\2\u00dc\u00de\3\2\2\2\u00dd\u00d3\3\2\2\2\u00dd"+
		"\u00d4\3\2\2\2\u00de\32\3\2\2\2\u00df\u00e7\5\35\17\2\u00e0\u00e2\5!\21"+
		"\2\u00e1\u00e0\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4"+
		"\3\2\2\2\u00e4\u00e6\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00e8\5\35\17\2"+
		"\u00e7\u00e3\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\34\3\2\2\2\u00e9\u00ec"+
		"\7\62\2\2\u00ea\u00ec\5\37\20\2\u00eb\u00e9\3\2\2\2\u00eb\u00ea\3\2\2"+
		"\2\u00ec\36\3\2\2\2\u00ed\u00ee\t\4\2\2\u00ee \3\2\2\2\u00ef\u00f2\5\35"+
		"\17\2\u00f0\u00f2\7a\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2"+
		"\"\3\2\2\2\u00f3\u00f5\7a\2\2\u00f4\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2"+
		"\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7$\3\2\2\2\u00f8\u00f9\5"+
		"\'\24\2\u00f9&\3\2\2\2\u00fa\u00fb\5\33\16\2\u00fb\u00fd\7\60\2\2\u00fc"+
		"\u00fe\5\33\16\2\u00fd\u00fc\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u0100\3"+
		"\2\2\2\u00ff\u0101\5)\25\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0103\3\2\2\2\u0102\u0104\5\61\31\2\u0103\u0102\3\2\2\2\u0103\u0104\3"+
		"\2\2\2\u0104\u0116\3\2\2\2\u0105\u0106\7\60\2\2\u0106\u0108\5\33\16\2"+
		"\u0107\u0109\5)\25\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b"+
		"\3\2\2\2\u010a\u010c\5\61\31\2\u010b\u010a\3\2\2\2\u010b\u010c\3\2\2\2"+
		"\u010c\u0116\3\2\2\2\u010d\u010e\5\33\16\2\u010e\u0110\5)\25\2\u010f\u0111"+
		"\5\61\31\2\u0110\u010f\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0116\3\2\2\2"+
		"\u0112\u0113\5\33\16\2\u0113\u0114\5\61\31\2\u0114\u0116\3\2\2\2\u0115"+
		"\u00fa\3\2\2\2\u0115\u0105\3\2\2\2\u0115\u010d\3\2\2\2\u0115\u0112\3\2"+
		"\2\2\u0116(\3\2\2\2\u0117\u0118\5+\26\2\u0118\u0119\5-\27\2\u0119*\3\2"+
		"\2\2\u011a\u011b\t\5\2\2\u011b,\3\2\2\2\u011c\u011e\5/\30\2\u011d\u011c"+
		"\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120\5\33\16\2"+
		"\u0120.\3\2\2\2\u0121\u0122\t\6\2\2\u0122\60\3\2\2\2\u0123\u0124\t\7\2"+
		"\2\u0124\62\3\2\2\2\u0125\u0126\7v\2\2\u0126\u0127\7t\2\2\u0127\u0128"+
		"\7w\2\2\u0128\u012f\7g\2\2\u0129\u012a\7h\2\2\u012a\u012b\7c\2\2\u012b"+
		"\u012c\7n\2\2\u012c\u012d\7u\2\2\u012d\u012f\7g\2\2\u012e\u0125\3\2\2"+
		"\2\u012e\u0129\3\2\2\2\u012f\64\3\2\2\2\u0130\u0131\7)\2\2\u0131\u0132"+
		"\5\67\34\2\u0132\u0133\7)\2\2\u0133\66\3\2\2\2\u0134\u0135\n\b\2\2\u0135"+
		"8\3\2\2\2\u0136\u0138\7$\2\2\u0137\u0139\5;\36\2\u0138\u0137\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u013b\7$\2\2\u013b:\3\2\2\2\u013c"+
		"\u013e\5=\37\2\u013d\u013c\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u013d\3\2"+
		"\2\2\u013f\u0140\3\2\2\2\u0140<\3\2\2\2\u0141\u0142\n\t\2\2\u0142>\3\2"+
		"\2\2\u0143\u0144\7*\2\2\u0144@\3\2\2\2\u0145\u0146\7+\2\2\u0146B\3\2\2"+
		"\2\u0147\u0148\7}\2\2\u0148D\3\2\2\2\u0149\u014a\7\177\2\2\u014aF\3\2"+
		"\2\2\u014b\u014c\7]\2\2\u014cH\3\2\2\2\u014d\u014e\7_\2\2\u014eJ\3\2\2"+
		"\2\u014f\u0150\7.\2\2\u0150L\3\2\2\2\u0151\u0152\7\60\2\2\u0152N\3\2\2"+
		"\2\u0153\u0154\7?\2\2\u0154P\3\2\2\2\u0155\u0156\7@\2\2\u0156R\3\2\2\2"+
		"\u0157\u0158\7>\2\2\u0158T\3\2\2\2\u0159\u015a\7#\2\2\u015aV\3\2\2\2\u015b"+
		"\u015c\7\u0080\2\2\u015cX\3\2\2\2\u015d\u015e\7A\2\2\u015eZ\3\2\2\2\u015f"+
		"\u0160\7<\2\2\u0160\\\3\2\2\2\u0161\u0162\7?\2\2\u0162\u0163\7?\2\2\u0163"+
		"^\3\2\2\2\u0164\u0165\7>\2\2\u0165\u0166\7?\2\2\u0166`\3\2\2\2\u0167\u0168"+
		"\7@\2\2\u0168\u0169\7?\2\2\u0169b\3\2\2\2\u016a\u016b\7#\2\2\u016b\u016c"+
		"\7?\2\2\u016cd\3\2\2\2\u016d\u016e\7(\2\2\u016e\u016f\7(\2\2\u016ff\3"+
		"\2\2\2\u0170\u0171\7~\2\2\u0171\u0172\7~\2\2\u0172h\3\2\2\2\u0173\u0174"+
		"\7-\2\2\u0174\u0175\7-\2\2\u0175j\3\2\2\2\u0176\u0177\7/\2\2\u0177\u0178"+
		"\7/\2\2\u0178l\3\2\2\2\u0179\u017a\7-\2\2\u017an\3\2\2\2\u017b\u017c\7"+
		"/\2\2\u017cp\3\2\2\2\u017d\u017e\7,\2\2\u017er\3\2\2\2\u017f\u0180\7\61"+
		"\2\2\u0180t\3\2\2\2\u0181\u0182\7(\2\2\u0182v\3\2\2\2\u0183\u0184\7`\2"+
		"\2\u0184x\3\2\2\2\u0185\u0186\7\'\2\2\u0186z\3\2\2\2\u0187\u0188\7-\2"+
		"\2\u0188\u0189\7?\2\2\u0189|\3\2\2\2\u018a\u018b\7/\2\2\u018b\u018c\7"+
		"?\2\2\u018c~\3\2\2\2\u018d\u018e\7,\2\2\u018e\u018f\7?\2\2\u018f\u0080"+
		"\3\2\2\2\u0190\u0191\7\61\2\2\u0191\u0192\7?\2\2\u0192\u0082\3\2\2\2\u0193"+
		"\u0194\7(\2\2\u0194\u0195\7?\2\2\u0195\u0084\3\2\2\2\u0196\u0197\7~\2"+
		"\2\u0197\u0198\7?\2\2\u0198\u0086\3\2\2\2\u0199\u019a\7`\2\2\u019a\u019b"+
		"\7?\2\2\u019b\u0088\3\2\2\2\u019c\u019d\7\'\2\2\u019d\u019e\7?\2\2\u019e"+
		"\u008a\3\2\2\2\u019f\u01a0\7>\2\2\u01a0\u01a1\7>\2\2\u01a1\u01a2\7?\2"+
		"\2\u01a2\u008c\3\2\2\2\u01a3\u01a4\7@\2\2\u01a4\u01a5\7@\2\2\u01a5\u01a6"+
		"\7?\2\2\u01a6\u008e\3\2\2\2\u01a7\u01a8\7@\2\2\u01a8\u01a9\7@\2\2\u01a9"+
		"\u01aa\7@\2\2\u01aa\u01ab\7?\2\2\u01ab\u0090\3\2\2\2\u01ac\u01b0\5\u0093"+
		"J\2\u01ad\u01af\5\u0095K\2\u01ae\u01ad\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0"+
		"\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u0092\3\2\2\2\u01b2\u01b0\3\2"+
		"\2\2\u01b3\u01ba\t\n\2\2\u01b4\u01b5\n\13\2\2\u01b5\u01ba\6J\2\2\u01b6"+
		"\u01b7\t\f\2\2\u01b7\u01b8\t\r\2\2\u01b8\u01ba\6J\3\2\u01b9\u01b3\3\2"+
		"\2\2\u01b9\u01b4\3\2\2\2\u01b9\u01b6\3\2\2\2\u01ba\u0094\3\2\2\2\u01bb"+
		"\u01c2\t\16\2\2\u01bc\u01bd\n\13\2\2\u01bd\u01c2\6K\4\2\u01be\u01bf\t"+
		"\f\2\2\u01bf\u01c0\t\r\2\2\u01c0\u01c2\6K\5\2\u01c1\u01bb\3\2\2\2\u01c1"+
		"\u01bc\3\2\2\2\u01c1\u01be\3\2\2\2\u01c2\u0096\3\2\2\2\u01c3\u01c4\7B"+
		"\2\2\u01c4\u0098\3\2\2\2\u01c5\u01c6\7\60\2\2\u01c6\u01c7\7\60\2\2\u01c7"+
		"\u01c8\7\60\2\2\u01c8\u009a\3\2\2\2\u01c9\u01cb\t\17\2\2\u01ca\u01c9\3"+
		"\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01ca\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd"+
		"\u01ce\3\2\2\2\u01ce\u01cf\bN\2\2\u01cf\u009c\3\2\2\2\u01d0\u01d1\7\61"+
		"\2\2\u01d1\u01d2\7,\2\2\u01d2\u01d6\3\2\2\2\u01d3\u01d5\13\2\2\2\u01d4"+
		"\u01d3\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d6\u01d4\3\2"+
		"\2\2\u01d7\u01d9\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01da\7,\2\2\u01da"+
		"\u01db\7\61\2\2\u01db\u01dc\3\2\2\2\u01dc\u01dd\bO\2\2\u01dd\u009e\3\2"+
		"\2\2\u01de\u01df\7\61\2\2\u01df\u01e0\7\61\2\2\u01e0\u01e4\3\2\2\2\u01e1"+
		"\u01e3\n\20\2\2\u01e2\u01e1\3\2\2\2\u01e3\u01e6\3\2\2\2\u01e4\u01e2\3"+
		"\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e7\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7"+
		"\u01e8\bP\2\2\u01e8\u00a0\3\2\2\2\u01e9\u01ea\7~\2\2\u01ea\u00a2\3\2\2"+
		"\2\u01eb\u01ec\7v\2\2\u01ec\u01ed\7j\2\2\u01ed\u01ee\7k\2\2\u01ee\u01ef"+
		"\7u\2\2\u01ef\u00a4\3\2\2\2\u01f0\u01f1\7/\2\2\u01f1\u01f2\7@\2\2\u01f2"+
		"\u00a6\3\2\2\2\36\2\u00c4\u00cf\u00d6\u00db\u00dd\u00e3\u00e7\u00eb\u00f1"+
		"\u00f6\u00fd\u0100\u0103\u0108\u010b\u0110\u0115\u011d\u012e\u0138\u013f"+
		"\u01b0\u01b9\u01c1\u01cc\u01d6\u01e4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}