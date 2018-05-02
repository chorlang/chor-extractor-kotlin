package ast.sp.nodes

import ast.sp.nodes.interfaces.Behaviour
import ast.sp.nodes.interfaces.ActionSP

/**
 * Created by fmontesi on 03/04/17.
 */
data class SendingSP(val continuation: Behaviour, val receiver: String, val expression: String) : ActionSP(receiver) {
    override fun toString(): String {
        return process + "!<" + expression + ">; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }

    override fun copy(): ActionSP {
        return SendingSP(continuation.copy(), ""+process, ""+expression)
    }

    override fun equals(b: Behaviour): Boolean {
        return b is SendingSP &&
                b.process == process &&
                b.expression == expression &&
                b.continuation.equals(continuation)
    }
}
