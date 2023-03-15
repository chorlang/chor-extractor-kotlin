package ast.cc.interfaces

/**
 * Created by fmontesi on 03/04/17.
 */
interface CCNode {
    fun <T> accept(visitor: CCVisitor<T>): T
}
