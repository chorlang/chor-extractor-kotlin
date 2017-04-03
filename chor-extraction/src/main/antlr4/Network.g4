grammar Network;
import CommonLexerRules;

prog: network (Parallel network)* EOF;

network : TERMINATE
    |   process 'has' behaviour
    ;

behaviour : process Send LT expr GT Continue behaviour <SendAction>
    |   process Receive Continue behaviour
    |   process Select label Continue behaviour
    |   process Choose LBRACE (label COLON behaviour) (',' label COLON behaviour)* RBRACE Continue behaviour
    |   'if' expr 'then' behaviour 'else' behaviour
    |   'def' procedure ASSIGN behaviour 'in' behaviour
    |   procedure
    |   TERMINATE
    ;

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
