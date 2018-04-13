package ce

import ce.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith


@RunWith(Theories::class)
class MulticomTest : Assert() {

    @Test
    fun nbtst1(){
        val test =
                "p {main {q!<0>; q?; stop}} |" +
                "q {main {p!<1>; p?; stop}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }
}
