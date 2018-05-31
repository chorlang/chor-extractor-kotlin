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

communication: process '.' expression '->' process ';' choreography;
selection: process '->' process '[' expression '];' choreography;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

TERMINATE : 'stop';