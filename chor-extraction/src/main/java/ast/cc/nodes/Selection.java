package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.interfaces.Interaction;
import epp.MergingException;

/**
 * Created by fmontesi on 03/04/17.
 */
public class Selection implements Interaction
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

    public <T> T accept( CCVisitor<T> visitor ) throws MergingException {
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

    public String toString(){
        return sender + "->" + receiver + "[" + label + "]; " + continuation.toString();
    }

}
