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

    private var np: ChoreographyExtraction? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        np = ChoreographyExtraction()
    }

    @Theory
    @Throws(Exception::class)
    fun testProject(vararg testData: Any) {
        println("\n" + "Test: " + testData[0])
        val graph = np!!.extractChoreography(testData[0] as String)

        //assertEquals(testData[1], graph.toString());
    }

    companion object {

        @DataPoints  @JvmField
        var data = arrayOf(

                /* interaction with recursive procedure */
                arrayOf<Any>("p {def X {q!<e>;X} main {X}} " +
                        "| q {def Y{p?; Y} main {Y}} " +
                        "| r { main {stop}}", "p.e->q; q.u->p; stop"),

                /* simple interaction */
                arrayOf<Any>("p { main {q!<e>; q?; stop}} " +
                        "| q { main {p?; p!<u>; stop}} " +
                        "| r { main {stop}}", "p.e->q; q.u->p; stop"),

                /* interaction with procedure */
                arrayOf<Any>("p { def X {q!<e>; stop} main {X}} " +
                        "| q { def X {p?; stop} main {X}} " +
                        "| r { main {stop}}", "p.e->q; stop"),

                /* interaction with procedure on the one of the processes */
                arrayOf<Any>("p {main{q?;stop}} | q { def X {p!<e>;stop} main{X}}", "q.e->p; stop"))
    }
}