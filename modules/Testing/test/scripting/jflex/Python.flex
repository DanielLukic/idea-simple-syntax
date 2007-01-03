/* It's an automatically generated code. Do not modify it. */
package scripting.jflex;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class GroovyPythonLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}
%{
def myLanguageConfiguration;
GroovyPythonLexer( aLanguageConfiguration ) {
    myLanguageConfiguration = aLanguageConfiguration;
    ZZ_ACTION = zzUnpackAction();
    ZZ_ROWMAP = zzUnpackRowMap();
    ZZ_TRANS = zzUnpackTrans();
    ZZ_ATTRIBUTE = zzUnpackAttribute();
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

[\n\r]+               { return myLanguageConfiguration.getToken( "LINE_BREAK" ); }
[\ ]                  { return myLanguageConfiguration.getToken( "SPACE" ); }
[\t]                  { return myLanguageConfiguration.getToken( "TAB" ); }
[\f]                  { return myLanguageConfiguration.getToken( "FORMFEED" ); }

{END_OF_LINE_COMMENT} { return myLanguageConfiguration.getToken( "END_OF_LINE_COMMENT" ); }

{LONGINTEGER}         { return myLanguageConfiguration.getToken( "INTEGER_LITERAL" ); }
{INTEGER}             { return myLanguageConfiguration.getToken( "INTEGER_LITERAL" ); }
{FLOATNUMBER}         { return myLanguageConfiguration.getToken( "FLOAT_LITERAL" ); }
{IMAGNUMBER}          { return myLanguageConfiguration.getToken( "IMAGINARY_LITERAL" ); }

{STRING_LITERAL}      { return myLanguageConfiguration.getToken( "STRING_LITERAL" ); }

"and"                 { return myLanguageConfiguration.getToken( "AND_KEYWORD" ); }
"assert"              { return myLanguageConfiguration.getToken( "ASSERT_KEYWORD" ); }
"break"               { return myLanguageConfiguration.getToken( "BREAK_KEYWORD" ); }
"class"               { return myLanguageConfiguration.getToken( "CLASS_KEYWORD" ); }
"continue"            { return myLanguageConfiguration.getToken( "CONTINUE_KEYWORD" ); }
"def"                 { return myLanguageConfiguration.getToken( "DEF_KEYWORD" ); }
"del"                 { return myLanguageConfiguration.getToken( "DEL_KEYWORD" ); }
"elif"                { return myLanguageConfiguration.getToken( "ELIF_KEYWORD" ); }
"else"                { return myLanguageConfiguration.getToken( "ELSE_KEYWORD" ); }
"except"              { return myLanguageConfiguration.getToken( "EXCEPT_KEYWORD" ); }
"exec"                { return myLanguageConfiguration.getToken( "EXEC_KEYWORD" ); }
"finally"             { return myLanguageConfiguration.getToken( "FINALLY_KEYWORD" ); }
"for"                 { return myLanguageConfiguration.getToken( "FOR_KEYWORD" ); }
"from"                { return myLanguageConfiguration.getToken( "FROM_KEYWORD" ); }
"global"              { return myLanguageConfiguration.getToken( "GLOBAL_KEYWORD" ); }
"if"                  { return myLanguageConfiguration.getToken( "IF_KEYWORD" ); }
"import"              { return myLanguageConfiguration.getToken( "IMPORT_KEYWORD" ); }
"in"                  { return myLanguageConfiguration.getToken( "IN_KEYWORD" ); }
"is"                  { return myLanguageConfiguration.getToken( "IS_KEYWORD" ); }
"lambda"              { return myLanguageConfiguration.getToken( "LAMBDA_KEYWORD" ); }
"not"                 { return myLanguageConfiguration.getToken( "NOT_KEYWORD" ); }
"or"                  { return myLanguageConfiguration.getToken( "OR_KEYWORD" ); }
"pass"                { return myLanguageConfiguration.getToken( "PASS_KEYWORD" ); }
"print"               { return myLanguageConfiguration.getToken( "PRINT_KEYWORD" ); }
"raise"               { return myLanguageConfiguration.getToken( "RAISE_KEYWORD" ); }
"return"              { return myLanguageConfiguration.getToken( "RETURN_KEYWORD" ); }
"try"                 { return myLanguageConfiguration.getToken( "TRY_KEYWORD" ); }
"while"               { return myLanguageConfiguration.getToken( "WHILE_KEYWORD" ); }
"yield"               { return myLanguageConfiguration.getToken( "YIELD_KEYWORD" ); }

{IDENTIFIER}          { return myLanguageConfiguration.getToken( "IDENTIFIER" ); }

"+="                  { return myLanguageConfiguration.getToken( "PLUSEQ" ); }
"-="                  { return myLanguageConfiguration.getToken( "MINUSEQ" ); }
"**="                 { return myLanguageConfiguration.getToken( "EXPEQ" ); }
"*="                  { return myLanguageConfiguration.getToken( "MULTEQ" ); }
"//="                 { return myLanguageConfiguration.getToken( "FLOORDIVEQ" ); }
"/="                  { return myLanguageConfiguration.getToken( "DIVEQ" ); }
"%="                  { return myLanguageConfiguration.getToken( "PERCEQ" ); }
"&="                  { return myLanguageConfiguration.getToken( "ANDEQ" ); }
"|="                  { return myLanguageConfiguration.getToken( "OREQ" ); }
"^="                  { return myLanguageConfiguration.getToken( "XOREQ" ); }
">>="                 { return myLanguageConfiguration.getToken( "GTGTEQ" ); }
"<<="                 { return myLanguageConfiguration.getToken( "LTLTEQ" ); }
"<<"                  { return myLanguageConfiguration.getToken( "LTLT" ); }
">>"                  { return myLanguageConfiguration.getToken( "GTGT" ); }
"**"                  { return myLanguageConfiguration.getToken( "EXP" ); }
"//"                  { return myLanguageConfiguration.getToken( "FLOORDIV" ); }
"<="                  { return myLanguageConfiguration.getToken( "LE" ); }
">="                  { return myLanguageConfiguration.getToken( "GE" ); }
"=="                  { return myLanguageConfiguration.getToken( "EQEQ" ); }
"!="                  { return myLanguageConfiguration.getToken( "NE" ); }
"<>"                  { return myLanguageConfiguration.getToken( "NE_OLD" ); }
"+"                   { return myLanguageConfiguration.getToken( "PLUS" ); }
"-"                   { return myLanguageConfiguration.getToken( "MINUS" ); }
"*"                   { return myLanguageConfiguration.getToken( "MULT" ); }
"/"                   { return myLanguageConfiguration.getToken( "DIV" ); }
"%"                   { return myLanguageConfiguration.getToken( "PERC" ); }
"&"                   { return myLanguageConfiguration.getToken( "AND" ); }
"|"                   { return myLanguageConfiguration.getToken( "OR" ); }
"^"                   { return myLanguageConfiguration.getToken( "XOR" ); }
"~"                   { return myLanguageConfiguration.getToken( "TILDE" ); }
"<"                   { return myLanguageConfiguration.getToken( "LT" ); }
">"                   { return myLanguageConfiguration.getToken( "GT" ); }
"("                   { return myLanguageConfiguration.getToken( "LPAR" ); }
")"                   { return myLanguageConfiguration.getToken( "RPAR" ); }
"["                   { return myLanguageConfiguration.getToken( "LBRACKET" ); }
"]"                   { return myLanguageConfiguration.getToken( "RBRACKET" ); }
"{"                   { return myLanguageConfiguration.getToken( "LBRACE" ); }
"}"                   { return myLanguageConfiguration.getToken( "RBRACE" ); }
"@"                   { return myLanguageConfiguration.getToken( "AT" ); }
","                   { return myLanguageConfiguration.getToken( "COMMA" ); }
":"                   { return myLanguageConfiguration.getToken( "COLON" ); }
"."                   { return myLanguageConfiguration.getToken( "DOT" ); }
"`"                   { return myLanguageConfiguration.getToken( "TICK" ); }
"="                   { return myLanguageConfiguration.getToken( "EQ" ); }
";"                   { return myLanguageConfiguration.getToken( "SEMICOLON" ); }
"\\"                  { return myLanguageConfiguration.getToken( "BACKSLASH" ); }

.                     { return myLanguageConfiguration.getToken( "BAD_CHARACTER" ); }

