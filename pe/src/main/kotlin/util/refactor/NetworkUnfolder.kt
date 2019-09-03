package util.refactor

import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*
import util.NetworkUtils
import util.ParseUtils
import java.lang.IllegalStateException
import kotlin.random.Random

object NetworkUnfolder {
    private fun compute(b:Behaviour, procedures:Map<String, Behaviour>, p:Double):Behaviour {
        return when( b ) {
            is TerminationSP -> TerminationSP()
            is SendSP -> SendSP(compute(b.continuation, procedures, p), b.receiver, b.expression)
            is ReceiveSP -> ReceiveSP(compute(b.continuation, procedures, p), b.sender)
            is SelectionSP -> SelectionSP(compute(b.continuation, procedures, p), b.receiver, b.label)
            is OfferingSP -> {
                val branches = HashMap<String, Behaviour>()
                b.branches.forEach { (key, value) -> branches[key] = compute(value, procedures, p) }
                OfferingSP(b.sender, branches)
            }
            is ConditionSP -> ConditionSP( b.expression, compute( b.thenBehaviour, procedures, p ), compute( b.elseBehaviour, procedures, p ) )
            is ProcedureInvocationSP -> {
                if ( Random.nextDouble() < p ) {
                    procedures[b.procedure]!!.copy()
                } else {
                    ProcedureInvocationSP(b.procedure)
                }
            }
            else -> throw IllegalStateException()
        }
    }

    private fun compute(processTerm:ProcessTerm, p:Double, iterations:Int):ProcessTerm {
        val result = processTerm.copy()

        for( i in 1..iterations ) {
            result.main = compute(result.main, result.procedures, p)
            for( name in result.procedures.keys ) {
                result.procedures[name] = compute( result.procedures[name]!!, processTerm.procedures, p )
            }
        }
        return result
    }

    fun compute(inputNetwork:String, p:Double, iterations:Int):Network {
        val n = NetworkUtils.purgeNetwork(ParseUtils.stringToNetwork(inputNetwork))

        for( processName in n.processes.keys ) {
            n.processes[processName] = compute(n.processes[processName]!!, p, iterations)
        }
        return n
    }
}
