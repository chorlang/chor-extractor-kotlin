package extraction

import ast.cc.interfaces.CCNode
import org.junit.Assert
import org.junit.Before
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class InteractionTest : Assert() {
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

        @DataPoints
        @JvmField
        var data = arrayOf(

                /* simple interaction */
                arrayOf<Any>("p { main {q!<e>; q?; stop}} " +
                        "| q { main {p?; p!<u>; stop}} " +
                        "| r { main {stop}}",

                        "main { p.e->q; q.u->p; 0 }"),

                /* interaction with procedure */
                arrayOf<Any>("p { def X {q!<e>; stop} main {X}} " +
                        "| q { def X {p?; stop} main {X}} " +
                        "| r { main {stop}}",

                        "main { p.e->q; 0 }"),

                /* interaction with procedure on the one of the processes */
                arrayOf<Any>("p {main{q?;stop}} | q { def X {p!<e>;stop} main{X}}",

                        "main { q.e->p; 0 }"),

                /* interaction with recursive procedure */
                arrayOf<Any>("p {def X {q!<e>;X} main {X}} " +
                        "| q {def Y{p?; Y} main {Y}} " +
                        "| r { main {stop}}",

                        "def X1 { p.e->q; X1 } main { X1 }")
        )
    }
}