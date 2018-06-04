package ce

import ast.sp.nodes.*
import java.util.*


enum class Strategy {
    SelectFirst {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network.put(process.key, process.value.copy()) }
            net.forEach { process -> if (process.value.main is SendingSP || process.value.main is ReceiveSP) network.put(process.key, process.value.copy()) }
            net.forEach { process -> network.put(process.key, process.value.copy()) }
            return network
        }
    },
    ConditionFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process -> if (process.value.main is ConditionSP) network.put(process.key, process.value.copy()) }
            net.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network.put(process.key, process.value.copy()) }
            net.forEach { process -> network.put(process.key, process.value.copy()) }
            return network
        }
    },
    UnmarkedFirst() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process ->
                val mark = marking[process.key]
                if (mark != null && !mark) network.put(process.key, process.value.copy())
            }
            net.forEach { process -> network.put(process.key, process.value.copy()) }
            return network
        }
    },
    RandomProcess() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()
            val shuffledKeys = net.keys.shuffled()
            shuffledKeys.forEach { network.put(""+it, net[it]!!.copy()) }
            return network
        }
    },
    UnmarkedThenRandom() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process ->
                val mark = marking[process.key]
                if (mark != null && !mark) network.put(process.key, process.value.copy())
            }

            val leftKeys = net.minus(network.keys)
            val shuffledKeys = leftKeys.keys.shuffled()
            shuffledKeys.forEach { network.put(""+it, net[it]!!.copy()) }

            return network
        }
    },
    UnmarkedThenSelect() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process ->
                val mark = marking[process.key]
                if (mark != null && !mark) network.put(process.key, process.value.copy())
            }

            val leftKeys = net.minus(network.keys)
            leftKeys.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network.put(process.key, process.value.copy()) }
            leftKeys.forEach { process -> if (process.value.main is SendingSP || process.value.main is ReceiveSP) network.put(process.key, process.value.copy()) }
            leftKeys.forEach { process -> network.put(process.key, process.value.copy()) }

            return network
        }
    },
    UnmarkedThenCondition() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            val network = LinkedHashMap<String, ProcessTerm>()

            net.forEach { process ->
                val mark = marking[process.key]
                if (mark != null && !mark) network.put(process.key, process.value.copy())
            }

            val leftKeys = net.minus(network.keys)
            leftKeys.forEach { process -> if (process.value.main is ConditionSP) network.put(process.key, process.value.copy()) }
            leftKeys.forEach { process -> if (process.value.main is SelectionSP || process.value.main is OfferingSP) network.put(process.key, process.value.copy()) }
            leftKeys.forEach { process -> network.put(process.key, process.value.copy()) }

            return network
        }
    },
    Default() {
        override fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm> {
            return ConditionFirst.sort(marking, net)
        }
    };

    abstract fun sort(marking: Marking, net: HashMap<String, ProcessTerm>): HashMap<String, ProcessTerm>
}