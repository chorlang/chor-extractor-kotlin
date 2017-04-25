package ast.cc.nodes;


import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.interfaces.Choreography;
import epp.MergingException;

import java.util.List;

public class Program implements CCNode {
    private final Choreography main;
    private final List<CCNode> procedures;

    public Program(Choreography main, List<CCNode> procedures) {
        this.main = main;
        this.procedures = procedures;
    }

    public Choreography getMain() {
        return main;
    }

    public List<CCNode> getProcedures() {
        return procedures;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }
}
