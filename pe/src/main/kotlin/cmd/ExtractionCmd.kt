package cmd

import cmd.internal.PaperTest

object ExtractionCmd {
    @JvmStatic
    fun main(args:Array<String>) {
        if ( args.isEmpty() ) {
            println("No arguments provided. Please see Readme.txt for instructions.")
        } else when( args[0] ) {
            "orig" -> runTests(PaperTest)
            else -> println("Couldn't recognise arguments. Please see Readme.txt for instructions.")
        }
    }

    private fun runTests(obj:Any) {
        obj.javaClass.methods.forEach {
            val t1 = System.currentTimeMillis()
            it.invoke(obj)
            println("Elapsed time: ${System.currentTimeMillis() - t1}ms")
        }
    }
}
