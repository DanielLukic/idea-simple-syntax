package net.intensicode.idea.syntax;

import java.util.List;

/**
 * TODO: Describe this!
 */
public interface SimpleLexer
{
    void start( char[] aBuffer, int aStartOffset, int aEndOffset );

    List<SimpleToken> tokenize();

    SimpleToken findToken( int aStartOffset );
}
