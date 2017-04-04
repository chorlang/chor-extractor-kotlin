package epp;

import antlr4.ChoreographyBaseVisitor;
import antlr4.ChoreographyParser.ProcessContext;

import java.util.HashSet;

public class ProcessesCollectingVisitor extends ChoreographyBaseVisitor<HashSet<String>> {

    private HashSet<String> processes;

    public ProcessesCollectingVisitor(){
        processes = new HashSet();
    }

    @Override
    public HashSet<String> visitProcess(ProcessContext ctx) {
        processes.add(ctx.getText());
        return processes;
    }

    public HashSet<String> getProcesses() {
        return processes;
    }

}
