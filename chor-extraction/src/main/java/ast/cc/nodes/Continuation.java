package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 03/04/17.
 */
public class Continuation {

    private final CCNode continuation;

    public Continuation(CCNode continuation) {
        this.continuation = continuation;
    }

    public CCNode getContinuation() {
        return continuation;
    }

    public <T> T accept( CCVisitor<T> visitor )
    {
        return visitor.visit( this );
    }
}
