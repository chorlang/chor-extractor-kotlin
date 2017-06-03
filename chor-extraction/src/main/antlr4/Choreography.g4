grammar Choreography;
import CommonLexerRules;

program : procedureDefinition* main;

procedureDefinition : 'def' procedure '{' choreography '}';

main : 'main {' choreography '}';

choreography : interaction
    |   condition
    |   procedureInvocation
    |   TERMINATE
    ;

condition : 'if' process '.' expression 'then' choreography 'else' choreography;

procedureInvocation: procedure;

interaction : communication | selection;

communication: process DOT expression Arrow process ';' choreography;
selection: process Arrow process LBRACK expression RBRACK ';' choreography;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

INT     : [0-9]+ ;
TERMINATE : 'stop';