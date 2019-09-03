package util.fuzzing

import ast.sp.interfaces.Behaviour
import ast.sp.nodes.*
import util.NetworkUtils
import util.ParseUtils
import java.lang.IllegalStateException
import kotlin.random.Random
import kotlin.random.nextInt

data class FuzzerParams(var deletions:Int, var swaps:Int)

object NetworkFuzzer {
    fun fuzz(inputNetwork:String, deletions:Int, swaps:Int):Network {
        val n = NetworkUtils.purgeNetwork(ParseUtils.stringToNetwork(inputNetwork))
        val processName = n.processes.keys.random()
        val processTerm = n.processes[processName]!!

        val keys = processTerm.procedures.keys.toMutableList()
        val sizeList = keys.map {
            ProcessSize.compute( processTerm.procedures[it]!! )
        }.toMutableList()
        keys.add("main")
        sizeList.add( ProcessSize.compute( processTerm.main ) )

        val paramsMap = splitParams(FuzzerParams(deletions, swaps), mkSizes(keys, sizeList))

        paramsMap.forEach { procName, params ->
            if( procName == "main" ) {
                processTerm.main = fuzzProcess( processTerm.main, ProcessSize.compute(processTerm.main), params )
            } else {
                processTerm.procedures[procName] = fuzzProcess( processTerm.procedures[procName]!!, ProcessSize.compute(processTerm.procedures[procName]!!), params )
            }
        }
        return n
    }

    private fun mkSizes(keys:List<String>, values:List<Int>):Map<String, Int> {
        assert(keys.size == values.size)
        val map = HashMap<String, Int>()
        for( i in 0 until keys.size ) {
            map[keys[i]] = values[i]
        }
        return map
    }

    private fun decDels(params:FuzzerParams) = FuzzerParams(params.deletions - 1, params.swaps)
    private fun decSwaps(params:FuzzerParams) = FuzzerParams(params.deletions, params.swaps - 1)

