package ce

import np.MergingProjection
import np.NetworkProjection
import org.junit.jupiter.api.Test
import lcf.ChoreographyGenerator

class GeneratedTest {

    /*Test 1: communications only, increasing lengths*/
    @Test
    fun test1() {
        for (i in 10..100 step 10) {
            val tester = ChoreographyGenerator(i, 6, 0, 0)
            val test = tester.generate().toString()
            println("Behaviour: $test")
            println("Network: ${NetworkProjection.project(test)}")
        }
    }

    /*Test 2: communications and ifs, fixed length, increasing number of ifs*/
    @Test
    fun test2() {
        for (i in 10..50 step 10) {
            val tester = ChoreographyGenerator(50, 6, i, 0)
            val test = tester.generate().toString()
            println("Behaviour: $test")
            try {
                println("Network: ${NetworkProjection.project(test)}")
            } catch (m: MergingProjection.MergingException) { continue }
        }
    }

    /*Test 3: inserting recursion; two varying parameters - #ifs and #procs*/
    @Test
    fun test3() {
        for (i in 0..5) {
            for (j in 0..5) {
                val tester = ChoreographyGenerator(10, 5, i, j)
                val test = tester.generate().toString()
                println("Behaviour: $test")
                try {
                    println("Network: ${NetworkProjection.project(test)}")
                } catch (m: MergingProjection.MergingException) { continue }
            }
        }
    }

    /*Test 4: communications only, fixed length, increasing number of processesInChoreography*/
    @Test
    fun test4() {
        for (i in 5..100 step 5) {
            val tester = ChoreographyGenerator(40, i, 0, 0)
            val test = tester.generate().toString()
            println("Behaviour: $test")
            try {
                println("Network: ${NetworkProjection.project(test)}")
            } catch (m: MergingProjection.MergingException) { continue }
        }
    }
}


