package util.networkStatistic

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkAddActions : SPVisitor<Behaviour> {
    private var counter: Int = 0

    override fun visit(n: ConditionSP): Behaviour {
        /*val numOfProcesses = NetworkProcessActions().visit(n.thenBehaviour)
        return when {
            counter > numOfProcesses -> {
                counter -= numOfProcesses
                ConditionSP(n.label, n.thenBehaviour, n.elseBehaviour.accept(this))
            }
            else -> ConditionSP(n.label, n.thenBehaviour.accept(this), n.elseBehaviour)
        }*/
        throw OperationNotSupportedException()

    }

    override fun visit(n: ProcessTerm): Behaviour {
        return n.main.accept(this)
    }

    override fun visit(n: ReceiveSP): Behaviour {
        return if (counter > 0) {
            counter--
            ReceiveSP(n.continuation.accept(this), n.sender)

        } else generateAction(n.continuation)
    }

    override fun visit(n: SendSP): Behaviour {
        return if (counter > 0) {
            counter--
            SendSP(n.continuation.accept(this), n.receiver, n.expression)
        } else generateAction(n.continuation)
    }

    override fun visit(n: OfferingSP): Behaviour {
        /*n.branches.forEach { label, behaviour ->
            val numOfProcesses = NetworkProcessActions().visit(behaviour)
            when {
                numOfProcesses < counter -> counter-= numOfProcesses
                numOfProcesses >= counter -> {
                    n.branches.replace(label, behaviour.accept(this))
                    return@forEach
                }
            }
        }
        return n*/

        throw OperationNotSupportedException()
    }

    override fun visit(n: SelectionSP): Behaviour {
        return if (counter > 0) {
            counter--
            SelectionSP(n.continuation.accept(this), n.receiver, n.expression)
        } else generateAction(n.continuation)

    }

    fun addAction(processTerm: ProcessTerm, position: Int): ProcessTerm {
        counter = position
        val newMain = this.visit(processTerm)
        return ProcessTerm(processTerm.procedures, newMain)
    }

    private fun generateAction(continuation: Behaviour): Behaviour {
        val process = ('a'..'z').shuffled().first().toString()
        return ReceiveSP(continuation, process)

    }

    override fun visit(n: Network): Behaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ParallelNetworks): Behaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: TerminationSP): Behaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): Behaviour {
        throw OperationNotSupportedException()
    }
}