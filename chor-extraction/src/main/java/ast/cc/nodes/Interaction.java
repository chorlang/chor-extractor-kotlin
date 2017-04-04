package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 04/04/17.
 */
public class Interaction extends CCNode {

    private final CCNode node;
    private final Continuation continuation;

    public Interaction(CCNode node, Continuation continuation) {
        this.node = node;
        this.continuation = continuation;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
