
WS = [ \t\f]*
EOL = [\n\r]+

COMMENT_BEGIN = "=begin"
COMMENT_LINE = [^\n\r]*
COMMENT_END = "=end"

LINE_COMMENT = "#"[^\r\n]*

IDENTIFIER = [a-z][a-zA-Z0-9_]*

SYMBOL = ":"[a-zA-Z][a-zA-Z0-9_]*
CONSTANT = [A-Z][A-Z0-9_]+
CLASS_NAME = [A-Z][a-zA-Z0-9_]*
CLASS_VARIABLE = "@@" {IDENTIFIER}
GLOBAL_VARIABLE = "$"[A-Z0-9]+
INSTANCE_VARIABLE = "@" {IDENTIFIER}

NUMBER_HEX = "0x"[0-9a-fA-F]+
NUMBER_FLOAT= ( [0-9]*\.[0-9]+ | [0-9]+\.[0-9]* )
NUMBER_INTEGER = [0-9]+

REGEX_SLASHED = "/" ([^ \/]*|\\\ )+ "/"?

%states BLOCK1,BLOCK2,COMMENT,PARENS,STRING_DQ,STRING_SQ,STRING_Q

%%

<COMMENT> {
  {COMMENT_END}		{ pop(COMMENT); return sym( "COMMENT_END" ); }
  {COMMENT_LINE}	{ return sym( "COMMENT_LINE" ); }
}

<PARENS> {
  ")"			{ pop(PARENS); return sym( "PARENS_CLOSE" ); }
}

