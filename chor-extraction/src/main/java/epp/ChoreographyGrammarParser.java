package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Set;

public class ChoreographyGrammarParser {
    private ANTLRInputStream stream;
    private ChoreographyLexer lexer;
    private ChoreographyParser parser;
    private ParseTree tree;

    public ChoreographyGrammarParser(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new ChoreographyLexer(stream);
            parser = new ChoreographyParser(new CommonTokenStream(lexer));
            tree = parser.choreography();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ParseTree getTree() {
        return tree;
    }

    public static void main(String[] args){
        ChoreographyGrammarParser grammarParser = new ChoreographyGrammarParser("p.expr->q;stop");
        ParseTree tree = grammarParser.getTree();
        if (tree!=null) {
            PreprocessingVisitor preprocessingVisitor = new PreprocessingVisitor();
            preprocessingVisitor.visit(tree);
            Set<String> processes = preprocessingVisitor.getProcesses();
            HashMap procedures = preprocessingVisitor.getProcedures();

            ChoreographyVisitor choreographyVisitor = new ChoreographyVisitor(processes, procedures);
            choreographyVisitor.visit(tree);

            HashMap<String, String> chorProcesses = choreographyVisitor.getProcesses();
            System.out.println("Return:");
            for (Object value : chorProcesses.values())
                System.out.println(value);


        }

    }
}
