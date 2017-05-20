package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.Interaction;

public class Receiving implements Interaction {
    private final Behaviour continuation;
    private final String process;

    public Receiving(Behaviour continuation, String process) {
        this.continuation = continuation;
        this.process = process;
    }

    public Behaviour getContinuation() {
        return continuation;
    }

    public String getProcess() {
        return process;
    }

    public String toString() {
        return process + "?; " + continuation.toString();
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return continuation.findRecProcCall(procname);
    }
}
