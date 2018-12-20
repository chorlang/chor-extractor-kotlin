package bisim

import antlrgen.ChoreographyLexer
import antlrgen.ChoreographyParser
import ast.sp.labels.interfaces.ExtractionLabel
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.labels.ElseLabel
import ast.sp.labels.SelectionLabel
import ast.sp.labels.SendingLabel
import ast.sp.labels.ThenLabel
import ast.sp.nodes.ProcedureName
import np.ChoreographyVisitor
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import ast.cc.interfaces.ChoreographyBody

private fun parseChoreography(choreography: String): ChoreographyParser.ProgramContext
{
    val stream = ANTLRInputStream(choreography)
    val lexer = ChoreographyLexer(stream)
    val parser = ChoreographyParser(CommonTokenStream(lexer))
    return parser.program()
}

fun bisimilar( c1:String, c2:String ):Boolean
{
    val program1 = ChoreographyVisitor().getProgram(parseChoreography(c1)) as Program
    val program2 = ChoreographyVisitor().getProgram(parseChoreography(c2)) as Program

    return bisimilar( program1.choreographies, program2.choreographies )
}

fun similar( c1:String, c2:String ):Boolean
{
    val program1 = ChoreographyVisitor().getProgram(parseChoreography(c1)) as Program
    val program2 = ChoreographyVisitor().getProgram(parseChoreography(c2)) as Program

    return similar( program1.choreographies, program2.choreographies )
}

fun bisimilar( list1:List<Choreography?>, list2:List<Choreography?> ):Boolean
{
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

    todo.add(Pair(c1.main, c2.main));

    while( todo.isNotEmpty() ) {
        val (one, two) = todo.removeAt(0);
        val actionsWithContinuations = getActionsWithContinuations(one, c1.procedures)
        for( (action1, continuation1) in actionsWithContinuations ) {
            val continuation2 = getContinuation( two, action1, c2.procedures )
            if ( continuation2 == null ) {
                println( "Could not match ${action1} with continuation ${two}" )
                return false
            } else {
                if( !done.contains( Pair(continuation1,continuation2) ) && !todo.contains( Pair(continuation1, continuation2) ) ) {
                    todo.add( Pair( continuation1, continuation2 ) );
                }
            }
        }
        done.add( Pair( one, two ) )
    }

    return true
}

private fun pn(label:ExtractionLabel):Set<String>
{
    return when( label ) {
        is ThenLabel -> setOf(label.process)
        is ElseLabel -> setOf(label.process)
        is SendingLabel -> setOf(label.sender, label.receiver)
        is SelectionLabel -> setOf(label.sender, label.receiver)
        else -> throw java.lang.IllegalArgumentException("Invalid label")
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
            if ( procedureBody == null ) {
                return null
            } else {
                return getContinuation( procedureBody, label, proceduresCopy )
            }
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
        is SendingLabel -> when( l2 ) { is SendingLabel -> l1.sender == l2.sender && l1.receiver == l2.receiver && l1.expression == l2.expression else -> false }
        is SelectionLabel -> when( l2 ) { is SelectionLabel -> l1.sender == l2.sender && l1.receiver == l2.receiver && l1.label == l2.label else -> false }
        is ThenLabel -> when( l2 ) { is ThenLabel -> l1.process == l2.process && l1.expression == l2.expression else -> false }
        is ElseLabel -> when( l2 ) { is ElseLabel -> l1.process == l2.process && l1.expression == l2.expression else -> false }
        else -> false
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
        is Communication -> SendingLabel( interaction.sender, interaction.receiver, interaction.expression )
        is Selection -> SelectionLabel( interaction.sender, interaction.receiver, interaction.label )
        else -> throw IllegalArgumentException()
    }
}