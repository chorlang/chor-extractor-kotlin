package ast.cc;

/**
 * Created by fmontesi on 03/04/17.
 */
public abstract class CCNode
{
    public abstract <T> T accept( CCVisitor<T> visitor );
}
