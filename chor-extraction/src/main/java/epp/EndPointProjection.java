package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import ast.cc.interfaces.CCNode;
import ast.sp.nodes.Network;
import ast.sp.nodes.ProcessBehaviour;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashSet;

public class EndPointProjection {
    private ANTLRInputStream stream;
    private ChoreographyLexer lexer;
    private ChoreographyParser parser;
    private ParseTree tree;


    private ParseTree parse(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new ChoreographyLexer(stream);
            parser = new ChoreographyParser(new CommonTokenStream(lexer));
            tree = parser.program();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tree;
    }

    public Network project(String choreography) throws MergingException {
        ParseTree tree = this.parse(choreography);
        ArrayList<ProcessBehaviour> network = new ArrayList<>();

        if (tree!=null) {
            ChoreographyVisitor choreographyVisitor = new ChoreographyVisitor();
            CCNode ccast = choreographyVisitor.getCCAST(tree);
            HashSet<String> processes = choreographyVisitor.getProcesses();

            BehaviourProjection behaviourProjection = new BehaviourProjection();
            for (String process: processes) {
                network.add ((ProcessBehaviour) behaviourProjection.getSPAST(ccast, process));
            }
        }
        return new Network (network);
    }
}
