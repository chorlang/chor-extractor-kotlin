package ce

import ast.sp.nodes.Network

interface Node

data class ConcreteNode(val nodenet: Network, val str: String, val bad: Bad) : Node {
        fun copy(): ConcreteNode {
            val newnet = nodenet.copy()
            val newb = HashSet<ConcreteNode>()
            bad.badset.mapTo(newb) { it.copy() }

            return ConcreteNode(newnet, "" + str, Bad(newb))
        }
    }

data class Bad(val badset: HashSet<ConcreteNode>)

data class FakeNode(val procedureName: String, val source: Node) : Node
