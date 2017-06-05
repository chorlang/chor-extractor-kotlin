package ast.cc.interfaces

import ast.cc.CCVisitor
import epp.MergingException

/**
 * Created by fmontesi on 03/04/17.
 */
interface CCNode {
    @Throws(MergingException::class)
    fun <T> accept(visitor: CCVisitor<T>): T
}
