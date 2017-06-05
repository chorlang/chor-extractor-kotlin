package ast.sp.interfaces

interface Behaviour : SPNode {
    fun findRecProcCall(procname: String): Boolean
}
