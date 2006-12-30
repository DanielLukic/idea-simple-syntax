package net.intensicode.idea.syntax;

import jfun.parsec.*;
import jfun.parsec.pattern.Pattern;
import jfun.parsec.pattern.Patterns;
import jfun.parsec.tokens.Tokenizers;

import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public class JParsecLexerBase
{
    public JParsecLexerBase()
    {
    }

    public final Parser<Tok[]> lexer()
    {
        setup();

        myRuleStack.add( _s( "UNRECOGNIZED", Scanners.anyChar() ) );

        Parser[] parsers = new Parser[myRuleStack.size()];
        for ( int idx = 0; idx < parsers.length; idx++ )
        {
            parsers[ idx ] = myRuleStack.get( idx );
        }

        final Parser<Tok> l_all = Parsers.alt( parsers );
        final Parser<_> s_whitespace = Scanners.isWhitespaces().many();
        return Lexers.lexeme( s_whitespace, l_all );
    }

    public final void regex( final String aTokenID, final String aRegex )
    {
        myRuleStack.add( _r( aTokenID, aRegex ) );
    }

    public final void scanner( final String aTokenID, final Parser<_> aScanner )
    {
        myRuleStack.add( _s( aTokenID, aScanner ) );
    }

    public final Parser<Tok> _r( final String aTokenID, final String aRegex )
    {
        return _p( aTokenID, Patterns.regex( aRegex ) );
    }

    public final Parser<Tok> _p( final String aTokenID, final Pattern aPattern )
    {
        return _s( aTokenID, Scanners.isPattern( aPattern, aTokenID ) );
    }

    public final Parser<Tok> _s( final String aTokenID, final Parser<_> aScanner )
    {
        return Lexers.lexer( aScanner, Tokenizers.forTypedToken( aTokenID ) );
    }

    // Protected Interface

    protected void setup()
    {
    }

    private final ArrayList<Parser<Tok>> myRuleStack = new ArrayList<Parser<Tok>>();
}
