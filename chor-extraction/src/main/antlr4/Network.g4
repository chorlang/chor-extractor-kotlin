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

sending: process BANG LT expression GT ';' behaviour;
receiving: process QUESTION ';' behaviour;
selection: process ADD expression ';' behaviour;

offering: process '&' LBRACE (labeledBehaviour) (',' labeledBehaviour)* RBRACE;
labeledBehaviour: expression COLON behaviour;

condition: 'if' process '.' expression 'then' behaviour 'else' behaviour;

procedureInvocation: procedure;

expression : Identifier
    |   BooleanLiteral
    |   Wildcard
    |   INT
    ;

INT     : [0-9]+ ;
TERMINATE : 'stop';
