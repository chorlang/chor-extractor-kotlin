package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.interfaces.Choreography;
import ast.sp.nodes.expr.BooleanExpression;
import epp.MergingException;

/**
 * Created by lara on 03/04/17.
 */
public class Condition implements Choreography{
    private final String process;
    private final BooleanExpression expression;
    private final CCNode thenChoreography;
    private final CCNode elseChoreograpy;

    public Condition(String process, BooleanExpression expression, CCNode thenChoreography, CCNode elseChoreograpy) {
        this.process = process;
        this.expression = expression;
        this.thenChoreography = thenChoreography;
        this.elseChoreograpy = elseChoreograpy;
    }

    public String getProcess() {
        return process;
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public CCNode getThenChoreography() {
        return thenChoreography;
    }

    public CCNode getElseChoreograpy() {
        return elseChoreograpy;
    }


    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }

    public String toString(){
        return "if " + process + "." + expression + " then " + thenChoreography.toString() + " else " + elseChoreograpy.toString();
    }
}
