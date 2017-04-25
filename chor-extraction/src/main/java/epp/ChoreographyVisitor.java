package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.*;
import ast.cc.interfaces.CCNode;
import ast.cc.interfaces.Choreography;
import ast.cc.nodes.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ChoreographyVisitor extends ChoreographyBaseVisitor<CCNode> {

    private HashSet<String> processes;

    public HashSet<String> getProcesses() {
        return processes;
    }

    public ChoreographyVisitor() {
        processes = new HashSet<>();
    }

    public CCNode getCCAST(ParseTree parseTree){
        return this.visit(parseTree);
    }

    @Override
    public CCNode visitCommunication(CommunicationContext ctx) {
        String sender = ctx.process(0).getText();
        String receiver = ctx.process(1).getText();
        String expression = ctx.expression().getText();

        CCNode continuation = visit(ctx.choreography());

        processes.add(sender);
        processes.add(receiver);

        return new Communication(sender,receiver,expression, continuation);
    }

    @Override
    public CCNode visitSelection(SelectionContext ctx) {

        String sender = ctx.process(0).getText();
        String receiver = ctx.process(1).getText();
        String expression = ctx.label().getText();

        CCNode continuation = visit(ctx.choreography());

        processes.add(sender);
        processes.add(receiver);

        return new Selection(sender,receiver,expression, continuation);
    }

    @Override
    public CCNode visitCondition(ConditionContext ctx) {
        String process = ctx.process().getText();
        String expression = ctx.expression().getText();
        CCNode thenChoreography = visit(ctx.choreography(0));
        CCNode elseChoreography = visit(ctx.choreography(1));

        processes.add(process);

        return new Condition(process, expression, thenChoreography, elseChoreography);
    }

    @Override
    public CCNode visitProgram(ProgramContext ctx) {
        List<CCNode> procedures = ctx.procedureDefinition().stream().map(this::visit).collect(Collectors.toList());
        return new Program((Choreography) visit(ctx.main()), procedures);
    }

    @Override
    public CCNode visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        return new ProcedureDefinition(ctx.procedure().getText(), visit(ctx.choreography()), processes);
    }

    @Override
    public CCNode visitMain(MainContext ctx) {
        return visit(ctx.choreography());
    }

    @Override public CCNode visitProcedureInvocation(ProcedureInvocationContext ctx) {
        String procedureName = ctx.procedure().getText();
        return new ProcedureInvocation(procedureName, processes);
    }

    @Override
    public CCNode visitTerminal(TerminalNode node) {
        return new Termination();
    }
}
