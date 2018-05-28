package bench

import ce.ChoreographyExtraction
import np.NetworkProjection
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.results.format.ResultFormatType
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.runner.RunnerException



@Warmup(iterations = 3)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)


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

    @Benchmark
    fun sanitaryAgency(){
        val test =
                "citizen{" +
                        "def X{" +
                        "sanagency!<request>; sanagency?; sanagency!<provInf>; sanagency&{" +
                        "refusal: X, " +
                        "acceptance: coop?; bank!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency{" +
                        "def X{" +
                        "citizen?; citizen!<askInfo>; citizen?; if infoProved " +
                        "then citizen+acceptance; coop!<req>; bank!<paymentPublicFee>; bank?; X " +
                        "else citizen+refusal; X }" +
                        "main {X}} | " +
                        "coop{def X{" +
                        "sanagency?; " +
                        "if fine " +
                        "then citizen!<provT>; bank+recMoneyPossT; bank?; X " +
                        "else citizen!<provM>; bank+recMoneyPossM; bank?; X} " +
                        "main{X}} | " +
                        "bank{" +
                        "def X{ coop&{" +
                        "recMoneyPossT: coop!<paymentT>; Y, " +
                        "recMoneyPossM: coop!<paymentM>; Y}} " +
                        "def Y{citizen?; sanagency?; sanagency!<done>; X} " +
                        "main{X}}"

        val args = arrayOf("-c", test, "-l", "coop, bank")

        val actual = ChoreographyExtraction.main(args)
    }

    @Benchmark
    fun sanitaryAgency2x(){
        val test =
                "citizen{" +
                        "def X{" +
                        "sanagency!<request>; sanagency?; sanagency!<provInf>; sanagency&{" +
                        "refusal: X, " +
                        "acceptance: coop?; bank!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency{" +
                        "def X{" +
                        "citizen?; citizen!<askInfo>; citizen?; if infoProved " +
                        "then citizen+acceptance; coop!<req>; bank!<paymentPublicFee>; bank?; X " +
                        "else citizen+refusal; X }" +
                        "main {X}} | " +
                        "coop{def X{" +
                        "sanagency?; " +
                        "if fine " +
                        "then citizen!<provT>; bank+recMoneyPossT; bank?; X " +
                        "else citizen!<provM>; bank+recMoneyPossM; bank?; X} " +
                        "main{X}} | " +
                        "bank{" +
                        "def X{ coop&{" +
                        "recMoneyPossT: coop!<paymentT>; Y, " +
                        "recMoneyPossM: coop!<paymentM>; Y}} " +
                        "def Y{citizen?; sanagency?; sanagency!<done>; X} " +
                        "main{X}}" +
                        " | citizen2{" +
                        "def X{" +
                        "sanagency2!<request>; sanagency2?; sanagency2!<provInf>; sanagency2&{" +
                        "refusal: X, " +
                        "acceptance: coop2?; bank2!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency2{" +
                        "def X{" +
                        "citizen2?; citizen2!<askInfo>; citizen2?; if infoProved " +
                        "then citizen2+acceptance; coop2!<req>; bank2!<paymentPublicFee>; bank2?; X " +
                        "else citizen2+refusal; X }" +
                        "main {X}} | " +
                        "coop2{def X{" +
                        "sanagency2?; " +
                        "if fine " +
                        "then citizen2!<provT>; bank2+recMoneyPossT; bank2?; X " +
                        "else citizen2!<provM>; bank2+recMoneyPossM; bank2?; X} " +
                        "main{X}} | " +
                        "bank2{" +
                        "def X{ coop2&{" +
                        "recMoneyPossT: coop2!<paymentT>; Y, " +
                        "recMoneyPossM: coop2!<paymentM>; Y}} " +
                        "def Y{citizen2?; sanagency2?; sanagency2!<done>; X} " +
                        "main{X}}"

        val args = arrayOf("-c", test, "-l", "coop, bank, coop2, bank2")

        val actual = ChoreographyExtraction.main(args)
    }
}