package epp

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import java.util.*


object Merging {
    class MergingException(var s: String) : Exception(s)

    fun merge(left: SPNode, right: SPNode): Behaviour {
        return when {
            left is SendSP && right is SendSP -> merge(left, right)
            left is ReceiveSP && right is ReceiveSP -> merge(left, right)
            left is TerminationSP && right is TerminationSP -> TerminationSP()
            left is SelectionSP && right is SelectionSP -> merge(left, right)
            left is OfferingSP && right is OfferingSP -> merge(left, right)
            left is ConditionSP && right is ConditionSP -> merge(left, right)
            left is ProcedureInvocationSP && right is ProcedureInvocationSP -> merge(left, right)
            else -> throw MergingException("Can't merge $left with $right")
        }
    }

    private fun merge(left: SendSP, right: SendSP): Behaviour {
        if (left.process != right.process || left.expression != right.expression) throw MergingException("Can't merge ${left.process} and ${right.process}")
        val m = merge(left.continuation, right.continuation)
        return SendSP(m, left.process, left.expression)
    }

    private fun merge(left: ReceiveSP, right: ReceiveSP): Behaviour {
        if (left.process != right.process) throw MergingException("Can't merge ${left.process} and ${right.process}")

        val m = merge(left.continuation, right.continuation)
        return ReceiveSP(m, left.process)

    }

    private fun merge(left: SelectionSP, right: SelectionSP): Behaviour {
        if (left.process != right.process || left.expression != right.expression) throw MergingException("Can't merge ${left.process}+${left.expression} and ${right.process}+${right.expression}")

        val continuation = merge(left.continuation, right.continuation)
        return SelectionSP(continuation, left.process, left.expression)
    }

    private fun merge(left: OfferingSP, right: OfferingSP): Behaviour {
        if (left.process != right.process) throw MergingException("Can't merge ${left.process} and ${right.process}")

        val leftBranches = left.branches
        val rightBranches = right.branches
        val labels = HashMap<String, Behaviour>()

        for (leftKey in leftBranches.keys) {
            if (rightBranches.containsKey(leftKey)) {
                labels[leftKey] = merge(left.branches[leftKey]!!, right.branches[leftKey]!!)
                rightBranches.remove(leftKey)
            } else {
                labels[leftKey] = left.branches[leftKey]!!
            }
        }
        for (rightKey in rightBranches.keys) {
            labels[rightKey] = right.branches[rightKey]!!
        }

        return OfferingSP(left.process, labels)
    }

    private fun merge(left: ConditionSP, right: ConditionSP): Behaviour {
        val leftCondition = merge(left.thenBehaviour, right.thenBehaviour)
        val rightCondition = merge(left.elseBehaviour, right.elseBehaviour)
        if (left.expression != right.expression)
            throw MergingException("Can't merge conditions $leftCondition and $rightCondition")

        return ConditionSP(left.expression, leftCondition, rightCondition)
    }

    private fun merge(left: ProcedureInvocationSP, right: ProcedureInvocationSP): Behaviour {
        if (left.procedure == right.procedure) {
            return ProcedureInvocationSP(left.procedure)
        } else
            throw MergingException("Can't merge procedures ${left.procedure} and ${right.procedure}")
    }
}
