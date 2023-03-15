package util

import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*

object NetworkUtils {
    fun purgeNetwork( network: Network ): Network {
        val map = HashMap<ProcessName, ProcessTerm>()
        network.processes.forEach { (processName, processTerm) ->
            val exclude =
                when( processTerm.main ) {
                    is TerminationSP -> true
                    is ProcedureInvocationSP -> processTerm.procedures[(processTerm.main as ProcedureInvocationSP).procedure]!! is TerminationSP
                    else -> false
                }
            if ( !exclude ) {
                map[processName] = processTerm
            }
        }

        if ( map.isEmpty() ) {
            val processName = network.processes.keys.first()
            map[processName] = network.processes[processName]!!
        }

        return Network(map)
    }

    fun isInteraction(b: Behaviour):Boolean = b is SendSP || b is ReceiveSP || b is SelectionSP || b is OfferingSP
}