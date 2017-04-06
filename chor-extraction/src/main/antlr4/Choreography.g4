grammar Choreography;
import CommonLexerRules;

choreography : interaction
    |   condition
    |   procedureInvocation
    |   procedureDefinition
    |   TERMINATE
    ;

condition : 'if' process '.' expression 'then' choreography 'else' choreography;

procedureDefinition: 'def' procedure ASSIGN choreography 'in' choreography;

procedureInvocation: procedure;

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