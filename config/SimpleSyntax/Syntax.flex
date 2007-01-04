
package simplesyntax;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class SimpleSyntaxLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}
%{
def myLanguageConfiguration;

def sym( aID ) {
    return myLanguageConfiguration.getToken( aID );
}

SimpleSyntaxLexer( aLanguageConfiguration ) {
    myLanguageConfiguration = aLanguageConfiguration;
    ZZ_ACTION = zzUnpackAction();
    ZZ_ROWMAP = zzUnpackRowMap();
    ZZ_TRANS = zzUnpackTrans();
    ZZ_ATTRIBUTE = zzUnpackAttribute();
}
%}

WS = [ \t]*
EOL = [\n\r]+

HEADER = "[SimpleSyntax:".+"]"?
LINE_COMMENT = "#"[^\r\n]*

IDENTIFIER = [a-zA-Z][a-zA-Z_]*

EQUAL = "="
COLON = ":"

DIRECTIVE = [A-Z][a-zA-Z]*("."{IDENTIFIER})*
ATTRIBUTE = "["{WS}{IDENTIFIER}{WS}"]"

VALUE = [^\n\r]*
UNKNOWN = [^ \t\n\r\[\]=:]+

%state ASSIGNMENT

%%

[\n\r]+               { return sym( "LINE_BREAK" ); }
[\ ]                  { return sym( "SPACE" ); }
[\t]                  { return sym( "TAB" ); }
[\f]                  { return sym( "FORMFEED" ); }

<ASSIGNMENT> {
  {VALUE}             { yybegin(YYINITIAL); return sym( "VALUE" ); }
}

{HEADER}              { return sym( "HEADER" ); }
{LINE_COMMENT}        { return sym( "LINE_COMMENT" ); }
{DIRECTIVE}           { return sym( "DIRECTIVE" ); }
{ATTRIBUTE}           { return sym( "ATTRIBUTE" ); }

"descriptions"        { return sym( "CONTAINER" ); }
"attributes"          { return sym( "CONTAINER" ); }

{EQUAL}               { yybegin(ASSIGNMENT); return sym( "OP_EQUAL" ); }
{COLON}               { yybegin(ASSIGNMENT); return sym( "OP_COLON" ); }

{UNKNOWN}             { return sym( "UNKNOWN" ); }

.                     { return sym( "BAD_CHARACTER" ); }
