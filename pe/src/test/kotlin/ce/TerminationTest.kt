package ce

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TerminationTest {

    @Test
    fun tst1(){
        val test = "p {main{stop}} | q {main{stop}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){
        val test = "p {main{stop}} | q {def X {stop} main{X}}"
        val args = arrayListOf("-c", test)

        val actual = Extraction.main(args).toString()
        val expected = "main {stop}"

        assertEquals(expected, actual)
    }

    /*@Test
    fun tst3(){
        value f = "p { def X{stop} main{q!<e>; X}} | q {def X {stop} main{p?; X}}"
        value args = arrayListOf("-c", f)

        value actual = Extraction.main(args)
        value expected = "main {stop}"

        assertEquals(expected, actual)
    }*/
}