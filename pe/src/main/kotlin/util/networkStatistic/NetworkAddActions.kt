package util.networkStatistic

import ast.sp.interfaces.IBehaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkAddActions : SPVisitor<IBehaviour> {
    private var counter: Int = 0

    override fun visit(n: ConditionSP): IBehaviour {
        /*val numOfProcesses = NetworkProcessActions().visit(n.thenBehaviour)
        return when {
            counter > numOfProcesses -> {
                counter -= numOfProcesses
                ConditionSP(n.expression, n.thenBehaviour, n.elseBehaviour.accept(this))
            }
            else -> ConditionSP(n.expression, n.thenBehaviour.accept(this), n.elseBehaviour)
        }*/
        throw OperationNotSupportedException()

    }

    override fun visit(n: ProcessTerm): IBehaviour {
        return n.main.accept(this)
    }

    override fun visit(n: ReceiveSP): IBehaviour {
        return if (counter > 0) {
            counter--
            ReceiveSP(n.continuation.accept(this), n.sender)

        } else generateAction(n.continuation)
    }

    override fun visit(n: SendingSP): IBehaviour {
        return if (counter > 0) {
            counter--
            SendingSP(n.continuation.accept(this), n.receiver, n.expression)
        } else generateAction(n.continuation)
    }

    override fun visit(n: OfferingSP): IBehaviour {
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

    override fun visit(n: SelectionSP): IBehaviour {
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

    private fun generateAction(continuation: IBehaviour): IBehaviour {
        val process = ('a'..'z').shuffled().first().toString()
        return ReceiveSP(continuation, process)

    }

    override fun visit(n: Network): IBehaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: Behaviour): IBehaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ParallelNetworks): IBehaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: TerminationSP): IBehaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcedureInvocationSP): IBehaviour {
        throw OperationNotSupportedException()
    }
}