package ast.sp.interfaces

interface Behaviour : SPNode {
    fun copy(): Behaviour
    override fun hashCode(): Int
}
