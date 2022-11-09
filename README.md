# ToyLangEx05
Toy Programming Language: Ex05

式文を一つ書けるようになった

現状の文法．

\<Program\> ::= \<ExprStmt>

\<ExprStmt> ::= \<AdditiExpr\>　 ';'

\<AdditiExpr\>　:: = \<MultiplicativeExpr\> [ ( '+' | '-' ) \<MultiplicativeExpr\> ]\*

\<MultiplicativeExpr\> :: = \<Primary\> [ ( '\*'  | '/' ) \<Primary\> ]\*

\<Primary\> :: = ( \<expr\> ) | \<Integer\>
