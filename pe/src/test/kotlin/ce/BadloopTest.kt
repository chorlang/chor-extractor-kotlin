package ce

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BadloopTest{

    @Test
    fun tst2p(){
        val test =
                "a1 {def X {b1!<e>; b1?; X} main {X}} |" +
                "b1 {def X {a1?; a1!<e>; X} main {X}} |" +
                "a2 {def X {b2!<e>; b2?; X} main {X}} |" +
                "b2 {def X {a2?; a2!<e>; X} main {X}}"

        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "def X1 { a2.e->b2; b2.e->a2; a1.e->b1; a2.e->b2; b2.e->a2; b1.e->a1; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst3p(){
        val test =
                "a1 {def X {b1!<e>; b1?; X} main {X}} |" +
                "b1 {def X {a1?; a1!<e>; X} main {X}} |" +
                "a2 {def X {b2!<e>; b2?; X} main {X}} |" +
                "b2 {def X {a2?; a2!<e>; X} main {X}} |" +
                "a3 {def X {b3!<e>; b3?; X} main {X}} |" +
                "b3 {def X {a3?; a3!<e>; X} main {X}}"
        val args = arrayListOf("-c", test, "-d")

        val actual = Extraction.main(args).toString()
        val expected = "def X1 { a1.e->b1; b2.e->a2; b1.e->a1; a3.e->b3; b3.e->a3; a2.e->b2; X1 } main {a2.e->b2; b2.e->a2; a3.e->b3; a2.e->b2; b2.e->a2; b3.e->a3; a2.e->b2; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst4p(){
        val test =
                "a1 {def X {b1!<e>; b1?; X} main {X}} |" +
                "b1 {def X {a1?; a1!<e>; X} main {X}} |" +
                "a2 {def X {b2!<e>; b2?; X} main {X}} |" +
                "b2 {def X {a2?; a2!<e>; X} main {X}} |" +
                "a3 {def X {b3!<e>; b3?; X} main {X}} |" +
                "b3 {def X {a3?; a3!<e>; X} main {X}} |" +
                "a4 {def X {b4!<e>; b4?; X} main {X}} |" +
                "b4 {def X {a4?; a4!<e>; X} main {X}}"
        val args = arrayListOf("-c", test, "-d")

        val actual = Extraction.main(args).toString()
        val expected = "def X1 { a2.e->b2; b2.e->a2; a3.e->b3; a2.e->b2; b2.e->a2; b3.e->a3; a2.e->b2; a4.e->b4; b2.e->a2; a3.e->b3; b3.e->a3; b4.e->a4; a2.e->b2; a3.e->b3; b2.e->a2; a4.e->b4; a2.e->b2; b3.e->a3; a1.e->b1; b2.e->a2; b1.e->a1; a3.e->b3; b3.e->a3; b4.e->a4; X1 } main {X1}"

        assertEquals(expected, actual)
    }
}


