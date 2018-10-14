package benchmark

import ast.sp.nodes.Network
import ast.sp.nodes.ProcessName
import util.networkStatistic.*


class NetworkScrewer {
    fun screwNetwork(network: Network): Pair<Network, ScrewedInformation> {
        val networkCopy = network.copy()
        val (networkAdd, add) = addAction(networkCopy)
        val (networkRemove, remove) = removeAction(networkAdd)
        val (networkSwap, swap) = swapActions(networkRemove)

        return Pair(networkSwap, ScrewedInformation(add, remove, swap))

    }

    private fun swapActions(network: Network): Pair<Network, String> {
        val (process, position) = getProcessAndPosition(network, OperationType.SwapActions)
        return if (process == null || position == null) {
            Pair(network, "no-swap")
        } else {
            val newProcessTerm = NetworkSwapActions().swapActions(network.processes[process]!!, position)
            network.processes.replace(process, newProcessTerm)
            Pair(network, "swap-$process-$position")
        }
    }

    private fun removeAction(network: Network): Pair<Network, String> {
        val (process, position) = getProcessAndPosition(network, OperationType.RemoveAction)
        return if (process == null || position == null) {
            Pair(network, "no-remove")
        } else {
            val newProcessTerm = NetworkRemoveActions().removeAction(network.processes[process]!!, position)
            network.processes.replace(process, newProcessTerm)
            return Pair(network, "remove-$process-$position")
        }
    }

    private fun addAction(network: Network): Pair<Network, String> {
        val (process, position) = getProcessAndPosition(network, OperationType.RemoveAction)
        return if (process == null || position == null) {
            Pair(network, "no-add")
        } else {
            val newProcessTerm = NetworkAddActions().addAction(network.processes[process]!!, position)
            network.processes.replace(process, newProcessTerm)
            return Pair(network, "add-$process-$position")
        }
    }


    /*private fun swapActions(processTerm: ProcessTerm, position: Int) = NetworkSwapActions().swapActions( processTerm, position)
    private fun addAction(processTerm: ProcessTerm, position: Int) = NetworkAddActions().addAction(processTerm, position)
    private fun removeAction(processTerm: ProcessTerm, position: Int) = NetworkRemoveActions().removeAction(processTerm, position)*/

    private fun random(l: Int) = (0..l).shuffled().first()

    private fun getProcessAndPosition(network: Network, operationType: OperationType): Pair<ProcessName?, Int?> {
        val tempKeys = network.processes.keys.toList() as ArrayList<String>
        var tempSize = network.processes.size

        while (tempKeys.isNotEmpty()) {
            val key = random(tempSize - 1)
            val process = tempKeys[key]

            val numOfActions = NetworkProcessActionsForScrewer().visit(network.processes[process]!!)
            if ((operationType.value == "swap" &&  numOfActions < 2) || numOfActions < 1) {
                    tempSize--
                    tempKeys.remove(process)
            } else {
                val position = random(numOfActions-1)
                return Pair(process, position)
            }
        }

        return Pair(null, null)
    }
}

data class ScrewedInformation(val add: String, val remove: String, val swap: String)

enum class OperationType(val value: String) {
    AddAction("add"),
    RemoveAction("remove"),
    SwapActions("swap")
}
