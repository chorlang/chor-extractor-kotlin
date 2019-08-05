package util.fuzzing

import antlrgen.NetworkParser
import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*
import util.NetworkUtils
import util.ParseUtils
import java.lang.IllegalStateException
import kotlin.random.Random

data class FuzzerParams(var deletions:Int, var swaps:Int)

object NetworkFuzzer {
    fun fuzz(inputNetwork:String, deletions:Int, swaps:Int):Network {
        val n = NetworkUtils.purgeNetwork(ParseUtils.stringToNetwork(inputNetwork))
        val processName = n.processes.keys.random()
        val processTerm = n.processes[processName]!!

        val keys = processTerm.procedures.keys.toMutableList()
        keys.add("main")

        val paramsMap = splitParams(FuzzerParams(deletions, swaps), keys)

        paramsMap.forEach { procName, params ->
            if( procName == "main" ) {
                processTerm.main = fuzzProcess( processTerm.main, ProcessSize.compute(processTerm.main), params )
            } else {
                processTerm.procedures[procName] = fuzzProcess( processTerm.procedures[procName]!!, ProcessSize.compute(processTerm.procedures[procName]!!), params )
            }
        }
        return n
    }

    private fun decDels(params:FuzzerParams) = FuzzerParams(params.deletions - 1, params.swaps)
    private fun decSwaps(params:FuzzerParams) = FuzzerParams(params.deletions, params.swaps - 1)

