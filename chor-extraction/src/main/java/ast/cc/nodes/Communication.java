package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.interfaces.Interaction;
import ast.sp.nodes.expr.BooleanExpression;
import epp.MergingException;

/**
 * Created by lara on 03/04/17.
 */
public class Communication implements Interaction
{
    private final String sender;
    private final String receiver;
    private final BooleanExpression expression;
    private final CCNode continuation;

    public CCNode getContinuation() {
        return continuation;
    }

    public Communication(String sender, String receiver, BooleanExpression expression, CCNode continuation) {
        this.sender = sender;
        this.receiver = receiver;
        this.expression = expression;
        this.continuation = continuation;
    }

    public <T> T accept( CCVisitor<T> visitor ) throws MergingException {
        return visitor.visit( this );
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public String toString(){
        return sender + "." + expression + "->" + receiver + "; " + continuation.toString();
    }
}
