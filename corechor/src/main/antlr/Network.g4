grammar Network;
import CommonLexerRules;

prog: Network;

Network : Process Has Behaviour R
    |   Terminate R
    ;

R: (Network Parallel Network)+;


Behaviour : Process Send LT Expression GT Continue Behaviour
    |   Process Receive Continue Behaviour
    |   Process Select Label Continue Behaviour
    |   Process Choose LBRACE (Label Has Behaviour)+ RBRACE Continue Behaviour
    |   IF Expression THEN Behaviour ELSE Behaviour
    |   DEF Procedure ASSIGN Behaviour IN Behaviour
    |   Procedure
    |   Terminate
    ;