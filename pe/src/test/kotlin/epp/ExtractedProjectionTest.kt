package epp

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.internal.runners.statements.ExpectException
import org.junit.runner.RunWith
import org.junit.rules.ExpectedException



@RunWith(Theories::class)
class ExtractedProjectionTest : Assert() {

    @Test (expected = MergingProjection.MergingException::class)
    fun test() {
        val test =
                "def X1 { " +
                    "p.e->q; if p.e " +
                        "then X2 " +
                        "else X4 } " +
                "def X2 { " +
                    "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then X2 " +
                            "else X3 } " +
                "def X3 { " +
                    "p->q[ko]; p->r[ko]; if q.e " +
                        "then q->p[ok]; q->r[ok]; X1 " +
                        "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                            "then X2 " +
                            "else X3 } " +
                "def X4 { " +
                    "p->q[ko]; p->r[ko]; if q.e " +
                        "then q->p[ok]; q->r[ok]; X1 " +
                        "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                            "then X5 " +
                            "else X4 } " +
                "def X5 { " +
                    "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                            "then X5 " +
                            "else X4 } " +
                "main {q.i->r; X1}"

        println("\n" + "Choreography: " + test)

        val epp = EndPointProjection()
        val actual = epp.project(test)
        println("Network: " + actual.toString())
        //Assert.assertEquals(test, testData[1], actual.toString())

    }
}
