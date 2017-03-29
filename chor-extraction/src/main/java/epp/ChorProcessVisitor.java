package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;

/**
 * Created by lara on 28/03/17.
 */
public class ChorProcessVisitor extends ChoreographyBaseVisitor {

    HashMap<String,String> proc;

    public ChorProcessVisitor(){
        proc = new HashMap();
    }

    @Override
    public Object visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public Object visitChildren(RuleNode node) {
        return super.visitChildren(node);
    }

    @Override
    public Object visitTerminal(TerminalNode node) {
        return super.visitTerminal(node);
    }

    @Override
    public Object visitErrorNode(ErrorNode node) {
        return super.visitErrorNode(node);
    }

    @Override
    public Object visitChoreography(ChoreographyParser.ChoreographyContext ctx) {
        return super.visitChoreography(ctx);
    }

    @Override
    public Object visitCondition(ChoreographyParser.ConditionContext ctx) {
        return super.visitCondition(ctx);
    }

    @Override
    public Object visitProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx) {
        return super.visitProcedureDefinition(ctx);
    }

    @Override
    public Object visitProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx) {
        return super.visitProcedureInvocation(ctx);
    }

    @Override
    public Object visitCommunication(ChoreographyParser.CommunicationContext ctx) {
        return super.visitCommunication(ctx);
    }

    @Override
    public Object visitSend(ChoreographyParser.SendContext ctx) {
        return super.visitSend(ctx);
    }

    @Override
    public Object visitChoose(ChoreographyParser.ChooseContext ctx) {
        return super.visitChoose(ctx);
    }

    @Override
    public Object visitSendingProcess(ChoreographyParser.SendingProcessContext ctx) {
        return super.visitSendingProcess(ctx);
    }

    @Override
    public Object visitReceivingProcess(ChoreographyParser.ReceivingProcessContext ctx) {
        return super.visitReceivingProcess(ctx);
    }

    @Override
    public Object visitExpr(ChoreographyParser.ExprContext ctx) {
        return super.visitExpr(ctx);
    }

    @Override
    public Object visitValue(ChoreographyParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }

    @Override
    public Object visitProcedure(ChoreographyParser.ProcedureContext ctx) {
        return super.visitProcedure(ctx);
    }

    @Override
    public Object visitProcess(ChoreographyParser.ProcessContext ctx) {
        System.out.println("Process");
        String process = ctx.getText();
        proc.put(process, process + " has ");
        return super.visitProcess(ctx);
    }

    @Override
    public Object visitLabel(ChoreographyParser.LabelContext ctx) {
        return super.visitLabel(ctx);
    }

    public HashMap<String,String> returnChoreography(){
        return (HashMap<String, String>) proc;
    }
}
