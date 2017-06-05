package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.Interaction

data class SelectionSP(val continuation: Behaviour, val process: String, val expression: String) : Interaction {

    override fun toString(): String {
        return process + " + " + expression + "; " + continuation.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return continuation.findRecProcCall(procname)
    }
}
