package ast.sp.interfaces

import ast.sp.nodes.*

/**
 * Created by fmontesi on 03/04/17.
 */
interface SPVisitor<T> {
    fun visit(n: ConditionSP): T

    fun visit(n: Network): T

    fun visit(n: OfferingSP): T

    fun visit(n: ParallelNetworks): T

    fun visit(n: ProcedureInvocationSP): T

    fun visit(n: ProcessTerm): T

    fun visit(n: ReceiveSP): T

    fun visit(n: SelectionSP): T

    fun visit(n: SendingSP): T

    fun visit(n: TerminationSP): T

    fun visit(n: Behaviour): T
}
