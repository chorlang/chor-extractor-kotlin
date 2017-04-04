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

    public Selection(String sender, String receiver, String label)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.label = label;
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

}
