package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import epp.MergingException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class NetworkProjection {

    private ANTLRInputStream stream;
    private NetworkLexer lexer;
    private NetworkParser parser;
    private ParseTree tree;


    private ParseTree getTree(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new NetworkLexer(stream);
            parser = new NetworkParser(new CommonTokenStream(lexer));
            tree = parser.network();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tree;
    }

    public ParseTree project(String network) throws MergingException {
        return tree = this.getTree(network);
    }

}