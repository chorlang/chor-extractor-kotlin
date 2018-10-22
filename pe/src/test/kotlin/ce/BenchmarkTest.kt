package ce

import Utils.Companion.resolveArgs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class BenchmarkTest {
    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun runningExample(strategy: String, debugMode: Boolean) {
        val test =
                "a {def Y {c?; d!<free>; X} def X {if e then b+win; c+lose; b?; Y else b+lose; c+win; b?; Y} main {X}} |" +
                        "b {def X {a&{win: a!<sig>; X, lose: a!<sig>; X}} main {X}} |" +
                        "c {def X {d!<busy>; a&{win: a!<msg>; X, lose: a!<msg>; X}} main {X}} |" +
                        "d {def X {c?; a?; X} main {X}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.add(test)
        val strategy = param.second
        val actual = ChoreographyExtraction.main(args).toString()

        when (strategy) {
            Strategy.SelectionFirst, Strategy.ConditionFirst, Strategy.UnmarkedFirst, Strategy.UnmarkedThenCondition, Strategy.UnmarkedThenSelection -> {
                val expected =
                        "def X1 { if a.e then a->b[win]; c.busy->d; a->c[lose]; b.sig->a; c.msg->a; a.free->d; X1 else a->b[lose]; c.busy->d; a->c[win]; b.sig->a; c.msg->a; a.free->d; X1 } main {X1}"

                assertEquals(expected, actual)
            }
            Strategy.RandomProcess, Strategy.UnmarkedThenRandom -> {
                /* value expected =
                        "def X1 { c.busy->d; if a.e then a->b[win]; a->c[lose]; b.sig->a; c.msg->a; a.free->d; X1 else a->b[lose]; a->c[win]; b.sig->a; c.msg->a; a.free->d; X1 } main {X1}"

                assertEquals(expected, actual) */
            }

        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun runningExample2x(strategy: String, debugMode: Boolean) {

        val test =
                "a1 {def X {if e then b1+win; c1+lose; b1?; c1?; d1!<free>; X else b1+lose; c1+win; b1?; c1?; d1!<free>; X} main {X}} |" +
                        "b1 {def X {a1&{win: c1!<lose>; a1!<sig>; X, lose: c1?; a1!<sig>; X}} main {X}} |" +
                        "c1 {def X {d1!<busy>; a1&{win: b1!<lose>; a1!<msg>; X, lose: b1?; a1!<msg>; X}} main {X}} |" +
                        "d1 {def X {c1?; a1?; X} main {X}} | " +
                        "a2 {def X {if e then b2+win; c2+lose; b2?; c2?; d2!<free>; X else b2+lose; c2+win; b2?; c2?; d2!<free>; X} main {X}} |" +
                        "b2 {def X {a2&{win: c2!<lose>; a2!<sig>; X, lose: c2?; a2!<sig>; X}} main {X}} |" +
                        "c2 {def X {d2!<busy>; a2&{win: b2!<lose>; a2!<msg>; X, lose: b2?; a2!<msg>; X}} main {X}} |" +
                        "d2 {def X {c2?; a2?; X} main {X}} "

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        val strategy = param.second
        args.add(test)

        val actual = ChoreographyExtraction.main(args).toString()

        when (strategy) {
            Strategy.SelectionFirst -> {
                val expected =
                        "def X1 { if a1.e then if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; a1->c1[lose]; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c1.busy->d1; c2.busy->d2; a1->b1[win]; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else c1.busy->d1; a1->b1[lose]; a1->c1[win]; c2.busy->d2; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; a1->c1[lose]; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c1.busy->d1; c2.busy->d2; a1->b1[win]; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else c1.busy->d1; a1->b1[lose]; a1->c1[win]; c2.busy->d2; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; a1->c1[win]; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c1.busy->d1; a1->b1[win]; a1->c1[lose]; c2.busy->d2; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else c1.busy->d1; c2.busy->d2; a1->b1[lose]; a1->c1[win]; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; a1->c1[win]; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c1.busy->d1; a1->b1[win]; a1->c1[lose]; c2.busy->d2; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 else c1.busy->d1; c2.busy->d2; a1->b1[lose]; a1->c1[win]; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; X1 } main {X1}"

                assertEquals(expected, actual)
            }
            Strategy.ConditionFirst -> {
                //if it doesn't fail then we are happy
            }
            Strategy.UnmarkedFirst -> {
                val expected =
                        "def X1 { c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; X2 } def X10 { c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; X11 } def X11 { c1.lose->b1; X12 } def X12 { b2.sig->a2; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b1.lose->c1; X12 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b1.lose->c1; X12 else c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; X11 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; X10 } def X2 { b1.lose->c1; X3 } def X3 { b2.sig->a2; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; X2 else c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; c1.lose->b1; X3 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; c1.lose->b1; X3 } def X4 { c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; X5 } def X5 { b1.lose->c1; X6 } def X6 { b2.sig->a2; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; X5 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; X4 else c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; c1.lose->b1; X6 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; c1.lose->b1; X6 } def X7 { c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; X8 } def X8 { c1.lose->b1; X9 } def X9 { b2.sig->a2; b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b1.lose->c1; X9 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b1.lose->c1; X9 else c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; X7 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; X8 } main {if a1.e then if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; X4 else if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; X7 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; X10}"

                assertEquals(expected, actual)
            }
            Strategy.UnmarkedThenCondition -> {
                val expected =
                        "def X1 { c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; X2 } def X10 { c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; X11 } def X11 { c1.lose->b1; X12 } def X12 { b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b1.lose->c1; X12 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b1.lose->c1; X12 else b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; X11 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; X10 } def X2 { b1.lose->c1; X3 } def X3 { b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; X2 else b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; c1.lose->b1; X3 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; c1.lose->b1; X3 } def X4 { c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; X5 } def X5 { b1.lose->c1; X6 } def X6 { b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; X5 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; X4 else b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; c1.lose->b1; X6 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; c1.lose->b1; X6 } def X7 { c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; X8 } def X8 { c1.lose->b1; X9 } def X9 { b1.sig->a1; c1.msg->a1; a1.free->d1; if a1.e then b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b1.lose->c1; X9 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b1.lose->c1; X9 else b2.sig->a2; c2.msg->a2; a2.free->d2; if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; X7 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; X8 } main {if a1.e then if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; X4 else if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; X7 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; X10}"

                assertEquals(expected, actual)
            }
            Strategy.UnmarkedThenSelection -> {
                val expected =
                        "def X1 { if a1.e then if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[lose]; b2.lose->c2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[win]; c2.busy->d2; a1->c1[lose]; a2->c2[win]; c2.lose->b2; b1.lose->c1; b1.sig->a1; c1.msg->a1; a1.free->d1; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else if a2.e then a2->b2[win]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[lose]; b2.lose->c2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else a2->b2[lose]; c1.busy->d1; a1->b1[lose]; c2.busy->d2; a1->c1[win]; a2->c2[win]; c2.lose->b2; c1.lose->b1; b1.sig->a1; c1.msg->a1; a1.free->d1; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 } main {X1}"

                assertEquals(expected, actual)
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun twoBit(strategy: String, debugMode: Boolean) {
        val test =
                "a { def X {b?; b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        val strategy = param.second
        args.add(test)

        val actual = ChoreographyExtraction.main(args).toString()

        when (strategy) {
            Strategy.UnmarkedThenRandom, Strategy.RandomProcess -> {
                //Random result, can't assert
            }
            Strategy.LengthFirst -> {
                val expected = "def X1 { (b.ack0->a, a.1->b); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"
                assertEquals(expected, actual)
            }
            Strategy.ShortestFirst -> {
                val expected = "def X1 { (a.1->b, b.ack0->a); (b.ack1->a, a.0->b); X1 } main {a.0->b; X1}"
                assertEquals(expected, actual)
            }
            else -> {
                val expected = "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"
                assertEquals(expected, actual)
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun twoBit2x(strategy: String, debugMode: Boolean) {
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}} | " +
                        "c { def X {d?;d!<0>;d?;d!<1>;X} main {d!<0>;d!<1>;X}} | " +
                        "d { def Y {c?;c!<ack0>;c?;c!<ack1>; Y} main {Y}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        val strategy = param.second
        args.add(test)

        val actual = ChoreographyExtraction.main(args).toString()

        when (strategy) {
            Strategy.SelectionFirst, Strategy.ConditionFirst -> {
                val expected =
                        "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); (c.1->d, d.ack0->c); (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); (c.0->d, d.ack1->c); X1 } main {a.0->b; c.0->d; X1}"

                assertEquals(expected, actual)
            }

            Strategy.UnmarkedFirst, Strategy.UnmarkedThenCondition, Strategy.UnmarkedThenSelection -> {
                val expected =
                        "def X1 { (a.1->b, b.ack0->a); (c.1->d, d.ack0->c); (a.0->b, b.ack1->a); (c.0->d, d.ack1->c); X1 } main {a.0->b; c.0->d; X1}"


                assertEquals(expected, actual)
            }

        }
    }

    /*@Test //(expected = NetworkExtraction.MulticomException::class)
    fun threeBit() {
        val test =
                "a { def X {b?; b!<0>;b?;b!<1>;b?;b!<2>;X} main {b!<0>;b!<1>;b!<2>; X}} | " +
                        "b { def Y {a!<ack0>;a?;a!<ack1>;a?;a!<ack2>;a?;Y} main {a?;Y}}"

        val args = arrayListOf("-c", test)

        assertThrows(NetworkExtraction.MulticomException::class.java
        ) { ChoreographyExtraction.main(args) }
    }*/

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun bargain(strategy: String, debugMode: Boolean) {
        val test =
                "a { def X {if notok then b+hag; b?; X else b+happy; c!<info>; stop} main {X}} | " +
                "b { def Y {a&{hag: a!<price>; Y, happy: stop}} main {Y}} | " +
                "c { main {a?; stop}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "c"))

        //value strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { if a.notok then a->b[hag]; b.price->a; X1 else a->b[happy]; a.info->c; stop } main {X1}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun bargain2x(strategy: String, debugMode: Boolean) {
        val test =
                "a { def X {b!<hag>; b?; if price then b+deal; b!<happy>; c!<info>; X else b+nodeal; X} main {X}} | " +
                        "b { def Y {a?; a!<price>; a&{deal: a?; Y, nodeal: Y}} main {Y}} | " +
                        "c { def Z {a?; Z} main {Z}} | " +
                        "d { def X {e!<hag>; e?; if price then e+deal; e!<happy>; f!<info>; X else e+nodeal; X} main {X}} | " +
                        "e { def Y {d?; d!<price>; d&{deal: d?; Y, nodeal: Y}} main {Y}} | " +
                        "f { def Z {d?; Z} main {Z}}"


        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "c, f"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        //assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun health(strategy: String, debugMode: Boolean) {
        val test =
                "hs{def X{p?; ss!<subscribed>; ss&{" +
                        "ok: p+subscribed; as!<account>; as?; t!<fwd>; t?; X, " +
                        "nok: p+notSubscribed; X}} main{X}} | " +
                        "p{def X{hs!<sendData>; hs&{subscribed: es?; X, notSubscribed: X}} main{X}} | " +
                        "ss{def X{hs?; if ok then hs+ok; X else hs+nok; X} main{X}} | " +
                        "as{def X{hs?; hs!<logCreated>; X} main{X}} | " +
                        "t{def X{hs?; hs!<fwdOk>; es!<helpReq>; X} main{X}} | " +
                        "es{def X{t?; p!<provideService>; X} main{X}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "as, t, es"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { p.sendData->hs; X2 } def X2 { hs.subscribed->ss; if ss.ok then ss->hs[ok]; hs->p[subscribed]; hs.account->as; as.logCreated->hs; hs.fwd->t; t.fwdOk->hs; t.helpReq->es; es.provideService->p; p.sendData->hs; X2 else ss->hs[nok]; hs->p[notSubscribed]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun filter(strategy: String, debugMode: Boolean) {
        val test =
                "filter {" +
                        "def X {data!<newFilterRequest>; Y} " +
                        "def Y {data&{" +
                        "item: data?; if itemToBeFiltered then data!<ok>; Y else data!<remove>; Y," +
                        "noItem: X}}" +
                        "main {X}} | " +
                        "data {" +
                        "def X {filter?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter+item; filter!<itemToBeFiltered>; filter?; Y " +
                        "else filter+noItem; X} " +
                        "main {X}}"


        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        val strategy = param.second
        args.add(test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected =
                "def X1 { filter.newFilterRequest->data; X2 } def X2 { if data.itemToBeFiltered then data->filter[item]; data.itemToBeFiltered->filter; if filter.itemToBeFiltered then filter.ok->data; X2 else filter.remove->data; X2 else data->filter[noItem]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun filter2x(strategy: String, debugMode: Boolean) {
        val test =
                "filter1 {" +
                        "def X {data1!<newFilterRequest>; Y} " +
                        "def Y {data1&{" +
                        "item: data1?; if itemToBeFiltered then data1!<ok>; Y else data1!<remove>; Y," +
                        "noItem: data1?; X}}" +
                        "main {X}} | " +
                        "data1 {" +
                        "def X {filter1?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter1+item; filter1!<itemToBeFiltered>; filter1?; Y " +
                        "else filter1+noItem; filter1!<noMoreItems>; X} " +
                        "main {X}} | " +
                        "filter2 {" +
                        "def X {data2!<newFilterRequest>; Y} " +
                        "def Y {data2&{" +
                        "item:  data2?; if itemToBeFiltered then data2!<ok>; Y else data2!<remove>; Y," +
                        "noItem: data2?; X}}" +
                        "main {X}} | " +
                        "data2 {" +
                        "def X {filter2?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter2+item; filter2!<itemToBeFiltered>; filter2?; Y " +
                        "else filter2+noItem; filter2!<noMoreItems>; X} " +
                        "main {X}}"


        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        val strategy = param.second
        args.add(test)

        val actual = ChoreographyExtraction.main(args).toString()

        //assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun logistic(strategy: String, debugMode: Boolean) {
        val test =
                "supplier {" +
                        "def X {shipper?; Y} " +
                        "def Y {if needToShip " +
                        "then shipper+item; X " +
                        "else shipper+done; retailer!<UpdatePOandDeliverySchedule>; retailer?; retailer?; retailer!<FinalizedPOandDeliverySchedule>; stop}" +
                        "main { retailer!<PlannedOrderVariations>; retailer?; retailer?; Y}" + "} | " +
                        "retailer {" +
                        "main {" +
                        "supplier?; supplier!<OrderDeliveryVariations>; supplier!<DeliverCheckPointRequest>; " +
                        "supplier?; supplier!<POandDeliveryScheduleMods>; shipper!<ConfirmationofDeliverySchedule>; " +
                        "supplier!<AcceptPOandDeliverySchedule>; supplier?; stop}} |" +
                        "shipper {" +
                        "def X{supplier!<DeliveryItem>; Y} " +
                        "def Y {supplier&{item: X, done: retailer?; stop}}" +
                        "main{Y}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "retailer"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected =
                "def X1 { supplier->shipper[item]; shipper.DeliveryItem->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop } main {supplier.PlannedOrderVariations->retailer; retailer.OrderDeliveryVariations->supplier; retailer.DeliverCheckPointRequest->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun logistic2(strategy: String, debugMode: Boolean) {
        val test =
                "supplier {" +
                        "def X {shipper?; consignee?; Y} " +
                        "def Y {if needToShip " +
                        "then shipper+item; consignee+item; X " +
                        "else shipper+done; consignee+done; " +
                        "retailer!<UpdatePOandDeliverySchedule>; retailer?; retailer?; retailer!<FinalizedPOandDeliverySchedule>; stop}" +
                        "main { retailer!<PlannedOrderVariations>; retailer?; retailer?; Y}" +
                        "} | " +
                        "retailer {main {" +
                        "supplier?; supplier!<OrderDeliveryVariations>; supplier!<DeliverCheckPointRequest>; " +
                        "supplier?; supplier!<POandDeliveryScheduleMods>; shipper!<ConfirmationofDeliverySchedule>; " +
                        "supplier!<AcceptPOandDeliverySchedule>; supplier?; stop}} |" +
                        "consignee {" +
                        "def X{supplier!<DeliveryItem>; Z} " +
                        "def Z {supplier&{item: X, done: stop}}" +
                        "main{Z}} | " +
                        "shipper {" +
                        "def X{supplier!<DeliveryItem>; Z} " +
                        "def Z {supplier&{item: X, done: retailer?; stop}}" +
                        "main{Z}}"


        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "retailer"))
        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected =
                "def X1 { supplier->shipper[item]; supplier->consignee[item]; shipper.DeliveryItem->supplier; consignee.DeliveryItem->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier->consignee[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop } main {supplier.PlannedOrderVariations->retailer; retailer.OrderDeliveryVariations->supplier; retailer.DeliverCheckPointRequest->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier->consignee[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun cloudSystem(strategy: String, debugMode: Boolean) {
        val test =
                "cl{" +
                        "def X{int!<connect>; int?; Y} " +
                        "def Y{if access then appli+awaitcl; appli!<access>; Y else int!<logout>; appli+syncLogout; appli?; X} " +
                        "main {X}} | " +
                        "appli{" +
                        "def X{int?; Y} " +
                        "def Y{cl&{awaitcl: cl?; Y, syncLogout: db!<log>; cl!<syncLog>; X}} " +
                        "main {X}} | " +
                        "int{" +
                        "def X{cl?; appli!<setup>; cl!<syncAccess>; cl?; X} " +
                        "main {X}} | " +
                        "db{" +
                        "def X{appli?; X} " +
                        "main {X}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "db, int"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected =
                "def X1 { cl.connect->int; int.setup->appli; int.syncAccess->cl; if cl.access then X2 else cl.logout->int; cl->appli[syncLogout]; appli.log->db; appli.syncLog->cl; X1 } def X2 { cl->appli[awaitcl]; cl.access->appli; if cl.access then X2 else cl.logout->int; cl->appli[syncLogout]; appli.log->db; appli.syncLog->cl; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun sanitaryAgency(strategy: String, debugMode: Boolean) {
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

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "coop, bank"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()
        val expected =
                "def X1 { citizen.request->sanagency; X2 } def X2 { sanagency.askInfo->citizen; citizen.provInf->sanagency; if sanagency.infoProved then sanagency->citizen[acceptance]; sanagency.req->coop; if coop.fine then coop.provT->citizen; coop->bank[recMoneyPossT]; bank.paymentT->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else coop.provM->citizen; coop->bank[recMoneyPossM]; bank.paymentM->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else sanagency->citizen[refusal]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    /*@ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun sanitaryAgency2x(strategy: String, debugMode: Boolean) {
        val test =
                "citizen1{def X{sanagency1!<request>; sanagency1?; sanagency1!<provInf>; sanagency1&{refusal: X, acceptance: coop1?; bank1!<paymentPrivateFee>; X}} main{X}} | " +
                "sanagency1{def X{citizen1?; citizen1!<askInfo>; citizen1?; if infoProved then citizen1+acceptance; coop1!<req>; bank1!<paymentPublicFee>; bank1?; X else citizen1+refusal; X} main {X}} | " +
                "coop1{def X{sanagency1?; if fine then citizen1!<provT>; bank1+recMoneyPossT; bank1?; X else citizen1!<provM>; bank1+recMoneyPossM; bank1?; X} main{X}} | " +
                "bank1{def X{ coop1&{recMoneyPossT: coop1!<paymentT>; Y, recMoneyPossM: coop1!<paymentM>; Y}} def Y{citizen1?; sanagency1?; sanagency1!<done>; X} main{X}} | " +
                "citizen2{def X{sanagency2!<request>; sanagency2?; sanagency2!<provInf>; sanagency2&{refusal: X, acceptance: coop2?; bank2!<paymentPrivateFee>; X}} main{X}} | " +
                "sanagency2{def X{citizen2?; citizen2!<askInfo>; citizen2?; if infoProved then citizen2+acceptance; coop2!<req>; bank2!<paymentPublicFee>; bank2?; X else citizen2+refusal; X} main {X}} | " +
                "coop2{def X{sanagency2?; if fine then citizen2!<provT>; bank2+recMoneyPossT; bank2?; X else citizen2!<provM>; bank2+recMoneyPossM; bank2?; X} main{X}} | " +
                "bank2{def X{ coop2&{recMoneyPossT: coop2!<paymentT>; Y, recMoneyPossM: coop2!<paymentM>; Y}} def Y{citizen2?; sanagency2?; sanagency2!<done>; X} main{X}}"

        val param = resolveArgs(strategy, debugMode)
        val args = param.first
        args.addAll(arrayListOf(test, "-l", "coop1, bank1, coop2, bank2"))

        val strategy = param.second

        val actual = ChoreographyExtraction.main(args).toString()

        when (strategy) {
            Strategy.SelectionFirst, Strategy.ConditionFirst -> {
                val expected =
                        "def X1 { citizen.provInf->sanagency; if sanagency.infoProved then sanagency->citizen[acceptance]; sanagency.req->coop; if coop.fine then coop.provT->citizen; coop->bank[recMoneyPossT]; bank.paymentT->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop.provM->citizen; coop->bank[recMoneyPossM]; bank.paymentM->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency->citizen[refusal]; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 } main {citizen.request->sanagency; sanagency.askInfo->citizen; X1}"

                assertEquals(expected, actual)
            }

            Strategy.UnmarkedFirst, Strategy.UnmarkedThenSelection, Strategy.UnmarkedThenCondition, Strategy.RandomProcess, Strategy.UnmarkedThenRandom -> {
                //"If it doesn't fail, we are happy "
            }
        }
    }*/

    /*@ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun tst(strategy: String, debugMode: Boolean) {
        value f =
                "r{def X{`value`!<e>; X} main{X}} | " +
                        "`value`{def X{r?; X} main{X}} | " +
                "p{def Y{q+l; X} def X{q+r; q!<e>; if e then Y else X} main{Y}} | " +
                "q{def X{p&{r: Z}} def Z{p?; p&{l: X, r: Z}} main{p&{l:X}}}"


        value param = resolveArgs(strategy, debugMode)
        value args = param.first
        args.addAll(arrayListOf(f))

        value strategy = param.second

        value actual = ChoreographyExtraction.main(args)

        value expected = "def X1 { p->q[r]; X2 } def X2 { p.e->q; if p.e then r.e->`value`; p->q[l]; X1 else r.e->`value`; p->q[r]; X2 } main {p->q[l]; X1}"
        assertEquals(expected, actual)

        *//*when (strategy) {
            Strategy.SelectionFirst, Strategy.ConditionFirst -> {
                value expected =
                        ""

                assertEquals(expected, actual)
            }

            Strategy.UnmarkedFirst, Strategy.UnmarkedThenSelection, Strategy.UnmarkedThenCondition, Strategy.RandomProcess, Strategy.UnmarkedThenRandom -> {
                //"If it doesn't fail, we are happy "
            }
        }*//*
    }

    @ParameterizedTest
    @CsvFileSource(resources = arrayOf("/settings.csv"), numLinesToSkip = 1)
    fun tst2(strategy: String, debugMode: Boolean) {
        value f =
                "r{def X {`value`!<0>; X} main {X}} | " +
                        "`value`{def X {r?; X} main {X}} | " +
                "p{def X {q!<1>; if e then q+L; q!<2>; X else q+R; X } main {q!<0>; q!<3>; X }} | " +
                "q{def X {p?; p&{L: p?; X, R: X} main {p?; p?; X}}"


        value param = resolveArgs(strategy, debugMode)
        value args = param.first
        args.addAll(arrayListOf(f))

        value strategy = param.second

        value actual = ChoreographyExtraction.main(args)

        value expected = ""
        assertEquals(expected, actual)

        *//*when (strategy) {
            Strategy.SelectionFirst, Strategy.ConditionFirst -> {
                value expected =
                        ""

                assertEquals(expected, actual)
            }

            Strategy.UnmarkedFirst, Strategy.UnmarkedThenSelection, Strategy.UnmarkedThenCondition, Strategy.RandomProcess, Strategy.UnmarkedThenRandom -> {
                //"If it doesn't fail, we are happy "
            }
        }*//*
    }*/
}