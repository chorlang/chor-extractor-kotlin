package ast.sp.nodes

import ast.sp.interfaces.Behaviour

data class ConditionSP(val expression: String, val thenBehaviour: Behaviour, val elseBehaviour: Behaviour) : Behaviour {
    override fun toString(): String {
        return "if " +  expression + " then " + thenBehaviour.toString() + " else " + elseBehaviour.toString()
    }

    override fun findRecProcCall(procname: String): Boolean {
        return thenBehaviour.findRecProcCall(procname) || elseBehaviour.findRecProcCall(procname)
    }

    override fun copy(): Behaviour {
        return ConditionSP(expression, thenBehaviour.copy(), elseBehaviour.copy())
    }

    override fun equals(b: Behaviour): Boolean {
        if (b is ConditionSP){
            return expression == b.expression &&
                    thenBehaviour.equals(b.thenBehaviour) &&
                    elseBehaviour.equals(b.elseBehaviour)
        }
        else return false
    }
}
