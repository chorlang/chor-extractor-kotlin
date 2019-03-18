package extraction

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConditionTest{

    @Test
    fun tst1(){ /* simple condition */
        val test =
                "p { main {if e then q!<e1>; stop else q!<e2>; stop}} " +
                        "| q { main {p?; stop}} " +
                        "| r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {if p.e then p.e1->q; stop else p.e2->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){ /* condition with selection */
        val test =
                "p { main {if e then q+R; q!<e1>; q?; stop else q+L; q!<e2>; q?; stop}} | " +
                        "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                        "r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {if p.e then p->q[R]; p.e1->q; q.u1->p; stop else p->q[L]; p.e2->q; q.u2->p; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst3(){ /* condition inside condition */
        val test =
                "p { main {if e then if u then q!<e1>; stop else q!<e2>; stop else q!<e3>; stop}} " +
                        "| q { main {p?; stop}} " +
                        "| r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {if p.e then if p.u then p.e1->q; stop else p.e2->q; stop else p.e3->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst4(){ /* condition with name */
        val test =
                "p {def X {if e then q!<u>;stop else q!<o>;stop} main {X}} " +
                        "| q {def Y{p?;stop} main {Y}} " +
                        "| r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {if p.e then p.u->q; stop else p.o->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst5(){ /* condition with name */
        val test =
                "p {def X {if e then q!<u>;X else q!<o>;X} main {X}} " +
                        "| q {def Y{p?;Y} main {Y}} " +
                        "| r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "def X1 { if p.e then p.u->q; X1 else p.o->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst6(){ /* condition with selection with recursion*/
        val test =
                "p { def X {if e then q+R; q!<e1>; q?; stop else q+L; q!<e2>; q?; stop} main {X}} | " +
                        "q { def X {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}} main {X}} | " +
                        "r { main {stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {if p.e then p->q[R]; p.e1->q; q.u1->p; stop else p->q[L]; p.e2->q; q.u2->p; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst7(){ /* condition with selection with recursion*/
        val test =
                "p { def X {q!<e>; X} main {X}} | " +
                        "q { def Y {p?; Y} main {Y}} | " +
                        "r { main {s!<e2>; stop}} | " +
                        "s { main {r?; stop}}"

        val actual = Extraction.extractChoreography(test).toString()
        val expected = "main {r.e2->s; stop} || def X1 { p.e->q; X1 } main {X1}"

        assertEquals(expected, actual)
    }
}