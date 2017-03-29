package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class ChorVisitor extends ChoreographyBaseVisitor< Void > {

    Map<String, String> proc = new HashMap<>();

    public ChorVisitor (HashMap proc){
        this.proc = proc;
    }

    @Override
    public Void visitChoreography(ChoreographyParser.ChoreographyContext ctx) {
        visitChildren(ctx);
        System.out.println("Choreography");
        return null;
    }

    @Override
    public Void visitCommunication(ChoreographyParser.CommunicationContext ctx) {
        System.out.println("Communication");
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitExpr(ChoreographyParser.ExprContext ctx) {
        System.out.println("Expression");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitValue(ChoreographyParser.ValueContext ctx) {
        System.out.println("Value");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitProcess(ChoreographyParser.ProcessContext ctx) {
        System.out.println("Process");
        //String process = ctx.getText();
        //proc.put(process, process);
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitProcedure(ChoreographyParser.ProcedureContext ctx) {
        System.out.println("Procedure");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitLabel(ChoreographyParser.LabelContext ctx) {
        System.out.println("Label");
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitTerminal( TerminalNode node ) {
        if (node.getText().equals("stop")){
            for(Map.Entry<String,String> entry: proc.entrySet()) {
                proc.put(entry.getKey(), entry.getValue() + "stop");
            }
        }
        System.out.println( this.toString() + " | Visiting terminal " + node.getText() );
        return null;
    }

    @Override
    public Void visitCondition(ChoreographyParser.ConditionContext ctx) {
        System.out.println("Condition");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitProcedureDefinition(ChoreographyParser.ProcedureDefinitionContext ctx) {
        System.out.println("ProcedureDefinition");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitProcedureInvocation(ChoreographyParser.ProcedureInvocationContext ctx) {
        System.out.println("ProcedureInvocation");
        visitChildren(ctx);
        return null;
    }

    @Override public Void visitSend(ChoreographyParser.SendContext ctx) {
        //System.out.println("Send");

        String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String expression = ctx.expr().getText();

        String s = new StringBuilder().append(receivingProcess).append("!<").append(expression).append(">;").toString();
        proc.put(sendingProcess, proc.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("?;").toString();
        proc.put(receivingProcess, proc.get(receivingProcess) + r);

        visitChildren(ctx);
        return null;
    }

    @Override public Void visitChoose(ChoreographyParser.ChooseContext ctx) {
        //System.out.println("Choose");
        String sendingProcess = ctx.sendingProcess().getText();
        String receivingProcess = ctx.receivingProcess().getText();
        String label = ctx.label().getText();

        String s = new StringBuilder().append(receivingProcess).append("+").append(label).append(";").toString();
        proc.put(sendingProcess, proc.get(sendingProcess) + s);

        String r = new StringBuilder().append(sendingProcess).append("&{").append("};").toString();
        proc.put(receivingProcess, proc.get(receivingProcess) + r);

        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitSendingProcess(ChoreographyParser.SendingProcessContext ctx) {
        System.out.println("SendingProcess");
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitReceivingProcess(ChoreographyParser.ReceivingProcessContext ctx) {
        System.out.println("VisitingProcess");
        visitChildren(ctx);
        return null;
    }

    public HashMap<String,String> returnChoreography(){
        return (HashMap<String, String>) proc;
    }

}
