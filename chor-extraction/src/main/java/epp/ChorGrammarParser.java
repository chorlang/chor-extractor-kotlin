package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;

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
        ChorGrammarParser grammarParser = new ChorGrammarParser("p.e->q;q.e->p;r.a->q;p->q[l];stop");
        ParseTree tree = grammarParser.getParseTree();
        if (tree!=null) {
            ChorProcessVisitor pr = new ChorProcessVisitor();
            pr.visit(tree);
            HashMap pro = pr.returnChoreography();
            System.out.println("Processes:");
            for (Object value : pro.values())
              System.out.println(value);

            ChorVisitor v = new ChorVisitor(pro);
            v.visit(tree);
            HashMap cho = v.returnChoreography();
            System.out.println("Return:");
            for (Object value : cho.values())
                System.out.println(value);


        }

    }
}
