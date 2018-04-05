package ce

import org.junit.Assert
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class TerminationTest : Assert() {

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

                /* simple termination */
                arrayOf<Any>("p {main{stop}} | q {main{stop}}", "main {stop}"),

                /* recursive termination */
                arrayOf<Any>("p {main{stop}} | q {def X {stop} main{X}}", "main {stop}"))
    }
}