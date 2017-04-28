package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

public class ProcedureDefinitionSP implements Behaviour {
    private final String procedure;
    private final Behaviour behaviour;

    public ProcedureDefinitionSP(String procedure, Behaviour behaviour) {
        this.procedure = procedure;
        this.behaviour = behaviour;
    }

    public String getProcedure() {
        return procedure;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public String toString() {
        return "def " + procedure + " =" + behaviour.toString();
    }
}
