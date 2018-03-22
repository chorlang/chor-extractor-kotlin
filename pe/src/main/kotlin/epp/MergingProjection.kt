package epp

import ast.cc.nodes.ProcedureDefinition
import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import java.util.*
import kotlin.collections.ArrayList


class MergingProjection() {
    class MergingException(var s: String): Exception(s)

        fun merge(left: SPNode, right: SPNode): SPNode {
            return when {
                left is Sending && right is Sending -> merge(left, right)
                left is Receiving && right is Receiving -> merge(left, right)
                left is TerminationSP && right is TerminationSP -> TerminationSP()
                left is SelectionSP && right is SelectionSP -> merge(left, right)
                left is Offering && right is Offering -> merge(left, right)
                left is ConditionSP && right is ConditionSP -> merge(left, right)
                left is ProcedureDefinitionSP && right is ProcedureDefinitionSP -> merge(left, right)
                left is ProcedureInvocationSP && right is ProcedureInvocationSP -> merge(left, right)
                else -> throw MergingException("Can't merge " + left.toString() + " with " + right.toString())
            }
        }

    private fun merge(left: Sending, right: Sending): SPNode {
            if (left.process != right.process || left.expression != right.expression) throw MergingException("Can't merge " + left.process + " and " + right.process )
            val m = merge(left.continuation, right.continuation) as Behaviour
            return Sending(m, left.process, left.expression)
        }

    private fun merge(left: Receiving, right: Receiving): SPNode {
            if (left.process != right.process) throw MergingException("Can't merge " + left.process + " and " + right.process )

            val m = merge(left.continuation, right.continuation) as Behaviour
            return Receiving(m, left.process)

        }

        private fun merge(left: SelectionSP, right: SelectionSP): SPNode {
            if (left.process != right.process ||left.expression != right.expression) throw MergingException("Can't merge " + left.process + " and " + right.process )

            val continuation = merge(left.continuation, right.continuation) as Behaviour
            return SelectionSP(continuation, left.process, left.expression)
        }

        private fun merge(left: Offering, right: Offering): SPNode {
            if (left.process != right.process) throw MergingException("Can't merge " + left.process + " and " + right.process )

            val leftmap = left.labels
            val rightmap = right.labels
            val labels = HashMap<String, Behaviour>()

            for (leftkey in leftmap.keys) {
                if (rightmap.containsKey(leftkey)) {
                    val b = merge(left.labels[leftkey] as Behaviour, right.labels[leftkey] as Behaviour) as Behaviour
                    labels.put(leftkey, b)
                    rightmap.remove(leftkey)
                } else {
                    labels.put(leftkey, left.labels[leftkey] as Behaviour)
                }

            }
            for (rightkey in rightmap.keys) {
                labels.put(rightkey, right.labels[rightkey] as Behaviour)
            }

            return Offering(left.process, labels)
        }

        private fun merge(left: ConditionSP, right: ConditionSP): SPNode {
            val leftCondition = merge(left.thenBehaviour, left.thenBehaviour) as Behaviour
            val rightCondition = merge(right.elseBehaviour, right.elseBehaviour) as Behaviour
            if (left.expression != right.expression)
                throw MergingException("Can't merge conditions " + leftCondition.toString() + " and " + rightCondition.toString())

            return ConditionSP(left.expression, leftCondition, rightCondition)
        }

        private fun merge(left: ProcedureDefinitionSP, right: ProcedureDefinitionSP): SPNode {
            val mergeBehaviour = merge(left.behaviour, right.behaviour) as Behaviour
            return ProcedureDefinitionSP(mergeBehaviour)
        }

        private fun merge(left: ProcedureInvocationSP, right: ProcedureInvocationSP): SPNode {
            if (left.procedure == right.procedure) {
                return ProcedureInvocationSP(left.procedure)
            } else
                throw MergingException("Can't merge procedures " + left.procedure + " and " + right.procedure)
        }
}
