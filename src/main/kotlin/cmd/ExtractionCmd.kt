package cmd

import cmd.internal.Benchmarks
import cmd.internal.LangeTuostoYoshida15
import cmd.internal.LangeTuostoYoshida15Sequential
import cmd.internal.CruzFilipeLarsenMontesi17
import extraction.Extraction

object ExtractionCmd {
    private data class Command(val runnable:() -> Unit, val description:String)

    private val commands = mapOf(
            "help" to Command({ printHelp() }, "Print this help"),
            "theory" to Command({ runTests(CruzFilipeLarsenMontesi17) }, "Run the tests from the original theoretical paper [Cruz-Filipe, Larsen, Montesi @ FoSSaCS 2017]"),
            "lty15" to Command({ runTests(LangeTuostoYoshida15) }, "Run the tests from the paper [Lange, Tuosto, Yoshida @ POPL 2015]"),
            "lty15-seq" to Command({ runTests(LangeTuostoYoshida15Sequential) }, "Run the tests from the paper [Lange, Tuosto, Yoshida @ POPL 2015] *with parallelisation disabled*"),
            "benchmark" to Command({ runBenchmarks() }, "Run the extraction benchmarking suite (Warning: this takes a *long* time, possibly days or weeks depending on your hardware)"),
            "bisimcheck" to Command({ runBisimilarity() }, "Check that the choreographies extracted in the benchmark are correct, i.e., they are bisimilar to the respective originals (Warning: this takes a *long* time, possibly days or weeks depending on your hardware)")
    )

    @JvmStatic
    fun main(args:Array<String>) {
        if ( args.isEmpty() ) {
            printHelp()
        } else {
            commands.getOrElse( args[0], {
                println("Couldn't recognise argument: ${args[0]}")
                commands["help"]
            })!!.runnable()
        }
    }

    private fun printHelp() {
        println("List of available commands (<name of command>\t<description>)")
        commands.forEach { (key, command) ->
            println("\t$key\t\t${command.description}")
        }
    }

    private fun warmup() {
        val test = "c { def X {a!<pwd>; a&{ok: s?; stop, ko: X}} main {X}} | " +
                "a { def X {c?; s?; if s then c+ok; s+ok; stop else c+ko; s+ko; X} main {X}} | " +
                "s { def X {a!<s>; a&{ok: c!<t>; stop, ko:X}} main {X}}"
        Extraction.extractChoreography(test).toString()
    }

    private fun runTests(obj:Any) {
        warmup()
        obj.javaClass.declaredMethods.forEach {
            val t1 = System.currentTimeMillis()
            println("Running $it")
            it.invoke(obj)
            println("Elapsed time: ${System.currentTimeMillis() - t1}ms\n")
        }
    }

    private fun runBenchmarks() {
        warmup()
        println("=== Extracting all test networks from directory tests ===\n")
        Benchmarks.extractionTest()
    }

    private fun runBisimilarity() {
        warmup()
        println("=== Checking that all extracted choreographies are correct, using bisimilarity ===\n")
        Benchmarks.extractionSoundness()
    }
}
