package ce

import org.junit.Assert
import org.junit.Test

class SelectionTest : Assert() {

    @Test
    fun tst1(){
        val test = "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                "r { main {stop}} | " +
                "p { main {q+R; q!<e>; q?; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p->q[R]; p.e->q; q.u1->p; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){
        val test = "p { main {q+L; q!<e>; q?; stop}} | " +
                "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                "r { main {stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p->q[L]; p.e->q; q.u2->p; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst3(){
        val test = "p{def X{q+R;q!<e>;X} main{X}} | " +
                "q{def Y{p&{R: p?;Y, L: p?;Y}} main{Y}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p->q[R]; p.e->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }
}