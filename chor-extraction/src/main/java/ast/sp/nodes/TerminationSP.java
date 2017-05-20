package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

public class TerminationSP implements Behaviour {
    private final String termination = "stop";

    @Override
    public String toString() {
        return "stop";
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return false;
    }
}