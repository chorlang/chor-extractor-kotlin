package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

public class ProcedureInvocationSP implements Behaviour {
    private final String procedure;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    private  boolean isVisited;

    private ProcedureDefinitionSP procedureDefinition;


    public ProcedureInvocationSP(String procedure) {
        this.procedure = procedure;
        isVisited = false;
    }

    public String getProcedure() {
        return procedure;
    }

    public String toString() {
        return procedure + ifVisited();
    }

    public ProcedureDefinitionSP getProcedureDefinition() {
        return procedureDefinition;
    }

    public void setProcedureDefinition(ProcedureDefinitionSP procedureDefinition) {
        this.procedureDefinition = procedureDefinition;
    }

    @Override
    public boolean findRecProcCall(String procname) {
        return procedure.equals(procname);
    }

    private String ifVisited(){
        if (isVisited) return "*";
        else return "";
    }
}
