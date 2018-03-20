package extraction

import ast.cc.interfaces.CCNode
import org.junit.Assert
import org.junit.Before
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class SelectionTest : Assert() {
    @Theory
    @Throws(Exception::class)
    fun test(vararg testData: Any) {
        println("\n" + "Test Network: " + testData[0])
        val np = ChoreographyExtraction(testData[0] as String)
        val program = np.extractChoreography()
        println("Choreography: " + program.toString())

        Assert.assertEquals(testData[1], program.toString())
    }

    companion object {
        @DataPoints @JvmField
        var data = arrayOf(

                arrayOf<Any>
                (
                        "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                        "r { main {stop}} | " +
                        "p { main {q+R; q!<e>; q?; stop}}",

                        "main {p->q[R]; p.e->q; q.u1->p; stop}"
                ),

                arrayOf<Any>("p { main {q+L; q!<e>; q?; stop}} | " +
                        "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                        "r { main {stop}}",

                        "main {p->q[L]; p.e->q; q.u2->p; stop}"),

                arrayOf<Any>
                (
                        "p { def X {q+R; q!<e>; X} main{X}} | " +
                        "q { def Y {p&{R: p?; Y, L: p?; Y}} main{Y}}",

                        "def X1 { p->q[R]; p.e->q; X1 } main {X1}")
        )
    }
}