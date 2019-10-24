package fuzz

import org.junit.jupiter.api.Test
import util.fuzzing.NetworkFuzzer


class FuzzTest {
    companion object {
        private fun testFuzz(orig:String, dels:Int, swaps:Int) {
            val fuzzed1 = NetworkFuzzer.fuzz(orig, 1, 0)
            val fuzzed2 = NetworkFuzzer.fuzz(orig, 0, 1)
            val fuzzed3 = NetworkFuzzer.fuzz(orig, dels, swaps)
            println("Original: $orig\nFuzzed(1,0): $fuzzed1\nFuzzed(0,1): $fuzzed2\nFuzzed($dels,$swaps):$fuzzed3")
        }
    }

    @Test
    fun ex2() {
        val orig = "c { def X {a!<pwd>; a&{ok: s?; stop, ko: X}} main {X}} | " +
                "a { def X {c?; s?; if s then c+ok; s+ok; stop else c+ko; s+ko; X} main {X}} | " +
                "s { def X {a!<s>; a&{ok: c!<t>; stop, ko:X}} main {X}}"
        testFuzz(orig, 2, 1)
    }

    @Test
    fun ex4() {
        val test = "p { def X {q!<e1>; X} main {X}} | " +
                "q { def Y {p?; Y} main {Y}} | " +
                "r { def Z {s!<e2>; Z} main {Z}} | " +
                "s { def W {r?; W} main {W}}"
        testFuzz(test, 1, 1)
    }

    @Test
    fun l1() {
        val test = "p { def X {q!<e1>; q!<e2>; q!<e3>; q!<e4>; q!<e5>; X} main {X}} | " +
                "q { def Y {1?; 2?; q!<e1>; 3?; q!<e2>; Y} main {p?; Y}}"
        testFuzz(test, 2, 2)
    }

    @Test
    fun buyerSeller() {
        val test =
                "buyer{main{seller!<quote>; seller?; if ok then seller+accept; seller?; stop else seller+reject; stop}} | " +
                        "shipper{main{seller&{" +
                        "send: seller?; seller!<t>; stop," +
                        "wait: stop}}} | " +
                        "seller{main{buyer?; buyer!<quote>; buyer&{" +
                        "accept: shipper+send; shipper!<deliv>; shipper?; buyer!<details>; stop, " +
                        "reject: shipper+wait; stop}}}"
        testFuzz(test, 2, 2)
    }

    @Test
    fun buyerSellerRec(){
        val test =
                "buyer{def X {seller?; if ok then seller+accept; seller?; stop else seller+reject; X} main {seller!<quote>; X}} | " +
                        "shipper{def X {seller&{" +
                        "send: seller?; seller!<t>; stop," +
                        "wait: X}} main {X}} | " +
                        "seller{def X {buyer!<quote>; buyer&{" +
                        "accept: shipper+send; shipper!<deliv>; shipper?; buyer!<details>; stop, " +
                        "reject: shipper+wait; X}} main {buyer?; X}}"

        testFuzz(test, 2, 3)
    }

    @Test
    fun instrumentControlling() {
        val test =
                "user{def X{instrument+move; instrument+photo; instrument+quit; stop} " +
                        "main {operator!<high>; operator&{" +
                        "ok: X," +
                        "no: stop}}} | " +
                        "operator{main{user?; if ok then user+ok; instrument+ok; stop else user+no; instrument+no; stop}} | " +
                        "instrument{def X{user&{" +
                        "move: X," +
                        "photo: X," +
                        "quit: stop}} main{ operator&{" +
                        "ok: X, " +
                        "no: stop}}}"

        testFuzz(test, 2, 2)
    }
}