package ast.sp.nodes;

import ast.sp.interfaces.SPNode;

import java.util.Collections;
import java.util.List;

public class Network implements SPNode{
    private final List<ProcessBehaviour> network;
    private boolean projectable;

    public Network(List<ProcessBehaviour> network) {
        this.network = network;
        projectable = true;
    }

    public List<ProcessBehaviour> getNetwork() {
        return network;
    }

    public boolean isProjectable() {
        return projectable;
    }

    public void setProjectable(boolean projectable) {
        this.projectable = projectable;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        Collections.sort(network, (pb1, pb2) -> pb1.getProcess().compareTo(pb2.getProcess()));

        for (ProcessBehaviour entry : network) {
            builder.append(entry.toString()).append(" | ");
        }

        if (builder.length()>=3){
            builder.delete(builder.length()-3, builder.length());
        }

        return builder.toString();
    }
}
