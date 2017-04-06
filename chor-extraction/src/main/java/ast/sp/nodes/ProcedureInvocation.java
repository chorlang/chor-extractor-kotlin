package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

/**
 * Created by lara on 04/04/17.
 */
public class ProcedureInvocation implements Behaviour {
    public final String procedure;

    public ProcedureInvocation(String procedure) {
        this.procedure = procedure;
    }

    public String getProcedure() {
        return procedure;
    }
}
