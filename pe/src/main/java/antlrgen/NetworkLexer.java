// Generated from Network.g4 by ANTLR 4.7.1

    package antlrgen;

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
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		TERMINATE=18, Identifier=19, BooleanLiteral=20, StringLiteral=21, Wildcard=22, 
		WS=23, INT=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"TERMINATE", "Identifier", "BooleanLiteral", "StringLiteral", "Wildcard", 
		"WS", "INT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'||'", "'|'", "'{'", "'def'", "'main'", "'}'", "'!<'", "'>;'", 
		"'?;'", "'+'", "';'", "'&{'", "','", "':'", "'if'", "'then'", "'else'", 
		"'stop'", null, null, null, "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "TERMINATE", "Identifier", "BooleanLiteral", 
		"StringLiteral", "Wildcard", "WS", "INT"
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
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\u0095\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r"+
		"\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\6\24m\n\24\r\24\16"+
		"\24n\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25z\n\25\3\26\3\26"+
		"\7\26~\n\26\f\26\16\26\u0081\13\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27"+
		"\3\30\6\30\u008b\n\30\r\30\16\30\u008c\3\30\3\30\3\31\6\31\u0092\n\31"+
		"\r\31\16\31\u0093\2\2\32\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\3"+
		"\2\6\5\2\62;C\\c|\5\2\f\f\17\17$$\5\2\13\f\17\17\"\"\3\2\62;\2\u0099\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\3\63\3\2\2\2\5\66\3\2\2\2\78\3\2\2\2\t:\3\2\2\2\13>\3\2"+
		"\2\2\rC\3\2\2\2\17E\3\2\2\2\21H\3\2\2\2\23K\3\2\2\2\25N\3\2\2\2\27P\3"+
		"\2\2\2\31R\3\2\2\2\33U\3\2\2\2\35W\3\2\2\2\37Y\3\2\2\2!\\\3\2\2\2#a\3"+
		"\2\2\2%f\3\2\2\2\'l\3\2\2\2)y\3\2\2\2+{\3\2\2\2-\u0084\3\2\2\2/\u008a"+
		"\3\2\2\2\61\u0091\3\2\2\2\63\64\7~\2\2\64\65\7~\2\2\65\4\3\2\2\2\66\67"+
		"\7~\2\2\67\6\3\2\2\289\7}\2\29\b\3\2\2\2:;\7f\2\2;<\7g\2\2<=\7h\2\2=\n"+
		"\3\2\2\2>?\7o\2\2?@\7c\2\2@A\7k\2\2AB\7p\2\2B\f\3\2\2\2CD\7\177\2\2D\16"+
		"\3\2\2\2EF\7#\2\2FG\7>\2\2G\20\3\2\2\2HI\7@\2\2IJ\7=\2\2J\22\3\2\2\2K"+
		"L\7A\2\2LM\7=\2\2M\24\3\2\2\2NO\7-\2\2O\26\3\2\2\2PQ\7=\2\2Q\30\3\2\2"+
		"\2RS\7(\2\2ST\7}\2\2T\32\3\2\2\2UV\7.\2\2V\34\3\2\2\2WX\7<\2\2X\36\3\2"+
		"\2\2YZ\7k\2\2Z[\7h\2\2[ \3\2\2\2\\]\7v\2\2]^\7j\2\2^_\7g\2\2_`\7p\2\2"+
		"`\"\3\2\2\2ab\7g\2\2bc\7n\2\2cd\7u\2\2de\7g\2\2e$\3\2\2\2fg\7u\2\2gh\7"+
		"v\2\2hi\7q\2\2ij\7r\2\2j&\3\2\2\2km\t\2\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2"+
		"\2\2no\3\2\2\2o(\3\2\2\2pq\7v\2\2qr\7t\2\2rs\7w\2\2sz\7g\2\2tu\7h\2\2"+
		"uv\7c\2\2vw\7n\2\2wx\7u\2\2xz\7g\2\2yp\3\2\2\2yt\3\2\2\2z*\3\2\2\2{\177"+
		"\7$\2\2|~\n\3\2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2"+
		"\2\2\u0080\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7$\2\2\u0083,\3"+
		"\2\2\2\u0084\u0085\7v\2\2\u0085\u0086\7j\2\2\u0086\u0087\7k\2\2\u0087"+
		"\u0088\7u\2\2\u0088.\3\2\2\2\u0089\u008b\t\4\2\2\u008a\u0089\3\2\2\2\u008b"+
		"\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\3\2"+
		"\2\2\u008e\u008f\b\30\2\2\u008f\60\3\2\2\2\u0090\u0092\t\5\2\2\u0091\u0090"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"\62\3\2\2\2\b\2ny\177\u008c\u0093\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}