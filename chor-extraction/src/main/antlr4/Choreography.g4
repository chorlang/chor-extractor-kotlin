grammar Choreography;
import CommonLexerRules;

//compilationUnit : choreography EOF;

choreography : communication Continue choreography
    |   condition
    |   procedureInvocation
    |   procedureDefinition
    |   TERMINATE
    ;

condition : 'if' firstExpression '=' secondExpression 'then' internal_choreography 'else' external_choreography;

procedureDefinition: 'def' procedure ASSIGN internal_choreography 'in' external_choreography;

procedureInvocation: procedure;

internal_choreography : choreography;
external_choreography: choreography;

communication : send | choose;

send: sendingProcess DOT expression Arrow receivingProcess;
choose: sendingProcess Arrow receivingProcess LBRACK label RBRACK;

sendingProcess: process;
receivingProcess: process;
firstExpression: expression;
secondExpression: expression;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   expr
    ;

expr: expr ('*'|'/') expr
    |	expr ('+'|'-') expr
    |	INT
    |	'(' expr ')'
;

INT     : [0-9]+ ;
TERMINATE : 'stop';
procedure : Identifier;