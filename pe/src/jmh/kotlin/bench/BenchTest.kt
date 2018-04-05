package bench

import np.NetworkProjection
import org.openjdk.jmh.annotations.*

@BenchmarkMode(Mode.All)
@Warmup(iterations = 1)
@Measurement(iterations = 1, batchSize = 10)
open class BenchTest{
    @Benchmark
    fun tst(){
        val args = arrayOf("-g", "50", "10")
        NetworkProjection.main(args)
    }
}