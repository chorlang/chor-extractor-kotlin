package lcf;

import java.util.HashSet;

/*
 * Class for amending a (partial) body from a eta, given the list of relevantprocesses.
 * Uses the visitor pattern.
 */
public class AmendNode implements CNVisitor {

    private HashSet<String> processes;
    private ChoreographyNode amended;

    public AmendNode(HashSet<String> processes) {
        this.processes = processes;
        amended = null;
    }

    /*
     * Only conditionals require that we do something.
     * For the remaining eta types, we simply recur.
     */
    public void visit(TerminationNode n) {
        amended = new TerminationNode();
    }

    public void visit(CommunicationNode n) {
        n.getNextAction().accept(this);
        amended = new CommunicationNode(n.getSender(), n.getReceiver(), n.getMessage(), amended);
    }

    public void visit(SelectionNode n) {
        n.getNextAction().accept(this);
        amended = new SelectionNode(n.getSender(), n.getReceiver(), n.getLabel(), amended);
    }

    public void visit(ConditionalNode n) {
        n.getPreAction().accept(this);
        ChoreographyNode preAction = amended;
        n.getThenAction().accept(this);
        ChoreographyNode thenNode = amended;
        n.getElseAction().accept(this);
        ChoreographyNode elseNode = amended;

        if (!Equals.run(thenNode,elseNode))
            for (String process:processes)
                if (!n.getDecider().equals(process)){
                    thenNode = new SelectionNode(n.getDecider(),process,"L",thenNode);
                    elseNode = new SelectionNode(n.getDecider(),process,"R",elseNode);
                }
        amended = new ConditionalNode(n.getDecider(),n.getCondition(),preAction,thenNode,elseNode);
    }

    public void visit(CallNode n) {
        amended = new CallNode(n.getProcedure());
    }

    /*
     * Method for running the algorithm.
     */
    public static ChoreographyNode run(ChoreographyNode n,HashSet<String> processes) {
        // No problem sharing the list of processesInChoreography, since it will only be read.
        AmendNode runObject = new AmendNode(processes);
        n.accept(runObject);
        return runObject.amended;
    }

}
