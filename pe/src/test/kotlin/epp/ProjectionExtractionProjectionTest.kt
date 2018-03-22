package epp

import extraction.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class ProjectionExtractionProjectionTest : Assert() {

    //@Theory
    fun test(vararg testData: Any) {
        val test = testData[0] as String
        println("\n" + "Choreography: " + test)

        val epp = EndPointProjection()
        val network = epp.project(test)
        println("Network: " + network.toString())

        val program = ChoreographyExtraction(network.toString()).extractChoreography()
        println("Choreography: " + program)

        println("Network: " + epp.project(program.toString()))

        Assert.assertEquals(test, testData[1], program.toString())

    }

    //@Test
    fun testN() {
        val network
                = "p{" +
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
                            "else p + ko; r + ko; Z, ok: p!<e>; r&{" +
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

        val n = "def X1 { " +
                    "p.e->q; if p.e " +
                        "then X2    " +
                        "else X4 } " +
                "def X2 { " +
                    "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then X2 else X3 } " +
                "def X3 { p->q[ko]; p->r[ko]; if q.e " +
                    "then q->p[ok]; q->r[ok]; X1 " +
                    "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X2 else X3 } " +
                "def X4 { p->q[ko]; p->r[ko]; if q.e " +
                    "then q->p[ok]; q->r[ok]; X1 " +
                    "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X5 else X4 } " +
                "def X5 { p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                    "then r->p[ok]; r->q[ok]; X1 " +
                    "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X5 else X4 } " +
                "main {q.i->r; X1}"



        //"p {main {q?; stop}} | q {main {p!<e>; stop}} | r {main {s+L; stop} | s {main {r&{L:stop}}}} "
        println(network)

        /* Extract choreography*/
        val program = ChoreographyExtraction(network.toString()).extractChoreography()
        println("Choreography: " + program)

        /* Try to project to the network */
        val epp = EndPointProjection()
        println("Network: " + epp.project(program.toString()))

        Assert.assertEquals(network, program.toString())

    }

    companion object {

        @DataPoints
        @JvmField
        var data = arrayOf(

/*                arrayOf<Any>(
                        "def X {" +
                                "q.e->p; if p.e " +
                                    "then p->q[ok]; q->r[ok]; X " +
                                    "else p->q[ko]; q->r[ko]; Y } " +
                        "def Y {" +
                                "q.e->r; if r.e " +
                                    "then r->q[ok]; r->p[ok]; q.e->r; stop " +
                                    "else r->q[ko]; r->p[ko]; Y}" +
                        "main {p.e->q;X}",

                        "def X1 { " +
                                "q.e->p; if p.e " +
                                    "then p->q[ok]; q->r[ok]; X1 " +
                                    "else p->q[ko]; q->r[ko]; X2 } " +
                        "def X2 { q.e->r; if r.e " +
                                "then r->q[ok]; r->p[ok]; q.e->r; stop " +
                                "else r->q[ko]; r->p[ko]; X2 } " +
                        "main {p.e->q; X1}"
                ), */

                arrayOf<Any>(
                        /*"def X {if p.e " +
                                "then p->q[ok]; p->r[ok]; if r.e " +
                                    "then q.e->p; r->p[ok];r->q[ok];p.e->q;X " +
                                    "else q.e->p; r->p[ko];r->q[ko];r.u->q;Y " +
                                "else p->q[ko]; p->r[ko]; if q.e " +
                                    "then q->p[ok];q->r[ok];p.e->q;X " +
                                    "else q->p[ko];q->r[ko];Z } " +
                        "def Y {p.e->q; X}" +
                        "def Z {p.e->q; Y}" +
                        "main {q.i->r; p.e->q; X}",

                        "def X1 { " +
                                "p.e->q; if p.e " +
                                    "then p->q[ok]; p->r[ok]; X2 " +
                                    "else p->q[ko]; if q.e " +
                                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                        "else p->r[ko]; X4 } " +
                        "def X2 { q.e->p; if r.e " +
                                "then r->p[ok]; r->q[ok]; X1 " +
                                "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                                    "then p->q[ok]; p->r[ok]; X2 " +
                                    "else p->q[ko]; if q.e " +
                                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                        "else p->r[ko]; X3 } " +
                        "def X3 { " +
                                "q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                                    "then p->q[ok]; p->r[ok]; X2 " +
                                    "else p->q[ko]; if q.e " +
                                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                        "else p->r[ko]; X3 } " +
                        "def X4 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                                "then p->q[ok]; p->r[ok]; X5 " +
                                "else p->q[ko]; if q.e " +
                                    "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                    "else p->r[ko]; X4 } " +
                        "def X5 { q.e->p; " +
                                "if r.e then r->p[ok]; r->q[ok]; X1 " +
                                "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                                    "then p->q[ok]; p->r[ok]; X5 " +
                                    "else p->q[ko]; if q.e " +
                                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                        "else p->r[ko]; X4 } " +
                        "main {q.i->r; X1}"*/
                "",""
                )
        )
    }
    @Test
    fun dumbTest(){
        val t1 = "def X1 { " +
                    "p.e->q; if p.e " +
                        "then X2 " +
                        "else X4 } " +
                "def X2 { " +
                    "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then X2 " +
                            "else X3 } " +
                "def X3 { p->q[ko]; p->r[ko]; if q.e " +
                    "then q->p[ok]; q->r[ok]; X1 " +
                    "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X2 " +
                        "else X3 } " +
                "def X4 { p->q[ko]; p->r[ko]; if q.e " +
                    "then q->p[ok]; q->r[ok]; X1 " +
                    "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                "def X5 { p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                    "then r->p[ok]; r->q[ok]; X1 " +
                    "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                "main {q.i->r; X1}"

        val t2 = "def X1 { " +
                    "p.e->q; if p.e " +
                        "then p->q[ok]; p->r[ok]; X2 " +
                        "else p->q[ko]; if q.e " +
                            "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                            "else p->r[ko]; X4 } " +
                "def X2 { " +
                    "q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then p->q[ok]; p->r[ok]; X2 " +
                            "else p->q[ko]; if q.e " +
                                "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                "else p->r[ko]; X3 } " +
                "def X3 { " +
                    "q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then p->q[ok]; p->r[ok]; X2 " +
                        "else p->q[ko]; if q.e " +
                            "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                            "else p->r[ko]; X3 } " +
                "def X4 { q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                    "then p->q[ok]; p->r[ok]; X5 " +
                    "else p->q[ko]; if q.e " +
                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                        "else p->r[ko]; X4 } " +
                "def X5 { " +
                    "q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then p->q[ok]; p->r[ok]; X5 " +
                            "else p->q[ko]; if q.e " +
                                "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                                "else p->r[ko]; X4 } " +
                "main {q.i->r; X1}"
    }
}

//Network: p{def X{q?; if e then q + ok; X else q + ko; Y} def Y{r&{ko: Y, ok: stop}} main {q!<e>; X}} | q{def X{p!<e>; p&{ko: r + ko; Y, ok: r + ok; X}} def Y{r!<e>; r&{ko: Y, ok: r!<e>; stop}} main {p?; X}} | r{def X{q&{ko: Y, ok: X}} def Y{q?; if e then q + ok; p + ok; q?; stop else q + ko; p + ko; Y} main {X}}
//Network: p{def X{q?; if e then q + ok; X else q + ko; Y} def Y{r&{ko: Y, ok: stop}} main {q!<e>; X}} | q{def X{p!<e>; p&{ko: r + ko; Y, ok: r + ok; X}} def Y{r!<e>; r&{ko: Y, ok: r!<e>; stop}} main {p?; X}} | r{def X{q&{ko: Y, ok: X}} def Y{q?; if e then q + ok; p + ok; q?; stop else q + ko; p + ko; Y} main {X}}
