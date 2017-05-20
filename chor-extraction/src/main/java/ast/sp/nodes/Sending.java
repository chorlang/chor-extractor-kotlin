package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.Interaction;

/**
 * Created by fmontesi on 03/04/17.
 */
public class Sending implements Interaction {
    private final Behaviour continuation;
    private final String process;
    private final String expression;


    public Sending(Behaviour continuation, String process, String expression) {
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
        return process + "!<" + expression + ">; " + continuation.toString();
    }

    @Override
    public boolean equals(Object ss){
        if (ss instanceof Sending) {
            Sending s = (Sending) ss;
            return this.getProcess().equals(s.getProcess()) &&
                    this.getExpression().equals(s.getExpression()) &&
                    this.getContinuation().equals(s.getContinuation());
        }
        return false;
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return continuation.findRecProcCall(procname);
    }
}
