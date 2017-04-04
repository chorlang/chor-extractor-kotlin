/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epp;

import ast.cc.CCVisitor;
import ast.cc.interfaces.CCNode;
import ast.cc.nodes.*;
import ast.cc.nodes.Condition;
import ast.cc.nodes.ProcedureDefinition;
import ast.cc.nodes.ProcedureInvocation;
import ast.cc.nodes.Selection;
import ast.cc.nodes.Termination;
import ast.sp.interfaces.Behaviour;
import ast.sp.interfaces.SPNode;
import ast.sp.nodes.*;

import java.util.HashSet;

/**
 *
 * @author Fabrizio Montesi <famontesi@gmail.com>
 */
public class BehaviourProjection implements CCVisitor< SPNode >
{
    private HashSet<String> processes;

    public SPNode getSPAST(CCNode node, HashSet<String> processes){
        this.processes = processes;
        return node.accept(this);
    }

    public SPNode visit(Selection n )
    {
        // Visit n.continuation() first, getting the projection of the continuation
        // Create the return Sending with the continuation you got and the information for the head here.
        //return Sending();

        String receiver = n.getReceiver();
        String label = n.getLabel();
        String sender = n.getSender();
        SPNode continuation = (n.getContinuation()).accept(this);



        return null;
    }

    @Override
    public SPNode visit(Communication n) {
        String receiver = n.getReceiver();
        String expression = n.getExpression();
        String sender = n.getSender();
        SPNode continuation = (n.getContinuation()).accept(this);

        SPNode sending = new Sending((Behaviour) continuation, receiver,expression);
        SPNode receiving = new Receiving((Behaviour) continuation, sender);


        return null;
    }

    @Override
    public SPNode visit(Condition n) {
        String process =  n.getProcess();
        String expression = n.getExpression();

        Behaviour thenbehaviour = (Behaviour) n.getThenChoreography();
        Behaviour elsebehaviour = (Behaviour) n.getElseChoreograpy();

        SPNode condition = new ast.sp.nodes.Condition(process,expression,thenbehaviour,elsebehaviour);

        return condition;
    }

    @Override
    public SPNode visit(Termination n) {
        return (SPNode) new Termination();
    }

    @Override
    public SPNode visit(ProcedureDefinition n) {
        String procedure =  n.getProcedure();
        Behaviour behaviour = (Behaviour) n.getChoreography();
        Behaviour inbehaviour = (Behaviour) n.getInChoreography();

        SPNode procedureDefinition = new ast.sp.nodes.ProcedureDefinition(procedure, behaviour, inbehaviour);
        return procedureDefinition;
    }

    @Override
    public SPNode visit(ProcedureInvocation n) {
        return new ast.sp.nodes.ProcedureInvocation(n.getProcedure());
    }
}
