package ast.cc;

/**
 * Created by fmontesi on 03/04/17.
 */
public interface CCVisitor<T>
{
    public T visit( Selection n );
}
