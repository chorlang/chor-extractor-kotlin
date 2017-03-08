// Generated from Network.g4 by ANTLR 4.5
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"Network", "R", "Behaviour", "IntegerLiteral", "DecimalIntegerLiteral", 
		"IntegerTypeSuffix", "DecimalNumeral", "Digits", "Digit", "NonZeroDigit", 
		"DigitOrUnderscore", "Underscores", "FloatingPointLiteral", "DecimalFloatingPointLiteral", 
		"ExponentPart", "ExponentIndicator", "SignedInteger", "Sign", "FloatTypeSuffix", 
		"BooleanLiteral", "CharacterLiteral", "SingleCharacter", "StringLiteral", 
		"StringCharacters", "StringCharacter", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"LBRACK", "RBRACK", "COMMA", "DOT", "ASSIGN", "GT", "LT", "TILDE", "EQUAL", 
		"LE", "GE", "NOTEQUAL", "AND", "OR", "INC", "DEC", "SUB", "DIV", "CARET", 
		"MOD", "ADD_ASSIGN", "SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", 
		"OR_ASSIGN", "XOR_ASSIGN", "MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", 
		"URSHIFT_ASSIGN", "Identifier", "JavaLetter", "JavaLetterOrDigit", "AT", 
		"ELLIPSIS", "WS", "COMMENT", "LINE_COMMENT", "IF", "THEN", "ELSE", "DEF", 
		"IN", "Expression", "Process", "Procedure", "Label", "Value", "INT", "Terminate", 
		"Parallel", "Wildcard", "Arrow", "Send", "Receive", "Select", "Choose", 
		"Has", "Continue"
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
		case 61:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 62:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2H\u0252\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5"+
		"\2\u00be\n\2\3\3\3\3\3\3\3\3\6\3\u00c4\n\3\r\3\16\3\u00c5\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\6\4\u00e2\n\4\r\4\16\4\u00e3\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00fa"+
		"\n\4\3\5\3\5\3\6\3\6\5\6\u0100\n\6\3\7\3\7\3\b\3\b\3\b\5\b\u0107\n\b\3"+
		"\b\3\b\3\b\5\b\u010c\n\b\5\b\u010e\n\b\3\t\3\t\7\t\u0112\n\t\f\t\16\t"+
		"\u0115\13\t\3\t\5\t\u0118\n\t\3\n\3\n\5\n\u011c\n\n\3\13\3\13\3\f\3\f"+
		"\5\f\u0122\n\f\3\r\6\r\u0125\n\r\r\r\16\r\u0126\3\16\3\16\3\17\3\17\3"+
		"\17\5\17\u012e\n\17\3\17\5\17\u0131\n\17\3\17\5\17\u0134\n\17\3\17\3\17"+
		"\3\17\5\17\u0139\n\17\3\17\5\17\u013c\n\17\3\17\3\17\3\17\5\17\u0141\n"+
		"\17\3\17\3\17\3\17\5\17\u0146\n\17\3\20\3\20\3\20\3\21\3\21\3\22\5\22"+
		"\u014e\n\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u015f\n\25\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\5\30\u0169\n\30\3\30\3\30\3\31\6\31\u016e\n\31\r\31\16\31\u016f\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3"+
		"\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*"+
		"\3+\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3"+
		"\62\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3"+
		"\67\3\67\38\38\38\39\39\39\3:\3:\3:\3;\3;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3"+
		"=\3=\3>\3>\7>\u01d3\n>\f>\16>\u01d6\13>\3?\3?\3?\3?\3?\3?\5?\u01de\n?"+
		"\3@\3@\3@\3@\3@\3@\5@\u01e6\n@\3A\3A\3B\3B\3B\3B\3C\6C\u01ef\nC\rC\16"+
		"C\u01f0\3C\3C\3D\3D\3D\3D\7D\u01f9\nD\fD\16D\u01fc\13D\3D\3D\3D\3D\3D"+
		"\3E\3E\3E\3E\7E\u0207\nE\fE\16E\u020a\13E\3E\3E\3F\3F\3F\3G\3G\3G\3G\3"+
		"G\3H\3H\3H\3H\3H\3I\3I\3I\3I\3J\3J\3J\3K\3K\3K\5K\u0225\nK\3L\3L\3M\3"+
		"M\3N\3N\3O\3O\3O\3O\5O\u0231\nO\3P\6P\u0234\nP\rP\16P\u0235\3Q\3Q\3Q\3"+
		"Q\3Q\3R\3R\3S\3S\3S\3S\3S\3T\3T\3T\3U\3U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3"+
		"Z\3\u01fa\2[\3\3\5\4\7\5\t\6\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33"+
		"\7\35\2\37\2!\2#\2%\2\'\2)\b+\t-\2/\n\61\2\63\2\65\13\67\f9\r;\16=\17"+
		"?\20A\21C\22E\23G\24I\25K\26M\27O\30Q\31S\32U\33W\34Y\35[\36]\37_ a!c"+
		"\"e#g$i%k&m\'o(q)s*u+w,y-{.}\2\177\2\u0081/\u0083\60\u0085\61\u0087\62"+
		"\u0089\63\u008b\64\u008d\65\u008f\66\u0091\67\u00938\u00959\u0097:\u0099"+
		";\u009b<\u009d=\u009f>\u00a1?\u00a3@\u00a5A\u00a7B\u00a9C\u00abD\u00ad"+
		"E\u00afF\u00b1G\u00b3H\3\2\21\4\2NNnn\3\2\63;\4\2GGgg\4\2--//\6\2FFHH"+
		"ffhh\4\2))^^\4\2$$^^\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01"+
		"\3\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\3\2"+
		"\62;\u026c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\33\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2/\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2"+
		"m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3"+
		"\2\2\2\2{\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087"+
		"\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2"+
		"\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099"+
		"\3\2\2\2\2\u009b\3\2\2\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2"+
		"\2\2\u00a3\3\2\2\2\2\u00a5\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2\2\2\u00ab"+
		"\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\2\u00b3\3\2\2"+
		"\2\3\u00bd\3\2\2\2\5\u00c3\3\2\2\2\7\u00f9\3\2\2\2\t\u00fb\3\2\2\2\13"+
		"\u00fd\3\2\2\2\r\u0101\3\2\2\2\17\u010d\3\2\2\2\21\u010f\3\2\2\2\23\u011b"+
		"\3\2\2\2\25\u011d\3\2\2\2\27\u0121\3\2\2\2\31\u0124\3\2\2\2\33\u0128\3"+
		"\2\2\2\35\u0145\3\2\2\2\37\u0147\3\2\2\2!\u014a\3\2\2\2#\u014d\3\2\2\2"+
		"%\u0151\3\2\2\2\'\u0153\3\2\2\2)\u015e\3\2\2\2+\u0160\3\2\2\2-\u0164\3"+
		"\2\2\2/\u0166\3\2\2\2\61\u016d\3\2\2\2\63\u0171\3\2\2\2\65\u0173\3\2\2"+
		"\2\67\u0175\3\2\2\29\u0177\3\2\2\2;\u0179\3\2\2\2=\u017b\3\2\2\2?\u017d"+
		"\3\2\2\2A\u017f\3\2\2\2C\u0181\3\2\2\2E\u0183\3\2\2\2G\u0185\3\2\2\2I"+
		"\u0187\3\2\2\2K\u0189\3\2\2\2M\u018b\3\2\2\2O\u018e\3\2\2\2Q\u0191\3\2"+
		"\2\2S\u0194\3\2\2\2U\u0197\3\2\2\2W\u019a\3\2\2\2Y\u019d\3\2\2\2[\u01a0"+
		"\3\2\2\2]\u01a3\3\2\2\2_\u01a5\3\2\2\2a\u01a7\3\2\2\2c\u01a9\3\2\2\2e"+
		"\u01ab\3\2\2\2g\u01ae\3\2\2\2i\u01b1\3\2\2\2k\u01b4\3\2\2\2m\u01b7\3\2"+
		"\2\2o\u01ba\3\2\2\2q\u01bd\3\2\2\2s\u01c0\3\2\2\2u\u01c3\3\2\2\2w\u01c7"+
		"\3\2\2\2y\u01cb\3\2\2\2{\u01d0\3\2\2\2}\u01dd\3\2\2\2\177\u01e5\3\2\2"+
		"\2\u0081\u01e7\3\2\2\2\u0083\u01e9\3\2\2\2\u0085\u01ee\3\2\2\2\u0087\u01f4"+
		"\3\2\2\2\u0089\u0202\3\2\2\2\u008b\u020d\3\2\2\2\u008d\u0210\3\2\2\2\u008f"+
		"\u0215\3\2\2\2\u0091\u021a\3\2\2\2\u0093\u021e\3\2\2\2\u0095\u0224\3\2"+
		"\2\2\u0097\u0226\3\2\2\2\u0099\u0228\3\2\2\2\u009b\u022a\3\2\2\2\u009d"+
		"\u0230\3\2\2\2\u009f\u0233\3\2\2\2\u00a1\u0237\3\2\2\2\u00a3\u023c\3\2"+
		"\2\2\u00a5\u023e\3\2\2\2\u00a7\u0243\3\2\2\2\u00a9\u0246\3\2\2\2\u00ab"+
		"\u0248\3\2\2\2\u00ad\u024a\3\2\2\2\u00af\u024c\3\2\2\2\u00b1\u024e\3\2"+
		"\2\2\u00b3\u0250\3\2\2\2\u00b5\u00b6\5\u0097L\2\u00b6\u00b7\5\u00b1Y\2"+
		"\u00b7\u00b8\5\7\4\2\u00b8\u00b9\5\5\3\2\u00b9\u00be\3\2\2\2\u00ba\u00bb"+
		"\5\u00a1Q\2\u00bb\u00bc\5\5\3\2\u00bc\u00be\3\2\2\2\u00bd\u00b5\3\2\2"+
		"\2\u00bd\u00ba\3\2\2\2\u00be\4\3\2\2\2\u00bf\u00c0\5\3\2\2\u00c0\u00c1"+
		"\5\u00a3R\2\u00c1\u00c2\5\3\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00bf\3\2\2"+
		"\2\u00c4\u00c5\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\6"+
		"\3\2\2\2\u00c7\u00c8\5\u0097L\2\u00c8\u00c9\5\u00a9U\2\u00c9\u00ca\5I"+
		"%\2\u00ca\u00cb\5\u0095K\2\u00cb\u00cc\5G$\2\u00cc\u00cd\5\u00b3Z\2\u00cd"+
		"\u00ce\5\7\4\2\u00ce\u00fa\3\2\2\2\u00cf\u00d0\5\u0097L\2\u00d0\u00d1"+
		"\5\u00abV\2\u00d1\u00d2\5\u00b3Z\2\u00d2\u00d3\5\7\4\2\u00d3\u00fa\3\2"+
		"\2\2\u00d4\u00d5\5\u0097L\2\u00d5\u00d6\5\u00adW\2\u00d6\u00d7\5\u009b"+
		"N\2\u00d7\u00d8\5\u00b3Z\2\u00d8\u00d9\5\7\4\2\u00d9\u00fa\3\2\2\2\u00da"+
		"\u00db\5\u0097L\2\u00db\u00dc\5\u00afX\2\u00dc\u00e1\59\35\2\u00dd\u00de"+
		"\5\u009bN\2\u00de\u00df\5\u00b1Y\2\u00df\u00e0\5\7\4\2\u00e0\u00e2\3\2"+
		"\2\2\u00e1\u00dd\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3"+
		"\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\5;\36\2\u00e6\u00e7\5\u00b3"+
		"Z\2\u00e7\u00e8\5\7\4\2\u00e8\u00fa\3\2\2\2\u00e9\u00ea\5\u008bF\2\u00ea"+
		"\u00eb\5\u0095K\2\u00eb\u00ec\5\u008dG\2\u00ec\u00ed\5\7\4\2\u00ed\u00ee"+
		"\5\u008fH\2\u00ee\u00ef\5\7\4\2\u00ef\u00fa\3\2\2\2\u00f0\u00f1\5\u0091"+
		"I\2\u00f1\u00f2\5\u0099M\2\u00f2\u00f3\5E#\2\u00f3\u00f4\5\7\4\2\u00f4"+
		"\u00f5\5\u0093J\2\u00f5\u00f6\5\7\4\2\u00f6\u00fa\3\2\2\2\u00f7\u00fa"+
		"\5\u0099M\2\u00f8\u00fa\5\u00a1Q\2\u00f9\u00c7\3\2\2\2\u00f9\u00cf\3\2"+
		"\2\2\u00f9\u00d4\3\2\2\2\u00f9\u00da\3\2\2\2\u00f9\u00e9\3\2\2\2\u00f9"+
		"\u00f0\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00f8\3\2\2\2\u00fa\b\3\2\2\2"+
		"\u00fb\u00fc\5\13\6\2\u00fc\n\3\2\2\2\u00fd\u00ff\5\17\b\2\u00fe\u0100"+
		"\5\r\7\2\u00ff\u00fe\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\f\3\2\2\2\u0101"+
		"\u0102\t\2\2\2\u0102\16\3\2\2\2\u0103\u010e\7\62\2\2\u0104\u010b\5\25"+
		"\13\2\u0105\u0107\5\21\t\2\u0106\u0105\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"\u010c\3\2\2\2\u0108\u0109\5\31\r\2\u0109\u010a\5\21\t\2\u010a\u010c\3"+
		"\2\2\2\u010b\u0106\3\2\2\2\u010b\u0108\3\2\2\2\u010c\u010e\3\2\2\2\u010d"+
		"\u0103\3\2\2\2\u010d\u0104\3\2\2\2\u010e\20\3\2\2\2\u010f\u0117\5\23\n"+
		"\2\u0110\u0112\5\27\f\2\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2\u0113"+
		"\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0116\3\2\2\2\u0115\u0113\3\2"+
		"\2\2\u0116\u0118\5\23\n\2\u0117\u0113\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\22\3\2\2\2\u0119\u011c\7\62\2\2\u011a\u011c\5\25\13\2\u011b\u0119\3\2"+
		"\2\2\u011b\u011a\3\2\2\2\u011c\24\3\2\2\2\u011d\u011e\t\3\2\2\u011e\26"+
		"\3\2\2\2\u011f\u0122\5\23\n\2\u0120\u0122\7a\2\2\u0121\u011f\3\2\2\2\u0121"+
		"\u0120\3\2\2\2\u0122\30\3\2\2\2\u0123\u0125\7a\2\2\u0124\u0123\3\2\2\2"+
		"\u0125\u0126\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\32"+
		"\3\2\2\2\u0128\u0129\5\35\17\2\u0129\34\3\2\2\2\u012a\u012b\5\21\t\2\u012b"+
		"\u012d\7\60\2\2\u012c\u012e\5\21\t\2\u012d\u012c\3\2\2\2\u012d\u012e\3"+
		"\2\2\2\u012e\u0130\3\2\2\2\u012f\u0131\5\37\20\2\u0130\u012f\3\2\2\2\u0130"+
		"\u0131\3\2\2\2\u0131\u0133\3\2\2\2\u0132\u0134\5\'\24\2\u0133\u0132\3"+
		"\2\2\2\u0133\u0134\3\2\2\2\u0134\u0146\3\2\2\2\u0135\u0136\7\60\2\2\u0136"+
		"\u0138\5\21\t\2\u0137\u0139\5\37\20\2\u0138\u0137\3\2\2\2\u0138\u0139"+
		"\3\2\2\2\u0139\u013b\3\2\2\2\u013a\u013c\5\'\24\2\u013b\u013a\3\2\2\2"+
		"\u013b\u013c\3\2\2\2\u013c\u0146\3\2\2\2\u013d\u013e\5\21\t\2\u013e\u0140"+
		"\5\37\20\2\u013f\u0141\5\'\24\2\u0140\u013f\3\2\2\2\u0140\u0141\3\2\2"+
		"\2\u0141\u0146\3\2\2\2\u0142\u0143\5\21\t\2\u0143\u0144\5\'\24\2\u0144"+
		"\u0146\3\2\2\2\u0145\u012a\3\2\2\2\u0145\u0135\3\2\2\2\u0145\u013d\3\2"+
		"\2\2\u0145\u0142\3\2\2\2\u0146\36\3\2\2\2\u0147\u0148\5!\21\2\u0148\u0149"+
		"\5#\22\2\u0149 \3\2\2\2\u014a\u014b\t\4\2\2\u014b\"\3\2\2\2\u014c\u014e"+
		"\5%\23\2\u014d\u014c\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u014f\3\2\2\2\u014f"+
		"\u0150\5\21\t\2\u0150$\3\2\2\2\u0151\u0152\t\5\2\2\u0152&\3\2\2\2\u0153"+
		"\u0154\t\6\2\2\u0154(\3\2\2\2\u0155\u0156\7v\2\2\u0156\u0157\7t\2\2\u0157"+
		"\u0158\7w\2\2\u0158\u015f\7g\2\2\u0159\u015a\7h\2\2\u015a\u015b\7c\2\2"+
		"\u015b\u015c\7n\2\2\u015c\u015d\7u\2\2\u015d\u015f\7g\2\2\u015e\u0155"+
		"\3\2\2\2\u015e\u0159\3\2\2\2\u015f*\3\2\2\2\u0160\u0161\7)\2\2\u0161\u0162"+
		"\5-\27\2\u0162\u0163\7)\2\2\u0163,\3\2\2\2\u0164\u0165\n\7\2\2\u0165."+
		"\3\2\2\2\u0166\u0168\7$\2\2\u0167\u0169\5\61\31\2\u0168\u0167\3\2\2\2"+
		"\u0168\u0169\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u016b\7$\2\2\u016b\60\3"+
		"\2\2\2\u016c\u016e\5\63\32\2\u016d\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f"+
		"\u016d\3\2\2\2\u016f\u0170\3\2\2\2\u0170\62\3\2\2\2\u0171\u0172\n\b\2"+
		"\2\u0172\64\3\2\2\2\u0173\u0174\7*\2\2\u0174\66\3\2\2\2\u0175\u0176\7"+
		"+\2\2\u01768\3\2\2\2\u0177\u0178\7}\2\2\u0178:\3\2\2\2\u0179\u017a\7\177"+
		"\2\2\u017a<\3\2\2\2\u017b\u017c\7]\2\2\u017c>\3\2\2\2\u017d\u017e\7_\2"+
		"\2\u017e@\3\2\2\2\u017f\u0180\7.\2\2\u0180B\3\2\2\2\u0181\u0182\7\60\2"+
		"\2\u0182D\3\2\2\2\u0183\u0184\7?\2\2\u0184F\3\2\2\2\u0185\u0186\7@\2\2"+
		"\u0186H\3\2\2\2\u0187\u0188\7>\2\2\u0188J\3\2\2\2\u0189\u018a\7\u0080"+
		"\2\2\u018aL\3\2\2\2\u018b\u018c\7?\2\2\u018c\u018d\7?\2\2\u018dN\3\2\2"+
		"\2\u018e\u018f\7>\2\2\u018f\u0190\7?\2\2\u0190P\3\2\2\2\u0191\u0192\7"+
		"@\2\2\u0192\u0193\7?\2\2\u0193R\3\2\2\2\u0194\u0195\7#\2\2\u0195\u0196"+
		"\7?\2\2\u0196T\3\2\2\2\u0197\u0198\7(\2\2\u0198\u0199\7(\2\2\u0199V\3"+
		"\2\2\2\u019a\u019b\7~\2\2\u019b\u019c\7~\2\2\u019cX\3\2\2\2\u019d\u019e"+
		"\7-\2\2\u019e\u019f\7-\2\2\u019fZ\3\2\2\2\u01a0\u01a1\7/\2\2\u01a1\u01a2"+
		"\7/\2\2\u01a2\\\3\2\2\2\u01a3\u01a4\7/\2\2\u01a4^\3\2\2\2\u01a5\u01a6"+
		"\7\61\2\2\u01a6`\3\2\2\2\u01a7\u01a8\7`\2\2\u01a8b\3\2\2\2\u01a9\u01aa"+
		"\7\'\2\2\u01aad\3\2\2\2\u01ab\u01ac\7-\2\2\u01ac\u01ad\7?\2\2\u01adf\3"+
		"\2\2\2\u01ae\u01af\7/\2\2\u01af\u01b0\7?\2\2\u01b0h\3\2\2\2\u01b1\u01b2"+
		"\7,\2\2\u01b2\u01b3\7?\2\2\u01b3j\3\2\2\2\u01b4\u01b5\7\61\2\2\u01b5\u01b6"+
		"\7?\2\2\u01b6l\3\2\2\2\u01b7\u01b8\7(\2\2\u01b8\u01b9\7?\2\2\u01b9n\3"+
		"\2\2\2\u01ba\u01bb\7~\2\2\u01bb\u01bc\7?\2\2\u01bcp\3\2\2\2\u01bd\u01be"+
		"\7`\2\2\u01be\u01bf\7?\2\2\u01bfr\3\2\2\2\u01c0\u01c1\7\'\2\2\u01c1\u01c2"+
		"\7?\2\2\u01c2t\3\2\2\2\u01c3\u01c4\7>\2\2\u01c4\u01c5\7>\2\2\u01c5\u01c6"+
		"\7?\2\2\u01c6v\3\2\2\2\u01c7\u01c8\7@\2\2\u01c8\u01c9\7@\2\2\u01c9\u01ca"+
		"\7?\2\2\u01cax\3\2\2\2\u01cb\u01cc\7@\2\2\u01cc\u01cd\7@\2\2\u01cd\u01ce"+
		"\7@\2\2\u01ce\u01cf\7?\2\2\u01cfz\3\2\2\2\u01d0\u01d4\5}?\2\u01d1\u01d3"+
		"\5\177@\2\u01d2\u01d1\3\2\2\2\u01d3\u01d6\3\2\2\2\u01d4\u01d2\3\2\2\2"+
		"\u01d4\u01d5\3\2\2\2\u01d5|\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d7\u01de\t"+
		"\t\2\2\u01d8\u01d9\n\n\2\2\u01d9\u01de\6?\2\2\u01da\u01db\t\13\2\2\u01db"+
		"\u01dc\t\f\2\2\u01dc\u01de\6?\3\2\u01dd\u01d7\3\2\2\2\u01dd\u01d8\3\2"+
		"\2\2\u01dd\u01da\3\2\2\2\u01de~\3\2\2\2\u01df\u01e6\t\r\2\2\u01e0\u01e1"+
		"\n\n\2\2\u01e1\u01e6\6@\4\2\u01e2\u01e3\t\13\2\2\u01e3\u01e4\t\f\2\2\u01e4"+
		"\u01e6\6@\5\2\u01e5\u01df\3\2\2\2\u01e5\u01e0\3\2\2\2\u01e5\u01e2\3\2"+
		"\2\2\u01e6\u0080\3\2\2\2\u01e7\u01e8\7B\2\2\u01e8\u0082\3\2\2\2\u01e9"+
		"\u01ea\7\60\2\2\u01ea\u01eb\7\60\2\2\u01eb\u01ec\7\60\2\2\u01ec\u0084"+
		"\3\2\2\2\u01ed\u01ef\t\16\2\2\u01ee\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2"+
		"\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f3"+
		"\bC\2\2\u01f3\u0086\3\2\2\2\u01f4\u01f5\7\61\2\2\u01f5\u01f6\7,\2\2\u01f6"+
		"\u01fa\3\2\2\2\u01f7\u01f9\13\2\2\2\u01f8\u01f7\3\2\2\2\u01f9\u01fc\3"+
		"\2\2\2\u01fa\u01fb\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fb\u01fd\3\2\2\2\u01fc"+
		"\u01fa\3\2\2\2\u01fd\u01fe\7,\2\2\u01fe\u01ff\7\61\2\2\u01ff\u0200\3\2"+
		"\2\2\u0200\u0201\bD\2\2\u0201\u0088\3\2\2\2\u0202\u0203\7\61\2\2\u0203"+
		"\u0204\7\61\2\2\u0204\u0208\3\2\2\2\u0205\u0207\n\17\2\2\u0206\u0205\3"+
		"\2\2\2\u0207\u020a\3\2\2\2\u0208\u0206\3\2\2\2\u0208\u0209\3\2\2\2\u0209"+
		"\u020b\3\2\2\2\u020a\u0208\3\2\2\2\u020b\u020c\bE\2\2\u020c\u008a\3\2"+
		"\2\2\u020d\u020e\7k\2\2\u020e\u020f\7h\2\2\u020f\u008c\3\2\2\2\u0210\u0211"+
		"\7v\2\2\u0211\u0212\7j\2\2\u0212\u0213\7g\2\2\u0213\u0214\7p\2\2\u0214"+
		"\u008e\3\2\2\2\u0215\u0216\7g\2\2\u0216\u0217\7n\2\2\u0217\u0218\7u\2"+
		"\2\u0218\u0219\7g\2\2\u0219\u0090\3\2\2\2\u021a\u021b\7f\2\2\u021b\u021c"+
		"\7g\2\2\u021c\u021d\7h\2\2\u021d\u0092\3\2\2\2\u021e\u021f\7k\2\2\u021f"+
		"\u0220\7p\2\2\u0220\u0094\3\2\2\2\u0221\u0225\5{>\2\u0222\u0225\5\u009d"+
		"O\2\u0223\u0225\5\u00a5S\2\u0224\u0221\3\2\2\2\u0224\u0222\3\2\2\2\u0224"+
		"\u0223\3\2\2\2\u0225\u0096\3\2\2\2\u0226\u0227\5{>\2\u0227\u0098\3\2\2"+
		"\2\u0228\u0229\5{>\2\u0229\u009a\3\2\2\2\u022a\u022b\5/\30\2\u022b\u009c"+
		"\3\2\2\2\u022c\u0231\5\u009fP\2\u022d\u0231\5+\26\2\u022e\u0231\5/\30"+
		"\2\u022f\u0231\5)\25\2\u0230\u022c\3\2\2\2\u0230\u022d\3\2\2\2\u0230\u022e"+
		"\3\2\2\2\u0230\u022f\3\2\2\2\u0231\u009e\3\2\2\2\u0232\u0234\t\20\2\2"+
		"\u0233\u0232\3\2\2\2\u0234\u0235\3\2\2\2\u0235\u0233\3\2\2\2\u0235\u0236"+
		"\3\2\2\2\u0236\u00a0\3\2\2\2\u0237\u0238\7u\2\2\u0238\u0239\7v\2\2\u0239"+
		"\u023a\7q\2\2\u023a\u023b\7r\2\2\u023b\u00a2\3\2\2\2\u023c\u023d\7~\2"+
		"\2\u023d\u00a4\3\2\2\2\u023e\u023f\7v\2\2\u023f\u0240\7j\2\2\u0240\u0241"+
		"\7k\2\2\u0241\u0242\7u\2\2\u0242\u00a6\3\2\2\2\u0243\u0244\7/\2\2\u0244"+
		"\u0245\7@\2\2\u0245\u00a8\3\2\2\2\u0246\u0247\7#\2\2\u0247\u00aa\3\2\2"+
		"\2\u0248\u0249\7A\2\2\u0249\u00ac\3\2\2\2\u024a\u024b\7-\2\2\u024b\u00ae"+
		"\3\2\2\2\u024c\u024d\7(\2\2\u024d\u00b0\3\2\2\2\u024e\u024f\7<\2\2\u024f"+
		"\u00b2\3\2\2\2\u0250\u0251\7=\2\2\u0251\u00b4\3\2\2\2$\2\u00bd\u00c5\u00e3"+
		"\u00f9\u00ff\u0106\u010b\u010d\u0113\u0117\u011b\u0121\u0126\u012d\u0130"+
		"\u0133\u0138\u013b\u0140\u0145\u014d\u015e\u0168\u016f\u01d4\u01dd\u01e5"+
		"\u01f0\u01fa\u0208\u0224\u0230\u0235\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}