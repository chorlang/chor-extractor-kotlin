package ast.sp.interfaces

interface IBehaviour : SPNode {
    fun copy(): IBehaviour
    override fun hashCode(): Int
}
