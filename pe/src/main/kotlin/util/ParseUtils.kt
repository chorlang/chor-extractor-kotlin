package util

import antlrgen.ChoreographyLexer
import antlrgen.ChoreographyParser
import antlrgen.NetworkLexer
import antlrgen.NetworkParser
import antlrgen.NetworkParser.NetworkContext
import ast.cc.nodes.Program
import ast.sp.nodes.Network
import ast.sp.nodes.ProcessName
import ast.sp.nodes.ProcessTerm
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import util.networkStatistics.NetworkProcessesInvolved

object ParseUtils {
    fun parseChoreography(choreography: String): ChoreographyParser.ProgramContext {
        val stream = ANTLRInputStream(choreography)
        val lexer = ChoreographyLexer(stream)
        val parser = ChoreographyParser(CommonTokenStream(lexer))
        return parser.program()
    }

    fun stringToProgram(choreography: String): Program = ChoreographyASTToProgram.toProgram(parseChoreography(choreography))

    fun parseNetwork(network: String): NetworkContext {
        val stream = ANTLRInputStream(network)
        val lexer = NetworkLexer(stream)
        val parser = NetworkParser(CommonTokenStream(lexer))
        return parser.network()
    }


    fun stringToNetwork(network: String): Network = NetworkASTToNetwork.toNetwork(ParseUtils.parseNetwork(network))

    fun stringToNetworks(n: String): HashSet<Network> {
        val network = stringToNetwork(n)

        val listOfProcessesSets = splitNetworkProcesses(network)
        return createNetworkFromProcessesSet(listOfProcessesSets, network)
    }

    private fun createNetworkFromProcessesSet(listOfProcessesSets: ArrayList<HashSet<String>>, network: Network): HashSet<Network> {
        val listOfNetworks = hashSetOf<Network>()

        for (elem in listOfProcessesSets) {
            val processes = hashMapOf<ProcessName, ProcessTerm>()
            elem.forEach { process -> processes.put(process, network.processes[process]!!) }
            listOfNetworks.add(Network(processes))
        }

        return listOfNetworks
    }

    private fun splitNetworkProcesses(network: Network): ArrayList<HashSet<String>> {
        val listOfProcessesSets = ArrayList<HashSet<String>>()
        network.processes.forEach { processName, processTerm ->
            val setOfProcesses = hashSetOf(processName)
            val hashes = hashSetOf<Int>()
            setOfProcesses.addAll(NetworkProcessesInvolved().visit(processTerm))

            listOfProcessesSets.parallelStream().forEach { set ->
                if (setOfProcesses.intersect(set).isNotEmpty()) {
                    setOfProcesses.addAll(set)
                    //listOfProcessesSets.remove(set)
                    hashes.add(set.hashCode())
                }
            }

            listOfProcessesSets.removeIf{hashes.contains(it.hashCode())}
            listOfProcessesSets.add(setOfProcesses)
        }

        return listOfProcessesSets
    }
}
