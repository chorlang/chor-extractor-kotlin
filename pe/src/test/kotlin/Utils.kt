import extraction.ExtractionStrategy

class Utils {
    companion object {
        fun parseStrategy(strategy: String): ExtractionStrategy {
            val s = ExtractionStrategy.values().find { it.name == strategy }
            return s ?: ExtractionStrategy.SelectionsFirst
        }
    }
}