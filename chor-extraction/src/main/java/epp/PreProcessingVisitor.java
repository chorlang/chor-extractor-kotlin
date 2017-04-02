package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.ProcessContext;
import antlr4.ChoreographyParser.ConditionContext;
import antlr4.ChoreographyParser.ChoreographyContext;
import antlr4.ChoreographyParser.ProcedureDefinitionContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PreprocessingVisitor extends ChoreographyBaseVisitor {

    private Set<String> processes;
    private HashMap<String,Set<String>> procedures;

    public PreprocessingVisitor(){
        processes = new HashSet();
        procedures = new HashMap();
    }


    @Override
    public Set<String> visitChoreography(ChoreographyContext ctx) {
        visitChildren(ctx);
        return processes;
    }

    @Override
    public Object visitCondition(ConditionContext ctx) {


        return super.visitCondition(ctx);
    }

    @Override
    public Object visitProcedureDefinition(ProcedureDefinitionContext ctx) {
        //procedures.put(ctx.procedure().getText(), visitChoreography());

        PreprocessingVisitor ppp = new PreprocessingVisitor();
        ppp.visit(ctx.internal_choreography());
        procedures.put(ctx.procedure().getText(), ppp.processes);
        return super.visitProcedureDefinition(ctx);
    }

    @Override
    public Object visitProcess(ProcessContext ctx) {
        processes.add(ctx.getText());
        Object p = visitChildren(ctx);
        return "a";
        //return super.visitProcess(ctx);
    }

    public Set<String> getProcesses() {
        return processes;
    }

    public HashMap<String, Set<String>> getProcedures() {
        return procedures;
    }
}
