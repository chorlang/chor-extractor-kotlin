package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.Interaction;

public class SelectionSP implements Interaction {
    private final Behaviour continuation;
    private final String process;
    private final String label;

    public SelectionSP(Behaviour continuation, String process, String label) {
        this.continuation = continuation;
        this.process = process;
        this.label = label;
    }

    public Behaviour getContinuation() {
        return continuation;
    }

    public String getProcess() {
        return process;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return process + " + " + label + "; " + continuation.toString();
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return continuation.findRecProcCall(procname);
    }
}
