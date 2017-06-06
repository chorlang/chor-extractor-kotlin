package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction

/**
 * Created by fmontesi on 03/04/17.
 */
data class Sending(val continuation: Behaviour, val process: String, val expression: String) : Interaction {

    override fun toString(): String {
        return process + "!<" + expression + ">; " + continuation.toString()
    }

    override fun equals(ss: Any?): Boolean {
        if (ss is Sending) {
            val s = ss
            return this.process == s.process &&
                    this.expression == s.expression &&
                    this.continuation == s.continuation
        }
        return false
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }
}