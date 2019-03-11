import extraction.Extraction
import extraction.Strategy

class Utils {
    companion object {
        fun parseStrategy(strategy: String): Strategy {
            val s = Strategy.values().find { it.name == strategy }
            return s ?: Strategy.SelectionFirst
        }
    }
}