package ast.sp.nodes

import ast.sp.interfaces.SPNode
import ast.sp.interfaces.SPVisitor

class ParallelNetworks (val networkList: ArrayList<Network>): SPNode {
    override fun <T> accept(visitor: SPVisitor<T>): T = visitor.visit(this)

    override fun toString(): String {
        val builder = StringBuilder()
        for (network in networkList) builder.append("$network || ")
        builder.delete(builder.length-4, builder.length)
        return builder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if( this === other ) return true
        if( other !is ParallelNetworks ) return false

        val otherNetwork = other.networkList
        if (networkList.size != otherNetwork.size) return false

        return networkList.containsAll(otherNetwork) && otherNetwork.containsAll(networkList)
    }

    override fun hashCode(): Int {
        return networkList.hashCode()
    }
}