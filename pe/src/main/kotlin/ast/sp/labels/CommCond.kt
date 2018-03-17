package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel
import ast.sp.nodes.Network

sealed class CommCond

data class Comm(val node: Network, val lbl: ExtractionLabel): CommCond()
data class Cond(val node1: Network, val lbl1: ExtractionLabel, val node2: Network, val lbl2: ExtractionLabel): CommCond()