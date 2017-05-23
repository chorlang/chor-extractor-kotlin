package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.Choreography;
import epp.MergingException;

public class BadTermination implements Choreography {
    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }

    public String toString(){
        return "break";
    }
}
