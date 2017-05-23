package ast.cc;

import ast.cc.nodes.*;
import epp.MergingException;

/**
 * Created by fmontesi on 03/04/17.
 */
public interface CCVisitor<T> {
    public T visit(Selection n) throws MergingException;

    public T visit(Communication n) throws MergingException;

    public T visit(Condition n) throws MergingException;

    public T visit(Termination n) throws MergingException;

    public T visit(ProcedureDefinition n) throws MergingException;

    public T visit(ProcedureInvocation n) throws MergingException;

    public T visit(Program n) throws MergingException;

    public T visit(BadTermination n);
}
