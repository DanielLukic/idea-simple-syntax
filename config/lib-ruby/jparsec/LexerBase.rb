
require "jparsec/LexerAdapter.rb"

require 'java'

include_class "jfun.parsec.Lexers"
include_class "jfun.parsec.Parser"
include_class "jfun.parsec.Parsers"
include_class "jfun.parsec.Scanners"

include_class "jfun.parsec.pattern.Pattern"
include_class "jfun.parsec.pattern.Patterns"

include_class "jfun.parsec.tokens.Tokenizers"

include_class "net.intensicode.idea.syntax.JParsecLexerAdapter"
include_class "net.intensicode.idea.syntax.SimpleLexerAdapter"



$rules = []

def lexer( aLanguageConfiguration )
    all_rules = $rules.dup
    all_rules << _s( "BAD_CHARACTER", Scanners.anyChar() )

    parsers = Parser[].new( all_rules.length )
    all_rules.each_index do |idx|
        parsers[ idx ] = all_rules[ idx ]
    end

    l_all = Parsers.alt( parsers )
    s_whitespace = Scanners.isWhitespaces().many()
    lexer = Lexers.lexeme( s_whitespace, l_all )

    # We use a pure Ruby LexerAdapter here:
    #adaptedLexer = LexerAdapter.new( lexer )

    # For better performance the Java class
    # "net.intensicode.idea.syntax.JParsecLexerAdapter"
    # should be used instead:
    adaptedLexer = JParsecLexerAdapter.new( lexer )
    return SimpleLexerAdapter.new( aLanguageConfiguration, adaptedLexer )
end

def store( aRule )
    $rules << aRule
end

def regex( aTokenID, aRegex )
    store _r( aTokenID, aRegex.source )
end

def scanner( aTokenID, aScanner )
    store _s( aTokenID, aScanner )
end

def _r( aTokenID, aRegex )
    return _p( aTokenID, Patterns.regex( aRegex ) )
end

def _p( aTokenID, aPattern )
    return _s( aTokenID, Scanners.isPattern( aPattern, aTokenID ) )
end

def _s( aTokenID, aScanner )
    return Lexers.lexer( aScanner, Tokenizers.forTypedToken( aTokenID ) )
end
