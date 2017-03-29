// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/Choreography.g4 by ANTLR 4.6
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
public class ChoreographyLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "INT", "TERMINATE", "IntegerLiteral", 
		"DecimalIntegerLiteral", "IntegerTypeSuffix", "DecimalNumeral", "Digits", 
		"Digit", "NonZeroDigit", "DigitOrUnderscore", "Underscores", "FloatingPointLiteral", 
		"DecimalFloatingPointLiteral", "ExponentPart", "ExponentIndicator", "SignedInteger", 
		"Sign", "FloatTypeSuffix", "BooleanLiteral", "CharacterLiteral", "SingleCharacter", 
		"StringLiteral", "StringCharacters", "StringCharacter", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "DOT", "ASSIGN", "GT", 
		"LT", "TILDE", "COLON", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", 
		"INC", "DEC", "SUB", "DIV", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", 
		"MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", 
		"LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "JavaLetter", 
		"JavaLetterOrDigit", "AT", "ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", 
		"Parallel", "Wildcard", "Arrow", "Send", "Receive", "Select", "Choose", 
		"Continue"
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
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 66:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 67:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2@\u01eb\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\7\6\7\u00bb\n\7\r\7\16\7\u00bc\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\5"+
		"\n\u00c8\n\n\3\13\3\13\3\f\3\f\3\f\5\f\u00cf\n\f\3\f\3\f\3\f\5\f\u00d4"+
		"\n\f\5\f\u00d6\n\f\3\r\3\r\7\r\u00da\n\r\f\r\16\r\u00dd\13\r\3\r\5\r\u00e0"+
		"\n\r\3\16\3\16\5\16\u00e4\n\16\3\17\3\17\3\20\3\20\5\20\u00ea\n\20\3\21"+
		"\6\21\u00ed\n\21\r\21\16\21\u00ee\3\22\3\22\3\23\3\23\3\23\5\23\u00f6"+
		"\n\23\3\23\5\23\u00f9\n\23\3\23\5\23\u00fc\n\23\3\23\3\23\3\23\5\23\u0101"+
		"\n\23\3\23\5\23\u0104\n\23\3\23\3\23\3\23\5\23\u0109\n\23\3\23\3\23\3"+
		"\23\5\23\u010e\n\23\3\24\3\24\3\24\3\25\3\25\3\26\5\26\u0116\n\26\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\5\31\u0127\n\31\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\5\34\u0131\n"+
		"\34\3\34\3\34\3\35\6\35\u0136\n\35\r\35\16\35\u0137\3\36\3\36\3\37\3\37"+
		"\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3"+
		"*\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3"+
		"\61\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3"+
		"\67\38\38\38\39\39\39\3:\3:\3:\3;\3;\3;\3<\3<\3<\3=\3=\3=\3>\3>\3>\3?"+
		"\3?\3?\3@\3@\3@\3@\3A\3A\3A\3A\3B\3B\3B\3B\3B\3C\3C\7C\u019d\nC\fC\16"+
		"C\u01a0\13C\3D\3D\3D\3D\3D\3D\5D\u01a8\nD\3E\3E\3E\3E\3E\3E\5E\u01b0\n"+
		"E\3F\3F\3G\3G\3G\3G\3H\6H\u01b9\nH\rH\16H\u01ba\3H\3H\3I\3I\3I\3I\7I\u01c3"+
		"\nI\fI\16I\u01c6\13I\3I\3I\3I\3I\3I\3J\3J\3J\3J\7J\u01d1\nJ\fJ\16J\u01d4"+
		"\13J\3J\3J\3K\3K\3L\3L\3L\3L\3L\3M\3M\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3"+
		"R\3\u01c4\2S\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\2\25\2\27\2\31\2\33"+
		"\2\35\2\37\2!\2#\13%\2\'\2)\2+\2-\2/\2\61\f\63\r\65\2\67\169\2;\2=\17"+
		"?\20A\21C\22E\23G\24I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c"+
		"\"e#g$i%k&m\'o(q)s*u+w,y-{.}/\177\60\u0081\61\u0083\62\u0085\63\u0087"+
		"\2\u0089\2\u008b\64\u008d\65\u008f\66\u0091\67\u00938\u00959\u0097:\u0099"+
		";\u009b<\u009d=\u009f>\u00a1?\u00a3@\3\2\21\3\2\62;\4\2NNnn\3\2\63;\4"+
		"\2GGgg\4\2--//\6\2FFHHffhh\4\2))^^\4\2$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802"+
		"\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17"+
		"\"\"\4\2\f\f\17\17\u01f6\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2#\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\67\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2"+
		"\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2"+
		"i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3"+
		"\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097"+
		"\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2"+
		"\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\3\u00a5\3\2\2\2\5\u00a8\3\2\2\2\7\u00ad"+
		"\3\2\2\2\t\u00b2\3\2\2\2\13\u00b6\3\2\2\2\r\u00ba\3\2\2\2\17\u00be\3\2"+
		"\2\2\21\u00c3\3\2\2\2\23\u00c5\3\2\2\2\25\u00c9\3\2\2\2\27\u00d5\3\2\2"+
		"\2\31\u00d7\3\2\2\2\33\u00e3\3\2\2\2\35\u00e5\3\2\2\2\37\u00e9\3\2\2\2"+
		"!\u00ec\3\2\2\2#\u00f0\3\2\2\2%\u010d\3\2\2\2\'\u010f\3\2\2\2)\u0112\3"+
		"\2\2\2+\u0115\3\2\2\2-\u0119\3\2\2\2/\u011b\3\2\2\2\61\u0126\3\2\2\2\63"+
		"\u0128\3\2\2\2\65\u012c\3\2\2\2\67\u012e\3\2\2\29\u0135\3\2\2\2;\u0139"+
		"\3\2\2\2=\u013b\3\2\2\2?\u013d\3\2\2\2A\u013f\3\2\2\2C\u0141\3\2\2\2E"+
		"\u0143\3\2\2\2G\u0145\3\2\2\2I\u0147\3\2\2\2K\u0149\3\2\2\2M\u014b\3\2"+
		"\2\2O\u014d\3\2\2\2Q\u014f\3\2\2\2S\u0151\3\2\2\2U\u0153\3\2\2\2W\u0155"+
		"\3\2\2\2Y\u0158\3\2\2\2[\u015b\3\2\2\2]\u015e\3\2\2\2_\u0161\3\2\2\2a"+
		"\u0164\3\2\2\2c\u0167\3\2\2\2e\u016a\3\2\2\2g\u016d\3\2\2\2i\u016f\3\2"+
		"\2\2k\u0171\3\2\2\2m\u0173\3\2\2\2o\u0175\3\2\2\2q\u0178\3\2\2\2s\u017b"+
		"\3\2\2\2u\u017e\3\2\2\2w\u0181\3\2\2\2y\u0184\3\2\2\2{\u0187\3\2\2\2}"+
		"\u018a\3\2\2\2\177\u018d\3\2\2\2\u0081\u0191\3\2\2\2\u0083\u0195\3\2\2"+
		"\2\u0085\u019a\3\2\2\2\u0087\u01a7\3\2\2\2\u0089\u01af\3\2\2\2\u008b\u01b1"+
		"\3\2\2\2\u008d\u01b3\3\2\2\2\u008f\u01b8\3\2\2\2\u0091\u01be\3\2\2\2\u0093"+
		"\u01cc\3\2\2\2\u0095\u01d7\3\2\2\2\u0097\u01d9\3\2\2\2\u0099\u01de\3\2"+
		"\2\2\u009b\u01e1\3\2\2\2\u009d\u01e3\3\2\2\2\u009f\u01e5\3\2\2\2\u00a1"+
		"\u01e7\3\2\2\2\u00a3\u01e9\3\2\2\2\u00a5\u00a6\7k\2\2\u00a6\u00a7\7h\2"+
		"\2\u00a7\4\3\2\2\2\u00a8\u00a9\7v\2\2\u00a9\u00aa\7j\2\2\u00aa\u00ab\7"+
		"g\2\2\u00ab\u00ac\7p\2\2\u00ac\6\3\2\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af"+
		"\7n\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1\7g\2\2\u00b1\b\3\2\2\2\u00b2\u00b3"+
		"\7f\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7h\2\2\u00b5\n\3\2\2\2\u00b6\u00b7"+
		"\7k\2\2\u00b7\u00b8\7p\2\2\u00b8\f\3\2\2\2\u00b9\u00bb\t\2\2\2\u00ba\u00b9"+
		"\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\16\3\2\2\2\u00be\u00bf\7u\2\2\u00bf\u00c0\7v\2\2\u00c0\u00c1\7q\2\2\u00c1"+
		"\u00c2\7r\2\2\u00c2\20\3\2\2\2\u00c3\u00c4\5\23\n\2\u00c4\22\3\2\2\2\u00c5"+
		"\u00c7\5\27\f\2\u00c6\u00c8\5\25\13\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8"+
		"\3\2\2\2\u00c8\24\3\2\2\2\u00c9\u00ca\t\3\2\2\u00ca\26\3\2\2\2\u00cb\u00d6"+
		"\7\62\2\2\u00cc\u00d3\5\35\17\2\u00cd\u00cf\5\31\r\2\u00ce\u00cd\3\2\2"+
		"\2\u00ce\u00cf\3\2\2\2\u00cf\u00d4\3\2\2\2\u00d0\u00d1\5!\21\2\u00d1\u00d2"+
		"\5\31\r\2\u00d2\u00d4\3\2\2\2\u00d3\u00ce\3\2\2\2\u00d3\u00d0\3\2\2\2"+
		"\u00d4\u00d6\3\2\2\2\u00d5\u00cb\3\2\2\2\u00d5\u00cc\3\2\2\2\u00d6\30"+
		"\3\2\2\2\u00d7\u00df\5\33\16\2\u00d8\u00da\5\37\20\2\u00d9\u00d8\3\2\2"+
		"\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00de"+
		"\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00e0\5\33\16\2\u00df\u00db\3\2\2\2"+
		"\u00df\u00e0\3\2\2\2\u00e0\32\3\2\2\2\u00e1\u00e4\7\62\2\2\u00e2\u00e4"+
		"\5\35\17\2\u00e3\u00e1\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4\34\3\2\2\2\u00e5"+
		"\u00e6\t\4\2\2\u00e6\36\3\2\2\2\u00e7\u00ea\5\33\16\2\u00e8\u00ea\7a\2"+
		"\2\u00e9\u00e7\3\2\2\2\u00e9\u00e8\3\2\2\2\u00ea \3\2\2\2\u00eb\u00ed"+
		"\7a\2\2\u00ec\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee"+
		"\u00ef\3\2\2\2\u00ef\"\3\2\2\2\u00f0\u00f1\5%\23\2\u00f1$\3\2\2\2\u00f2"+
		"\u00f3\5\31\r\2\u00f3\u00f5\7\60\2\2\u00f4\u00f6\5\31\r\2\u00f5\u00f4"+
		"\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f9\5\'\24\2"+
		"\u00f8\u00f7\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2\2\2\u00fa\u00fc"+
		"\5/\30\2\u00fb\u00fa\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u010e\3\2\2\2\u00fd"+
		"\u00fe\7\60\2\2\u00fe\u0100\5\31\r\2\u00ff\u0101\5\'\24\2\u0100\u00ff"+
		"\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0103\3\2\2\2\u0102\u0104\5/\30\2\u0103"+
		"\u0102\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u010e\3\2\2\2\u0105\u0106\5\31"+
		"\r\2\u0106\u0108\5\'\24\2\u0107\u0109\5/\30\2\u0108\u0107\3\2\2\2\u0108"+
		"\u0109\3\2\2\2\u0109\u010e\3\2\2\2\u010a\u010b\5\31\r\2\u010b\u010c\5"+
		"/\30\2\u010c\u010e\3\2\2\2\u010d\u00f2\3\2\2\2\u010d\u00fd\3\2\2\2\u010d"+
		"\u0105\3\2\2\2\u010d\u010a\3\2\2\2\u010e&\3\2\2\2\u010f\u0110\5)\25\2"+
		"\u0110\u0111\5+\26\2\u0111(\3\2\2\2\u0112\u0113\t\5\2\2\u0113*\3\2\2\2"+
		"\u0114\u0116\5-\27\2\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0118\5\31\r\2\u0118,\3\2\2\2\u0119\u011a\t\6\2\2\u011a"+
		".\3\2\2\2\u011b\u011c\t\7\2\2\u011c\60\3\2\2\2\u011d\u011e\7v\2\2\u011e"+
		"\u011f\7t\2\2\u011f\u0120\7w\2\2\u0120\u0127\7g\2\2\u0121\u0122\7h\2\2"+
		"\u0122\u0123\7c\2\2\u0123\u0124\7n\2\2\u0124\u0125\7u\2\2\u0125\u0127"+
		"\7g\2\2\u0126\u011d\3\2\2\2\u0126\u0121\3\2\2\2\u0127\62\3\2\2\2\u0128"+
		"\u0129\7)\2\2\u0129\u012a\5\65\33\2\u012a\u012b\7)\2\2\u012b\64\3\2\2"+
		"\2\u012c\u012d\n\b\2\2\u012d\66\3\2\2\2\u012e\u0130\7$\2\2\u012f\u0131"+
		"\59\35\2\u0130\u012f\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0132\3\2\2\2\u0132"+
		"\u0133\7$\2\2\u01338\3\2\2\2\u0134\u0136\5;\36\2\u0135\u0134\3\2\2\2\u0136"+
		"\u0137\3\2\2\2\u0137\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138:\3\2\2\2"+
		"\u0139\u013a\n\t\2\2\u013a<\3\2\2\2\u013b\u013c\7*\2\2\u013c>\3\2\2\2"+
		"\u013d\u013e\7+\2\2\u013e@\3\2\2\2\u013f\u0140\7}\2\2\u0140B\3\2\2\2\u0141"+
		"\u0142\7\177\2\2\u0142D\3\2\2\2\u0143\u0144\7]\2\2\u0144F\3\2\2\2\u0145"+
		"\u0146\7_\2\2\u0146H\3\2\2\2\u0147\u0148\7.\2\2\u0148J\3\2\2\2\u0149\u014a"+
		"\7\60\2\2\u014aL\3\2\2\2\u014b\u014c\7?\2\2\u014cN\3\2\2\2\u014d\u014e"+
		"\7@\2\2\u014eP\3\2\2\2\u014f\u0150\7>\2\2\u0150R\3\2\2\2\u0151\u0152\7"+
		"\u0080\2\2\u0152T\3\2\2\2\u0153\u0154\7<\2\2\u0154V\3\2\2\2\u0155\u0156"+
		"\7?\2\2\u0156\u0157\7?\2\2\u0157X\3\2\2\2\u0158\u0159\7>\2\2\u0159\u015a"+
		"\7?\2\2\u015aZ\3\2\2\2\u015b\u015c\7@\2\2\u015c\u015d\7?\2\2\u015d\\\3"+
		"\2\2\2\u015e\u015f\7#\2\2\u015f\u0160\7?\2\2\u0160^\3\2\2\2\u0161\u0162"+
		"\7(\2\2\u0162\u0163\7(\2\2\u0163`\3\2\2\2\u0164\u0165\7~\2\2\u0165\u0166"+
		"\7~\2\2\u0166b\3\2\2\2\u0167\u0168\7-\2\2\u0168\u0169\7-\2\2\u0169d\3"+
		"\2\2\2\u016a\u016b\7/\2\2\u016b\u016c\7/\2\2\u016cf\3\2\2\2\u016d\u016e"+
		"\7/\2\2\u016eh\3\2\2\2\u016f\u0170\7\61\2\2\u0170j\3\2\2\2\u0171\u0172"+
		"\7`\2\2\u0172l\3\2\2\2\u0173\u0174\7\'\2\2\u0174n\3\2\2\2\u0175\u0176"+
		"\7-\2\2\u0176\u0177\7?\2\2\u0177p\3\2\2\2\u0178\u0179\7/\2\2\u0179\u017a"+
		"\7?\2\2\u017ar\3\2\2\2\u017b\u017c\7,\2\2\u017c\u017d\7?\2\2\u017dt\3"+
		"\2\2\2\u017e\u017f\7\61\2\2\u017f\u0180\7?\2\2\u0180v\3\2\2\2\u0181\u0182"+
		"\7(\2\2\u0182\u0183\7?\2\2\u0183x\3\2\2\2\u0184\u0185\7~\2\2\u0185\u0186"+
		"\7?\2\2\u0186z\3\2\2\2\u0187\u0188\7`\2\2\u0188\u0189\7?\2\2\u0189|\3"+
		"\2\2\2\u018a\u018b\7\'\2\2\u018b\u018c\7?\2\2\u018c~\3\2\2\2\u018d\u018e"+
		"\7>\2\2\u018e\u018f\7>\2\2\u018f\u0190\7?\2\2\u0190\u0080\3\2\2\2\u0191"+
		"\u0192\7@\2\2\u0192\u0193\7@\2\2\u0193\u0194\7?\2\2\u0194\u0082\3\2\2"+
		"\2\u0195\u0196\7@\2\2\u0196\u0197\7@\2\2\u0197\u0198\7@\2\2\u0198\u0199"+
		"\7?\2\2\u0199\u0084\3\2\2\2\u019a\u019e\5\u0087D\2\u019b\u019d\5\u0089"+
		"E\2\u019c\u019b\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f\u0086\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1\u01a8\t\n"+
		"\2\2\u01a2\u01a3\n\13\2\2\u01a3\u01a8\6D\2\2\u01a4\u01a5\t\f\2\2\u01a5"+
		"\u01a6\t\r\2\2\u01a6\u01a8\6D\3\2\u01a7\u01a1\3\2\2\2\u01a7\u01a2\3\2"+
		"\2\2\u01a7\u01a4\3\2\2\2\u01a8\u0088\3\2\2\2\u01a9\u01b0\t\16\2\2\u01aa"+
		"\u01ab\n\13\2\2\u01ab\u01b0\6E\4\2\u01ac\u01ad\t\f\2\2\u01ad\u01ae\t\r"+
		"\2\2\u01ae\u01b0\6E\5\2\u01af\u01a9\3\2\2\2\u01af\u01aa\3\2\2\2\u01af"+
		"\u01ac\3\2\2\2\u01b0\u008a\3\2\2\2\u01b1\u01b2\7B\2\2\u01b2\u008c\3\2"+
		"\2\2\u01b3\u01b4\7\60\2\2\u01b4\u01b5\7\60\2\2\u01b5\u01b6\7\60\2\2\u01b6"+
		"\u008e\3\2\2\2\u01b7\u01b9\t\17\2\2\u01b8\u01b7\3\2\2\2\u01b9\u01ba\3"+
		"\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc"+
		"\u01bd\bH\2\2\u01bd\u0090\3\2\2\2\u01be\u01bf\7\61\2\2\u01bf\u01c0\7,"+
		"\2\2\u01c0\u01c4\3\2\2\2\u01c1\u01c3\13\2\2\2\u01c2\u01c1\3\2\2\2\u01c3"+
		"\u01c6\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c5\u01c7\3\2"+
		"\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01c8\7,\2\2\u01c8\u01c9\7\61\2\2\u01c9"+
		"\u01ca\3\2\2\2\u01ca\u01cb\bI\2\2\u01cb\u0092\3\2\2\2\u01cc\u01cd\7\61"+
		"\2\2\u01cd\u01ce\7\61\2\2\u01ce\u01d2\3\2\2\2\u01cf\u01d1\n\20\2\2\u01d0"+
		"\u01cf\3\2\2\2\u01d1\u01d4\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d2\u01d3\3\2"+
		"\2\2\u01d3\u01d5\3\2\2\2\u01d4\u01d2\3\2\2\2\u01d5\u01d6\bJ\2\2\u01d6"+
		"\u0094\3\2\2\2\u01d7\u01d8\7~\2\2\u01d8\u0096\3\2\2\2\u01d9\u01da\7v\2"+
		"\2\u01da\u01db\7j\2\2\u01db\u01dc\7k\2\2\u01dc\u01dd\7u\2\2\u01dd\u0098"+
		"\3\2\2\2\u01de\u01df\7/\2\2\u01df\u01e0\7@\2\2\u01e0\u009a\3\2\2\2\u01e1"+
		"\u01e2\7#\2\2\u01e2\u009c\3\2\2\2\u01e3\u01e4\7A\2\2\u01e4\u009e\3\2\2"+
		"\2\u01e5\u01e6\7-\2\2\u01e6\u00a0\3\2\2\2\u01e7\u01e8\7(\2\2\u01e8\u00a2"+
		"\3\2\2\2\u01e9\u01ea\7=\2\2\u01ea\u00a4\3\2\2\2\36\2\u00bc\u00c7\u00ce"+
		"\u00d3\u00d5\u00db\u00df\u00e3\u00e9\u00ee\u00f5\u00f8\u00fb\u0100\u0103"+
		"\u0108\u010d\u0115\u0126\u0130\u0137\u019e\u01a7\u01af\u01ba\u01c4\u01d2"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}