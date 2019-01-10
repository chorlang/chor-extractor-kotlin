package ce

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InteractionTest{

    /* simple interaction */
    @Test
    fun tst1(){
        val test = "p { main {q!<e>; q?; stop}} " +
                "| q { main {p?; p!<u>; stop}} " +
                "| r { main {stop}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "main {p.e->q; q.u->p; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with name */
    @Test
    fun tst2(){
        val test = "p { def X {q!<e>; stop} main {X}} " +
                "| q { def X {p?; stop} main {X}} " +
                "| r { main {stop}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "main {p.e->q; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with name on the one of the processesInChoreography */
    @Test
    fun tst3(){
        val test = "p {main{q?;stop}} | q { def X {p!<e>;stop} main{X}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "main {q.e->p; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with recursive name */
    @Test
    fun tst4(){
        val test = "p {def X {q!<e>;X} main {X}} " +
                "| q {def Y{p?; Y} main {Y}} " +
                "| r { main {stop}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "def X1 { p.e->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    // parallel execution is switched off now
    /*@Test
    fun tst5(){
        val test =
                "p {def X {q!<e>;X} main {X}} | q {def Y{p?; Y} main {Y}} | r { main {stop}} || " +
                "p {def X {q!<e>;X} main {X}} | q {def Y{p?; Y} main {Y}} | r { main {stop}}"

        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "def X1 { p.e->q; X1 } main {X1} || def X1 { p.e->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }*/
}