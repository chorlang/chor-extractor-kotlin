/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epp;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.nodes.*;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;

import java.util.HashMap;

/**
 *
 * @author Fabrizio Montesi <famontesi@gmail.com>
 */
public class BehaviourProjection implements CCVisitor< SPNode >
{
    private String processName;
    private HashMap<String, SPNode> procedureList;

    public SPNode getSPAST(CCNode node, String processName) throws MergingException {
        this.processName = processName;
        return node.accept(this);
    }

    public SPNode visit(Selection n ) throws MergingException {
        SPNode continuation = (n.getContinuation()).accept(this);
        final SPNode retVal;

        if( processName.equals( n.getSender() ) ) {
            retVal = new SelectionSP( (Behaviour) continuation, n.getReceiver(), n.getLabel());
        } else if ( processName.equals( n.getReceiver() ) ) {
            retVal = new Offering(n.getSender(), new HashMap<String, Behaviour>(){{put(n.getLabel(), (Behaviour) continuation);}});
        } else {
            retVal = continuation;
        }

        return retVal;
    }

    @Override
    public SPNode visit(Communication n) throws MergingException {
        SPNode continuation = (n.getContinuation()).accept(this);

        final SPNode retVal;
        if( processName.equals( n.getSender() ) ) {
            retVal = new Sending( (Behaviour) continuation, n.getReceiver(), n.getExpression() );
        } else if ( processName.equals( n.getReceiver() ) ) {
            retVal = new Receiving( (Behaviour) continuation, n.getSender() );
        } else {
            retVal = continuation;
        }

        return retVal;
    }

    @Override
    public SPNode visit(Condition n) throws MergingException {
        final SPNode retVal;

        if( processName.equals( n.getProcess() ) ) {
            retVal = new ConditionSP( n.getProcess(), n.getExpression(), (Behaviour) n.getThenChoreography().accept( this ), (Behaviour) n.getElseChoreograpy().accept( this ) );
        } else {
            Behaviour merge = null;
            try {
                merge = (Behaviour) MergingProjection.merge( n.getThenChoreography().accept( this ), n.getElseChoreograpy().accept( this ) );
            } catch (MergingException e) {
                e.printStackTrace();
            }
            retVal = merge;
        }

        return retVal;
    }

    @Override
    public SPNode visit(Termination n) {
        return new TerminationSP();
    }

    @Override
    public SPNode visit(ProcedureDefinition n) throws MergingException {
        String procedure = n.getProcedure();
        SPNode node =  n.getChoreography().accept(this);
        return new ProcedureDefinitionSP(procedure, (Behaviour) node, new ProcedureInvocationSP(procedure));
    }

    @Override
    public SPNode visit(ProcedureInvocation n) {
        final SPNode retval;

        if (n.getProcesses().contains(processName)){
            retval =  new ProcedureInvocationSP(n.getProcedure());
        } else {
            retval = (SPNode) new Termination();
            return retval;
        }

        return retval;
    }

    @Override
    public SPNode visit(Program n) throws MergingException {
        procedureList= new HashMap<>();
        for (CCNode procedure: n.getProcedures()) {
            SPNode node =  procedure.accept(this);
            procedureList.put(((ProcedureDefinitionSP) node).getProcedure(), node);
        }

        procedureList.put("main", n.getMain().accept(this));
        ProceduresUnwrapper pu = new ProceduresUnwrapper(procedureList);
        return pu.unwrap();
    }
}
