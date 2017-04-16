package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lara on 04/04/17.
 */
public class Offering implements Behaviour {
    private final String process;
    private final HashMap<String, Behaviour> labels;

    public Offering(String process, HashMap<String, Behaviour> labels) {
        this.process = process;
        this.labels = labels;
    }

    public String getProcess() {
        return process;
    }

    public HashMap<String, Behaviour> getLabels() {
        return labels;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(process + "&{");
        for (Map.Entry<String, Behaviour> entry : labels.entrySet()) {
            builder.append(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        builder.delete(builder.length()-2, builder.length());
        builder.append("}");
        return builder.toString();
    }
}
