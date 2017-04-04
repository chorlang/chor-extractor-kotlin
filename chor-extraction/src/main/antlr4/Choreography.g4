grammar Choreography;
import CommonLexerRules;

choreography : interaction ';' choreography
    |   condition
    |   procedureInvocation
    |   procedureDefinition
    |   TERMINATE
    ;

condition : 'if' process '.' expression 'then' choreography 'else' choreography;

procedureDefinition: 'def' procedure ASSIGN choreography 'in' choreography;

procedureInvocation: procedure;

interaction : communication | selection;

communication: process DOT expression Arrow process;
selection: process Arrow process LBRACK label RBRACK;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

//expr: expr ('*'|'/') expr
//    |	expr ('+'|'-') expr
//    |	INT
//    |	'(' expr ')'
//;

INT     : [0-9]+ ;
TERMINATE : 'stop';