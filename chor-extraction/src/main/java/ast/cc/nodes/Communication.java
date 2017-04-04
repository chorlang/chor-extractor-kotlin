package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 03/04/17.
 */
public class Communication extends CCNode
{
    private final String sender;
    private final String receiver;
    private final String expression;

    public Communication(String sender, String receiver, String expression) {
        this.sender = sender;
        this.receiver = receiver;
        this.expression = expression;
    }

    public <T> T accept( CCVisitor<T> visitor )
    {
        return visitor.visit( this );
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getExpression() {
        return expression;
    }
}
