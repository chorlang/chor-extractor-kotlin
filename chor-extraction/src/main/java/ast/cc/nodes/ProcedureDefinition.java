package ast.cc.nodes;

import ast.cc.interfaces.CCNode;
import ast.cc.CCVisitor;
import epp.MergingException;

import java.util.HashSet;

public class ProcedureDefinition implements CCNode {
    private final String procedure;
    private final CCNode choreography;

    private final HashSet<String> processes;

    public ProcedureDefinition(String procedure, CCNode choreography, HashSet<String> processes) {
        this.procedure = procedure;
        this.choreography = choreography;
        this.processes = processes;
    }

    public String getProcedure() {
        return procedure;
    }

    public CCNode getChoreography() {
        return choreography;
    }

    public HashSet<String> getProcesses() {
        return processes;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }
}
