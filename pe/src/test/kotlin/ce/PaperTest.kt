package ce
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class PaperTest : Assert() {
    @Test
    fun ex2(){
        val test = "c { def X {a!<pwd>; a&{ok: s?; stop, ko: X}} main {X}} | " +
                "a { def X {c?; s?; if s then c+ok; s+ok; stop else c+ko; s+ko; X} main {X}} | " +
                "s { def X {a!<s>; a&{ok: c!<t>; stop, ko:X}} main {X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { c.pwd->a; s.s->a; if a.s then a->c[ok]; a->s[ok]; s.t->c; stop else a->c[ko]; a->s[ko]; X1 } main {X1}"

        assertEquals(actual, expected)
    }

    @Test
    fun ex4(){
        val test =  "p { def X {q!<e1>; X} main {X}} | " +
                "q { def Y {p?; Y} main {Y}} | " +
                "r { def Z {s!<e2>; Z} main {Z}} | " +
                "s { def W {r?; W} main {W}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e1->q; r.e2->s; X1 } main {X1}"

        assertEquals(actual, expected)
    }

    @Test
    fun ex5(){
        val test =  "p { def X {q!<e>; X} main {X}} | " +
                "q { def Y {p?; Y} main {Y}} | " +
                "r { main {s!<e2>; stop}} | " +
                "s { main {r?; stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; X1 } main {r.e2->s; X1}"

        assertEquals(actual, expected)
    }

    @Test
    fun l1(){
        val test = "p { def X {q!<e>; q!<e>; q!<e>; X} main {X}} | " +
                "q { def Y {p?; p?; Y} main {p?; Y}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; p.e->q; p.e->r; X1 } main {p.e->q; p.e->r; X1}"

        assertEquals(actual, expected)
    }

    @Test
    fun l2(){
        val test = "p { def X {q!<e>; Y} def Y {r!<e>; Z} def Z {q!<e>; X} main {X}} | " +
                "q { def W {p?; W} main {W}} | " +
                "r { def T {p?; T} main {T}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.e->q; p.e->q; p.e->r; X1 } main {p.e->q; p.e->r; X1}"

        assertEquals(actual, expected)
    }

    @Test
    fun ex8(){ /* 2-bit protocol*/
        val test = "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"

        assertEquals(actual, expected)
    }
}