package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class NetworkGrammarParser {

    ANTLRInputStream stream;
    NetworkLexer lexer;
    NetworkParser parser;

    public NetworkGrammarParser(String grammar) {
        stream = new ANTLRInputStream(grammar);
        lexer = new NetworkLexer(stream);
        parser = new NetworkParser(new CommonTokenStream(lexer));
    }

    public ParseTree createParseTree(){
        try {
            ParseTree tree = parser.prog();
            System.out.println("text: " + tree.getText());
            System.out.println("parser: " + tree.toStringTree(parser));
            return tree;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args){
        NetworkGrammarParser grammarParser = new NetworkGrammarParser("stop | p has a!<qwe>; stop");
        ParseTree tree = grammarParser.createParseTree();
        if (tree!=null){
            NetworkVisitor v =  NetworkVisitor.getVisitor();
            v.visit(tree);
        }


    }
}