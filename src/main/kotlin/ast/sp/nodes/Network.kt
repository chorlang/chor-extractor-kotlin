package ast.sp.nodes

import ast.sp.interfaces.SPNode
import ast.sp.interfaces.SPVisitor
import kotlin.collections.HashMap

typealias ProcessName = String

data class Network(val processes: HashMap<ProcessName, ProcessTerm>) : SPNode {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val builder = StringBuilder()
        processes.forEach { t, u -> builder.append(t).append(u.toString()).append(" | ")}
        if (builder.length >= 3) { builder.delete(builder.length - 3, builder.length) }
        return builder.toString()
    }

    fun copy(): Network{
        val temp = HashMap<String, ProcessTerm>()
        for (p in processes){
            temp[p.key] = p.value.copy()
        }

        return Network(temp)
    }

    override fun equals(other: Any?): Boolean {
        if( this === other ) return true
        if( other !is Network ) return false

        val otherProcesses = other.processes

        if (processes.size != otherProcesses.size) return false

        for (process in processes){
            val otherProcess = otherProcesses[process.key]
            if (otherProcess == null || otherProcess != process.value) return false
        }

        return true
    }

    override fun hashCode(): Int {
        //return processesInChoreography.hashCode()
        var hash = 0
        for (p in processes.toSortedMap()) {
            hash += p.key.hashCode() * 31 + p.value.hashCode() * 29
        }
        return hash
    }

    companion object { var printMainOnly = false }
}


