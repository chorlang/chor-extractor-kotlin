package ce

import org.junit.Assert
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class TestTest : Assert() {
    @Theory
    @Throws(Exception::class)
    fun test(vararg testData: Any) {
        println("\n" + "Test Network: " + testData[0])
        val program = ChoreographyExtraction.main(arrayOf("-c", testData[0] as String))
        println("Choreography: " + program.toString())

        Assert.assertEquals(testData[1], program.toString())
    }

    companion object {

        @DataPoints
        @JvmField
        var data = arrayOf(

                /* interaction with recursive procedure */
                arrayOf<Any>(
                        "p {def X {q!<e>;X} main {X}} " +
                        "| q {def Y{p?; Y} main {Y}} " +
                                "| r {def X {s!<e>;X} main {X}} " +
                                "| s {def Y{r?; Y} main {Y}} ",


                        "def X1 { p.e->q; r.e->s; X1 } main {X1}")
        )
    }
}