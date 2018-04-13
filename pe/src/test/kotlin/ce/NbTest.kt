package ce

import ce.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith


@RunWith(Theories::class)
class NbTest : Assert() {

    @Test
    fun nbtst1(){
        val test = "" +
                "a {def X " +
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

    @Test
    fun nbtst1x2(){
        val test = "" +
                "a {def X " +
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
                "main {X}} | " +
                "a1 {def X {if e " +
                "then b1+win; c1+lose; b1?; c1?; d1!<free>; X " +
                    "else b1+lose; c1+win; b1?; c1?; d1!<free>; X} " +
                    "main {X}} |" +
                    "b1 {def X " +
                    "{a1&{" +
                    "win: c1!<lose>; a1!<sig>; X, " +
                    "lose: c1?; a1!<sig>; X}} " +
                    "main {X}} |" +
                    "c1 {def X " +
                    "{d1!<busy>; a1&{" +
                    "win: b1!<lose>; a1!<msg>; X, " +
                    "lose: b1?; a1!<msg>; X}} " +
                    "main {X}} |" +
                    "d1 {def X " +
                    "{c1?; a1?; X} " +
                    "main {X}} "
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun tst(){
        val test =
                        "a {def X " +
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
                        "main {X}} | " +
                                "a1 {def X {b1!<1>; b1?; X} main {X}} |" +
                                "b1 {def X {a1?; a1!<0>; X} main {X}} |" +
                                "a2 {def X {b2!<1>; b2?; X} main {X}} |" +
                                "b2 {def X {a2?; a2!<0>; X} main {X}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }
}
