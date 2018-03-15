package extraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class PaperTest : Assert() {

    @Theory
    @Throws(Exception::class)
    fun test(vararg testData: Any) {
        println("\n" + "Test Network: " + testData[0])
        val np = ChoreographyExtraction(testData[0] as String)
        val program = np?.extractChoreography()
        println("Choreography: " + program!!.toString())

        Assert.assertEquals(testData[1], program.toString())
    }

    /*@Test (expected = NetworkExtraction.ProcessStarvationException::class)
    fun testBadLoop() {
        val str = "p { def X {q!<e1>; q&{L: q!<e2>; X, R: stop}} main {q!<e>; X}} | " +
                "q { def Y {p?; p?; r?; if r then p+L; Y else p+R; stop} main {Y}} | " +
                "r { def Z {q!<e3>; Z} main {Z}}"

        println("Test Network: " + str)

        println("Throws ProcessStarvationException")
        ChoreographyExtraction(str).extractChoreography()

    }*/

    companion object {
        @DataPoints
        @JvmField

        var data = arrayOf(

                /* Example 2 */
                arrayOf<Any>
                (
                        "c { def X {a!<pwd>; a&{ok: s?; stop, ko: X}} main {X}} | " +
                        "a { def X {c?; s?; if s then c+ok; s+ok; stop else c+ko; s+ko; X} main {X}} | " +
                        "s { def X {a!<s>; a&{ok: c!<t>; stop, ko:X}} main {X}}",

                        "def X1 { c.pwd->a; s.s->a; if s then a->c[ok]; a->s[ok]; s.t->c; 0 else a->c[ko]; a->s[ko]; X1 } main { X1 }"),


                /* Example 4  - processes starvation */
                arrayOf<Any>
                (
                        "p { def X {q!<e1>; X} main {X}} | " +
                        "q { def Y {p?; Y} main {Y}} | " +
                        "r { def Z {s!<e2>; Z} main {Z}} | " +
                        "s { def W {r?; W} main {W}}",

                        "def X1 { p.e1->q; r.e2->s; X1 } main { X1 }"),


                /* Example 5 - deadlocked finite processes*/
                arrayOf<Any>
                (
                        "p { def X {q!<e>; X} main {X}} | " +
                        "q { def Y {p?; Y} main {Y}} | " +
                        "r { main {s!<e2>; stop}} | " +
                        "s { main {r?; stop}}",

                        "def X1 { p.e->q; X1 } main { r.e2->s; X1 }"
                )

                /* Example 8 -2-bit protocol*/
                /*arrayOf<Any>
                (
                        "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}",

                        ""
                )*/

        )

    }
}