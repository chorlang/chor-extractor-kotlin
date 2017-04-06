package epp;

import ast.cc.nodes.Termination;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by lara on 05/04/17.
 */
public class MergingProjection {
    private final String process;

    public MergingProjection(String process){
        this.process = process;
    }

    public SPNode merge(SPNode node1, SPNode node2) throws MergingException {
        try {
            Method m = getClass().getMethod("merge", node1.getClass(), node2.getClass());
            return (SPNode) m.invoke(node1, node2);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new MergingException("Can't merge", e);
        }
    }

    public SPNode merge(Termination left, Termination right){
        return new ast.sp.nodes.Termination();
    }

    public SPNode merge(Sending left, Sending right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess()))&&(left.getExpression().equals(right.getExpression())), "");
        Behaviour m = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new Sending(m, left.getProcess(), left.getExpression());
    }

    public SPNode merge(Receiving left, Receiving right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess())), "");

        Behaviour m = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new Receiving(m, left.getProcess());

    }

    public  SPNode merge(Selection left, Selection right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess())),"");
        assertCondition ((left.getLabel().equals(right.getLabel())),"");

        Behaviour continuation = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new Selection(continuation, left.getProcess(), left.getLabel());
    }

    public  SPNode merge(Offering left, Offering right) throws MergingException {
        assertCondition((left.getProcess().equals(right.getProcess())), "" );

        HashMap<String, Behaviour> leftmap = left.getLabels();
        HashMap<String, Behaviour> rightmap = right.getLabels();
        HashMap<String, Behaviour> labels = new HashMap();

        for (String leftkey: leftmap.keySet()){
            if (rightmap.containsKey(leftkey)){
                Behaviour b = (Behaviour) merge(left.getLabels().get(leftkey), right.getLabels().get(leftkey));
                labels.put(leftkey, b);
                rightmap.remove(leftkey);
            } else {
                labels.put(leftkey, left.getLabels().get(leftkey));
            }

        }
        for (String rightkey: rightmap.keySet()){
            labels.put(rightkey, right.getLabels().get(rightkey));
        }

        return new Offering(left.getProcess(), labels);
    }

    public SPNode merge(Condition left, Condition right) throws MergingException {
        Behaviour leftCondition = (Behaviour) merge(left.getThenBehaviour(), left.getThenBehaviour());
        Behaviour rightCondition = (Behaviour) merge(right.getElseBehaviour(), right.getElseBehaviour());
        return merge(leftCondition, rightCondition);
    }

    public static SPNode merge(ProcedureInvocation left, ProcedureInvocation right){
        if (left.getProcedure().equals(right.getProcedure())) {
            return new ProcedureInvocation(left.getProcedure());
        } else return null;
    }

    public SPNode merge(ProcedureDefinition left, ProcedureDefinition right) throws MergingException {
        assertCondition( left.getProcedure().equals( right.getProcedure() ), "" );

        Behaviour mergeBehaviour = (Behaviour) merge(left.getBehaviour(), right.getBehaviour());
        Behaviour mergeInBehaviour = (Behaviour) merge(left.getInBehaviour(), right.getInBehaviour());
        return new ProcedureDefinition(left.getProcedure(), mergeBehaviour, mergeInBehaviour);
    }

    private static void assertCondition( boolean condition, String message )
        throws MergingException {
        if ( !condition ) {
            throw new MergingException( message );
        }
    }
}
