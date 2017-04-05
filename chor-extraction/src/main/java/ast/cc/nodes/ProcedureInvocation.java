package ast.cc.nodes;

import ast.cc.interfaces.CCNode;
import ast.cc.CCVisitor;

import java.util.HashSet;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureInvocation implements CCNode{
    private final String procedure;
    private final HashSet<String> processes;

    public ProcedureInvocation(String procedure, HashSet<String> processes) {
        this.procedure = procedure;
        this.processes = processes;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String getProcedure() {
        return procedure;
    }

    public HashSet<String> getProcesses() {
        return processes;
    }
}
