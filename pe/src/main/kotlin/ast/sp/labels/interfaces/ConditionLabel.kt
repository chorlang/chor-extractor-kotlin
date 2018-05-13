package ast.sp.labels.interfaces

abstract class ConditionLabel(open val process: String, open val expression: String, flipped: Boolean) : ExtractionLabel(flipped) {
    abstract override fun equals(other: Any?): Boolean
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + flipped.hashCode()
        return result
    }
}
