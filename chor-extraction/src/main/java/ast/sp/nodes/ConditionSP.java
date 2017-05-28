package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.nodes.expr.BooleanExpression;

public class ConditionSP implements Behaviour{
    private final String process;
    private final BooleanExpression expression;
    private final Behaviour thenBehaviour;
    private final Behaviour elseBehaviour;

    public ConditionSP(String process, BooleanExpression expression, Behaviour thenBehaviour, Behaviour elseBehaviour) {
        this.process = process;
        this.expression = expression;
        this.thenBehaviour = thenBehaviour;
        this.elseBehaviour = elseBehaviour;
    }

    public String getProcess() {
        return process;
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public Behaviour getThenBehaviour() {
        return thenBehaviour;
    }

    public Behaviour getElseBehaviour() {
        return elseBehaviour;
    }

    public String toString() {
        return "if " + process + "." + expression + " then " + thenBehaviour.toString() + " else " + elseBehaviour.toString();
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return thenBehaviour.findRecProcCall(procname) || elseBehaviour.findRecProcCall(procname);
    }
}
