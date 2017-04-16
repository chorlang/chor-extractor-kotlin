package epp;

import antlr4.ChoreographyLexer;
import antlr4.ChoreographyParser;
import ast.cc.interfaces.CCNode;
import ast.sp.interfaces.SPNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EndPointProjection {
    private ANTLRInputStream stream;
    private ChoreographyLexer lexer;
    private ChoreographyParser parser;
    private ParseTree tree;


    private ParseTree getTree(String grammar) {
        try {
            stream = new ANTLRInputStream(grammar);
            lexer = new ChoreographyLexer(stream);
            parser = new ChoreographyParser(new CommonTokenStream(lexer));
            tree = parser.choreography();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return tree;
    }

    public HashMap<String, SPNode> projectToNodes(String choreography) throws MergingException {
        ParseTree tree = this.getTree(choreography);
        HashMap<String, SPNode> projectionNodes = new HashMap<>();
        if (tree!=null) {
            ChoreographyVisitor choreographyVisitor = new ChoreographyVisitor();
            CCNode ccast = choreographyVisitor.getCCAST(tree);
            HashSet<String> processes = choreographyVisitor.getProcesses();

            BehaviourProjection behaviourProjection = new BehaviourProjection();

            for (String process: processes) {
                SPNode ssast = behaviourProjection.getSPAST(ccast, process);
                projectionNodes.put(process, ssast);
            }
        }
        return projectionNodes;
    }

    public String projectToString(String choreography) throws MergingException {
        ParseTree tree = this.getTree(choreography);
        HashMap<String, SPNode> projectionNodes = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        if (tree!=null) {
            ChoreographyVisitor choreographyVisitor = new ChoreographyVisitor();
            CCNode ccast = choreographyVisitor.getCCAST(tree);
            HashSet<String> processes = choreographyVisitor.getProcesses();

            BehaviourProjection behaviourProjection = new BehaviourProjection();

            for (String process: processes) {
                SPNode ssast = behaviourProjection.getSPAST(ccast, process);
                projectionNodes.put(process, ssast);
            }
        }

        for (Map.Entry<String, SPNode> entry : projectionNodes.entrySet()) {
            builder.append(entry.getKey()).append(" is ").append(entry.getValue().toString()).append(" | ");
        }

        if (builder.length()>=3){
            builder.delete(builder.length()-3, builder.length());
        }

        return builder.toString();
    }
}
