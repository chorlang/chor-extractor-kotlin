package ast.cc.nodes;

import ast.cc.interfaces.CCNode;
import ast.cc.CCVisitor;

/**
 * Created by lara on 03/04/17.
 */
public class Condition implements CCNode{
    private final String process;
    private final String expression;
    private final CCNode thenChoreography;
    private final CCNode elseChoreograpy;

    public Condition(String process, String expression, CCNode thenChoreography, CCNode elseChoreograpy) {
        this.process = process;
        this.expression = expression;
        this.thenChoreography = thenChoreography;
        this.elseChoreograpy = elseChoreograpy;
    }

    public String getProcess() {
        return process;
    }

    public String getExpression() {
        return expression;
    }

    public CCNode getThenChoreography() {
        return thenChoreography;
    }

    public CCNode getElseChoreograpy() {
        return elseChoreograpy;
    }


    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
