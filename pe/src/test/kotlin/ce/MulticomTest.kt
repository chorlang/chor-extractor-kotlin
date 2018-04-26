package ce

import ce.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith


@RunWith(Theories::class)
class MulticomTest : Assert() {

    @Test
    fun mult1(){
        val test =
                "p {main {q!<0>; q?; stop}} |" +
                "q {main {p!<1>; p?; stop}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult2(){
        val test =
                "p {main {q+R; q&{L: q!<e>; stop}}} |" +
                "q {main {p+L; p&{R: p?;stop}}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult3(){
        val test =
                "p {main {q!<0>; q?; stop}} |" +
                "q {main {r!<2>; r?; p!<1>; p?; stop}} |" +
                "r {main {q!<0>; q?; stop}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult4(){
        val test =
                "p {def X {q!<0>; q?; stop} main {X}} |" +
                "q {def X {p!<1>; p?; stop} main {X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult5(){
        val test =
                "p {def X {q!<0>; q?; stop} main {X}} |" +
                "q {def X {r!<2>; r?; p!<1>; p?; stop} main {X}} |" +
                "r {def X {q!<0>; q?; stop} main {X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult6(){
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                "q {def X {p!<1>; p?; X} main {X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult7(){
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                "q {def X {p?; p!<1>; X} main {p!<1>; X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult8(){
        val test =
                "p {def X {q!<0>; q?; X} main {X}} |" +
                "q {def X {r!<2>; r?; p!<1>; p?; X} main {X}} |" +
                "r {def X {q!<0>; q?; X} main {X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }



    @Test
    fun multN(){
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }
}
