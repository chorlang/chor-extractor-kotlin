package ce

import org.junit.Assert
import org.junit.Test

class TerminationTest : Assert() {

    @Test
    fun tst1(){
        val test = "p {main{stop}} | q {main{stop}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){
        val test = "p {main{stop}} | q {def X {stop} main{X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    /*@Test
    fun tst3(){
        val test = "p { def X{stop} main{q!<e>; X}} | q {def X {stop} main{p?; X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }*/
}