package net.intensicode.idea.core;

/**
 * TODO: Describe this!
 */
public interface SimpleLexer
{
    SimpleToken findToken( char[] aBuffer, int aStartOffset, int aEndOffset );
}
