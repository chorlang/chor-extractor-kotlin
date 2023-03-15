package util.refactor

import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*
import util.NetworkUtils
import util.ParseUtils
import java.lang.IllegalStateException
import kotlin.random.Random

object NetworkShifter {
    private fun computeShift(name:String, b:Behaviour, p:Double):Pair<Behaviour, Behaviour> {
        return when( b ) {
            is SendSP -> {
                if ( Random.nextDouble() < p ) {
                    val (prefix, newBody) = computeShift(name, b.continuation, p)
                    Pair(SendSP(prefix, b.receiver, b.expression), newBody)
                } else {
                    Pair(ProcedureInvocationSP(name), b)
                }
            }
            is ReceiveSP -> {
                if ( Random.nextDouble() < p ) {
                    val (prefix, newBody) = computeShift(name, b.continuation, p)
                    Pair(ReceiveSP(prefix, b.sender), newBody)
                } else {
                    Pair(ProcedureInvocationSP(name), b)
                }
            }
            is SelectionSP -> {
                if ( Random.nextDouble() < p ) {
                    val (prefix, newBody) = computeShift(name, b.continuation, p)
                    Pair(SelectionSP(prefix, b.receiver, b.label), newBody)
                } else {
                    Pair(ProcedureInvocationSP(name), b)
                }
            }
            else -> Pair(ProcedureInvocationSP(name), b)
        }
    }

    private fun replace(newCalls:Map<String, Behaviour>, b:Behaviour):Behaviour = when( b ) {
        is TerminationSP -> b
        is ProcedureInvocationSP -> newCalls[b.procedure]!!.copy()
        is SendSP -> SendSP(replace(newCalls, b.continuation), b.receiver, b.expression)
        is ReceiveSP -> ReceiveSP(replace(newCalls, b.continuation), b.sender)
        is SelectionSP -> SelectionSP(replace(newCalls, b.continuation), b.receiver, b.label)
        is OfferingSP -> {
            val branches = HashMap<String, Behaviour>()
            b.branches.forEach { (k, v) -> branches[k] = replace(newCalls, v) }
            OfferingSP(b.sender, branches)
        }
        is ConditionSP -> ConditionSP(b.expression, replace(newCalls, b.thenBehaviour), replace(newCalls, b.elseBehaviour))
        else -> throw IllegalStateException()
    }

    fun compute(inputNetwork:String, p:Double):Network {
        val n = NetworkUtils.purgeNetwork(ParseUtils.stringToNetwork(inputNetwork))

        for( processTerm in n.processes.values ) {
            val newCalls = HashMap<String, Behaviour>()
            val newDefs = HashMap<String, Behaviour>()
            processTerm.procedures.forEach { (procedureName, procedureBody) ->
                val (newCall, newDef) = computeShift(procedureName, procedureBody, p)
                newCalls[procedureName] = newCall
                newDefs[procedureName] = newDef
            }
            processTerm.main = replace(newCalls, processTerm.main)
            newDefs.forEach { (name, newDef) -> processTerm.procedures[name] = replace(newCalls, newDef) }
        }

        return n
    }
}
