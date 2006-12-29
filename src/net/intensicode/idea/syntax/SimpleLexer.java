package net.intensicode.idea.syntax;

/**
 * TODO: Describe this!
 */
public interface SimpleLexer
{
    void start( char[] aBuffer, int aStartOffset, int aEndOffset );

    SimpleToken findToken( int aStartOffset );
}
