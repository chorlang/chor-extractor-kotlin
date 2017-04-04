package ast.sp.nodes;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.NetworkInterface;

/**
 * Created by lara on 04/04/17.
 */
public class Termination implements Behaviour, NetworkInterface {
    private final String termination = "stop";
}
