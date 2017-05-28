package ast.sp.nodes.expr;

import com.google.common.base.Strings;

public class AtomBooleanExpression extends BooleanExpression {
    private Boolean expression;

    public AtomBooleanExpression(String name, Boolean expression) {
        super(name);
        this.expression = expression;
    }

    public AtomBooleanExpression(String name) {
        super(name);
    }

    public AtomBooleanExpression(Boolean expression) {
        this.expression = expression;
    }

    public Boolean getExpression() {
        return expression;
    }

    public void setExpression(Boolean expression) {
        this.expression = expression;
    }

    public String toString(){
        String expr = expression.toString();
        String name = super.getName();
        if (Strings.isNullOrEmpty(name)){
            return expr;
        } else {
            return name + "=" + expr;
        }

    }
}
