package ast.sp.nodes

import ast.sp.interfaces.Behaviour

data class ConditionSP(val process: String, val expression: String, val thenBehaviour: Behaviour, val elseBehaviour: Behaviour) : Behaviour {
    override fun toString(): String {
        return "if " + process + "." + expression + " then " + thenBehaviour.toString() + " else " + elseBehaviour.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return thenBehaviour.findRecProcCall(procname) || elseBehaviour.findRecProcCall(procname)
    }

    override fun copy(): Behaviour {
        return ConditionSP(process, expression, thenBehaviour.copy(), elseBehaviour.copy())
    }
}
