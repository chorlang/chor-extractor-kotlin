package util

import extraction.ExtractionStrategy

class TestUtils {
    companion object {
        fun parseStrategy(strategy: String): ExtractionStrategy {
            val s = ExtractionStrategy.values().find { it.name == strategy }
            return s ?: ExtractionStrategy.InteractionsFirst
        }
    }
}