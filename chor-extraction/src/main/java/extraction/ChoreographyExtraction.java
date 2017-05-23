package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import ast.cc.interfaces.CCNode;
import ast.sp.interfaces.SPNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class ChoreographyExtraction {

    private ANTLRInputStream stream;
    private NetworkLexer lexer;
    private NetworkParser parser;
    private ParseTree tree;
    private NetworkVisitor networkVisitor;

    public ChoreographyExtraction(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new NetworkLexer(stream);
            parser = new NetworkParser(new CommonTokenStream(lexer));
            tree = parser.network();
            networkVisitor = new NetworkVisitor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CCNode extract() throws Exception {
        NetworkExtraction networkExtraction = new NetworkExtraction(networkVisitor.visit(tree));
        return networkExtraction.graphToChoreograpy();
    }

    public SPNode getNetwork(){
        return networkVisitor.visit(tree);
    }
}
