package bisim

import antlrgen.ChoreographyLexer
import antlrgen.ChoreographyParser
import ast.cc.interfaces.CCNode
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

    return bisimilar( program1.choreographyList, program2.choreographyList )
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
    val done:ArrayList<Pair<CCNode, CCNode>> = ArrayList()
    val todo:ArrayList<Pair<CCNode, CCNode>> = ArrayList()

    todo.add(Pair(c1.main, c2.main));

    while( todo.isNotEmpty() ) {
        val (one, two) = todo.removeAt(0);
        val actionsWithContinuations = getActionsWithContinuations(one, c1.procedures)
        for( (action1, continuation1) in actionsWithContinuations ) {
            val continuation2 = getContinuation( two, action1, c2.procedures, arrayListOf() )
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

fun getContinuation( c:CCNode, label:ExtractionLabel, procedures:List<ProcedureDefinition>, blockedProcesses:ArrayList<String> ):CCNode?
{
    when( c ) {
        is ProcedureInvocation -> {
            val proceduresCopy = ArrayList<ProcedureDefinition>()
            var procedureBody:CCNode? = null
            for( procedure in procedures ) {
                if( procedure.procedure != c.procedure ) {
                    proceduresCopy.add( procedure )
                } else {
                    procedureBody = procedure.choreography
                }
            }
            if ( procedureBody == null ) {
                return null
            } else {
                return getContinuation( procedureBody, label, proceduresCopy, blockedProcesses )
            }
        }
        is CommunicationSelection -> {
            if (blockedProcesses.contains(c.node.sender) || blockedProcesses.contains(c.node.receiver))
                return null

            if (equalLabels(labelFromInteraction(c.node), label)) {
                return c.continuation
            } else {
                blockedProcesses.add(c.node.sender)
                blockedProcesses.add(c.node.receiver)
                val cont = getContinuation(c.continuation, label, procedures, blockedProcesses)
                return if ( cont == null ) null else CommunicationSelection(c.node, cont )
            }
        }
        is Condition -> {
            if ( blockedProcesses.contains( c.process ) )
                return null

            when( label ) {
                is ThenLabel -> {
                    if ( c.process == label.process && c.expression == label.expression ) {
                        return c.thenChoreography
                    } else {
                        blockedProcesses.add( c.process )
                        val thenCont = getContinuation(c.thenChoreography, label, procedures, ArrayList(blockedProcesses))
                        val elseCont = getContinuation(c.elseChoreography, label, procedures, ArrayList(blockedProcesses))
                        return if ( thenCont == null || elseCont == null ) null else Condition( c.process, c.expression, thenCont, elseCont )
                    }
                }
                is ElseLabel -> {
                    if ( c.process == label.process && c.expression == label.expression ) {
                        return c.elseChoreography
                    } else {
                        blockedProcesses.add( c.process )
                        val thenCont = getContinuation(c.thenChoreography, label, procedures, ArrayList(blockedProcesses))
                        val elseCont = getContinuation(c.elseChoreography, label, procedures, ArrayList(blockedProcesses))
                        return if ( thenCont == null || elseCont == null ) null else Condition( c.process, c.expression, thenCont, elseCont )
                    }
                }
                else -> {
                    blockedProcesses.add( c.process )
                    val thenCont = getContinuation(c.thenChoreography, label, procedures, blockedProcesses)
                    val elseCont = getContinuation(c.elseChoreography, label, procedures, blockedProcesses)
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

fun getActionsWithContinuations( c:CCNode, procedures:List<ProcedureDefinition> ):List<Pair<ExtractionLabel, CCNode>>
{
    return when( c ) {
        is CommunicationSelection -> listOf(Pair(labelFromInteraction(c.node), c.continuation))
        is Condition -> listOf(
                Pair(ThenLabel(c.process, c.expression), c.thenChoreography),
                Pair(ElseLabel(c.process, c.expression), c.elseChoreography)
        )
        is Termination -> listOf()
        is ProcedureInvocation -> getActionsWithContinuations(getProcedure(c.procedure, procedures), procedures)
        else -> throw IllegalArgumentException()
    }
}

fun getProcedure( name:ProcedureName, procedures:List<ProcedureDefinition> ):CCNode
{
    for( procedure in procedures ) {
        if( procedure.procedure == name ) {
            return procedure.choreography
        }
    }
    throw IllegalArgumentException( "Called a procedure that does not exist: ${name}" )
}

fun labelFromInteraction(interaction:Interaction):ExtractionLabel
{
    return when( interaction ) {
        is Communication -> SendingLabel( interaction.sender, interaction.receiver, interaction.expression )
        is Selection -> SelectionLabel( interaction.sender, interaction.receiver, interaction.label )
        else -> throw IllegalArgumentException()
    }
}