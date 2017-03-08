grammar Choreography;

import CommonLexerRules;

Choreography : Communication Continue Choreography
    |   IF Process DOT Expression THEN Choreography ELSE Choreography
    |   DEF Procedure ASSIGN Choreography IN Choreography
    |   Procedure
    |   Terminate
    ;

Communication : Process DOT Expression Arrow Process
    |   Process Arrow Process LBRACK label RBRACK
    ;