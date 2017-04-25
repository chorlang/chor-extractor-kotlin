package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;

/**
 * Created by lara on 04/04/17.
 */
public class TerminationSP implements Behaviour, SPNode {
    private final String termination = "stop";

    public String toString() {
        return "stop";
    }
}
