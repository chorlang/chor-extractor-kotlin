package ast.sp.interfaces

import ast.sp.labels.InteractionLabel
import java.util.*
import java.util.Objects
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User





abstract class ExtractionLabel (var flipped: Boolean){
    val diff = Random().nextInt()

    override fun equals(other: Any?): Boolean {
        if (other !is ExtractionLabel) return false

        return diff == other.diff
    }

    override fun hashCode(): Int {
        return Objects.hash(diff)
    }
}
