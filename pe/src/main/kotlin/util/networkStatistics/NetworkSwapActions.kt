package util.networkStatistics

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPVisitor
import ast.sp.nodes.*
import javax.naming.OperationNotSupportedException

class NetworkSwapActions : SPVisitor<Behaviour> {
    private var counter: Int = 0

    override fun visit(n: ConditionSP): Behaviour {
        throw OperationNotSupportedException()
    }

    override fun visit(n: ProcessTerm): Behaviour {
        return n.main.accept(this)
    }

    override fun visit(n: ReceiveSP): Behaviour {
        return if (counter > 0) {
            counter--
            ReceiveSP(n.continuation.accept(this), n.sender)

        } else n.continuation
    }

    override fun visit(n: SendSP): Behaviour {
        return if (counter > 0) {
            counter--
            SendSP(n.continuation.accept(this), n.receiver, n.expression)
        } else n.continuation
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
            SelectionSP(n.continuation.accept(this), n.receiver, n.label)

        } else n.continuation
    }

    fun swapActions(processTerm: ProcessTerm, position: Int): ProcessTerm {
        counter = position
        val newMain = this.visit(processTerm)
        return ProcessTerm(processTerm.procedures, newMain)
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