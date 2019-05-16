package bisim

import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.labels.ExtractionLabel
import ast.sp.labels.ExtractionLabel.ConditionLabel.ElseLabel
import ast.sp.labels.ExtractionLabel.ConditionLabel.ThenLabel
import ast.sp.labels.ExtractionLabel.InteractionLabel.SelectionLabel
import ast.sp.labels.ExtractionLabel.InteractionLabel.CommunicationLabel
import ast.sp.labels.ExtractionLabel.MulticomLabel
import ast.sp.nodes.ProcedureName
import util.ChoreographyASTToProgram
import util.ParseUtils.parseChoreography

val MAX_SIMULATION_COUNTER = 200

fun bisimilar( c1:String, c2:String ):Boolean
{
    val program1 = ChoreographyASTToProgram().getProgram(parseChoreography(c1)) as Program
    val program2 = ChoreographyASTToProgram().getProgram(parseChoreography(c2)) as Program

    return bisimilar( program1.choreographies, program2.choreographies )
}

fun similar( c1:String, c2:String ):Boolean
{
    val program1 = ChoreographyASTToProgram().getProgram(parseChoreography(c1)) as Program
    val program2 = ChoreographyASTToProgram().getProgram(parseChoreography(c2)) as Program

    return similar( program1.choreographies, program2.choreographies )
}

fun bisimilar( list1:List<Choreography?>, list2:List<Choreography?> ):Boolean
{
    if ( list1.size != 1 || list2.size != 1 ) {
        println("Bisimilarity not implemented for parallel choreographies")
        return true
    }

    return similar(list1,list2) && similar(list2,list1)
}

fun similar( list1:List<Choreography?>, list2:List<Choreography?> ):Boolean
{
    for( c1 in list1 ) {
        if ( c1 == null )
            return false

        var ok = false
        for( c2 in list2 ) {
            if ( c2 == null )
                continue

            if ( c1.processes.containsAll( c2.processes ) ) {
                if ( !similar( c1, c2 ) )
                    return false
                else
                    ok = true
            }
        }
        if (!ok)
            return false
    }
    return true
}

fun similar(c1:Choreography, c2:Choreography):Boolean
{
    val done:ArrayList<Pair<ChoreographyBody, ChoreographyBody>> = ArrayList()
    val todo:ArrayList<Pair<ChoreographyBody, ChoreographyBody>> = ArrayList()

    todo.add(Pair(c1.main, c2.main))

    var counter = 0

    while( todo.isNotEmpty() && counter < MAX_SIMULATION_COUNTER ) {
        counter++
        val (one, two) = todo.removeAt(0)
//        println("Getting actions")
        val actionsWithContinuations = getActionsWithContinuations(one, c1.procedures)
        for( (action1, continuation1) in actionsWithContinuations ) {
//            println("getting continuation ${two.toString().length}")
            val continuation2 = getContinuation( two, action1, c2.procedures )
            if ( continuation2 == null ) {
                println( "Could not match $action1 with continuation $two" )
                return false
            } else {
                if( !done.contains( Pair(continuation1,continuation2) ) && !todo.contains( Pair(continuation1, continuation2) ) ) {
//                    System.out.println( "TODO $continuation1, $continuation2 (size of todo: ${todo.size}, size of done: ${done.size})" )
//                    System.out.println( "TODO (size of todo: ${todo.size}, size of done: ${done.size})" )
                    todo.add( Pair( continuation1, continuation2 ) )
                }
            }
        }
//        System.out.println( "DONE $one, $two (size of todo: ${todo.size}, size of done: ${done.size})" )
//        System.out.println( "DONE (size of todo: ${todo.size}, size of done: ${done.size})" )
        done.add( Pair( one, two ) )
    }

    if ( counter == MAX_SIMULATION_COUNTER ) {
        println( "Warning: could not check simulation" )
    }

    return true
}

private fun pn(label: ExtractionLabel):Set<String>
{
    return when( label ) {
        is ThenLabel -> setOf(label.process)
        is ElseLabel -> setOf(label.process)
        is CommunicationLabel -> setOf(label.sender, label.receiver)
        is SelectionLabel -> setOf(label.sender, label.receiver)
        is MulticomLabel -> TODO()
    }
}

