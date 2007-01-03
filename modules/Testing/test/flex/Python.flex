/* It's an automatically generated code. Do not modify it. */
package ru.yole.pythonid;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class _PythonLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}
%{
private PyTokenTypes tokenTypes = null;
void setLanguage(PythonLanguage language) {
    this.tokenTypes = language.getTokenTypes();
}
%}

DIGIT = [0-9]
NONZERODIGIT = [1-9]
OCTDIGIT = [0-7]
HEXDIGIT = [0-9A-Fa-f]

HEXINTEGER = 0[Xx]({HEXDIGIT})+
OCTINTEGER = 0({OCTDIGIT})+
DECIMALINTEGER = (({NONZERODIGIT}({DIGIT})*)|0)
INTEGER = {DECIMALINTEGER}|{OCTINTEGER}|{HEXINTEGER}
LONGINTEGER = {INTEGER}[Ll]

END_OF_LINE_COMMENT="#"[^\r\n]*

IDENTIFIER = [a-zA-Z_][a-zA-Z0-9_]*

FLOATNUMBER=({POINTFLOAT})|({EXPONENTFLOAT})
POINTFLOAT=(({INTPART})?{FRACTION})|({INTPART}\.)
EXPONENTFLOAT=(({INTPART})|({POINTFLOAT})){EXPONENT}
INTPART = ({DIGIT})+
FRACTION = \.({DIGIT})+
EXPONENT = [eE][+\-]?({DIGIT})+

IMAGNUMBER=({FLOATNUMBER})|({INTPART})[Jj]

