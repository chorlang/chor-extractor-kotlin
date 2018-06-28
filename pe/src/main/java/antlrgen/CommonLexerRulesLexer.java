// Generated from CommonLexerRules.g4 by ANTLR 4.7.1
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
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Identifier=1, BooleanLiteral=2, StringLiteral=3, Wildcard=4, WS=5, INT=6;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"Identifier", "BooleanLiteral", "StringLiteral", "Wildcard", "WS", "INT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Identifier", "BooleanLiteral", "StringLiteral", "Wildcard", "WS", 
		"INT"
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
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\b9\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\6\2\21\n\2\r\2\16\2\22\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\36\n\3\3\4\3\4\7\4\"\n\4\f\4\16\4"+
		"%\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\6\6/\n\6\r\6\16\6\60\3\6\3\6\3"+
		"\7\6\7\66\n\7\r\7\16\7\67\2\2\b\3\3\5\4\7\5\t\6\13\7\r\b\3\2\6\5\2\62"+
		";C\\c|\5\2\f\f\17\17$$\5\2\13\f\17\17\"\"\3\2\62;\2=\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\3\20\3\2\2\2\5"+
		"\35\3\2\2\2\7\37\3\2\2\2\t(\3\2\2\2\13.\3\2\2\2\r\65\3\2\2\2\17\21\t\2"+
		"\2\2\20\17\3\2\2\2\21\22\3\2\2\2\22\20\3\2\2\2\22\23\3\2\2\2\23\4\3\2"+
		"\2\2\24\25\7v\2\2\25\26\7t\2\2\26\27\7w\2\2\27\36\7g\2\2\30\31\7h\2\2"+
		"\31\32\7c\2\2\32\33\7n\2\2\33\34\7u\2\2\34\36\7g\2\2\35\24\3\2\2\2\35"+
		"\30\3\2\2\2\36\6\3\2\2\2\37#\7$\2\2 \"\n\3\2\2! \3\2\2\2\"%\3\2\2\2#!"+
		"\3\2\2\2#$\3\2\2\2$&\3\2\2\2%#\3\2\2\2&\'\7$\2\2\'\b\3\2\2\2()\7v\2\2"+
		")*\7j\2\2*+\7k\2\2+,\7u\2\2,\n\3\2\2\2-/\t\4\2\2.-\3\2\2\2/\60\3\2\2\2"+
		"\60.\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\62\63\b\6\2\2\63\f\3\2\2\2\64"+
		"\66\t\5\2\2\65\64\3\2\2\2\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\16"+
		"\3\2\2\2\b\2\22\35#\60\67\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}