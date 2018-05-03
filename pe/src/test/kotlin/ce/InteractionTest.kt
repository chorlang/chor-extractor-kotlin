package ce

import org.junit.Assert
import org.junit.Test

class InteractionTest : Assert() {

    /* simple interaction */
    @Test
    fun tst1(){
        val test = "p { main {q!<e>; q?; stop}} " +
                "| q { main {p?; p!<u>; stop}} " +
                "| r { main {stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p.e->q; q.u->p; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with procedure */
    @Test
    fun tst2(){
        val test = "p { def X {q!<e>; stop} main {X}} " +
                "| q { def X {p?; stop} main {X}} " +
                "| r { main {stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p.e->q; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with procedure on the one of the processes */
    @Test
    fun tst3(){
        val test = "p {main{q?;stop}} | q { def X {p!<e>;stop} main{X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {q.e->p; stop}"

        assertEquals(expected, actual)
    }

    /* interaction with recursive procedure */
    @Test
    fun tst4(){
        val test = "p {def X {q!<e>;X} main {X}} " +
                "| q {def Y{p?; Y} main {Y}} " +
                "| r { main {stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }
}