STRING_LITERAL=[Uu]?({RAW_STRING}|{QUOTED_STRING})
RAW_STRING=[Rr]{QUOTED_STRING}
QUOTED_STRING=({TRIPLE_APOS_LITERAL})|({QUOTED_LITERAL})|({DOUBLE_QUOTED_LITERAL})|({TRIPLE_QUOTED_LITERAL})
QUOTED_LITERAL="'"([^\\\'\r\n]|{ESCAPE_SEQUENCE}|(\\[\r\n]))*("'"|\\)?
DOUBLE_QUOTED_LITERAL=\"([^\\\"\r\n]|{ESCAPE_SEQUENCE}|(\\[\r\n]))*?(\"|\\)?
TRIPLE_QUOTED_LITERAL="\"\"\""~([^\\]"\"\"\"")
TRIPLE_APOS_LITERAL="\'\'\'"~([^\\]"\'\'\'")
ESCAPE_SEQUENCE=\\[^\r\n]

%%

[\n\r]+               { return tokenTypes.LINE_BREAK; }
[\ ]                  { return tokenTypes.SPACE; }
[\t]                  { return tokenTypes.TAB; }
[\f]                  { return tokenTypes.FORMFEED; }

{END_OF_LINE_COMMENT} { return tokenTypes.END_OF_LINE_COMMENT; }

{LONGINTEGER}         { return tokenTypes.INTEGER_LITERAL; }
{INTEGER}             { return tokenTypes.INTEGER_LITERAL; }
{FLOATNUMBER}         { return tokenTypes.FLOAT_LITERAL; }
{IMAGNUMBER}          { return tokenTypes.IMAGINARY_LITERAL; }

{STRING_LITERAL}      { return tokenTypes.STRING_LITERAL; }

"and"                 { return tokenTypes.AND_KEYWORD; }
"assert"              { return tokenTypes.ASSERT_KEYWORD; }
"break"               { return tokenTypes.BREAK_KEYWORD; }
"class"               { return tokenTypes.CLASS_KEYWORD; }
"continue"            { return tokenTypes.CONTINUE_KEYWORD; }
"def"                 { return tokenTypes.DEF_KEYWORD; }
"del"                 { return tokenTypes.DEL_KEYWORD; }
"elif"                { return tokenTypes.ELIF_KEYWORD; }
"else"                { return tokenTypes.ELSE_KEYWORD; }
"except"              { return tokenTypes.EXCEPT_KEYWORD; }
"exec"                { return tokenTypes.EXEC_KEYWORD; }
"finally"             { return tokenTypes.FINALLY_KEYWORD; }
"for"                 { return tokenTypes.FOR_KEYWORD; }
"from"                { return tokenTypes.FROM_KEYWORD; }
"global"              { return tokenTypes.GLOBAL_KEYWORD; }
"if"                  { return tokenTypes.IF_KEYWORD; }
"import"              { return tokenTypes.IMPORT_KEYWORD; }
"in"                  { return tokenTypes.IN_KEYWORD; }
"is"                  { return tokenTypes.IS_KEYWORD; }
"lambda"              { return tokenTypes.LAMBDA_KEYWORD; }
"not"                 { return tokenTypes.NOT_KEYWORD; }
"or"                  { return tokenTypes.OR_KEYWORD; }
"pass"                { return tokenTypes.PASS_KEYWORD; }
"print"               { return tokenTypes.PRINT_KEYWORD; }
"raise"               { return tokenTypes.RAISE_KEYWORD; }
"return"              { return tokenTypes.RETURN_KEYWORD; }
"try"                 { return tokenTypes.TRY_KEYWORD; }
"while"               { return tokenTypes.WHILE_KEYWORD; }
"yield"               { return tokenTypes.YIELD_KEYWORD; }

{IDENTIFIER}          { return tokenTypes.IDENTIFIER; }

"+="                  { return tokenTypes.PLUSEQ; }
"-="                  { return tokenTypes.MINUSEQ; }
"**="                 { return tokenTypes.EXPEQ; }
"*="                  { return tokenTypes.MULTEQ; }
"//="                 { return tokenTypes.FLOORDIVEQ; }
"/="                  { return tokenTypes.DIVEQ; }
"%="                  { return tokenTypes.PERCEQ; }
"&="                  { return tokenTypes.ANDEQ; }
"|="                  { return tokenTypes.OREQ; }
"^="                  { return tokenTypes.XOREQ; }
">>="                 { return tokenTypes.GTGTEQ; }
"<<="                 { return tokenTypes.LTLTEQ; }
"<<"                  { return tokenTypes.LTLT; }
">>"                  { return tokenTypes.GTGT; }
"**"                  { return tokenTypes.EXP; }
"//"                  { return tokenTypes.FLOORDIV; }
"<="                  { return tokenTypes.LE; }
">="                  { return tokenTypes.GE; }
"=="                  { return tokenTypes.EQEQ; }
"!="                  { return tokenTypes.NE; }
"<>"                  { return tokenTypes.NE_OLD; }
"+"                   { return tokenTypes.PLUS; }
"-"                   { return tokenTypes.MINUS; }
"*"                   { return tokenTypes.MULT; }
"/"                   { return tokenTypes.DIV; }
"%"                   { return tokenTypes.PERC; }
"&"                   { return tokenTypes.AND; }
"|"                   { return tokenTypes.OR; }
"^"                   { return tokenTypes.XOR; }
"~"                   { return tokenTypes.TILDE; }
"<"                   { return tokenTypes.LT; }
">"                   { return tokenTypes.GT; }
"("                   { return tokenTypes.LPAR; }
")"                   { return tokenTypes.RPAR; }
"["                   { return tokenTypes.LBRACKET; }
"]"                   { return tokenTypes.RBRACKET; }
"{"                   { return tokenTypes.LBRACE; }
"}"                   { return tokenTypes.RBRACE; }
"@"                   { return tokenTypes.AT; }
","                   { return tokenTypes.COMMA; }
":"                   { return tokenTypes.COLON; }
"."                   { return tokenTypes.DOT; }
"`"                   { return tokenTypes.TICK; }
"="                   { return tokenTypes.EQ; }
";"                   { return tokenTypes.SEMICOLON; }
"\\"                  { return tokenTypes.BACKSLASH; }

.                     { return tokenTypes.BAD_CHARACTER; }

