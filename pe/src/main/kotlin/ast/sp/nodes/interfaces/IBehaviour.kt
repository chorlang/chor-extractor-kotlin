package ast.sp.nodes.interfaces

interface IBehaviour : SPNode {
    fun copy(): IBehaviour
    override fun hashCode(): Int
}
