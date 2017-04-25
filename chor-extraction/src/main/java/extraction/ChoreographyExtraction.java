package extraction;

import antlr4.NetworkLexer;
import antlr4.NetworkParser;
import ast.sp.interfaces.SPNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.lang.reflect.InvocationTargetException;

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

    public void extract() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        NetworkVisitor networkVisitor = new NetworkVisitor();
        SPNode sp = networkVisitor.visit(tree);
        NetworkExtraction np = new NetworkExtraction(sp);
        np.graphToChoreograpy();
    }

    /*public static ProcedureDefinition graphToChoreography(DirectedGraph<HashMap<String,SPNode>, String> graph )
    {
        return null;
    }*/
}