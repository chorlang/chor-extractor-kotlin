package epp;

import ast.sp.SPVisitor;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.TerminationSP;

import java.util.HashMap;

/**
 * Created by lara on 26/04/17.
 */
public class ProceduresUnwrapper extends SPVisitor<SPNode> {
    private HashMap<String, SPNode> procedureList;

    public ProceduresUnwrapper(HashMap<String, SPNode> procedureList) {
        this.procedureList = procedureList;
    }

    public SPNode unwrap(){
        return new TerminationSP();

    }
}
