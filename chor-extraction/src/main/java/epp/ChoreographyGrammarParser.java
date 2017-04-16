package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import ast.cc.interfaces.CCNode;
import ast.sp.interfaces.SPNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashSet;

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

    public static void main(String[] args) throws MergingException {
        ChoreographyGrammarParser grammarParser = new ChoreographyGrammarParser("if p.e then p.e->q;stop else p.e->q;stop");
        ParseTree tree = grammarParser.getTree();
        if (tree!=null) {
            ChoreographyVisitor choreographyVisitor = new ChoreographyVisitor();
            CCNode ccast = choreographyVisitor.getCCAST(tree);
            HashSet<String> processes = choreographyVisitor.getProcesses();

            BehaviourProjection behaviourProjection = new BehaviourProjection();
            HashSet<SPNode> projectionNodes = new HashSet<>();
            for (String process: processes) {
                SPNode ssast = behaviourProjection.getSPAST(ccast, process);
                projectionNodes.add(ssast);
            }
            projectionNodes.toString();
        }
    }
}
