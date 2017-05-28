package ast.sp.nodes.expr;

import com.google.common.base.Strings;

public class NegatedBooleanExpression extends BooleanExpression{
    private BooleanExpression expr;

    public NegatedBooleanExpression(BooleanExpression expr) {
        this.expr = expr;
    }

    public NegatedBooleanExpression(String name, BooleanExpression expr) {
        super(name);
        this.expr = expr;
    }

    public BooleanExpression getExpr() {
        return expr;
    }

    public void setExpr(BooleanExpression expr) {
        this.expr = expr;
    }

    public String toString(){
        String e = "~" + expr.toString();
        String name = super.getName();
        if (Strings.isNullOrEmpty(name)){
            return e;
        } else {
            return name + "=" + e;
        }

    }
}
