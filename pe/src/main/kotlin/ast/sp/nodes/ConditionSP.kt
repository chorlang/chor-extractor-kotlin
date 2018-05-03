package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

data class ConditionSP(val expression: String, val thenBehaviour: IBehaviour, val elseBehaviour: IBehaviour) : IBehaviour {
    override fun toString(): String {
        return "if " +  expression + " then " + thenBehaviour.toString() + " else " + elseBehaviour.toString()
    }

    override fun copy(): IBehaviour {
        return ConditionSP(""+expression, thenBehaviour.copy(), elseBehaviour.copy())
    }

    override fun equals(b: IBehaviour): Boolean {
        if (b is ConditionSP){
            return expression == b.expression &&
                    thenBehaviour.equals(b.thenBehaviour) &&
                    elseBehaviour.equals(b.elseBehaviour)
        }
        else return false
    }
}
