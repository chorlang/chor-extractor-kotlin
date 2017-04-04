package ast.cc.nodes;

import ast.cc.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by fmontesi on 03/04/17.
 */
public class Selection extends CCNode
{
    private final String sender;
    private final String receiver;
    private final String label;
    private final CCNode continuation;

    public Selection(String sender, String receiver, String label, CCNode continuation)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.label = label;
        this.continuation = continuation;
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

    public String getLabel() {
        return label;
    }

    public CCNode getContinuation() {
        return continuation;
    }
}
