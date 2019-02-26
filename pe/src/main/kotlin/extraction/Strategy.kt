package extraction

import ast.sp.nodes.*
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Sorting strategy for the first action of each process in a network
 */
enum class Strategy {
    SelectionFirst {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network[process.key] = process.value.copy() }
            net.forEach { process -> if (process.value.main is SendSP || process.value.main is ReceiveSP) network[process.key] = process.value.copy() }
            net.forEach { process -> network[process.key] = process.value.copy() }
            return network
        }
    },
    ConditionFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process -> if (process.value.main is ConditionSP) network[process.key] = process.value.copy() }
            net.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network[process.key] = process.value.copy() }
            net.forEach { process -> network[process.key] = process.value.copy() }
            return network
        }
    },
    UnmarkedFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            marking.forEach { processName, marked ->
                if (!marked) {
                    ret[processName] = net.remove(processName)!!
                }
            }

            ret.putAll(net)
            return ret
        }
    },
    RandomProcess() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            net.keys.shuffled().forEach { ret[it] = net[it]!! }
            return ret
        }
    },
    LengthFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            val sortedProcessesist = net.entries.sortedBy { 0 - it.value.main.toString().length }
            sortedProcessesist.forEach { ret[it.key] = it.value }

            return ret
        }
    },
    ShortestFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()
            val sortedProcessesist = net.entries.sortedBy { it.value.main.toString().length }
            sortedProcessesist.forEach { ret[it.key] = it.value }

            return ret
        }
    },
    UnmarkedThenRandom() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedList = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedList = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()

            marking.forEach { processName, marked ->
                val processTerm = net[processName]!!
                if (marked){
                    markedList.add(AbstractMap.SimpleEntry(processName, processTerm))
                } else {
                    unmarkedList.add(AbstractMap.SimpleEntry(processName, processTerm))
                }
            }

            markedList.shuffle()
            unmarkedList.shuffle()

            unmarkedList.forEach { ret[it.key] = it.value }
            markedList.forEach { ret[it.key] = it.value }

            return ret
        }
    },
    UnmarkedThenSelection() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedSelections = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedSelections = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val markedSending = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedSending = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val markedElse = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedElse = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()

            marking.forEach { processName, marked ->
                val processTerm = net[processName]
                val main = processTerm!!.main
                when (main) {
                    is SelectionSP, is OfferingSP -> {
                        if (!marked) {
                            unmarkedSelections.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedSelections.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }
                    is SendSP, is ReceiveSP -> {
                        if (!marked) {
                            unmarkedSending.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedSending.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }
                    else -> {
                        if (!marked) {
                            unmarkedElse.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedElse.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }

                }
            }

            unmarkedSelections.forEach { ret[it.key] = it.value }
            unmarkedSending.forEach { ret[it.key] = it.value }
            unmarkedElse.forEach { ret[it.key] = it.value }
            markedSelections.forEach { ret[it.key] = it.value }
            markedSending.forEach { ret[it.key] = it.value }
            markedElse.forEach { ret[it.key] = it.value }

            return ret
        }
    },
    UnmarkedThenCondition() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val ret = LinkedHashMap<String, ProcessTerm>()

            val markedSelections = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedSelections = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val markedCondition = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedCondition = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val markedElse = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()
            val unmarkedElse = LinkedList<Map.Entry<ProcessName, ProcessTerm>>()

            marking.forEach { processName, marked ->
                val processTerm = net[processName]
                val main = processTerm!!.main
                when (main) {
                    is SelectionSP, is OfferingSP -> {
                        if (!marked) {
                            unmarkedSelections.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedSelections.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }
                    is ConditionSP -> {
                        if (!marked) {
                            unmarkedCondition.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedCondition.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }
                    else -> {
                        if (!marked) {
                            unmarkedElse.add(AbstractMap.SimpleEntry(processName, processTerm))
                        } else {
                            markedElse.add(AbstractMap.SimpleEntry(processName, processTerm))
                        }
                    }

                }
            }

            unmarkedCondition.forEach { ret[it.key] = it.value }
            unmarkedSelections.forEach { ret[it.key] = it.value }
            unmarkedElse.forEach { ret[it.key] = it.value }
            markedCondition.forEach { ret[it.key] = it.value }
            markedSelections.forEach { ret[it.key] = it.value }
            markedElse.forEach { ret[it.key] = it.value }

            return ret
        }
    },

    Default() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            return SelectionFirst.sort(marking, net)
        }
    };

    abstract fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm>
}