fun getContinuation( c:ChoreographyBody, label:ExtractionLabel, procedures:List<ProcedureDefinition> ):ChoreographyBody?
{
    when( c ) {
        is ProcedureInvocation -> {
            val proceduresCopy = ArrayList<ProcedureDefinition>()
            var procedureBody:ChoreographyBody? = null
            for( procedure in procedures ) {
                if( procedure.name != c.procedure ) {
                    proceduresCopy.add( procedure )
                } else {
                    procedureBody = procedure.body
                }
            }
            return if ( procedureBody == null ) null else getContinuation( procedureBody, label, proceduresCopy )
        }
        is CommunicationSelection -> {
            if (equalLabels(labelFromInteraction(c.eta), label)) {
                return c.continuation
            } else {
                val lNames = pn(label)
                if ( lNames.contains(c.eta.sender) || lNames.contains(c.eta.receiver) )
                    return null

                val cont = getContinuation(c.continuation, label, procedures)
                return if ( cont == null ) null else CommunicationSelection(c.eta, cont )
            }
        }
        is Condition -> {
            when( label ) {
                is ThenLabel -> {
                    if ( c.process == label.process && c.expression == label.expression ) {
                        return c.thenChoreography
                    } else {
                        if ( pn(label).contains(c.process) )
                            return null

                        val thenCont = getContinuation(c.thenChoreography, label, procedures)
                        val elseCont = getContinuation(c.elseChoreography, label, procedures)
                        return if ( thenCont == null || elseCont == null ) null else Condition( c.process, c.expression, thenCont, elseCont )
                    }
                }
                is ElseLabel -> {
                    if ( c.process == label.process && c.expression == label.expression ) {
                        return c.elseChoreography
                    } else {
                        if ( pn(label).contains(c.process) )
                            return null

                        val thenCont = getContinuation(c.thenChoreography, label, procedures)
                        val elseCont = getContinuation(c.elseChoreography, label, procedures)
                        return if ( thenCont == null || elseCont == null ) null else Condition( c.process, c.expression, thenCont, elseCont )
                    }
                }
                else -> {
                    if ( pn(label).contains(c.process) )
                        return null

                    val thenCont = getContinuation(c.thenChoreography, label, procedures)
                    val elseCont = getContinuation(c.elseChoreography, label, procedures)
                    return if ( thenCont == null || elseCont == null ) null else Condition( c.process, c.expression, thenCont, elseCont )
                }
            }
        }
        is Termination -> return null
        else -> return null
    }
}

fun equalLabels( l1:ExtractionLabel, l2:ExtractionLabel ):Boolean
{
    return when( l1 ) {
        is CommunicationLabel -> when( l2 ) { is CommunicationLabel -> l1.sender == l2.sender && l1.receiver == l2.receiver && l1.expression == l2.expression else -> false }
        is SelectionLabel -> when( l2 ) { is SelectionLabel -> l1.sender == l2.sender && l1.receiver == l2.receiver && l1.label == l2.label else -> false }
        is ThenLabel -> when( l2 ) { is ThenLabel -> l1.process == l2.process && l1.expression == l2.expression else -> false }
        is ElseLabel -> when( l2 ) { is ElseLabel -> l1.process == l2.process && l1.expression == l2.expression else -> false }
        is MulticomLabel -> TODO()
    }
}

fun getActionsWithContinuations( c:ChoreographyBody, procedures:List<ProcedureDefinition> ):List<Pair<ExtractionLabel, ChoreographyBody>>
{
    return when( c ) {
        is CommunicationSelection -> listOf(Pair(labelFromInteraction(c.eta), c.continuation))
        is Condition -> listOf(
                Pair(ThenLabel(c.process, c.expression), c.thenChoreography),
                Pair(ElseLabel(c.process, c.expression), c.elseChoreography)
        )
        is Termination -> listOf()
        is ProcedureInvocation -> getActionsWithContinuations(getProcedure(c.procedure, procedures), procedures)
        else -> throw IllegalArgumentException()
    }
}

fun getProcedure( name:ProcedureName, procedures:List<ProcedureDefinition> ):ChoreographyBody
{
    for( procedure in procedures ) {
        if( procedure.name == name ) {
            return procedure.body
        }
    }
    throw IllegalArgumentException( "Called a name that does not exist: $name" )
}

fun labelFromInteraction(interaction:Interaction):ExtractionLabel
{
    return when( interaction ) {
        is Communication -> CommunicationLabel(interaction.sender, interaction.receiver, interaction.expression)
        is Selection -> SelectionLabel(interaction.sender, interaction.receiver, interaction.label)
        else -> throw IllegalArgumentException()
    }
}