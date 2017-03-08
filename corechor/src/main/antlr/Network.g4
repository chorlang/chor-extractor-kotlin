grammar Network;

import CommonLexerRules;

Network : Process Has Behaviour
    |   Network Parallel Network
    |   Terminate
    ;

Behaviour : Process Send LT Expression GT Continue Behaviour
    |   Process Receive Continue Behaviour
    |   Process Select Label Continue Behaviour
    |   Process Choose LBRACE (Label COLON Behaviour)+ RBRACE Continue Behaviour
    |   IF Expression THEN Behaviour ELSE Behaviour
    |   DEF Procedure ASSIGN Behaviour IN Behaviour
    |   Procedure
    |   Terminate
    ;
