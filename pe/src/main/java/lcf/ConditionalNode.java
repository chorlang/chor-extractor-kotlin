package lcf;

/*
 * Conditional actions, with two possible continuations.
 */
public class ConditionalNode implements ChoreographyNode {

    private String decider, condition;
    // preAction: common prefix to both branches (see toString method)
    private ChoreographyNode preAction, thenAction, elseAction;

    public ConditionalNode(String decider, String condition, ChoreographyNode thenAction, ChoreographyNode elseAction) {
        this(decider, condition, new TerminationNode(), thenAction, elseAction);
    }

    public ConditionalNode(String decider, String condition, ChoreographyNode preAction, ChoreographyNode thenAction, ChoreographyNode elseAction) {
        this.decider = decider;
        this.condition = condition;
        this.preAction = preAction;
        this.thenAction = thenAction;
        this.elseAction = elseAction;
    }

    /*
     * Getters (and no setters).
     */
    public String getDecider() {
        return decider;
    }

    public String getCondition() {
        return condition;
    }

    public ChoreographyNode getPreAction() {
        return preAction;
    }

    public ChoreographyNode getThenAction() {
        return thenAction;
    }

    public ChoreographyNode getElseAction() {
        return elseAction;
    }

    /*
     * For implementing the visitor pattern.
     */
    public void accept(CNVisitor v) {
        v.visit(this);
    }

    public String toString() {
        ChoreographyNode realThen = FatSemi.run(preAction,thenAction),
            realElse = FatSemi.run(preAction,elseAction);
        return "if " + decider + "." + condition + " then " + realThen.toString() + " else " + realElse.toString();
    }

}
