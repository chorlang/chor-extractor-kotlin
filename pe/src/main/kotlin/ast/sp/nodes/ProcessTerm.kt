package ast.sp.nodes

import ast.sp.interfaces.Behaviour
import ast.sp.interfaces.SPNode
import ast.sp.interfaces.SPVisitor

typealias ProcedureName = String

class ProcessTerm(val procedures: HashMap<ProcedureName, Behaviour>, var main: Behaviour) : SPNode {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        procedures.forEach { t, u -> builder.append("def $t{$u} ")}
        builder.append("main {$main}}")

        return builder.toString()
    }

    fun copy(): ProcessTerm{
        val proceduresCopy = HashMap<String, Behaviour>()
        procedures.forEach{ proceduresCopy[it.key] = it.value.copy() }
        return ProcessTerm(proceduresCopy, main.copy())
    }

    override fun equals(other: Any?): Boolean {
        if( this === other ) return true
        if( other !is ProcessTerm ) return false

        if (main != other.main) return false

        for (procedure in procedures){
            val procedureBehaviour = other.procedures[procedure.key]
            if (procedureBehaviour === null || procedureBehaviour != procedure.value) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = procedures.hashCode()
        result = 31 * result + main.hashCode()
        return result
    }
}
