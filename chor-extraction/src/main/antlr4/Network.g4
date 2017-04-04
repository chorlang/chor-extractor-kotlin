grammar Network;
import CommonLexerRules;

prog: network (Parallel network)*;

network : TERMINATE
    |   process '=' behaviour
    ;

behaviour : interaction
    |   offering
    |   condition
    |   procedureDefinition
    |   procedureInvocation
    |   TERMINATE
    ;

interaction : sending ';' behaviour
    |   receiving ';' behaviour
    |   selection ';' behaviour
    ;

sending: process BANG LT expression GT;
receiving: process QUESTION;
selection: process ADD label;

offering: process '&' LBRACE (label COLON behaviour) (',' label COLON behaviour)* RBRACE;
condition: 'if' process '.' expression 'then' behaviour 'else' behaviour;
procedureDefinition: 'def' procedure ASSIGN behaviour 'in' behaviour;
procedureInvocation: procedure;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

INT     : [0-9]+ ;
TERMINATE : 'stop';
