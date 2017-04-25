package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.Choreography;
import epp.MergingException;

/**
 * Created by lara on 04/04/17.
 */
public class Termination implements Choreography {
    private final String termination = "stop";

    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }

    public String getTermination() {
        return termination;
    }
}
