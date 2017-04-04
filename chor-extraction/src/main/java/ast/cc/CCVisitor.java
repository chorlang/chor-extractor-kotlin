package ast.cc;

import ast.cc.nodes.*;

/**
 * Created by fmontesi on 03/04/17.
 */
public interface CCVisitor<T>
{
    public T visit( Selection n );
    public T visit( Communication n );
    public T visit( Condition n );
    public T visit( Termination n );
    public T visit( ProcedureDefinition n );
    public T visit( ProcedureInvocation n );
}
