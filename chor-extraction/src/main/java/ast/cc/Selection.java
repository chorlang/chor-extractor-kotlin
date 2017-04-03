package ast.cc;

/**
 * Created by fmontesi on 03/04/17.
 */
public class Selection extends CCNode
{
    private final String sender;
    private final String receiver;
    private final String label;
    private final CCNode continuation;

    public Selection( String sender, String receiver, String label, CCNode continuation )
    {
        this.sender = sender;
        this.receiver = receiver;
        this.label = label;
    }

    public String sender()
    {
        return sender;
    }

    public String receiver()
    {
        return receiver;
    }

    public String label()
    {
        return label;
    }

    public <T> T accept( CCVisitor<T> visitor )
    {
        return visitor.visit( this );
    }
}
