package ast.sp.nodes.interfaces

interface IBehaviour : SPNode {
    fun copy(): IBehaviour
    fun equals(b: IBehaviour): Boolean
}
