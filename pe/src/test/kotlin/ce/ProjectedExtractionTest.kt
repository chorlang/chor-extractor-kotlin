package ce

import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
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

    @Test
    fun tst9(){
        val test = "p{" +
                "def X{if e then q + ok; r + ok; q?; r&{ko: Y, ok: q!<e>; X} else q + ko; r + ko; q&{ko: Z, ok: q!<e>; X}} " +
                "def Y{q!<e>; X} " +
                "def Z{q!<e>; Y} " +
                "main {q!<e>; X}} | " +
                "q{" +
                "def X{p&{ko: if e then p + ok; r + ok; p?; X else p + ko; r + ko; Z, ok: p!<e>; r&{ko: r?; Y, ok: p?; X}}} " +
                "def Y{p?; X} " +
                "def Z{p?; Y} " +
                "main {r!<i>; p?; X}} | " +
                "r{" +
                "def X{p&{ko: q&{ko: Z, ok: X}, ok: if e then p + ok; q + ok; X else p + ko; q + ko; q!<u>; Y}} " +
                "def Y{X} " +
                "def Z{Y} main {q?; X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X2 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X3 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X4 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X5 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } main {q.i->r; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst10(){
        val test = "p{def X{if e then q + ok; r + ok; q?; r&{ko: Y, ok: q!<e>; X} else q + ko; r + ko; q&{ko: Z, ok: q!<e>; X}} def Y{q!<e>; X} def Z{q!<e>; Y} main {q!<e>; X}} | q{def X{p&{ko: if e then p + ok; r + ok; p?; X else p + ko; r + ko; Z, ok: p!<e>; r&{ko: r?; Y, ok: p?; X}}} def Y{p?; X} def Z{p?; Y} main {r!<i>; p?; X}} | r{def X{p&{ko: q&{ko: Z, ok: X}, ok: if e then p + ok; q + ok; X else p + ko; q + ko; q!<u>; Y}} def Y{X} def Z{Y} main {q?; X}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X2 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X3 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X4 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X5 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } main {q.i->r; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst11(){
        val test = "p{" +
                "def X{q?; " +
                "if e then q + ok; X " +
                "else q + ko; Y} " +
                "def Y{r&{" +
                "ko: Y, " +
                "ok: stop}} " +
                "main {q!<e>; X}} | " +
                "q{" +
                "def X {p!<e>; p&{" +
                "ko: r + ko; Y, " +
                "ok: r + ok; X}} " +
                "def Y{r!<e>; r&{" +
                "ko: Y, " +
                "ok: r!<e>; stop}} " +
                "main {p?; X}} | " +
                "r{" +
                "def X{q&{" +
                "ko: Y, " +
                "ok: X}} " +
                "def Y{q?; if e " +
                "then q + ok; p + ok; q?; stop " +
                "else q + ko; p + ko; Y} " +
                "main {X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =  "def X1 { q.e->p; if p.e then p->q[ok]; q->r[ok]; X1 else p->q[ko]; q->r[ko]; X2 } " +
                "def X2 { q.e->r; if r.e then r->q[ok]; r->p[ok]; q.e->r; stop else r->q[ko]; r->p[ko]; X2 } " +
                "main {p.e->q; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst12(){
        val test = "p{" +
                "def X{if e " +
                "then q + ok; r + ok; q?; r&{" +
                "ko: Y, " +
                "ok: q!<e>; X} " +
                "else q + ko; r + ko; q&{" +
                "ko: Z, " +
                "ok: q!<e>; X}} " +
                "def Y{q!<e>; X} " +
                "def Z{q!<e>; Y} " +
                "main {q!<e>; X}} | " +
                "q{" +
                "def X{p&{" +
                "ko: if e " +
                "then p + ok; r + ok; p?; X " +
                "else p + ko; r + ko; Z, " +
                "ok: p!<e>; r&{" +
                "ko: r?; Y, " +
                "ok: p?; X}}} " +
                "def Y{p?; X} " +
                "def Z{p?; Y} " +
                "main {r!<i>; p?; X}} | " +
                "r{" +
                "def X{p&{" +
                "ko: q&{" +
                "ko: Z, " +
                "ok: X}, " +
                "ok: if e " +
                "then p + ok; q + ok; X " +
                "else p + ko; q + ko; q!<u>; Y}} " +
                "def Y{X} " +
                "def Z{Y} " +
                "main {q?; X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X2 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X3 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X2 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X3 } def X4 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } def X5 { q.e->p; if r.e then r->p[ok]; r->q[ok]; X1 else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e then p->q[ok]; p->r[ok]; X5 else p->q[ko]; if q.e then p->r[ko]; q->p[ok]; q->r[ok]; X1 else p->r[ko]; X4 } main {q.i->r; X1}"

        assertEquals(expected, actual)
    }
}