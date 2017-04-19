grammar Network;
import CommonLexerRules;

network: processBehaviour (Parallel processBehaviour)*;

processBehaviour : TERMINATE
    |   process 'is' behaviour
    ;

behaviour : interaction
    |   offering
    |   condition
    |   procedureDefinition
    |   procedureInvocation
    |   TERMINATE
    ;

interaction : sending
    |   receiving
    |   selection
    ;

sending: process BANG LT expression GT ';' behaviour;
receiving: process QUESTION ';' behaviour;
selection: process ADD label ';' behaviour;

offering: process '&' LBRACE (labeledBehaviour) (',' labeledBehaviour)* RBRACE;
labeledBehaviour: label COLON behaviour;

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
