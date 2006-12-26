package net.intensicode.idea.core;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;

/**
 * TODO: Describe this!
 */
public final class NullLexer implements Lexer
{
    public static final NullLexer INSTANCE = new NullLexer();

    public void advance()
    {
        throw new RuntimeException( "NYI" );
    }

    public char[] getBuffer()
    {
        throw new RuntimeException( "NYI" );
    }

    public int getBufferEnd()
    {
        throw new RuntimeException( "NYI" );
    }

    public LexerPosition getCurrentPosition()
    {
        throw new RuntimeException( "NYI" );
    }

    public int getState()
    {
        throw new RuntimeException( "NYI" );
    }

    public int getTokenEnd()
    {
        throw new RuntimeException( "NYI" );
    }

    public int getTokenStart()
    {
        throw new RuntimeException( "NYI" );
    }

    public IElementType getTokenType()
    {
        throw new RuntimeException( "NYI" );
    }

    public void restore( LexerPosition position )
    {
        throw new RuntimeException( "NYI" );
    }

    public void start( char[] buffer )
    {
        throw new RuntimeException( "NYI" );
    }

    public void start( char[] buffer, int startOffset, int endOffset )
    {
        throw new RuntimeException( "NYI" );
    }

    public void start( char[] buffer, int startOffset, int endOffset, int initialState )
    {
        throw new RuntimeException( "NYI" );
    }

    private NullLexer()
    {
    }
}
