package net.intensicode.idea.syntax;

import jfun.parsec.Parser;
import jfun.parsec.Parsers;
import jfun.parsec.Tok;
import jfun.parsec.tokens.TypedToken;
import net.intensicode.idea.util.MutableCharSequence;

import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public final class JParsecLexerAdapter implements SimpleLexer
{
    public JParsecLexerAdapter( final Parser<Tok[]> aLexer )
    {
        myLexer = aLexer;
    }

    // From SimpleLexer

    public final void start( final char[] aBuffer, final int aStartOffset, final int aEndOffset )
    {
        myBuffer = aBuffer;
        myStartOffset = aStartOffset;
        myEndOffset = aEndOffset;

        myCache.clear();
    }

    public final SimpleToken findToken( final int aStartOffset )
    {
        if ( aStartOffset < myStartOffset || aStartOffset >= myEndOffset ) throw new IllegalArgumentException();

        if ( myCache.isEmpty() )
        {
            myCharSequence.reset( myBuffer, myStartOffset, myEndOffset );

            final Tok[] tokens = Parsers.runParser( myCharSequence, myLexer, null );
            if ( tokens == null || tokens.length == 0 ) return null;

            for ( final Tok token : tokens )
            {
                final SimpleToken simpleToken = makeSimpleToken( myStartOffset, token );
                myCache.add( simpleToken );
            }
        }

        final int cacheSize = myCache.size();
        for ( int idx = 0; idx < cacheSize; idx++ )
        {
            final SimpleToken token = myCache.get( idx );
            if ( aStartOffset >= token.start && aStartOffset < token.end ) return token;
            if ( aStartOffset < token.end ) return token;
        }

        return null;
    }

    // Implementation

    private final SimpleToken makeSimpleToken( final int aStartOffset, final Tok aToken )
    {
        final SimpleToken newToken = new SimpleToken();
        newToken.start = aStartOffset + aToken.getIndex();
        newToken.end = newToken.start + aToken.getLength();

        final TypedToken typedToken = ( TypedToken ) aToken.getToken();
        newToken.id = typedToken.getType();

        return newToken;
    }



    private char[] myBuffer;

    private int myEndOffset;

    private int myStartOffset;

    private final Parser<Tok[]> myLexer;

    private final ArrayList<SimpleToken> myCache = new ArrayList<SimpleToken>();

    private final MutableCharSequence myCharSequence = new MutableCharSequence();
}
