package bench

import ce.ChoreographyExtraction
import np.NetworkProjection
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.results.format.ResultFormatType
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.runner.RunnerException



@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)


open class BenchTest{
    @Benchmark
    fun nbtst1(){
        val test = "a {def X " +
                "{if e " +
                    "then b+win; c+lose; b?; c?; d!<free>; X " +
                    "else b+lose; c+win; b?; c?; d!<free>; X} " +
                "main {X}} |" +
                "b {def X " +
                    "{a&{" +
                    "win: c!<lose>; a!<sig>; X, " +
                    "lose: c?; a!<sig>; X}} " +
                    "main {X}} |" +
                "c {def X " +
                    "{d!<busy>; a&{" +
                    "win: b!<lose>; a!<msg>; X, " +
                    "lose: b?; a!<msg>; X}} " +
                    "main {X}} |" +
                "d {def X " +
                    "{c?; a?; X} " +
                    "main {X}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Benchmark
    fun tst(){
        val args = arrayOf("-g", "50", "10")
        NetworkProjection.main(args)
    }
}