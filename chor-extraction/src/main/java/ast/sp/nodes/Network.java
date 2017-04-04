package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.NetworkInterface;

public class Network implements NetworkInterface{

    private final String process;
    private final Behaviour behaviour;


    private Network nextNetwork;

    public Network(String process, Behaviour behaviour) {
        this.process = process;
        this.behaviour = behaviour;
    }

    public String getProcess() {
        return process;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setNextNetwork(Network nextNetwork) {
        this.nextNetwork = nextNetwork;
    }


}
