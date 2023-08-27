functionDefinition
    :   declarationSpecifier+? declarator
        directDeclarator
        ('__asm' '(' StringLiteral+ ')'
        |'__attribute__' '(' '(' gccAttributeList ')' ')')*
    :   declarationSpecifier+
        (declarator('=' initializer)? (',' declarator ('=' initializer)?)*)? ';'
    |
        '{'statement | declaration'}'

declarationSpecifier
    :   'typedef'
    |   'extern'
    |   'static'
    |   '_Thread_local'
    |   'auto'
    |   'register'
    :   'void'
    |   'char'
    |   'short'
    |   'int'
    |   'long'
    |   'float'
    |   'double'
    |   'signed'
    |   'unsigned'
    |   '_Bool'
    |   '_Complex'
    |   '__m128'
    |   '__m128d'
    |   '__m128i'
    |   '__extension__' '(' ('__m128' | '__m128d' | '__m128i') ')'
    :   '_Atomic' '(' specifierQualifierList abstractDeclarator? ')'
    :   ('struct' | 'union') Identifier? '{' structDeclarationList '}'
    |   ('struct' | 'union') Identifier
    :   'enum' Identifier? '{' enumerator (',' enumerator)* ','? '}'
    |   'enum' Identifier
    :   Identifier
    |   '__typeof__' '(' constantExpression ')' // GCC extension
    :   'const'
    |   'restrict'
    |   'volatile'
    |   '_Atomic'
    :   'inline'
    |   '_Noreturn'
    |   '__inline__' // GCC extension
    |   '__stdcall'
    :   '__attribute__' '(' '(' :   gccAttribute? (',' gccAttribute?)* ')' ')'
    |   '__declspec' '(' Identifier ')'
    :   '_Alignas' '(' (specifierQualifierList abstractDeclarator? | constantExpression) ')'
    ;

gccAttribute
    :   ~(',' | '(' | ')') // relaxed def for "identifier or reserved word"
        ('(' :   assignmentExpression (',' assignmentExpression)*? ')')?
    ;

assignmentExpression
    :   conditionalExpression
    |   unaryExpression assignmentOperator assignmentExpression
    |   DigitSequence // for
    ;

declarator
    :   pointer? directDeclarator gccDeclaratorExtension*
    ;

pointer
    :  (('*'|'^') typeQualifier+?)+ // ^ - Blocks language extension
    ;
typeQualifier
    :   'const'
    |   'restrict'
    |   'volatile'
    |   '_Atomic'
    ;

directDeclarator
    :   Identifier
    |   '(' declarator ')'
    |   directDeclarator '[' typeQualifierList? assignmentExpression? ']'
    |   directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
    |   directDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
    |   directDeclarator '[' typeQualifierList? '*' ']'
    |   directDeclarator '(' parameterTypeList ')'
    |   directDeclarator '(' identifierList? ')'
    |   Identifier ':' DigitSequence  // bit field
    |   vcSpecificModifer Identifier // Visual C Extension
    |   '(' vcSpecificModifer declarator ')' // Visual C Extension
    ;

declarationList
    :   declarationSpecifier+
        (initDeclaratodeclarator('=' initializer)? (',' declarator ('=' initializer)?)*)?
        ';'
    |   '_Static_assert' '(' constantExpression ',' StringLiteral+ ')' ';'
    ;
