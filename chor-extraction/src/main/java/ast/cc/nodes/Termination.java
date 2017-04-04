package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;

/**
 * Created by lara on 04/04/17.
 */
public class Termination implements CCNode {
    private final String termination = "stop";

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getTermination() {
        return termination;
    }
}
