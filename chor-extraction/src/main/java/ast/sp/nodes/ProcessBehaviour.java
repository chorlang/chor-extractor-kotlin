package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;

import java.util.ArrayList;
import java.util.List;

public class ProcessBehaviour implements SPNode {
    private final String process;
    private final List<ProcedureDefinitionSP> procedures;
    private final Behaviour main;

    private List<String> visitedProcedures;

    public ProcessBehaviour(String process, List<ProcedureDefinitionSP> procedures, Behaviour main) {
        this.process = process;
        this.procedures = procedures;
        this.main = main;
        this.visitedProcedures = new ArrayList<>();
    }

    public String getProcess() {
        return process;
    }

    public List<ProcedureDefinitionSP> getProcedures() {
        return procedures;
    }

    public Behaviour getMain() {
        return main;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(process + "{");
        for (ProcedureDefinitionSP procedure: procedures) {
            builder.append(procedure.toString());
        }
        return  builder.append("main {" + main.toString() + "}}").toString();
    }

    public List<String> getVisitedProcedures() {
        return visitedProcedures;
    }

    public void setVisitedProcedures(String proc) {
        visitedProcedures.add(proc);
    }

    public void clearVisitedProcedures() {
        visitedProcedures.clear();
    }
}
