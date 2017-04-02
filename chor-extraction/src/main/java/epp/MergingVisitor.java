package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class MergingVisitor extends ChoreographyBaseVisitor implements Runnable {

    private final ParseTree tree;
    private final ChoreographyContextComparator comparator;
    private boolean mergeResult;

    public MergingVisitor(ParseTree tree, ChoreographyContextComparator comparator) {
        this.tree = tree;
        this.comparator = comparator;
    }

    @Override
    public Object visitCondition(ConditionContext ctx) {
        try {
            comparator.addToList(ctx);
            mergeResult = isMergeable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mergeResult) {
            mergeResult = false;
            return super.visitCondition(ctx);
        }
        else {
            return false;
        }
    }

    @Override
    public Object visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        try {
            comparator.addToList(ctx);
            mergeResult = isMergeable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mergeResult) {
            mergeResult = false;
            return super.visitProcedureDefinition(ctx);
        }
        else {
            return false;
        }
    }

    @Override
    public Object visitProcedureInvocation(ProcedureInvocationContext ctx) {
        try {
            comparator.addToList(ctx);
            mergeResult = isMergeable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mergeResult) {
            mergeResult = false;
            return super.visitProcedureInvocation(ctx);
        }
        else {
            return false;
        }
    }

    @Override
    public Object visitSend(SendContext ctx) {
        try {
            comparator.addToList(ctx);
            mergeResult = isMergeable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mergeResult) {
            mergeResult = false;
            return super.visitSend(ctx);
        }
        else {
            return false;
        }
    }

    @Override
    public Object visitChoose(ChooseContext ctx) {
        try {
            comparator.addToList(ctx);
            mergeResult = isMergeable();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mergeResult) {
            mergeResult = false;
            return super.visitChoose(ctx);
        }
        else {
            return false;
        }
    }

    @Override
    public void run() {
        visit(tree);
    }

    private boolean isMergeable() throws InterruptedException {
        synchronized (comparator) {
            if (comparator.readyToCompare()) {
                comparator.compare(comparator.getFirst(), comparator.getSecond());
                comparator.wait();
            }
        }
        return false;
    }
}
