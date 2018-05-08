package ast.sp.nodes

import ast.sp.nodes.interfaces.IBehaviour

class TerminationSP : IBehaviour {
    private val termination = "stop"

    override fun toString() = termination

    override fun copy(): IBehaviour = TerminationSP()

    override fun equals(b: IBehaviour) = b is TerminationSP && b.termination == termination
}