
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

        myRuleStack.add( _s( "UNRECOGNIZED", Scanners.anyChar() ) )

        def parsers = new Parser[ myRuleStack.size() ]
        for ( idx in 0..parsers.length - 1 )
        {
            parsers[ idx ] = myRuleStack.get( idx )
        }

        def l_all = Parsers.alt( parsers )
        def s_whitespace = Scanners.isWhitespaces().many()
        return Lexers.lexeme( s_whitespace, l_all )
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