    private fun fuzzProcess(b: Behaviour, size:Int, params:FuzzerParams): Behaviour {
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( cont.branches.keys.toList(), cont.branches.values.map { ProcessSize.compute(it) }.toList() ))
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( listOf("then", "else"), listOf( ProcessSize.compute(cont.thenBehaviour), ProcessSize.compute(cont.elseBehaviour) ) ) )
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( cont.branches.keys.toList(), cont.branches.values.map { ProcessSize.compute(it) }.toList() ) )
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( listOf("then", "else"), listOf( ProcessSize.compute(cont.thenBehaviour), ProcessSize.compute(cont.elseBehaviour) ) ))
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( cont.branches.keys.toList(), cont.branches.values.map { ProcessSize.compute(it) }.toList() ))
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
                            val splitParams = splitParams(decSwaps(params), mkSizes( listOf("then", "else"), listOf( ProcessSize.compute(cont.thenBehaviour), ProcessSize.compute(cont.elseBehaviour) ) ))
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
                                val splitParams = splitParams(decSwaps(params), mkSizes( cont.branches.keys.toList(), cont.branches.values.map { ProcessSize.compute(it) }.toList() ))
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
                                val splitParams = splitParams(decSwaps(params), mkSizes( listOf("then", "else"), listOf( ProcessSize.compute(cont.thenBehaviour), ProcessSize.compute(cont.elseBehaviour) ) ))
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
                    is ConditionSP -> return when( val cont = b.thenBehaviour ) {
                        is SendSP -> SendSP( fuzzProcess(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour), ProcessSize.compute(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour)), decSwaps(params)), cont.receiver, cont.expression)
                        is ReceiveSP -> ReceiveSP( fuzzProcess(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour), ProcessSize.compute(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour)), decSwaps(params)), cont.sender )
                        is SelectionSP -> SelectionSP( fuzzProcess(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour), ProcessSize.compute(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour)), decSwaps(params)), cont.receiver, cont.label )
                        is OfferingSP -> {
                            val splitParams = splitParams(decSwaps(params), mkSizes( cont.branches.keys.toList(), cont.branches.values.map { ProcessSize.compute(it) }.toList() ))
                            val newBranches = HashMap<String, Behaviour>()
                            splitParams.forEach { label, lblParams ->
                                newBranches[label] =
                                        if (label == cont.branches.keys.sorted().first())
                                            fuzzProcess(
                                                    ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour),
                                                    ProcessSize.compute(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour)),
                                                    lblParams
                                            )
                                        else
                                            fuzzProcess(cont.branches[label]!!, ProcessSize.compute(cont.branches[label]!!), lblParams)
                            }
                            return OfferingSP(cont.sender, newBranches)
                        }
                        is ConditionSP -> {
                            val splitParams = splitParams(decSwaps(params), mkSizes( listOf("then", "else"), listOf( ProcessSize.compute(cont.thenBehaviour), ProcessSize.compute(cont.elseBehaviour) ) ))
                            ConditionSP(
                                    cont.expression,
                                    fuzzProcess(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour), ProcessSize.compute(ConditionSP(b.expression, b.thenBehaviour, b.elseBehaviour)), splitParams["then"]!!),
                                    fuzzProcess(cont.elseBehaviour, ProcessSize.compute(cont.elseBehaviour), splitParams["else"]!!)
                            )
                        }
                        is TerminationSP -> return fuzzProcess(cont, 0, decSwaps(params))
                        is ProcedureInvocationSP -> return fuzzProcess(cont, 1, decSwaps(params))
                        else -> throw IllegalStateException("Unreachable case")
                    }
                    else -> throw IllegalStateException("Unreachable code")
                }
            }
            throw IllegalStateException("Unreachable code")
        } else {
            // We fuzz the continuation
            when (b) {
                is SendSP -> return SendSP(fuzzProcess(b.continuation, size - 1, params), b.receiver, b.expression)
                is ReceiveSP -> return ReceiveSP(fuzzProcess(b.continuation, size - 1, params), b.sender)
                is SelectionSP -> return SelectionSP(fuzzProcess(b.continuation, size - 1, params), b.receiver, b.label)
                is OfferingSP -> {
                    val splitParams = splitParams(params, mkSizes(b.branches.keys.toList(), b.branches.values.map { ProcessSize.compute(it) }.toList()))
                    val branches = HashMap<String, Behaviour>()
                    splitParams.forEach { label, lblParams ->
                        branches[label] = fuzzProcess(b.branches[label]!!, ProcessSize.compute(b.branches[label]!!), lblParams)
                    }
                    return OfferingSP(b.sender, branches)
                }
                is ConditionSP -> {
                    val splitParams = splitParams(params, mkSizes(listOf("then", "else"), listOf(ProcessSize.compute(b.thenBehaviour), ProcessSize.compute(b.elseBehaviour))))
                    return ConditionSP(
                            b.expression,
                            fuzzProcess(b.thenBehaviour, ProcessSize.compute(b.thenBehaviour), splitParams["then"]!!),
                            fuzzProcess(b.elseBehaviour, ProcessSize.compute(b.elseBehaviour), splitParams["else"]!!)
                    )
                }
                is TerminationSP -> throw IllegalStateException("Reached termination")
                is ProcedureInvocationSP -> throw IllegalStateException("Reached procedure call")
                else -> throw IllegalStateException("Unreachable code")
            }
        }
    }

    private fun splitParams(params:FuzzerParams, sizes:Map<String, Int>): Map<String, FuzzerParams> {
        val paramsMap = HashMap<String, FuzzerParams>()
        sizes.keys.forEach { paramsMap[it] = FuzzerParams(0, 0) }

        var dels = params.deletions
        var swaps = params.swaps
        while(dels + swaps > 0) {
            val chooseDel = Random.nextInt(dels + swaps) < dels

            var flag = false
            while( !flag ) {
                val random = paramsMap.keys.random()
                if ( paramsMap[random]!!.deletions + paramsMap[random]!!.swaps < sizes[random]!!) {
                    if ( chooseDel ) {
                        paramsMap[random]!!.deletions++
                        dels--
                    } else {
                        paramsMap[random]!!.swaps++
                        swaps--
                    }
                    flag = true
                }
            }
        }

        return paramsMap
    }
}