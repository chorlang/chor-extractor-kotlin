package ce

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MulticomTest {

    @Test
    fun mult1() {
        val test =
                "p {main {q!<0>; q?; stop}} |" +
                        "q {main {p!<1>; p?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(p.0->q, q.1->p); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2() {
        val test =
                "p {main {q+R; q&{L: q!<e>; stop}}} |" +
                        "q {main {p+L; p&{R: p?;stop}}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(p->q[R], q->p[L]); p.e->q; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult3() {
        val test =
                "p {main {q!<0>; q?; stop}} |" +
                        "q {main {r!<2>; r?; p!<1>; p?; stop}} |" +
                        "r {main {q!<0>; q?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(q.2->r, r.0->q); (p.0->q, q.1->p); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult4() {
        val test =
                "p {def X {q!<0>; q?; stop} main {X}} |" +
                        "q {def X {p!<1>; p?; stop} main {X}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(p.0->q, q.1->p); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult5() {
        val test =
                "p {def X {q!<0>; q?; stop} main {X}} |" +
                        "q {def X {r!<2>; r?; p!<1>; p?; stop} main {X}} |" +
                        "r {def X {q!<0>; q?; stop} main {X}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(q.2->r, r.0->q); (q.1->p, p.0->q); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult6() {
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                        "q {def X {p!<1>; p?; X} main {X}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { (p.0->q, q.1->p); X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult7() {
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                        "q {def X {p?; p!<1>; X} main {p!<1>; X}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { (q.1->p, p.0->q); X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult8() {
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                        "q {def X {r!<2>; r?; p!<1>; p?; X} main {X}} |" +
                        "r {def X {q!<0>; q?; X} main {X}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { (q.2->r, r.0->q); (q.1->p, p.0->q); X1 } main {X1}"

        assertEquals(expected, actual)
    }


    @Test
    fun mult2bit() {
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun multl1() {
        val test = "p {main {q!<e1>; r!<e2>; s!<e3>; q?; stop}} | " +
                "q {main {p!<e4>; p?; t!<e5>; stop}} | " +
                "r {main {p?; stop}} | " +
                "s {main {p?; stop}} | " +
                "t {main {q?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(p.e1->q, q.e4->p, p.e3->s, p.e2->r); q.e5->t; stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun multl2() {
        val test = "p {main {q!<e1>; s?; stop}} | " +
                "q {main {r!<e2>; p?; stop}} | " +
                "r {main {s!<e3>; q?; stop}} | " +
                "s {main {p!<e4>; r?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {(p.e1->q, q.e2->r, r.e3->s, s.e4->p); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun multl3() {
        val test = "p {main {q!<e1>; r!<e2>; q?; stop}} | " +
                "q {main {p!<e3>; p?; stop}} | " +
                "r {main {s!<e4>; p?; stop}} | " +
                "s {main {r?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {r.e4->s; (p.e1->q, q.e3->p, p.e2->r); stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun multl4() {
        val test = "p {main {q!<e1>; q?; r!<e2>; s!<e3>; stop}} | " +
                "q {main {t!<e5>; p!<e4>; p?; stop}} | " +
                "r {main {p?; stop}} | " +
                "s {main {p?; stop}} | " +
                "t {main {q?; stop}}"

        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args).toString()
        val expected = "main {q.e5->t; (p.e1->q, q.e4->p); p.e2->r; p.e3->s; stop}"

        assertEquals(expected, actual)
    }

    /*@Test (expected = NetworkExtraction.MulticomException::class)
    fun multl5() {
        val test = "p {main {q!<e1>; r!<e2>; s!<e3>; q?; q!<e4>; s?; q!<e5>; r!<e6>; q?; stop}} | " +
                "q {main {p!<e7>; p?; t!<e8>; r!<e9>; p?; p!<e10>; p?; stop}} | " +
                "r {main {p?; s!<e11>; s!<e12>; q?; p?; stop}} | " +
                "s {main {p?; p!<e13>; r?; r?; stop}} | " +
                "t {main {q?; stop}}"

        val args = arrayListOf("-c", test)

        Assertions.assertThrows(NetworkExtraction.MulticomException::class.java
        ) { ChoreographyExtraction.main(args) }
    }*/
}
