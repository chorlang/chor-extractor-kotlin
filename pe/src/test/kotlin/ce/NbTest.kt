package ce

import org.junit.Assert
import org.junit.Test

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

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { " +
                    "if a.e " +
                        "then a->b[win]; c.busy->d; a->c[lose]; b.lose->c; b.sig->a; c.msg->a; a.free->d; X1 " +
                        "else a->b[lose]; c.busy->d; a->c[win]; c.lose->b; b.sig->a; c.msg->a; a.free->d; X1 " +
                "} main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun nbtst1x2(){

        val test =
                "a1 {def X {if e then b1+win; c1+lose; b1?; c1?; d1!<free>; X else b1+lose; c1+win; b1?; c1?; d1!<free>; X} main {X}} |" +
                "b1 {def X {a1&{win: c1!<lose>; a1!<sig>; X, lose: c1?; a1!<sig>; X}} main {X}} |" +
                "c1 {def X {d1!<busy>; a1&{win: b1!<lose>; a1!<msg>; X, lose: b1?; a1!<msg>; X}} main {X}} |" +
                "d1 {def X {c1?; a1?; X} main {X}} | " +
                "a2 {def X {if e then b2+win; c2+lose; b2?; c2?; d2!<free>; X else b2+lose; c2+win; b2?; c2?; d2!<free>; X} main {X}} |" +
                "b2 {def X {a2&{win: c2!<lose>; a2!<sig>; X, lose: c2?; a2!<sig>; X}} main {X}} |" +
                "c2 {def X {d2!<busy>; a2&{win: b2!<lose>; a2!<msg>; X, lose: b2?; a2!<msg>; X}} main {X}} |" +
                "d2 {def X {c2?; a2?; X} main {X}} "
        val args = arrayOf("-c", test)

        ChoreographyExtraction.main(args)
    }
}


