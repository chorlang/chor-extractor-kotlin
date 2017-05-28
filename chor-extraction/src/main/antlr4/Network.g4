grammar Network;
import CommonLexerRules;

network: processBehaviour (Parallel processBehaviour)*;

// p { def X { q?;stop } main { q?; X } } | q { main { p!1; p!2; stop } }

processBehaviour : TERMINATE
    |   process '{' procedureDefinition* 'main' '{' behaviour '}' '}'
    ;

procedureDefinition : 'def' procedure '{' behaviour '}';

behaviour : interaction
    |   offering
    |   condition
    |   procedureInvocation
    |   TERMINATE
    ;

interaction : sending
    |   receiving
    |   selection
    ;

sending: process BANG LT expr GT ';' behaviour;
receiving: process QUESTION ';' behaviour;
selection: process ADD label ';' behaviour;

offering: process '&' LBRACE (labeledBehaviour) (',' labeledBehaviour)* RBRACE;
labeledBehaviour: label COLON behaviour;

condition: 'if' process '.' ( expr | expression) 'then' behaviour 'else' behaviour;

procedureInvocation: procedure;

expr: Identifier ASSIGN expression;

expression
        :	'(' expression ')'                          #parExpr
        |   NOT expression                              #notExpr
    	|   left=expression operand right=expression    #opExpr
    	|   BooleanLiteral                              #atomExpr
    	|   Identifier                                  #refExpr
    	;

operand: AND | OR | EQUAL | NOTEQUAL;

NOT         : '~';
INT         : [0-9]+ ;
TERMINATE   : 'stop';