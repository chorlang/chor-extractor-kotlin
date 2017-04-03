/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epp;

import ast.cc.CCVisitor;
import ast.cc.Selection;
import ast.sp.SPNode;
import ast.sp.SendLabel;

/**
 *
 * @author Fabrizio Montesi <famontesi@gmail.com>
 */
public class BehaviourProjection implements CCVisitor< SPNode >
{
	public SPNode visit( Selection n )
    {
        // Visit n.continuation() first, getting the projection of the continuation
        // Create the return SendLabel with the continuation you got and the information for the head here.
        return SendLabel();
    }
}
