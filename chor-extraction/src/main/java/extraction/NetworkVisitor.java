package extraction;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by lara on 28/03/17.
 */
public class NetworkVisitor {
    private final static NetworkVisitor v = new NetworkVisitor();
    private NetworkVisitor(){}
    public static NetworkVisitor getVisitor(){ return v;}

    public void visit(ParseTree tree){

    }
}
