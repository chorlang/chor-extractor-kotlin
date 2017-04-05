package epp;

import ast.cc.interfaces.CCNode;
import ast.cc.nodes.Communication;
import ast.cc.nodes.ProcedureDefinition;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;

/**
 * Created by lara on 05/04/17.
 */
public class MergingProjection {
    String process;
    public MergingProjection(String process){
        this.process = process;
    }
    public SPNode merge(Communication c1, Communication c2){
        String sender = c1.getSender();
        String receiver = c1.getReceiver();
        if ((sender.equals(c2.getSender())) && (receiver.equals(c2.getReceiver()))) {
            if (sender.equals(process)){

                //what is expression here??? c1.expression or c2.expression?
                return new Sending();

            } else if (receiver.equals(process)){
                SPNode a = (SPNode) c1.getContinuation();
                Behaviour continuation = merge(a, a);
                return new Receiving(continuation, sender);

            } else {
                return merge(c1.getContinuation(), c2.getContinuation());
            }

        } else {
            return null; //God forgive me
        }
    }



    public  SPNode merge(Selection s1, Selection s2){
        return new Selection();
    }

    public static SPNode merge(Condition c1, Condition c2){
        
        return new Condition();
    }

    public static SPNode merge(Termination t1, Termination t2){
        return new ast.sp.nodes.Termination();
    }

    public static SPNode merge(ProcedureDefinition p1, ProcedureDefinition p2){
        return new ast.sp.nodes.ProcedureDefinition();
    }

    public static SPNode merge(ProcedureInvocation p1, ProcedureInvocation p2){
        return new ProcedureInvocation();
    }
}
