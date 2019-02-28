package ast.cc.interfaces

abstract class ChoreographyBody : CCNode {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ChoreographyBody

        return other.toString() == toString()
    }

    override fun hashCode(): Int = toString().hashCode()
}