    private fun fuzzProcess(b: Behaviour, size:Int, params:FuzzerParams): Behaviour {
        println(b)
        if( params.deletions + params.swaps == 0 )
            return b

        if( size == 0 )
            throw IllegalStateException("Reached size 0")

        if( Random.nextDouble() <= (params.deletions + params.swaps + 0.0)/size ) {
            val p = Random.nextInt(params.deletions + params.swaps)
            if(p < params.deletions) {
                // We do a deletion
                when( b ) {
                    is SendSP -> return fuzzProcess(b.continuation, size - 1, decDels(params))
                    is ReceiveSP -> return fuzzProcess(b.continuation, size - 1, decDels(params))
                    is SelectionSP -> return fuzzProcess(b.continuation, size - 1, decDels(params))
                    is OfferingSP -> {
                        val cont = b.branches[b.branches.keys.random()]!!
                        return fuzzProcess( cont, ProcessSize.compute(cont), decDels(params) )
                    }
                    is ConditionSP -> {
                        val cont = if ( Random.nextBoolean() ) b.thenBehaviour else b.elseBehaviour
                        return fuzzProcess( cont, ProcessSize.compute(cont), decDels(params) )
                    }
                    is TerminationSP -> throw IllegalStateException("Reached termination")
                    is ProcedureInvocationSP -> return fuzzProcess(TerminationSP(), 0, decDels(params))
                }
            } else {
                // We do a swap
                when( b ) {
                    is SendSP -> when( val cont = b.continuation ) {
                        is SendSP -> return SendSP(fuzzProcess(SendSP(cont.continuation, b.receiver, b.expression), size - 1, decSwaps(params)), cont.receiver, cont.expression)
                        is ReceiveSP -> return ReceiveSP(fuzzProcess(SendSP(cont.continuation, b.receiver, b.expression), size - 1, decSwaps(params)), cont.sender)
                        is SelectionSP -> return SelectionSP(fuzzProcess(SendSP(cont.continuation, b.receiver, b.expression), size - 1, decSwaps(params)), cont.receiver, cont.label)
                        is OfferingSP -> {
                            val splitParams = splitParams(decSwaps(params), cont.branches.keys.toList())
                            val branches = HashMap<String, Behaviour>()
                            val chosen = cont.branches.keys.random()
                            splitParams.forEach { label, lblParams ->
                                branches[label] =
                                    if (label == chosen)
                                            fuzzProcess(
                                                SendSP( cont.branches[label]!!, b.receiver, b.expression),
                                                ProcessSize.compute(cont.branches[label]!!) + 1,
                                                lblParams
                                            )
                                    else
                                        fuzzProcess(cont.branches[label]!!, ProcessSize.compute(cont.branches[label]!!), lblParams)
                            }
                            return OfferingSP(cont.sender, branches)
                        }
                        is ConditionSP -> {
                            val splitParams = splitParams(decSwaps(params), listOf("then", "else"))
                            return ConditionSP(
                                    cont.expression,
                                    fuzzProcess( SendSP(cont.thenBehaviour, b.receiver, b.expression), ProcessSize.compute(cont.thenBehaviour), splitParams["then"]!! ),
                                    fuzzProcess( cont.elseBehaviour, ProcessSize.compute(cont.elseBehaviour), splitParams["else"]!! )
                            )
                        }
                        is TerminationSP -> return fuzzProcess(cont, 0, decSwaps(params))
                        is ProcedureInvocationSP -> return fuzzProcess(cont, 1, decSwaps(params))
                    }
                    is ReceiveSP -> when( val cont = b.continuation ) {
                        is SendSP -> return SendSP(fuzzProcess(ReceiveSP(cont.continuation, b.sender), size - 1, decSwaps(params)), cont.receiver, cont.expression)
                        is ReceiveSP -> return ReceiveSP(fuzzProcess(ReceiveSP(cont.continuation, b.sender), size - 1, decSwaps(params)), cont.sender)
                        is SelectionSP -> return SelectionSP(fuzzProcess(ReceiveSP(cont.continuation, b.sender), size - 1, decSwaps(params)), cont.receiver, cont.label)
                        is OfferingSP -> {
                            val splitParams = splitParams(decSwaps(params), cont.branches.keys.toList())
                            val branches = HashMap<String, Behaviour>()
                            val chosen = cont.branches.keys.random()
                            splitParams.forEach { label, lblParams ->
                                branches[label] =
                                        if (label == chosen)
                                            fuzzProcess(
                                                    ReceiveSP( cont.branches[label]!!, b.sender),
                                                    ProcessSize.compute(cont.branches[label]!!) + 1,
                                                    lblParams
                                            )
                                        else
                                            fuzzProcess(cont.branches[label]!!, ProcessSize.compute(cont.branches[label]!!), lblParams)
                            }
                            return OfferingSP(cont.sender, branches)
                        }
                        is ConditionSP -> {
                            val splitParams = splitParams(decSwaps(params), listOf("then", "else"))
                            return ConditionSP(
                                    cont.expression,
                                    fuzzProcess( ReceiveSP(cont.thenBehaviour, b.sender), ProcessSize.compute(cont.thenBehaviour), splitParams["then"]!! ),
                                    fuzzProcess( cont.elseBehaviour, ProcessSize.compute(cont.elseBehaviour), splitParams["else"]!! )
                            )
                        }
                        is TerminationSP -> return fuzzProcess(cont, 0, decSwaps(params))
                        is ProcedureInvocationSP -> return fuzzProcess(cont, 1, decSwaps(params))
                    }
                    is SelectionSP -> when( val cont = b.continuation ) {
                        is SendSP -> return SendSP(fuzzProcess(SelectionSP(cont.continuation, b.receiver, b.label), size - 1, decSwaps(params)), cont.receiver, cont.expression)
                        is ReceiveSP -> return ReceiveSP(fuzzProcess(SelectionSP(cont.continuation, b.receiver, b.label), size - 1, decSwaps(params)), cont.sender)
                        is SelectionSP -> return SelectionSP(fuzzProcess(SelectionSP(cont.continuation, b.receiver, b.label), size - 1, decSwaps(params)), cont.receiver, cont.label)
                        is OfferingSP -> {
                            val splitParams = splitParams(decSwaps(params), cont.branches.keys.toList())
                            val branches = HashMap<String, Behaviour>()
                            val chosen = cont.branches.keys.random()
                            splitParams.forEach { label, lblParams ->
                                branches[label] =
                                        if (label == chosen)
                                            fuzzProcess(
                                                    SelectionSP( cont.branches[label]!!, b.receiver, b.label),
                                                    ProcessSize.compute(cont.branches[label]!!) + 1,
                                                    lblParams
                                            )
                                        else
                                            fuzzProcess(cont.branches[label]!!, ProcessSize.compute(cont.branches[label]!!), lblParams)
                            }
                            return OfferingSP(cont.sender, branches)
                        }
                        is ConditionSP -> {
                            val splitParams = splitParams(decSwaps(params), listOf("then", "else"))
                            return ConditionSP(
                                    cont.expression,
                                    fuzzProcess( SelectionSP(cont.thenBehaviour, b.receiver, b.label), ProcessSize.compute(cont.thenBehaviour), splitParams["then"]!! ),
                                    fuzzProcess( cont.elseBehaviour, ProcessSize.compute(cont.elseBehaviour), splitParams["else"]!! )
                            )
                        }
                        is TerminationSP -> return fuzzProcess(cont, 0, decSwaps(params))
                        is ProcedureInvocationSP -> return fuzzProcess(cont, 1, decSwaps(params))
                    }
                    is OfferingSP -> {
                        val chosen = b.branches.keys.random()
                        val cont = b.branches[chosen]!!
                        val branches = HashMap<String, Behaviour>()
                        b.branches.keys.forEach { label ->
                            branches[label] =
                                    if (label == chosen)
                                        when( cont ) {
                                            is SendSP -> cont.continuation
                                            is ReceiveSP -> cont.continuation
                                            is SelectionSP -> cont.continuation
                                            is OfferingSP -> cont.branches[cont.branches.keys.sorted().first()]!!
                                            is ConditionSP -> cont.thenBehaviour
                                            is TerminationSP -> throw IllegalStateException("Reached termination")
                                            is ProcedureInvocationSP -> TerminationSP()
                                            else -> throw IllegalStateException("Unreachable case")
                                        }
                                    else
                                        b.branches[label]!!
                        }

                        return when( cont ) {
                            is SendSP -> SendSP( fuzzProcess(OfferingSP(b.sender, branches), ProcessSize.compute(OfferingSP(b.sender, branches)), decSwaps(params)), cont.receiver, cont.expression)
                            is ReceiveSP -> ReceiveSP( fuzzProcess(OfferingSP(b.sender, branches), ProcessSize.compute(OfferingSP(b.sender, branches)), decSwaps(params)), cont.sender )
                            is SelectionSP -> SelectionSP( fuzzProcess(OfferingSP(b.sender, branches), ProcessSize.compute(OfferingSP(b.sender, branches)), decSwaps(params)), cont.receiver, cont.label )
                            is OfferingSP -> {
                                val splitParams = splitParams(decSwaps(params), cont.branches.keys.toList())
                                val newBranches = HashMap<String, Behaviour>()
                                splitParams.forEach { label, lblParams ->
                                    newBranches[label] =
                                            if (label == cont.branches.keys.sorted().first())
                                                fuzzProcess(
                                                        OfferingSP( b.sender, branches ),
                                                        ProcessSize.compute(OfferingSP( b.sender, branches )),
                                                        lblParams
                                                )
                                            else
                                                fuzzProcess(cont.branches[label]!!, ProcessSize.compute(cont.branches[label]!!), lblParams)
                                }
                                return OfferingSP(cont.sender, newBranches)
                            }
                            is ConditionSP -> {
                                val splitParams = splitParams(decSwaps(params), listOf("then", "else"))
                                ConditionSP(
                                        cont.expression,
                                        fuzzProcess(OfferingSP(b.sender, branches), ProcessSize.compute(OfferingSP(b.sender, branches)), splitParams["then"]!!),
                                        fuzzProcess(cont.elseBehaviour, ProcessSize.compute(cont.elseBehaviour), splitParams["else"]!!)
                                )
                            }
                            is TerminationSP -> return fuzzProcess(cont, 0, decSwaps(params))
                            is ProcedureInvocationSP -> return fuzzProcess(cont, 1, decSwaps(params))
                            else -> throw IllegalStateException("Unreachable case")
                        }
                    }
                    is TerminationSP -> throw IllegalStateException("Reached termination")
                    is ProcedureInvocationSP -> return fuzzProcess(TerminationSP(), 0, decSwaps(params))
                }
            }
        } else {
            // We fuzz the continuation
            when( b ) {
                is SendSP -> return SendSP(fuzzProcess(b.continuation, size - 1, params), b.receiver, b.expression)
                is ReceiveSP -> return ReceiveSP(fuzzProcess(b.continuation, size - 1, params), b.sender)
                is SelectionSP -> return SelectionSP(fuzzProcess(b.continuation, size - 1, params), b.receiver, b.label)
                is OfferingSP -> {
                    val splitParams = splitParams(params, b.branches.keys.toList())
                    val branches = HashMap<String, Behaviour>()
                    splitParams.forEach { label, lblParams ->
                        branches[label] = fuzzProcess( b.branches[label]!!, ProcessSize.compute(b.branches[label]!!), lblParams )
                    }
                    return OfferingSP(b.sender, branches)
                }
                is ConditionSP -> {
                    val splitParams = splitParams(params, listOf("then", "else"))
                    return ConditionSP(
                            b.expression,
                            fuzzProcess( b.thenBehaviour, ProcessSize.compute(b.thenBehaviour), splitParams["then"]!! ),
                            fuzzProcess( b.elseBehaviour, ProcessSize.compute(b.elseBehaviour), splitParams["else"]!! )
                    )
                }
                is TerminationSP -> throw IllegalStateException("Reached termination")
                is ProcedureInvocationSP -> throw IllegalStateException("Reached procedure call")
            }
        }

        throw IllegalStateException("Unreachable code")
    }

    private fun splitParams(params:FuzzerParams, keys:List<String>): Map<String, FuzzerParams> {
        val paramsMap = HashMap<String, FuzzerParams>()
        keys.forEach { paramsMap[it] = FuzzerParams(0, 0) }

        for (i in params.deletions downTo 1 ) {
            paramsMap[paramsMap.keys.random()]!!.deletions++
        }

        for (i in params.swaps downTo 1 ) {
            paramsMap[paramsMap.keys.random()]!!.swaps++
        }

        return paramsMap
    }
}