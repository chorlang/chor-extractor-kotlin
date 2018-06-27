package lcf;

/*
 * Visitor for choreography nodes.
 */
public interface CNVisitor {

    public void visit(TerminationNode n);

    public void visit(CommunicationNode n);

    public void visit(SelectionNode n);

    public void visit(ConditionalNode n);

    public void visit(CallNode n);

}