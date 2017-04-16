package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureDefinition implements Behaviour {
    private final String procedure;
    private final Behaviour behaviour;
    private final Behaviour inBehaviour;

    public ProcedureDefinition(String procedure, Behaviour behaviour, Behaviour inBehaviour) {
        this.procedure = procedure;
        this.behaviour = behaviour;
        this.inBehaviour = inBehaviour;
    }

    public String getProcedure() {
        return procedure;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public Behaviour getInBehaviour() {
        return inBehaviour;
    }

    public String toString() {
        return "def " + procedure + " =" + behaviour.toString() + " in " + inBehaviour.toString();
    }
}
