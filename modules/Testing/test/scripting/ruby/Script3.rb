
source "LexerStuff.rb"

s_block_open = Scanners.isPattern( Patterns.regex( /(?m:^=begin)/.source ), "BLOCK_OPEN" )
s_block_close = Scanners.isPattern( Patterns.regex( /(?m:^=end$)/.source ), "BLOCK_CLOSE" )
s_block_commented = Scanners.anyChar()

scanner "DOC_COMMENT",	        Scanners.isBlockComment( s_block_open, s_block_close, s_block_commented )

regex "LINE_COMMENT",           /(?m:#.*$)/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\{(?:(?:\\\})|(?:[^\}]))*\}/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\[(?:(?:\\\])|(?:[^\]]))*\]/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\((?:(?:\\\))|(?:[^\)]))*\)/
regex "SPECIAL_QUOTED_STRING",  /%[qQ](.).*\1/
regex "SINGLE_QUOTED_STRING",   /\'(?:[^\'\n\r]|\\')*\'/
regex "DOUBLE_QUOTED_STRING",   /\"(?:[^\"\n\r]|\\")*\"/

regex "KEYWORD",                /\b(?:alias|and|BEGIN|begin|break|case|class|def|defined|do|else|elsif|END|end|ensure|false|for|if|in|module|next|nil|not|or|redo|rescue|retry|return|self|super|then|true|undef|unless|until|when|while|yield)\b/

regex "CONSTANT",               /\b[A-Z][A-Z_]*\b/
regex "CLASS_NAME",             /\b[A-Z]*(?:(?:[A-Z][a-z_]+)|[A-Z])+\b/
regex "INSTANCE_VARIABLE",      /@[a-z_]+\b/
regex "CLASS_VARIABLE",         /@@[a-z_]+\b/
regex "SYMBOL",                 /\:\w+\b/
regex "IDENTIFIER",             /\b[a-z]\w*\b/
regex "NUMBER",                 /\b(?:0x[0-9A-Fa-f]+)|(?:[0-9]+(?:\.[0-9]+)?)\b/

lexer
