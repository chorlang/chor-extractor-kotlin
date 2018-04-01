package bench

import extraction.ChoreographyExtraction
import org.openjdk.jmh.annotations.*

@BenchmarkMode(Mode.All)
@Warmup(iterations = 1)
@Measurement(iterations = 1, batchSize = 10)
open class BenchTest{
    @Benchmark
    fun tst(){
        ChoreographyExtraction("p {main{stop}} | q {main{stop}}").extractChoreography()
    }
}