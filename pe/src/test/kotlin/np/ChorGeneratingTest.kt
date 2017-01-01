package np

import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith


@RunWith(Theories::class)
class ChorGeneratingTest : Assert() {
    @Test
    fun test() {
        val test = "main {p->q[R]; if r.e " +
                "then if p.u " +
                    "then if q.u " +
                        "then q.a->r; stop " +
                        "else q.a->r; stop " +
                    "else if q.u " +
                        "then q.a->r; stop " +
                        "else q.a->r; stop " +
                "else if p.u " +
                    "then if q.u " +
                        "then q.a->r; stop " +
                        "else q.a->r; stop " +
                    "else if q.u " +
                        "then q.a->r; stop " +
                        "else q.a->r; stop}"

        println("\n" + "Choreography: " + test)

        val epp = NetworkProjection
        val actual = epp.project(test)
        println("Network: " + actual.toString())
        //Assert.assertEquals(test, testData[1], actual.toString())

    }
}
