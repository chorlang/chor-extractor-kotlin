package ast.sp.labels;

import ast.sp.interfaces.ExtractionLabel;
import ast.sp.nodes.expr.BooleanExpression;

public class Else implements ExtractionLabel {
    private final String process;
    private final BooleanExpression expression;

    public Else(String process, BooleanExpression expression) {
        this.process = process;
        this.expression = expression;
    }


    public String getProcess() {
        return process;
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public String toString(){
        return "if" + process + "." + expression + " else ";
    }
}
