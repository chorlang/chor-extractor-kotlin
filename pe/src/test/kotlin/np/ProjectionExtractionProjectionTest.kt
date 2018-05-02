package np

import ce.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith

class ProjectionExtractionProjectionTest : Assert() {
    @Test
    fun tst1() {
        val test = "def X {" +
                "q.e->p; if p.e " +
                "then p->q[ok]; q->r[ok]; X " +
                "else p->q[ko]; q->r[ko]; Y } " +
                "def Y {" +
                "q.e->r; if r.e " +
                "then r->q[ok]; r->p[ok]; q.e->r; stop " +
                "else r->q[ko]; r->p[ko]; Y}" +
                "main {p.e->q;X}"

        val extraction = NetworkProjection.project(test).toString()
        val args = arrayOf("-c", extraction)

        val actual = ChoreographyExtraction.main(args)

        val expected = "def X1 { " +
                "q.e->p; if p.e " +
                "then p->q[ok]; q->r[ok]; X1 " +
                "else p->q[ko]; q->r[ko]; X2 } " +
                "def X2 { q.e->r; if r.e " +
                "then r->q[ok]; r->p[ok]; q.e->r; stop " +
                "else r->q[ko]; r->p[ko]; X2 } " +
                "main {p.e->q; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2() {
        val test = "def X {if p.e " +
                "then p->q[ok]; p->r[ok]; if r.e " +
                "then q.e->p; r->p[ok];r->q[ok];p.e->q;X " +
                "else q.e->p; r->p[ko];r->q[ko];r.u->q;Y " +
                "else p->q[ko]; p->r[ko]; if q.e " +
                "then q->p[ok];q->r[ok];p.e->q;X " +
                "else q->p[ko];q->r[ko];Z } " +
                "def Y {p.e->q; X}" +
                "def Z {p.e->q; Y}" +
                "main {q.i->r; p.e->q; X}"

        val extraction = NetworkProjection.project(test).toString()
        val args = arrayOf("-c", extraction)

        val actual = ChoreographyExtraction.main(args)

        val expected = "def X1 { " +
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
                "main {q.i->r; X1}"

        assertEquals(expected, actual)
    }
}