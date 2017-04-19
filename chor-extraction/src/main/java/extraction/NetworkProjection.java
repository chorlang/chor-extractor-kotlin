package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import ast.sp.interfaces.SPNode;
import epp.MergingException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;

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
            tree = parser.prog();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tree;
    }

    public HashMap<String, SPNode> projectToNodes(String network) throws MergingException {
        ParseTree tree = this.getTree(network);
        HashMap<String, SPNode> projectionNodes = new HashMap<>();
        if (tree!=null) {
        }
        return projectionNodes;
    }
}