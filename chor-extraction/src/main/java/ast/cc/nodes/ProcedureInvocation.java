package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureInvocation extends CCNode{
    public final String procedure;

    public ProcedureInvocation(String procedure) {
        this.procedure = procedure;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getProcedure() {
        return procedure;
    }
}
