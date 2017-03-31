package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Set;

public class ChorGrammarParser {
    private ANTLRInputStream stream;
    private ChoreographyLexer lexer;
    private ChoreographyParser parser;
    private ParseTree tree;

    public ChorGrammarParser(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new ChoreographyLexer(stream);
            parser = new ChoreographyParser(new CommonTokenStream(lexer));
            tree = parser.choreography();
            //System.out.println("text: " + tree.getText());
            //System.out.println("parser: " + tree.toStringTree(parser));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ParseTree getParseTree(){
        return tree;
    }

    public static void main(String[] args){
        ChorGrammarParser grammarParser = new ChorGrammarParser("r.e->u;def A = p.e->q in A");
        ParseTree tree = grammarParser.getParseTree();
        if (tree!=null) {
            PreProcessingVisitor preProcessingVisitor = new PreProcessingVisitor();
            preProcessingVisitor.visit(tree);
            Set<String> processes = preProcessingVisitor.getProcesses();
            HashMap procedures = preProcessingVisitor.getProcedures();

            ChorVisitor chorVisitor = new ChorVisitor(processes, procedures);
            chorVisitor.visit(tree);

            HashMap<String, String> chorProcesses = chorVisitor.getProc();
            System.out.println("Return:");
            for (Object value : chorProcesses.values())
                System.out.println(value);


        }

    }
}
