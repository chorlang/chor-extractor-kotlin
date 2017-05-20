package ast.cc.nodes;

import ast.cc.CCVisitor;
import ast.cc.interfaces.Choreography;
import epp.MergingException;

import java.util.HashSet;

public class ProcedureInvocation implements Choreography{
    private final String procedure;
    private final HashSet<String> processes;

    public ProcedureInvocation(String procedure, HashSet<String> processes) {
        this.procedure = procedure;
        this.processes = processes;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }

    public String getProcedure() {
        return procedure;
    }

    public HashSet<String> getProcesses() {
        return processes;
    }

}
