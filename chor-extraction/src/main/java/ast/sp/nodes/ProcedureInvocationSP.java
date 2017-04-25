package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureInvocationSP implements Behaviour {
    public final String procedure;

    public ProcedureInvocationSP(String procedure) {
        this.procedure = procedure;
    }

    public String getProcedure() {
        return procedure;
    }

    public String toString() {
        return procedure;
    }
}
