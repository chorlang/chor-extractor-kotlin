package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.InteractionSP

data class Receiving(val continuation: Behaviour, val pr: String) : InteractionSP(pr) {
    override fun toString(): String {
        return process + "?; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): InteractionSP {
        return Receiving(continuation.copy(), ""+process)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is Receiving &&
                b.process == process &&
                b.continuation.equals(continuation)
    }
}
