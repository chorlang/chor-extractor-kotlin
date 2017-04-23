package ast.sp.nodes;

import ast.sp.interfaces.SPNode;

import java.util.HashMap;
import java.util.Map;

public class Network implements SPNode{
    private HashMap<String, SPNode> network = new HashMap<>();

    public Network(HashMap<String, SPNode> network) {
        this.network = network;
    }

    public HashMap<String, SPNode> getNetwork() {
        return network;
    }

    public void addProcess(Network network){
        this.network.putAll(network.getNetwork());
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, SPNode> entry : network.entrySet()) {
            builder.append(entry.getKey()).append(" is ").append(entry.getValue().toString()).append(" | ");
        }

        if (builder.length()>=3){
            builder.delete(builder.length()-3, builder.length());
        }

        return builder.toString();
    }
}
