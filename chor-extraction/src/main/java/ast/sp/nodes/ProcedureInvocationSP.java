package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

public class ProcedureInvocationSP implements Behaviour {
    private final String procedure;

    private ProcedureDefinitionSP procedureDefinition;


    public ProcedureInvocationSP(String procedure) {
        this.procedure = procedure;
    }

    public String getProcedure() {
        return procedure;
    }

    public String toString() {
        return procedure;
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
}
