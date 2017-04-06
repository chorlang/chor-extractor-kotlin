package ast.cc.nodes;

import ast.cc.interfaces.CCNode;
import ast.cc.CCVisitor;
import epp.MergingException;

import java.util.HashSet;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureDefinition implements CCNode {
    private final String procedure;
    private final CCNode choreography;
    private final CCNode inChoreography;

    private final HashSet<String> processes;

    public ProcedureDefinition(String procedure, CCNode choreography, CCNode inChoreography, HashSet<String> processes) {
        this.procedure = procedure;
        this.choreography = choreography;
        this.inChoreography = inChoreography;
        this.processes = processes;
    }

    public String getProcedure() {
        return procedure;
    }

    public CCNode getChoreography() {
        return choreography;
    }

    public CCNode getInChoreography() {
        return inChoreography;
    }

    public HashSet<String> getProcesses() {
        return processes;
    }

    @Override
    public <T> T accept(CCVisitor<T> visitor) throws MergingException {
        return visitor.visit(this);
    }
}
