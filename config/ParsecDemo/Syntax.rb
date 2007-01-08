
# This script is executed in the 'options/SimpleSyntax' folder. You may also use the global function 'source' to read
# and evaluate a Ruby script. These variables are available, too:
# configFolder: is a File representing the config/options/SimpleSyntax folder
# systemContext: references the shared SystemContext object of the plugin
# configuration: is the LanguageConfiguration for this script

require "jparsec/LexerBase.rb"

s_block_open = Scanners.isPattern( Patterns.regex( /(?m:^=begin)/.source ), "BLOCK_OPEN" )
s_block_close = Scanners.isPattern( Patterns.regex( /(?m:^=end$)/.source ), "BLOCK_CLOSE" )
s_block_commented = Scanners.anyChar()

scanner "DOC_COMMENT",          Scanners.isBlockComment( s_block_open, s_block_close, s_block_commented )

regex "LINE_COMMENT",           /(?m:#.*$)/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\{(?:(?:\\\})|(?:[^\}]))*\}/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\[(?:(?:\\\])|(?:[^\]]))*\]/
regex "SPECIAL_QUOTED_STRING",  /%[qQ]\((?:(?:\\\))|(?:[^\)]))*\)/
regex "SPECIAL_QUOTED_STRING",  /%[qQ](.).*\1/
regex "SINGLE_QUOTED_STRING",   /\'(?:[^\'\n\r]|\\')*\'/
regex "DOUBLE_QUOTED_STRING",   /\"(?:[^\"\n\r]|\\")*\"/

keywords = IO.readlines( 'ParsecDemo/Keywords.txt' ).map { |k| k.chop }
keywords.each { |k| regex "KEYWORD", /\b#{k}\b/ }

regex "REGEX_SLASHED",          /(?m:\/(?:[^\/\n\r]|\\\/)*\/)/
regex "CONSTANT",               /\b[A-Z][A-Z_]*\b/
regex "CLASS_NAME",             /\b[A-Z]*(?:(?:[A-Z][a-z_]+)|[A-Z])+\b/
regex "INSTANCE_VARIABLE",      /@[a-z_]+\b/
regex "CLASS_VARIABLE",         /@@[a-z_]+\b/
regex "SYMBOL",                 /\:\w+\b/
regex "IDENTIFIER",             /\b[a-z]\w*\b/
regex "NUMBER",                 /\b(?:0x[0-9A-Fa-f]+)|(?:[0-9]+(?:\.[0-9]+)?)\b/

return lexer( configuration )
