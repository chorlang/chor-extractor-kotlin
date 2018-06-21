/*
 * Conditional actions, with two possible continuations.
 */
public class ConditionalNode implements ChoreographyNode {

    private String decider, condition;
    private ChoreographyNode thenAction, elseAction;

    public ConditionalNode(String decider, String condition, ChoreographyNode thenAction, ChoreographyNode elseAction) {
        this.decider = decider;
        this.condition = condition;
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
        return "if " + decider + "." + condition + " then " + thenAction.toString() + " else " + elseAction.toString();
    }

}
