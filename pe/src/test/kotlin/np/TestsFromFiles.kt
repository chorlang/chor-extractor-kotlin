package np

import org.junit.jupiter.api.Test
import java.io.File
import java.io.PrintStream

class TestsFromFiles {
    @Test
    fun test() {
        //finding tests
        val dirPath = "tests"
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory() && dir.list().isNotEmpty())

        //configuration crap
        val tests = LinkedHashMap<String, String>()
        val testName = "([*]{3}) C([0-9]+) ([*]{3})".toRegex()
        val emptyString =  "^\$".toRegex()
        var title = ""
        var text = ""

        //collecting choreographies from f files
        for (fileName in dir.list()){
            val file = File("$dirPath/$fileName")
            file.forEachLine {
                when {
                    it.matches(testName) -> {
                        title = it.removeSurrounding("***").removeSurrounding(" ")
                    }
                    it.matches(emptyString) -> {
                        tests[title] = text
                        title = ""
                        text = ""
                    }
                    else -> text+=it
                }
            }
        }

        //process choreographies
        tests.forEach { title, test ->
            println("TEST: $title")
            println("CHOREOGRAPHY: $test")

            try {
                val result = NetworkProjection.project(test).toString()
                println("NETWORK: $result")
                println()
            } catch (e: MergingProjection.MergingException){
                e.printStackTrace(PrintStream(System.out))
                println()
            }

        }
    }
}