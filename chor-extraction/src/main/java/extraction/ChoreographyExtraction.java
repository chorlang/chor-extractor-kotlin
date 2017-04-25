package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import ast.cc.interfaces.CCNode;
import ast.sp.interfaces.SPNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jgrapht.DirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ChoreographyExtraction {

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

    public ParseTree parse(String network)  {
        return tree = this.getTree(network);
    }

    public DirectedGraph<HashMap<String,SPNode>, String> extract() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        NetworkVisitor networkVisitor = new NetworkVisitor();
        SPNode sp = networkVisitor.visit(tree);
        NetworkExtraction np = new NetworkExtraction(sp);
        return np.getGraph();
    }

    public static CCNode graphToChoreography( DirectedGraph<HashMap<String,SPNode>, String> graph )
    {
        return null;
    }
}