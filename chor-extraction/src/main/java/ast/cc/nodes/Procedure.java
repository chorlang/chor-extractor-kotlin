package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 04/04/17.
 */
public class Procedure extends CCNode {
    private final String procedure;
    private final CCNode choreography;
    private final CCNode inChoreography;

    public Procedure(String procedure, CCNode choreography, CCNode inChoreography) {
        this.procedure = procedure;
        this.choreography = choreography;
        this.inChoreography = inChoreography;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
