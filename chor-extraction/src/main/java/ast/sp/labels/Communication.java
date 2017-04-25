package ast.sp.labels;

import ast.sp.interfaces.ExtractionLabel;

public class Communication implements ExtractionLabel {
    private final String sender;
    private final String receiver;
    private final String expression;

    public Communication(String sender, String receiver, String expression) {
        this.sender = sender;
        this.receiver = receiver;
        this.expression = expression;
    }

    public String toString(){
        return sender + "." + expression + "->" + receiver;
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
