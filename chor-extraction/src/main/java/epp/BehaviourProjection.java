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
import ast.sp.nodes.Receiving;
import ast.sp.nodes.Sending;

/**
 *
 * @author Fabrizio Montesi <famontesi@gmail.com>
 */
public class BehaviourProjection implements CCVisitor< SPNode >
{
    private String processName;

    public SPNode getSPAST(CCNode node, String processName) throws MergingException {
        this.processName = processName;
        return node.accept(this);
    }

    public SPNode visit(Selection n ) throws MergingException {
        SPNode continuation = (n.getContinuation()).accept(this);
        final SPNode retVal;

        if( processName.equals( n.getSender() ) ) {
            retVal = new ast.sp.nodes.Selection( (Behaviour) continuation, n.getReceiver(), n.getLabel());
        } else if ( processName.equals( n.getReceiver() ) ) {
            retVal = new ast.sp.nodes.Selection( (Behaviour) continuation, n.getSender(), n.getLabel() );
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
            retVal = new ast.sp.nodes.Condition( n.getProcess(), n.getExpression(), (Behaviour) n.getThenChoreography().accept( this ), (Behaviour) n.getElseChoreograpy().accept( this ) );
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
        return (SPNode) new ast.sp.nodes.Termination();
    }

    @Override
    public SPNode visit(ProcedureDefinition n) throws MergingException {
        final SPNode retval;

        if (n.getProcesses().contains(processName)){
            retval = new ast.sp.nodes.ProcedureDefinition(n.getProcedure(), (Behaviour) n.getChoreography().accept(this), (Behaviour) n.getInChoreography().accept(this));
        } else {
            retval = (SPNode) n.getInChoreography();
            return retval;
        }

        return retval;

    }

    @Override
    public SPNode visit(ProcedureInvocation n) {
        final SPNode retval;

        if (n.getProcesses().contains(processName)){
            retval =  new ast.sp.nodes.ProcedureInvocation(n.getProcedure());
        } else {
            retval = (SPNode) new Termination();
            return retval;
        }

        return retval;
    }
}
