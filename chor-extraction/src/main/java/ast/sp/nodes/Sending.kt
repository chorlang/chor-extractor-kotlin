package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction

/**
 * Created by fmontesi on 03/04/17.
 */
data class Sending(val continuation: Behaviour, val pr: String, val expression: String) : Interaction(pr) {
    override fun toString(): String {
        return process + "!<" + expression + ">; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): Interaction {
        return Sending(continuation.copy(), ""+process, ""+expression)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is Sending &&
                b.process == process &&
                b.expression == expression &&
                b.continuation.equals(continuation)
    }
}
