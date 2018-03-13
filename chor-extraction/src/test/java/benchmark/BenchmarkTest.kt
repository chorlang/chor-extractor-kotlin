package benchmark

import extraction.ChoreographyExtraction
import extraction.InteractionTest
import org.junit.Test
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

@BenchmarkMode(Mode.All)
@Warmup(iterations = 10)
@Measurement(iterations = 100, batchSize = 10)
open class BenchmarkTest {

    val str = "p { main {q!<e>; q?; stop}} " +
            "| q { main {p?; p!<u>; stop}} " +
            "| r { main {stop}}"

    @Test
    @Benchmark
    fun interactiontest() {
        ChoreographyExtraction(str).extractChoreography()

    }
}
