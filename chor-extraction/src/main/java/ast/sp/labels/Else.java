package ast.sp.labels;

import ast.sp.interfaces.ExtractionLabel;

public class Else implements ExtractionLabel {
    private final String process;
    private final String expression;

    public Else(String process, String expression) {
        this.process = process;
        this.expression = expression;
    }


    public String getProcess() {
        return process;
    }

    public String getExpression() {
        return expression;
    }

    public String toString(){
        return "if" + process + "." + expression + " else ";
    }
}
