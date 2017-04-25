grammar Choreography;
import CommonLexerRules;

program : procedureDefinition* main;

procedureDefinition : 'def' ID LCURLY choreography RCURLY;

main : 'main' LCURLY choreography RCURLY;

choreography : interaction
    |   condition
    |   procedureInvocation
    |   TERMINATE
    ;

condition : 'if' process '.' expression 'then' choreography 'else' choreography;

//procedureDefinition: 'def' procedure ASSIGN choreography 'in' choreography;

procedureInvocation: ID;

interaction : communication | selection;

communication: process DOT expression Arrow process ';' choreography;
selection: process Arrow process LBRACK label RBRACK ';' choreography;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

INT     : [0-9]+ ;
TERMINATE : 'stop';