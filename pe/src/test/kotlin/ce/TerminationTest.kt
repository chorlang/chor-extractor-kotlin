package ce

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

class TerminationTest {

    @Test
    fun tst1(){
        val test = "p {main{stop}} | q {main{stop}}"
        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){
        val test = "p {main{stop}} | q {def X {stop} main{X}}"
        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    /*@ParameterizedTest
    fun tst3(){
        val test = "p { def X{stop} main{q!<e>; X}} | q {def X {stop} main{p?; X}}"
        val args = arrayListOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }*/
}