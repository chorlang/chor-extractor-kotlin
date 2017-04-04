package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.NetworkInterface;

import java.util.HashSet;

public class Network implements NetworkInterface{

    private final String process;
    private final Behaviour behaviour;

    private HashSet<NetworkInterface> parallelNetworks;

    public Network(String process, Behaviour behaviour) {
        this.process = process;
        this.behaviour = behaviour;
        parallelNetworks = new HashSet<>();
    }

    public String getProcess() {
        return process;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public HashSet<NetworkInterface> getParallelNetworks() {
        return parallelNetworks;
    }

    public void setParallelNetworks(HashSet<NetworkInterface> parallelNetworks) {
        this.parallelNetworks = parallelNetworks;
    }
}
