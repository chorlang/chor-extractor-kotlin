package epp

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.nodes.*
import java.lang.reflect.InvocationTargetException
import java.util.*


class MergingProjection {

    @Throws(MergingException::class)
    fun merge(left: ProcedureDefinitionSP, right: ProcedureDefinitionSP): SPNode {
        val mergeBehaviour = merge(left.behaviour, right.behaviour) as Behaviour
        return ProcedureDefinitionSP(mergeBehaviour)
    }

    companion object {
        @Throws(MergingException::class)
        fun merge(node1: SPNode, node2: SPNode): SPNode {
            try {
                val mp = MergingProjection::class.java
                val m = mp.getMethod("merge", node1.javaClass, node2.javaClass)
                return m.invoke(null, node1, node2) as SPNode
            } catch (e: NoSuchMethodException) {
                throw MergingException("Can't merge " + node1.javaClass + " with " + node2.javaClass, e)
            } catch (e: InvocationTargetException) {
                throw MergingException("Can't merge " + node1.javaClass + " with " + node2.javaClass, e)
            } catch (e: IllegalAccessException) {
                throw MergingException("Can't merge " + node1.javaClass + " with " + node2.javaClass, e)
            }

        }

        fun merge(left: TerminationSP, right: TerminationSP): SPNode {
            return TerminationSP()
        }

        @Throws(MergingException::class)
        fun merge(left: Sending, right: Sending): SPNode {
            assertCondition(left.process == right.process && left.expression == right.expression)
            val m = merge(left.continuation, right.continuation) as Behaviour
            return Sending(m, left.process, left.expression)
        }

        @Throws(MergingException::class)
        fun merge(left: Receiving, right: Receiving): SPNode {
            assertCondition(left.process == right.process)

            val m = merge(left.continuation, right.continuation) as Behaviour
            return Receiving(m, left.process)

        }

        @Throws(MergingException::class)
        fun merge(left: SelectionSP, right: SelectionSP): SPNode {
            assertCondition(left.process == right.process)
            assertCondition(left.expression == right.expression)

            val continuation = merge(left.continuation, right.continuation) as Behaviour
            return SelectionSP(continuation, left.process, left.expression)
        }

        @Throws(MergingException::class)
        fun merge(left: Offering, right: Offering): SPNode {
            assertCondition(left.process == right.process)

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

        @Throws(MergingException::class)
        fun merge(left: ConditionSP, right: ConditionSP): SPNode {
            val leftCondition = merge(left.thenBehaviour, left.thenBehaviour) as Behaviour
            val rightCondition = merge(right.elseBehaviour, right.elseBehaviour) as Behaviour
            assertCondition(left.process == right.process)
            assertCondition(left.expression == right.expression)
            return ConditionSP(left.process, left.expression, leftCondition, rightCondition)
        }

        fun merge(left: ProcedureInvocationSP, right: ProcedureInvocationSP): SPNode? {
            if (left.procedure == right.procedure) {
                return ProcedureInvocationSP(left.procedure)
            } else
                return null
        }

        @Throws(MergingException::class)
        private fun assertCondition(condition: Boolean) {
            if (!condition) {
                throw MergingException("")
            }
        }
    }
}
