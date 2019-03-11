package extraction

import ast.sp.nodes.*
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Sorting strategy for the first action of each process in a network
 */
enum class ExtractionStrategy {
    SelectionFirst {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            node.network.processes.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network[process.key] = process.value.copy() }
            node.network.processes.forEach { process -> if (process.value.main is SendSP || process.value.main is ReceiveSP) network[process.key] = process.value.copy() }
            node.network.processes.forEach { process -> network[process.key] = process.value.copy() }
            return network
        }
    },
    ConditionFirst() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            node.network.processes.forEach { process -> if (process.value.main is ConditionSP) network[process.key] = process.value.copy() }
            node.network.processes.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network[process.key] = process.value.copy() }
            node.network.processes.forEach { process -> network[process.key] = process.value.copy() }
            return network
        }
    },
    UnmarkedFirst() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            node.network.processes.forEach { processName, processTerm ->
                if ( !node.marking[processName]!! ) {
                    ret[processName] = processTerm.copy()
                }
            }

            node.network.processes.forEach { processName, processTerm ->
                if ( node.marking[processName]!! ) {
                    ret[processName] = processTerm.copy()
                }
            }

            return ret
        }
    },
    Random() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            node.network.processes.keys.shuffled().forEach { ret[it] = node.network.processes[it]!!.copy() }
            return ret
        }
    },
    LengthFirst() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            node.network.processes.entries.sortedByDescending { it.value.main.toString().length }.forEach { ret[it.key] = it.value.copy() }
            return ret
        }
    },
    ShortestFirst() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            node.network.processes.entries.sortedBy { it.value.main.toString().length }.forEach { ret[it.key] = it.value.copy() }
            return ret
        }
    },
    UnmarkedThenRandom() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedList = ArrayList<ProcessName>()
            val unmarkedList = ArrayList<ProcessName>()

            node.marking.forEach { processName, marked ->
                if (marked) {
                    markedList.add(processName)
                } else {
                    unmarkedList.add(processName)
                }
            }

            unmarkedList.shuffle()
            markedList.shuffle()

            unmarkedList.forEach { ret[it] = node.network.processes[it]!!.copy() }
            markedList.forEach { ret[it] = node.network.processes[it]!!.copy() }

            return ret
        }
    },
    UnmarkedThenSelection() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedSelections = ArrayList<ProcessName>()
            val unmarkedSelections = ArrayList<ProcessName>()
            val markedSending = ArrayList<ProcessName>()
            val unmarkedSending = ArrayList<ProcessName>()
            val markedElse = ArrayList<ProcessName>()
            val unmarkedElse = ArrayList<ProcessName>()

            node.marking.forEach { processName, marked ->
                val main = node.network.processes[processName]!!.main
                when (main) {
                    is SelectionSP, is OfferingSP -> {
                        if (!marked) {
                            unmarkedSelections.add(processName)
                        } else {
                            markedSelections.add(processName)
                        }
                    }
                    is SendSP, is ReceiveSP -> {
                        if (!marked) {
                            unmarkedSending.add(processName)
                        } else {
                            markedSending.add(processName)
                        }
                    }
                    else -> {
                        if (!marked) {
                            unmarkedElse.add(processName)
                        } else {
                            markedElse.add(processName)
                        }
                    }

                }
            }

            val copyFun = { processName:ProcessName -> ret[processName] = node.network.processes[processName]!!.copy() }

            unmarkedSelections.forEach(copyFun)
            unmarkedSending.forEach(copyFun)
            unmarkedElse.forEach(copyFun)
            markedSelections.forEach(copyFun)
            markedSending.forEach(copyFun)
            markedElse.forEach(copyFun)

            return ret
        }
    },
    UnmarkedThenCondition() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedSelections = ArrayList<ProcessName>()
            val unmarkedSelections = ArrayList<ProcessName>()
            val markedConditions = ArrayList<ProcessName>()
            val unmarkedConditions = ArrayList<ProcessName>()
            val markedElse = ArrayList<ProcessName>()
            val unmarkedElse = ArrayList<ProcessName>()

            node.marking.forEach { processName, marked ->
                val main = node.network.processes[processName]!!.main
                when (main) {
                    is SelectionSP, is OfferingSP -> {
                        if (!marked) {
                            unmarkedSelections.add(processName)
                        } else {
                            markedSelections.add(processName)
                        }
                    }
                    is ConditionSP -> {
                        if (!marked) {
                            unmarkedConditions.add(processName)
                        } else {
                            markedConditions.add(processName)
                        }
                    }
                    else -> {
                        if (!marked) {
                            unmarkedElse.add(processName)
                        } else {
                            markedElse.add(processName)
                        }
                    }

                }
            }

            val copyFun = { processName:ProcessName -> ret[processName] = node.network.processes[processName]!!.copy() }

            unmarkedConditions.forEach(copyFun)
            unmarkedSelections.forEach(copyFun)
            unmarkedElse.forEach(copyFun)
            markedConditions.forEach(copyFun)
            markedSelections.forEach(copyFun)
            markedElse.forEach(copyFun)

            return ret
        }
    },

    Default() {
        override fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm> = SelectionFirst.copyAndSort(node)
    };

    abstract fun copyAndSort(node: Extraction.ConcreteNode): HashMap<String, ProcessTerm>
}