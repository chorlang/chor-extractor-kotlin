grammar Corechor;

Choreography ::= 
Communication Continue Choreography | 
IF Process DOT Expression THEN Choreography ELSE Choreography | 
DEF Procedure ASSIGN Choreography IN Choreography | 
Procedure |
Terminate

Communication ::= 
Process DOT Expression Arrow Process | 
Process Arrow Process LBRACK label RBRACK

Behaviour ::= 
Process Send LT Expression GT Continue Behaviour |
Process Receive Continue Behaviour |
Process Select Label Continue Behaviour |
Process Choose LBRACE (Label COLON Behaviour)+ RBRACE Continue Behaviour |
IF Expression THEN Behaviour ELSE Behaviour |
DEF Procedure ASSIGN Behaviour IN Behaviour |
Procedure |
Terminate

Network ::= 
Process Has Behaviour | 
Network Parallel Network |
Terminate

Expression ::= Value | Wildcard
Process :: = [a-z]
Procedure ::= [A-Z]
Label ::= 
Value ::= Identifier | Number | String

Terminate :: = “0”
Parallel:: = “|”
Wildcard ::= “*”
Arrow ::= “->”
Send ::= “!”
Receive ::= “?”
Select :: = “+”
Choose ::= “&”
Has ::= “:”
Continue ::= “;”