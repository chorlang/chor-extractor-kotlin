package ast.sp.nodes.interfaces

interface Behaviour : SPNode {
    fun findRecProcCall(procname: String): Boolean
    fun copy(): Behaviour
    fun equals(b: Behaviour): Boolean
}
