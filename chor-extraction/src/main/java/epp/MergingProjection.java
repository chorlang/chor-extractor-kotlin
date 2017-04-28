package epp;

import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class MergingProjection {
    public static SPNode merge(SPNode node1, SPNode node2) throws MergingException {
        try {
            Class<?> mp = MergingProjection.class;
            Method m = mp.getMethod("merge", node1.getClass(), node2.getClass());
            return (SPNode) m.invoke( null, node1, node2 );
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new MergingException("Can't merge " + node1.getClass() + " with " + node2.getClass(), e);
        }
    }

    public static SPNode merge(TerminationSP left, TerminationSP right){
        return new TerminationSP();
    }

    public static SPNode merge(Sending left, Sending right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess()))&&(left.getExpression().equals(right.getExpression())), "");
        Behaviour m = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new Sending(m, left.getProcess(), left.getExpression());
    }

    public static SPNode merge(Receiving left, Receiving right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess())), "");

        Behaviour m = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new Receiving(m, left.getProcess());

    }

    public static SPNode merge(SelectionSP left, SelectionSP right) throws MergingException {
        assertCondition ((left.getProcess().equals(right.getProcess())),"");
        assertCondition ((left.getLabel().equals(right.getLabel())),"");

        Behaviour continuation = (Behaviour) merge(left.getContinuation(), right.getContinuation());
        return new SelectionSP(continuation, left.getProcess(), left.getLabel());
    }

    public static SPNode merge(Offering left, Offering right) throws MergingException {
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

    public static SPNode merge(ConditionSP left, ConditionSP right) throws MergingException {
        Behaviour leftCondition = (Behaviour) merge(left.getThenBehaviour(), left.getThenBehaviour());
        Behaviour rightCondition = (Behaviour) merge(right.getElseBehaviour(), right.getElseBehaviour());
        assertCondition( left.getProcess().equals( right.getProcess() ), "" );
        assertCondition( left.getExpression().equals( right.getExpression() ), "" );
        return new ConditionSP(left.getProcess(), left.getExpression(), leftCondition, rightCondition );
    }

    public static SPNode merge(ProcedureInvocationSP left, ProcedureInvocationSP right){
        if (left.getProcedure().equals(right.getProcedure())) {
            return new ProcedureInvocationSP(left.getProcedure());
        } else return null;
    }

    public SPNode merge(ProcedureDefinitionSP left, ProcedureDefinitionSP right) throws MergingException {
        assertCondition( left.getProcedure().equals( right.getProcedure() ), "" );

        Behaviour mergeBehaviour = (Behaviour) merge(left.getBehaviour(), right.getBehaviour());
        return new ProcedureDefinitionSP(left.getProcedure(), mergeBehaviour);
    }

    private static void assertCondition( boolean condition, String message )
        throws MergingException {
        if ( !condition ) {
            throw new MergingException( message );
        }
    }
}
