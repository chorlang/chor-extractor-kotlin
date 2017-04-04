package ast.cc.nodes;

import ast.cc.interfaces.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureDefinition implements CCNode {
    private final String procedure;
    private final CCNode choreography;
    private final CCNode inChoreography;

    public ProcedureDefinition(String procedure, CCNode choreography, CCNode inChoreography) {
        this.procedure = procedure;
        this.choreography = choreography;
        this.inChoreography = inChoreography;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
