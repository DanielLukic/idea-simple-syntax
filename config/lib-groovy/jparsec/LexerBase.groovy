
package jparsec;

import jfun.parsec.*
import jfun.parsec.pattern.Pattern
import jfun.parsec.pattern.Patterns
import jfun.parsec.tokens.Tokenizers

import java.util.ArrayList



class LexerBase
{
    def lexer()
    {
        setup()

        def parsers = new Parser[ myRuleStack.size() + 1 ]
        for ( idx in 0..parsers.length - 2 )
        {
            parsers[ idx ] = myRuleStack.get( idx )
        }
        parsers[ parsers.length - 1 ] = _s( "UNRECOGNIZED", Scanners.anyChar() )

        def l_all = Parsers.alt( parsers )
        def s_whitespace = Scanners.isWhitespaces().many()
        def lexer = Lexers.lexeme( s_whitespace, l_all )

        // We use a Groovy adapter here:
        return new LexerAdapter( lexer )

        // We could also use the Java class
        // "net.intensicode.idea.syntax.JParsecLexerAdapter"
        // instead.
    }

    def setup()
    {
    }

    def regex( aTokenID, aRegex )
    {
        myRuleStack.add( _r( aTokenID, aRegex ) )
    }

    def scanner( aTokenID, aScanner )
    {
        myRuleStack.add( _s( aTokenID, aScanner ) )
    }

    def _r( aTokenID, aRegex )
    {
        return _p( aTokenID, Patterns.regex( aRegex ) )
    }

    def _p( aTokenID, aPattern )
    {
        return _s( aTokenID, Scanners.isPattern( aPattern, aTokenID ) )
    }

    def _s( aTokenID, aScanner )
    {
        return Lexers.lexer( aScanner, Tokenizers.forTypedToken( aTokenID ) )
    }

    def myRuleStack = new ArrayList()
}
