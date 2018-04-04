package bench

import ce.ChoreographyExtraction
import org.openjdk.jmh.annotations.*

@BenchmarkMode(Mode.All)
@Warmup(iterations = 1)
@Measurement(iterations = 1, batchSize = 10)
@Fork(1)
open class BenchTest{
    @Benchmark
    fun tst(){
        ChoreographyExtraction.extractChoreography("p {main{stop}} | q {main{stop}}")
    }
}