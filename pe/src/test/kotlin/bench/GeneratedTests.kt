package bench

import org.junit.jupiter.api.Test
import java.io.File
import java.text.ParseException

class GeneratedTests {

    @Test
    fun run() {
        val filesWithNetworks = parseFiles("tests")
        filesWithNetworks.forEach { file ->
            file.forEach { name, network ->

            }
        }

    }

    fun parseFiles(dirPath: String): ArrayList<HashMap<String, String>> {
        val dir = File(dirPath)
        require(dir.exists() && dir.isDirectory())

        val fileWithNetworks = ArrayList<HashMap<String, String>>()

        for (file in dir.list()) {
            val f = File("$dirPath/$file")
            fileWithNetworks.add(parse(f))
        }

        return fileWithNetworks
    }

    fun parse(file: File): HashMap<String, String> {
        var network = ""
        var name = ""
        val networksMap = HashMap<String, String>()

        file.forEachLine { line ->
            when {
                line.startsWith("***") -> name = line.substring(4, 7).trim()
                line.startsWith("def") -> network = line
                line.startsWith("main") -> network = "$network $line"
                line.isEmpty() -> {
                    networksMap.put(name, network)
                    network = ""
                    name = ""
                }
                else -> throw ParseException("line $line was unexpected", 0)
            }
        }

        return  networksMap
    }

}
