import extraction.Extraction
import extraction.Strategy

class Utils {
    companion object {
        fun resolveArgs(str: String, debugMode: Boolean): Pair<ArrayList<String>, Strategy> {
            val args = arrayListOf<String>()

            val strategy = Extraction.parseStrategy(str)
            args.add("-s")
            args.add(strategy.toString())

            if (debugMode) args.add("-d")
            args.add("-c")

            return Pair(args, strategy)
        }
    }
}