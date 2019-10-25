package util

import extraction.Extraction
import extraction.ExtractionStrategy

class TestUtils {
    companion object {
        fun parseStrategy(strategy: String): ExtractionStrategy {
            val s = ExtractionStrategy.values().find { it.name == strategy }
            return s ?: ExtractionStrategy.InteractionsFirst
        }

        fun printExtractionResult(network:String, choreography:String, expected:String) {
            if ( expected != choreography ) {
                println("ERROR")
            } else {
                printExtractionResult(network, choreography)
            }
        }

        fun printExtractionResult(network:String, choreography:String) {
            println("Input network:\n\t$network\nOutput choreography:\n\t$choreography")
        }

        fun runExtractionTest(network:String, vararg services:String) {
            printExtractionResult(network, Extraction.extractChoreography(network, ExtractionStrategy.Default, services.toMutableList()).toString())
        }

        fun runSequentialExtractionTest(network:String, vararg services:String) {
            printExtractionResult(network, Extraction.extractChoreographySequentially(network, ExtractionStrategy.Default, services.toMutableList()).toString())
        }
    }
}