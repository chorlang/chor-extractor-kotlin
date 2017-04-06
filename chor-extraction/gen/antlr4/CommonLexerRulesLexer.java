// Generated from /Users/lara/Documents/projects/core-choreographies/chor-extraction/src/main/antlr4/CommonLexerRules.g4 by ANTLR 4.6
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
public class CommonLexerRulesLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IntegerLiteral", "DecimalIntegerLiteral", "IntegerTypeSuffix", "DecimalNumeral", 
		"Digits", "Digit", "NonZeroDigit", "DigitOrUnderscore", "Underscores", 
		"FloatingPointLiteral", "DecimalFloatingPointLiteral", "ExponentPart", 
		"ExponentIndicator", "SignedInteger", "Sign", "FloatTypeSuffix", "BooleanLiteral", 
		"CharacterLiteral", "SingleCharacter", "StringLiteral", "StringCharacters", 
		"StringCharacter", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "TILDE", "QUESTION", "COLON", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "ADD", "SUB", 
		"DIV", "BITAND", "CARET", "MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", 
		"DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", 
		"RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "Identifier", "JavaLetter", "JavaLetterOrDigit", 
		"AT", "ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "INT", "TERMINATE", 
		"Parallel", "Wildcard", "Arrow", "Continue"
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


	public CommonLexerRulesLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommonLexerRules.g4"; }

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
		case 63:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 64:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2;\u01cd\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\3\2\3\2\3\3\3\3\5\3\u00a0\n\3\3\4\3\4\3\5"+
		"\3\5\3\5\5\5\u00a7\n\5\3\5\3\5\3\5\5\5\u00ac\n\5\5\5\u00ae\n\5\3\6\3\6"+
		"\7\6\u00b2\n\6\f\6\16\6\u00b5\13\6\3\6\5\6\u00b8\n\6\3\7\3\7\5\7\u00bc"+
		"\n\7\3\b\3\b\3\t\3\t\5\t\u00c2\n\t\3\n\6\n\u00c5\n\n\r\n\16\n\u00c6\3"+
		"\13\3\13\3\f\3\f\3\f\5\f\u00ce\n\f\3\f\5\f\u00d1\n\f\3\f\5\f\u00d4\n\f"+
		"\3\f\3\f\3\f\5\f\u00d9\n\f\3\f\5\f\u00dc\n\f\3\f\3\f\3\f\5\f\u00e1\n\f"+
		"\3\f\3\f\3\f\5\f\u00e6\n\f\3\r\3\r\3\r\3\16\3\16\3\17\5\17\u00ee\n\17"+
		"\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\5\22\u00ff\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\5\25\u0109"+
		"\n\25\3\25\3\25\3\26\6\26\u010e\n\26\r\26\16\26\u010f\3\27\3\27\3\30\3"+
		"\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3(\3(\3(\3"+
		")\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3"+
		"\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3"+
		"\67\3\67\3\67\38\38\38\39\39\39\3:\3:\3:\3;\3;\3;\3<\3<\3<\3=\3=\3=\3"+
		"=\3>\3>\3>\3>\3?\3?\3?\3?\3?\3@\3@\7@\u017d\n@\f@\16@\u0180\13@\3A\3A"+
		"\3A\3A\3A\3A\5A\u0188\nA\3B\3B\3B\3B\3B\3B\5B\u0190\nB\3C\3C\3D\3D\3D"+
		"\3D\3E\6E\u0199\nE\rE\16E\u019a\3E\3E\3F\3F\3F\3F\7F\u01a3\nF\fF\16F\u01a6"+
		"\13F\3F\3F\3F\3F\3F\3G\3G\3G\3G\7G\u01b1\nG\fG\16G\u01b4\13G\3G\3G\3H"+
		"\6H\u01b9\nH\rH\16H\u01ba\3I\3I\3I\3I\3I\3J\3J\3K\3K\3K\3K\3K\3L\3L\3"+
		"L\3M\3M\3\u01a4\2N\3\3\5\2\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\4\27\2\31"+
		"\2\33\2\35\2\37\2!\2#\5%\6\'\2)\7+\2-\2/\b\61\t\63\n\65\13\67\f9\r;\16"+
		"=\17?\20A\21C\22E\23G\24I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_"+
		" a!c\"e#g$i%k&m\'o(q)s*u+w,y-{.}/\177\60\u0081\2\u0083\2\u0085\61\u0087"+
		"\62\u0089\63\u008b\64\u008d\65\u008f\66\u0091\67\u00938\u00959\u0097:"+
		"\u0099;\3\2\21\4\2NNnn\3\2\63;\4\2GGgg\4\2--//\6\2FFHHffhh\4\2))^^\4\2"+
		"$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3\2\udc02\ue001"+
		"\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\3\2\62;\u01d8\2\3\3"+
		"\2\2\2\2\25\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2)\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3"+
		"\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2"+
		"\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2"+
		"c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3"+
		"\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2"+
		"\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3"+
		"\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2"+
		"\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\3\u009b"+
		"\3\2\2\2\5\u009d\3\2\2\2\7\u00a1\3\2\2\2\t\u00ad\3\2\2\2\13\u00af\3\2"+
		"\2\2\r\u00bb\3\2\2\2\17\u00bd\3\2\2\2\21\u00c1\3\2\2\2\23\u00c4\3\2\2"+
		"\2\25\u00c8\3\2\2\2\27\u00e5\3\2\2\2\31\u00e7\3\2\2\2\33\u00ea\3\2\2\2"+
		"\35\u00ed\3\2\2\2\37\u00f1\3\2\2\2!\u00f3\3\2\2\2#\u00fe\3\2\2\2%\u0100"+
		"\3\2\2\2\'\u0104\3\2\2\2)\u0106\3\2\2\2+\u010d\3\2\2\2-\u0111\3\2\2\2"+
		"/\u0113\3\2\2\2\61\u0115\3\2\2\2\63\u0117\3\2\2\2\65\u0119\3\2\2\2\67"+
		"\u011b\3\2\2\29\u011d\3\2\2\2;\u011f\3\2\2\2=\u0121\3\2\2\2?\u0123\3\2"+
		"\2\2A\u0125\3\2\2\2C\u0127\3\2\2\2E\u0129\3\2\2\2G\u012b\3\2\2\2I\u012d"+
		"\3\2\2\2K\u012f\3\2\2\2M\u0131\3\2\2\2O\u0134\3\2\2\2Q\u0137\3\2\2\2S"+
		"\u013a\3\2\2\2U\u013d\3\2\2\2W\u0140\3\2\2\2Y\u0143\3\2\2\2[\u0146\3\2"+
		"\2\2]\u0149\3\2\2\2_\u014b\3\2\2\2a\u014d\3\2\2\2c\u014f\3\2\2\2e\u0151"+
		"\3\2\2\2g\u0153\3\2\2\2i\u0155\3\2\2\2k\u0158\3\2\2\2m\u015b\3\2\2\2o"+
		"\u015e\3\2\2\2q\u0161\3\2\2\2s\u0164\3\2\2\2u\u0167\3\2\2\2w\u016a\3\2"+
		"\2\2y\u016d\3\2\2\2{\u0171\3\2\2\2}\u0175\3\2\2\2\177\u017a\3\2\2\2\u0081"+
		"\u0187\3\2\2\2\u0083\u018f\3\2\2\2\u0085\u0191\3\2\2\2\u0087\u0193\3\2"+
		"\2\2\u0089\u0198\3\2\2\2\u008b\u019e\3\2\2\2\u008d\u01ac\3\2\2\2\u008f"+
		"\u01b8\3\2\2\2\u0091\u01bc\3\2\2\2\u0093\u01c1\3\2\2\2\u0095\u01c3\3\2"+
		"\2\2\u0097\u01c8\3\2\2\2\u0099\u01cb\3\2\2\2\u009b\u009c\5\5\3\2\u009c"+
		"\4\3\2\2\2\u009d\u009f\5\t\5\2\u009e\u00a0\5\7\4\2\u009f\u009e\3\2\2\2"+
		"\u009f\u00a0\3\2\2\2\u00a0\6\3\2\2\2\u00a1\u00a2\t\2\2\2\u00a2\b\3\2\2"+
		"\2\u00a3\u00ae\7\62\2\2\u00a4\u00ab\5\17\b\2\u00a5\u00a7\5\13\6\2\u00a6"+
		"\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00ac\3\2\2\2\u00a8\u00a9\5\23"+
		"\n\2\u00a9\u00aa\5\13\6\2\u00aa\u00ac\3\2\2\2\u00ab\u00a6\3\2\2\2\u00ab"+
		"\u00a8\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00a3\3\2\2\2\u00ad\u00a4\3\2"+
		"\2\2\u00ae\n\3\2\2\2\u00af\u00b7\5\r\7\2\u00b0\u00b2\5\21\t\2\u00b1\u00b0"+
		"\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00b6\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b8\5\r\7\2\u00b7\u00b3\3\2"+
		"\2\2\u00b7\u00b8\3\2\2\2\u00b8\f\3\2\2\2\u00b9\u00bc\7\62\2\2\u00ba\u00bc"+
		"\5\17\b\2\u00bb\u00b9\3\2\2\2\u00bb\u00ba\3\2\2\2\u00bc\16\3\2\2\2\u00bd"+
		"\u00be\t\3\2\2\u00be\20\3\2\2\2\u00bf\u00c2\5\r\7\2\u00c0\u00c2\7a\2\2"+
		"\u00c1\u00bf\3\2\2\2\u00c1\u00c0\3\2\2\2\u00c2\22\3\2\2\2\u00c3\u00c5"+
		"\7a\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\24\3\2\2\2\u00c8\u00c9\5\27\f\2\u00c9\26\3\2\2\2"+
		"\u00ca\u00cb\5\13\6\2\u00cb\u00cd\7\60\2\2\u00cc\u00ce\5\13\6\2\u00cd"+
		"\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3\2\2\2\u00cf\u00d1\5\31"+
		"\r\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2"+
		"\u00d4\5!\21\2\u00d3\u00d2\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00e6\3\2"+
		"\2\2\u00d5\u00d6\7\60\2\2\u00d6\u00d8\5\13\6\2\u00d7\u00d9\5\31\r\2\u00d8"+
		"\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00db\3\2\2\2\u00da\u00dc\5!"+
		"\21\2\u00db\u00da\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00e6\3\2\2\2\u00dd"+
		"\u00de\5\13\6\2\u00de\u00e0\5\31\r\2\u00df\u00e1\5!\21\2\u00e0\u00df\3"+
		"\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e6\3\2\2\2\u00e2\u00e3\5\13\6\2\u00e3"+
		"\u00e4\5!\21\2\u00e4\u00e6\3\2\2\2\u00e5\u00ca\3\2\2\2\u00e5\u00d5\3\2"+
		"\2\2\u00e5\u00dd\3\2\2\2\u00e5\u00e2\3\2\2\2\u00e6\30\3\2\2\2\u00e7\u00e8"+
		"\5\33\16\2\u00e8\u00e9\5\35\17\2\u00e9\32\3\2\2\2\u00ea\u00eb\t\4\2\2"+
		"\u00eb\34\3\2\2\2\u00ec\u00ee\5\37\20\2\u00ed\u00ec\3\2\2\2\u00ed\u00ee"+
		"\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\5\13\6\2\u00f0\36\3\2\2\2\u00f1"+
		"\u00f2\t\5\2\2\u00f2 \3\2\2\2\u00f3\u00f4\t\6\2\2\u00f4\"\3\2\2\2\u00f5"+
		"\u00f6\7v\2\2\u00f6\u00f7\7t\2\2\u00f7\u00f8\7w\2\2\u00f8\u00ff\7g\2\2"+
		"\u00f9\u00fa\7h\2\2\u00fa\u00fb\7c\2\2\u00fb\u00fc\7n\2\2\u00fc\u00fd"+
		"\7u\2\2\u00fd\u00ff\7g\2\2\u00fe\u00f5\3\2\2\2\u00fe\u00f9\3\2\2\2\u00ff"+
		"$\3\2\2\2\u0100\u0101\7)\2\2\u0101\u0102\5\'\24\2\u0102\u0103\7)\2\2\u0103"+
		"&\3\2\2\2\u0104\u0105\n\7\2\2\u0105(\3\2\2\2\u0106\u0108\7$\2\2\u0107"+
		"\u0109\5+\26\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010a\3\2"+
		"\2\2\u010a\u010b\7$\2\2\u010b*\3\2\2\2\u010c\u010e\5-\27\2\u010d\u010c"+
		"\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110"+
		",\3\2\2\2\u0111\u0112\n\b\2\2\u0112.\3\2\2\2\u0113\u0114\7*\2\2\u0114"+
		"\60\3\2\2\2\u0115\u0116\7+\2\2\u0116\62\3\2\2\2\u0117\u0118\7}\2\2\u0118"+
		"\64\3\2\2\2\u0119\u011a\7\177\2\2\u011a\66\3\2\2\2\u011b\u011c\7]\2\2"+
		"\u011c8\3\2\2\2\u011d\u011e\7_\2\2\u011e:\3\2\2\2\u011f\u0120\7.\2\2\u0120"+
		"<\3\2\2\2\u0121\u0122\7\60\2\2\u0122>\3\2\2\2\u0123\u0124\7?\2\2\u0124"+
		"@\3\2\2\2\u0125\u0126\7@\2\2\u0126B\3\2\2\2\u0127\u0128\7>\2\2\u0128D"+
		"\3\2\2\2\u0129\u012a\7#\2\2\u012aF\3\2\2\2\u012b\u012c\7\u0080\2\2\u012c"+
		"H\3\2\2\2\u012d\u012e\7A\2\2\u012eJ\3\2\2\2\u012f\u0130\7<\2\2\u0130L"+
		"\3\2\2\2\u0131\u0132\7?\2\2\u0132\u0133\7?\2\2\u0133N\3\2\2\2\u0134\u0135"+
		"\7>\2\2\u0135\u0136\7?\2\2\u0136P\3\2\2\2\u0137\u0138\7@\2\2\u0138\u0139"+
		"\7?\2\2\u0139R\3\2\2\2\u013a\u013b\7#\2\2\u013b\u013c\7?\2\2\u013cT\3"+
		"\2\2\2\u013d\u013e\7(\2\2\u013e\u013f\7(\2\2\u013fV\3\2\2\2\u0140\u0141"+
		"\7~\2\2\u0141\u0142\7~\2\2\u0142X\3\2\2\2\u0143\u0144\7-\2\2\u0144\u0145"+
		"\7-\2\2\u0145Z\3\2\2\2\u0146\u0147\7/\2\2\u0147\u0148\7/\2\2\u0148\\\3"+
		"\2\2\2\u0149\u014a\7-\2\2\u014a^\3\2\2\2\u014b\u014c\7/\2\2\u014c`\3\2"+
		"\2\2\u014d\u014e\7\61\2\2\u014eb\3\2\2\2\u014f\u0150\7(\2\2\u0150d\3\2"+
		"\2\2\u0151\u0152\7`\2\2\u0152f\3\2\2\2\u0153\u0154\7\'\2\2\u0154h\3\2"+
		"\2\2\u0155\u0156\7-\2\2\u0156\u0157\7?\2\2\u0157j\3\2\2\2\u0158\u0159"+
		"\7/\2\2\u0159\u015a\7?\2\2\u015al\3\2\2\2\u015b\u015c\7,\2\2\u015c\u015d"+
		"\7?\2\2\u015dn\3\2\2\2\u015e\u015f\7\61\2\2\u015f\u0160\7?\2\2\u0160p"+
		"\3\2\2\2\u0161\u0162\7(\2\2\u0162\u0163\7?\2\2\u0163r\3\2\2\2\u0164\u0165"+
		"\7~\2\2\u0165\u0166\7?\2\2\u0166t\3\2\2\2\u0167\u0168\7`\2\2\u0168\u0169"+
		"\7?\2\2\u0169v\3\2\2\2\u016a\u016b\7\'\2\2\u016b\u016c\7?\2\2\u016cx\3"+
		"\2\2\2\u016d\u016e\7>\2\2\u016e\u016f\7>\2\2\u016f\u0170\7?\2\2\u0170"+
		"z\3\2\2\2\u0171\u0172\7@\2\2\u0172\u0173\7@\2\2\u0173\u0174\7?\2\2\u0174"+
		"|\3\2\2\2\u0175\u0176\7@\2\2\u0176\u0177\7@\2\2\u0177\u0178\7@\2\2\u0178"+
		"\u0179\7?\2\2\u0179~\3\2\2\2\u017a\u017e\5\u0081A\2\u017b\u017d\5\u0083"+
		"B\2\u017c\u017b\3\2\2\2\u017d\u0180\3\2\2\2\u017e\u017c\3\2\2\2\u017e"+
		"\u017f\3\2\2\2\u017f\u0080\3\2\2\2\u0180\u017e\3\2\2\2\u0181\u0188\t\t"+
		"\2\2\u0182\u0183\n\n\2\2\u0183\u0188\6A\2\2\u0184\u0185\t\13\2\2\u0185"+
		"\u0186\t\f\2\2\u0186\u0188\6A\3\2\u0187\u0181\3\2\2\2\u0187\u0182\3\2"+
		"\2\2\u0187\u0184\3\2\2\2\u0188\u0082\3\2\2\2\u0189\u0190\t\r\2\2\u018a"+
		"\u018b\n\n\2\2\u018b\u0190\6B\4\2\u018c\u018d\t\13\2\2\u018d\u018e\t\f"+
		"\2\2\u018e\u0190\6B\5\2\u018f\u0189\3\2\2\2\u018f\u018a\3\2\2\2\u018f"+
		"\u018c\3\2\2\2\u0190\u0084\3\2\2\2\u0191\u0192\7B\2\2\u0192\u0086\3\2"+
		"\2\2\u0193\u0194\7\60\2\2\u0194\u0195\7\60\2\2\u0195\u0196\7\60\2\2\u0196"+
		"\u0088\3\2\2\2\u0197\u0199\t\16\2\2\u0198\u0197\3\2\2\2\u0199\u019a\3"+
		"\2\2\2\u019a\u0198\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u019c\3\2\2\2\u019c"+
		"\u019d\bE\2\2\u019d\u008a\3\2\2\2\u019e\u019f\7\61\2\2\u019f\u01a0\7,"+
		"\2\2\u01a0\u01a4\3\2\2\2\u01a1\u01a3\13\2\2\2\u01a2\u01a1\3\2\2\2\u01a3"+
		"\u01a6\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a5\u01a7\3\2"+
		"\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8\7,\2\2\u01a8\u01a9\7\61\2\2\u01a9"+
		"\u01aa\3\2\2\2\u01aa\u01ab\bF\2\2\u01ab\u008c\3\2\2\2\u01ac\u01ad\7\61"+
		"\2\2\u01ad\u01ae\7\61\2\2\u01ae\u01b2\3\2\2\2\u01af\u01b1\n\17\2\2\u01b0"+
		"\u01af\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2"+
		"\2\2\u01b3\u01b5\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01b6\bG\2\2\u01b6"+
		"\u008e\3\2\2\2\u01b7\u01b9\t\20\2\2\u01b8\u01b7\3\2\2\2\u01b9\u01ba\3"+
		"\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u0090\3\2\2\2\u01bc"+
		"\u01bd\7u\2\2\u01bd\u01be\7v\2\2\u01be\u01bf\7q\2\2\u01bf\u01c0\7r\2\2"+
		"\u01c0\u0092\3\2\2\2\u01c1\u01c2\7~\2\2\u01c2\u0094\3\2\2\2\u01c3\u01c4"+
		"\7v\2\2\u01c4\u01c5\7j\2\2\u01c5\u01c6\7k\2\2\u01c6\u01c7\7u\2\2\u01c7"+
		"\u0096\3\2\2\2\u01c8\u01c9\7/\2\2\u01c9\u01ca\7@\2\2\u01ca\u0098\3\2\2"+
		"\2\u01cb\u01cc\7=\2\2\u01cc\u009a\3\2\2\2\36\2\u009f\u00a6\u00ab\u00ad"+
		"\u00b3\u00b7\u00bb\u00c1\u00c6\u00cd\u00d0\u00d3\u00d8\u00db\u00e0\u00e5"+
		"\u00ed\u00fe\u0108\u010f\u017e\u0187\u018f\u019a\u01a4\u01b2\u01ba\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}