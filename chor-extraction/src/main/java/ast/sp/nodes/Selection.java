package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.Interaction;

/**
 * Created by lara on 04/04/17.
 */
public class Selection implements Interaction {
    private final Behaviour continuation;
    private final String process;
    private final String label;

    public Selection(Behaviour continuation, String process, String label) {
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
}
