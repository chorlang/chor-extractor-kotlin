package ast.sp.labels;

import ast.sp.interfaces.SPNode;

public class Selection implements SPNode{
    private final String sender;
    private final String receiver;
    private final String label;

    public Selection(String sender, String receiver, String label) {
        this.sender = sender;
        this.receiver = receiver;
        this.label = label;
    }

    public String toString(){
        return sender + "->" + receiver + "[" + label + "]";
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
