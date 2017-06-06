package ast.sp.nodes

import ast.sp.interfaces.SPNode

data class FakeProcedureInvocation(private val procedure: String, private val link: Network): SPNode
