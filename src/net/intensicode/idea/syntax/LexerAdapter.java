package net.intensicode.idea.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.core.SimpleLexer;
import net.intensicode.idea.core.SimpleToken;
import net.intensicode.idea.util.LoggerFactory;

/**
 * TODO: Describe this!
 */
public final class LexerAdapter implements Lexer
{
    public LexerAdapter( final LanguageConfiguration aLanguageConfiguration, final SimpleLexer aLexer )
    {
        myLanguageConfiguration = aLanguageConfiguration;
        myLexer = aLexer;
    }

    // From Lexer

    public final void advance()
    {
        updateTokenType( myTokenEnd );
    }

    public final char[] getBuffer()
    {
        return myBuffer;
    }

    public final int getBufferEnd()
    {
        return myEndOffset;
    }

    public final LexerPosition getCurrentPosition()
    {
        throw new RuntimeException( "NYI" );
    }

    public final int getState()
    {
        return 0;
    }

    public final int getTokenEnd()
    {
        return Math.min( myEndOffset, myTokenEnd );
    }

    public final int getTokenStart()
    {
        return Math.max( myStartOffset, myTokenStart );
    }

    public final IElementType getTokenType()
    {
        return myTokenType;
    }

    public final void restore( final LexerPosition position )
    {
        throw new RuntimeException( "NYI" );
    }

    public final void start( char[] buffer )
    {
        start( buffer, 0, buffer.length );
    }

    public final void start( char[] buffer, int startOffset, int endOffset )
    {
        start( buffer, startOffset, endOffset, 0 );
    }

    public final void start( char[] buffer, int startOffset, int endOffset, int initialState )
    {
        myBuffer = buffer;
        myStartOffset = startOffset;
        myEndOffset = endOffset;

        myTokenType = null;
        myTokenStart = myTokenEnd = startOffset;

        myLexer.start( buffer, startOffset, endOffset );

        advance();
    }

    // Implementation

    private final void updateTokenType( final int aStartOffset )
    {
        myTokenType = null;
        myTokenStart = aStartOffset;
        myTokenEnd = aStartOffset + 1;

        if ( aStartOffset >= myEndOffset )
        {
            LOG.debug( "EOF reached - returning with myTokenType = null" );
            myTokenStart = myTokenEnd = myEndOffset;
            return;
        }

        final SimpleToken foundToken = myLexer.findToken( aStartOffset );
        if ( foundToken == null )
        {
            LOG.debug( "myLexer found no token - advancing with myTokenType = UNRECOGNIZED" );
            myTokenType = myLanguageConfiguration.getToken( "UNRECOGNIZED" );
            return;
        }

        // Interesting fix I needed here: In production version of IDEA it requires whitespace to be a token, too.
        // I enfore WHITESPACE tokens in case the lexer configuration does not take care of it.
        if ( foundToken.start > aStartOffset )
        {
            myTokenStart = aStartOffset;
            myTokenEnd = foundToken.start;
            myTokenType = myLanguageConfiguration.getToken( "WHITESPACE" );
            return;
        }

        myTokenType = myLanguageConfiguration.getToken( foundToken.id );
        myTokenStart = foundToken.start;
        myTokenEnd = foundToken.end;
    }



    private char[] myBuffer;

    private int myStartOffset;

    private int myEndOffset;

    private int myTokenStart;

    private int myTokenEnd;

    private IElementType myTokenType;


    private final SimpleLexer myLexer;

    private final LanguageConfiguration myLanguageConfiguration;

    private static final Logger LOG = LoggerFactory.getLogger();
}
