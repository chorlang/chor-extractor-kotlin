package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

import java.util.HashMap;

/**
 * Created by lara on 04/04/17.
 */
public class Offering implements Behaviour {
    private final String process;

    private HashMap<String, Behaviour> labels;

    public Offering(String process) {
        this.process = process;
        labels = new HashMap<>();
    }

    public String getProcess() {
        return process;
    }

    public HashMap<String, Behaviour> getLabels() {
        return labels;
    }

    public void setLabels(HashMap<String, Behaviour> labels) {
        this.labels = labels;
    }
}
