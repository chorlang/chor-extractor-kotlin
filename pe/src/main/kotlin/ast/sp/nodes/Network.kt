package ast.sp.nodes

import ast.sp.nodes.interfaces.SPNode
import org.apache.logging.log4j.LogManager
import java.util.logging.Logger
import kotlin.collections.HashMap

typealias ProcessName = String

data class Network(val processes: HashMap<ProcessName, ProcessTerm>) : SPNode {
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

    override fun equals(n: Any?): Boolean {
        if( this === n ) return true
        if( n !is Network ) return false

        return processes.equals(n.processes)
    }

    override fun hashCode(): Int {
        return processes.hashCode()
    }

    companion object {
        var i = 0
    }
}


