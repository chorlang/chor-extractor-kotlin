package ast.sp.interfaces

/**
 * Created by fmontesi on 03/04/17.
 */
interface SPNode {
    fun <T> accept(visitor: SPVisitor<T>): T
}

