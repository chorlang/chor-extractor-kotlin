grammar Choreography;
import CommonLexerRules;

program : procedureDefinition* main;

procedureDefinition : 'def' ID '{' choreography '}';

main : 'main {' choreography '}';

choreography : interaction
    |   condition
    |   procedureInvocation
    |   TERMINATE
    ;

condition : 'if' process '.' expression 'then' choreography 'else' choreography;

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