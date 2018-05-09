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
    fun nbtst1X2(){

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

    @Test
    fun mult2bit(){
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2bitX2(){
        val test =
                "a1 { def X {b1?;b1!<0>;b1?;b1!<1>;X} main {b1!<0>;b1!<1>;X}} | " +
                "b1 { def Y {a1?;a1!<ack0>;a1?;a1!<ack1>;Y} main {Y}} | " +
                "a2 { def X {b2?;b2!<0>;b2?;b2!<1>;X} main {b2!<0>;b2!<1>;X}} | " +
                "b2 { def Y {a2?;a2!<ack0>;a2?;a2!<ack1>;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { " +
                        "(a1.1->b1, b1.ack0->a1); " +
                        "(a1.0->b1, b1.ack1->a1); " +
                        "(b2.ack0->a2, a2.1->b2); " +
                        "(a1.0->b1, b1.ack1->a1); " +
                        "(a1.1->b1, b1.ack0->a1); " +
                        "(b2.ack1->a2, a2.0->b2); " +
                        "X1 } " +
                "main {a2.0->b2; a1.0->b1; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2bit3p(){
        val test =
                "a { def X {b!<0>;b?;b!<1>;b?;b!<2>;b?;X} main {X}} | " +
                "b { def Y {a!<ack0>;a?;a!<ack1>;a?;a!<ack2>;a?;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.0->b, b.ack0->a); (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); X1 } main {X1}"

        assertEquals(expected, actual)
    }

    /*@Test
    fun mult2bit3pX2(){
        val test =
                "a1 { def X {b1!<0>;b1?;b1!<1>;b1?;b1!<2>;b1?;X} main {X}} | " +
                "b1 { def Y {a1!<ack0>;a1?;a1!<ack1>;a1?;a1!<ack2>;a1?;Y} main {Y}} | " +
                "a2 { def X {b2!<0>;b2?;b2!<1>;b2?;b2!<2>;b2?;X} main {X}} | " +
                "b2 { def Y {a2!<ack0>;a2?;a2!<ack1>;a2?;a2!<ack2>;a2?;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.0->b, b.ack0->a); (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); X1 } main {X1}"

        assertEquals(expected, actual)
    }*/
}


