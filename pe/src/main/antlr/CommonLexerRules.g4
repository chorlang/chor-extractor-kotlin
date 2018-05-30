grammar CommonLexerRules;


Identifier : [a-zA-Z1-9]+;

process : Identifier;

procedure : Identifier;

expression : Identifier;

BooleanLiteral : 'true' | 'false';
StringLiteral : '"' ~('\r' | '\n' | '"')* '"' ;

INT : [0-9]+;

value : INT
    |   StringLiteral
    |   BooleanLiteral
    ;

TERMINATE : 'stop';
Parallel : '|';
Wildcard : 'this';
Arrow : '->';

