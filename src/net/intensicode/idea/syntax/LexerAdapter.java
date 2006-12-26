package net.intensicode.idea.syntax;

import com.intellij.lang.Language;
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

        advance();
    }

    // Implementation

    private final void updateTokenType( final int aStartOffset )
    {
        myTokenType = null;
        myTokenStart = aStartOffset;
        myTokenEnd = Math.min( myBuffer.length, myEndOffset );

        if ( aStartOffset >= myEndOffset ) return;

        final SimpleToken foundToken = myLexer.findToken( myBuffer, aStartOffset, myEndOffset );
        if ( foundToken == null )
        {
            myTokenType = DEFAULT_TOKEN;
            return;
        }

        myTokenType = myLanguageConfiguration.getToken( foundToken.id );
        myTokenStart = Math.max( 0, foundToken.start );
        myTokenEnd = Math.min( myBuffer.length, foundToken.end );
    }



    private char[] myBuffer;

    private int myStartOffset;

    private int myEndOffset;

    private int myTokenStart;

    private int myTokenEnd;

    private IElementType myTokenType;


    private final SimpleLexer myLexer;

    private final LanguageConfiguration myLanguageConfiguration;

    private static final IElementType DEFAULT_TOKEN = new IElementType( "DEFAULT", Language.ANY );

    private static final Logger LOG = LoggerFactory.getLogger();
}
