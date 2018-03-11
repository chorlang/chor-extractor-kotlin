package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction

data class Receiving(val continuation: Behaviour, val process: String) : Interaction {
    override fun toString(): String {
        return process + "?; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): Interaction {
        return Receiving(continuation.copy(), ""+process)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is Receiving &&
                b.process == process &&
                b.continuation == continuation
    }
}
