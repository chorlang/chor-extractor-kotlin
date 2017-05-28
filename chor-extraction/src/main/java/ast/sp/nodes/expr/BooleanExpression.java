package ast.sp.nodes.expr;

import ast.sp.interfaces.SPNode;

public abstract class BooleanExpression implements SPNode {
    private String name;

    public BooleanExpression() {
    }

    public BooleanExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
