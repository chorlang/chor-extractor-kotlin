grammar Choreography;
import CommonLexerRules;

//compilationUnit : choreography EOF;

choreography : communication Continue choreography
    |   condition
    |   procedureInvocation
    |   procedureDefinition
    |   TERMINATE
    ;

condition : 'if' process DOT expr 'then' choreography 'else' choreography;

procedureDefinition: 'def' procedure ASSIGN choreography 'in' choreography;

procedureInvocation: procedure;

communication : send | choose;

send: sendingProcess DOT expr Arrow receivingProcess;
choose: sendingProcess Arrow receivingProcess LBRACK label RBRACK;

sendingProcess: process;
receivingProcess: process;

expr : Identifier
    |   value
    |   Wildcard
    ;

value : INT
    |   CharacterLiteral
    |   StringLiteral
    |   BooleanLiteral
    ;

INT     : [0-9]+ ;
TERMINATE : 'stop';
procedure : Identifier; // previously 'A'..'Z';