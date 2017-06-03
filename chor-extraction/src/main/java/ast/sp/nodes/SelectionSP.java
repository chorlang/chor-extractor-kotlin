package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.Interaction;

public class SelectionSP implements Interaction {
    private final Behaviour continuation;
    private final String process;
    private final String expression;

    public SelectionSP(Behaviour continuation, String process, String expression) {
        this.continuation = continuation;
        this.process = process;
        this.expression = expression;
    }

    public Behaviour getContinuation() {
        return continuation;
    }

    public String getProcess() {
        return process;
    }

    public String getExpression() {
        return expression;
    }

    public String toString() {
        return process + " + " + expression + "; " + continuation.toString();
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return continuation.findRecProcCall(procname);
    }
}
