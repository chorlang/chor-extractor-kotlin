package ce

import org.junit.Assert
import org.junit.Test

class ProjectedExtractionTest : Assert() {
    @Test
    fun tst1(){
        val test = "p{" +
                "def X{Y} " +
                "def Y{q!<e>; stop} " +
                "main {q?; X}} | " +
                "q{" +
                "def X{Y} " +
                "def Y{p?; stop} " +
                "main {p!<e>; X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {q.e->p; p.e->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){
        val test = "p{" +
                "def X{q!<e>; stop} " +
                "main {X}} | " +
                "q{" +
                "def X{p?; stop} " +
                "main {X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p.e->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst3(){
        val test = "p{main {q!<e>; stop}} | q{main {p?; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p.e->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst4(){
        val test = "p{main {q + l; stop}} | q{main {p&{l: stop}}}"
        val args = arrayOf("-c", test)


        val actual = ChoreographyExtraction.main(args)
        val expected = "main {p->q[l]; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst5(){
        val test = "p{main {if e then q!<e>; stop else q!<e>; stop}} | " +
                "q{main {p?; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {if p.e then p.e->q; stop else p.e->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst6(){
        val test = "p{main {if e then q!<e1>; stop else q!<e3>; stop}} | " +
                "q{main {if e2 then p?; stop else p?; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {if p.e then if q.e2 then p.e1->q; stop else p.e1->q; stop else if q.e2 then p.e3->q; stop else p.e3->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst7(){
        val test = "p{main {if e " +
                "then q + L; q!<e>; stop " +
                "else q + R; q?; stop}} | " +
                "q{main {p&{" +
                "R: r!<y>; r?; p!<u>; stop, " +
                "L: p?; r!<x>; r?; stop}}} | " +
                "r{main {q?; q!<z>; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {if p.e then p->q[L]; p.e->q; q.x->r; r.z->q; stop " +
                "else p->q[R]; q.y->r; r.z->q; q.u->p; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst8(){
        val test = "p{main {if e then q + L; q!<e>; stop else q + R; q?; stop}} | " +
                "q{main {p&{R: r + R1; r?; p!<u>; stop, L: p?; r + L1; r?; stop}}} | " +
                "r{main {q&{L1: q!<z1>; stop, R1: q!<z2>; stop}}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {if p.e then p->q[L]; p.e->q; q->r[L1]; r.z1->q; stop else p->q[R]; q->r[R1]; r.z2->q; q.u->p; stop}"

        assertEquals(expected, actual)
    }
}