<STRING_DQ> {
  "\\\""		{ return sym( "STRING_DQ_DATA" ); }
  "\""|\n|\r		{ pop(STRING_DQ); return sym( "STRING_DQ_END" ); }
  [^\"\\\n\r]+		{ return sym( "STRING_DQ_DATA" ); }
}

<STRING_SQ> {
  "\\'"			{ return sym( "STRING_SQ_DATA" ); }
  "'"|\n|\r		{ pop(STRING_SQ); return sym( "STRING_SQ_END" ); }
  [^'\\\n\r]+		{ return sym( "STRING_SQ_DATA" ); }
}

<STRING_Q> {
  .			{
			  if ( yycharat( 0 ) == myStringDelimiter )
			    {
			    return onEndOfString( yylength(), "STRING_Q_DATA", "STRING_Q_END" )
			    }
			  myStringCounter++
			  break;
			}
  {EOL}		{ return onEndOfString( yylength(), "STRING_Q_DATA", "STRING_Q_END" ) }
}

<YYINITIAL> {
  {COMMENT_BEGIN}       { push(COMMENT); return sym( "COMMENT_BEGIN" ); }
  {COMMENT_END}         { return sym( "COMMENT_END" ); }
}

<BLOCK1> {
  "}"			{ pop(BLOCK1); return sym( "BLOCK_END" ); }
}

<BLOCK2> {
  "end"                 { pop(BLOCK2); return sym( "KEYWORD_END" ); }
}

<YYINITIAL,BLOCK1,BLOCK2> {
  {LINE_COMMENT}        { return sym( "LINE_COMMENT" ); }

  "alias"               { return sym( "KEYWORD_ALIAS" ); }
  "and"                 { return sym( "KEYWORD_AND" ); }
  "begin"               { push(BLOCK2); return sym( "KEYWORD_BEGIN" ); }
  "break"               { return sym( "KEYWORD_BREAK" ); }
  "case"                { push(BLOCK2); return sym( "KEYWORD_CASE" ); }
  "class"               { push(BLOCK2); return sym( "KEYWORD_CLASS" ); }
  "def"                 { push(BLOCK2); return sym( "KEYWORD_DEF" ); }
  "defined"             { return sym( "KEYWORD_DEFINED" ); }
  "do"                  { push(BLOCK2); return sym( "KEYWORD_DO" ); }
  "else"                { return sym( "KEYWORD_ELSE" ); }
  "elsif"               { return sym( "KEYWORD_ELSIF" ); }
  "end"                 { return sym( "KEYWORD_END" ); }
  "ensure"              { return sym( "KEYWORD_ENSURE" ); }
  "false"               { return sym( "KEYWORD_FALSE" ); }
  "for"                 { push(BLOCK2); return sym( "KEYWORD_FOR" ); }
  "if"                  { push(BLOCK2); return sym( "KEYWORD_IF" ); }
  "in"                  { return sym( "KEYWORD_IN" ); }
  "module"              { push(BLOCK2); return sym( "KEYWORD_MODULE" ); }
  "next"                { return sym( "KEYWORD_NEXT" ); }
  "nil"                 { return sym( "KEYWORD_NIL" ); }
  "not"                 { return sym( "KEYWORD_NOT" ); }
  "or"                  { return sym( "KEYWORD_OR" ); }
  "redo"                { return sym( "KEYWORD_REDO" ); }
  "rescue"              { return sym( "KEYWORD_RESCUE" ); }
  "retry"               { return sym( "KEYWORD_RETRY" ); }
  "return"              { return sym( "KEYWORD_RETURN" ); }
  "self"                { return sym( "KEYWORD_SELF" ); }
  "super"               { return sym( "KEYWORD_SUPER" ); }
  "then"                { return sym( "KEYWORD_THEN" ); }
  "true"                { return sym( "KEYWORD_TRUE" ); }
  "undef"               { return sym( "KEYWORD_UNDEF" ); }
  "unless"              { push(BLOCK2); return sym( "KEYWORD_UNLESS" ); }
  "until"               { push(BLOCK2); return sym( "KEYWORD_UNTIL" ); }
  "when"                { return sym( "KEYWORD_WHEN" ); }
  "while"               { push(BLOCK2); return sym( "KEYWORD_WHILE" ); }
  "yield"               { return sym( "KEYWORD_YIELD" ); }
}

<YYINITIAL,BLOCK1,BLOCK2,PARENS> {
  "%"[qQ].		{
			  myStringDelimiter = yycharat( 2 );
			  switch ( myStringDelimiter )
			    {
			    case '{': myStringDelimiter = '}'; break;
			    case '[': myStringDelimiter = ']'; break;
			    case '(': myStringDelimiter = ')'; break;
			    case '<': myStringDelimiter = '>'; break;
			    }
			  push(STRING_Q); return sym( "STRING_Q_BEGIN" );
			}

  "\""			{ push(STRING_DQ); return sym( "STRING_DQ_BEGIN" ); }
  "'"			{ push(STRING_SQ); return sym( "STRING_SQ_BEGIN" ); }

  "("			{ push(PARENS); return sym( "PARENS_OPEN" ); }
  ")"			{ return sym( "PARENS_CLOSE" ); }

  "{"			{ push(BLOCK1); return sym( "BLOCK_BEGIN" ); }
  "}"			{ return sym( "BLOCK_END" ); }

  "+"			{ return sym( "OP_PLUS" ); }
  "-"			{ return sym( "OP_MINUS" ); }
  "*"			{ return sym( "OP_MULT" ); }
  "/"			{ return sym( "OP_DIV" ); }
  "%"			{ return sym( "OP_MOD" ); }

  "||"			{ return sym( "OP_OR" ); }
  "&&"			{ return sym( "OP_AND" ); }

  "|"			{ return sym( "OP_BIT_OR" ); }
  "&"			{ return sym( "OP_BIT_AND" ); }

  "=="			{ return sym( "OP_EQUAL" ); }
  "!="			{ return sym( "OP_UNEQUAL" ); }

  "."			{ return sym( "OP_DOT" ); }
  ","			{ return sym( "OP_SEPARATOR" ); }
  "="			{ return sym( "OP_ASSIGNMENT" ); }

  {NUMBER_HEX}		{ return sym( "NUMBER_HEX" ); }
  {NUMBER_FLOAT}	{ return sym( "NUMBER_FLOAT" ); }
  {NUMBER_INTEGER}	{ return sym( "NUMBER_INTEGER" ); }

  {SYMBOL}		{ return sym( "SYMBOL" ); }
  {CONSTANT}		{ return sym( "CONSTANT" ); }
  {CLASS_NAME}		{ return sym( "CLASS_NAME" ); }
  {CLASS_VARIABLE}	{ return sym( "CLASS_VARIABLE" ); }
  {GLOBAL_VARIABLE}	{ return sym( "GLOBAL_VARIABLE" ); }
  {INSTANCE_VARIABLE}	{ return sym( "INSTANCE_VARIABLE" ); }

  {IDENTIFIER}		{ return sym( "IDENTIFIER" ); }

  {REGEX_SLASHED}	{ return sym( "REGEX_SLASHED" ); }
}

{WS}			{ return sym( "SPACE" ); }
{EOL}			{ return sym( "LINE_BREAK" ); }

.|\n|\r			{ return sym( "BAD_CHARACTER" ); }
