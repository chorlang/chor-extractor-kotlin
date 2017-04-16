package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

/**
 * Created by lara on 04/04/17.
 */
public class Condition implements Behaviour{
    private final String process;
    private final String expression;
    private final Behaviour thenBehaviour;
    private final Behaviour elseBehaviour;

    public Condition(String process, String expression, Behaviour thenBehaviour, Behaviour elseBehaviour) {
        this.process = process;
        this.expression = expression;
        this.thenBehaviour = thenBehaviour;
        this.elseBehaviour = elseBehaviour;
    }

    public String getProcess() {
        return process;
    }

    public String getExpression() {
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
}
