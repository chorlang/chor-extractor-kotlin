package ast.sp.nodes.expr;

import com.google.common.base.Strings;

public class BinaryBooleanExpression extends BooleanExpression {
    private final BooleanExpression left;
    private final BooleanExpression right;
    private final Operand operand;

    public BinaryBooleanExpression(String name, BooleanExpression left, BooleanExpression right, Operand operand) {
        super(name);
        this.left = left;
        this.right = right;
        this.operand = operand;
    }

    public BinaryBooleanExpression(BooleanExpression left, BooleanExpression right, Operand operand) {
        this.left = left;
        this.right = right;
        this.operand = operand;
    }

    public BooleanExpression getLeft() {
        return left;
    }

    public BooleanExpression getRight() {
        return right;
    }

    public Operand getOperand() {
        return operand;
    }

    public String toString(){
        String expr = "(" + left.toString() + operand.getText() + right.toString() + ")";
        String name = super.getName();
        if (Strings.isNullOrEmpty(name)){
            return expr;
        } else {
            return name + "=" + expr;
        }

    }
}
