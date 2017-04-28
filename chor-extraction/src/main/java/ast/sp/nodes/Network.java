package ast.sp.nodes;

import ast.sp.interfaces.SPNode;

import java.util.List;

public class Network implements SPNode{
    private final List<ProcessBehaviour> network;

    public Network(List<ProcessBehaviour> network) {
        this.network = network;
    }

    public List<ProcessBehaviour> getNetwork() {
        return network;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (ProcessBehaviour entry : network) {
            builder.append(entry.toString()).append(" | ");
        }

        if (builder.length()>=3){
            builder.delete(builder.length()-3, builder.length());
        }

        return builder.toString();
    }
}
