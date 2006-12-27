package net.intensicode.idea.core;

/**
 * TODO: Describe this!
 */
public final class SimpleToken
{
    public Object id;

    public int start;

    public int end;



    public final String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "SimpleToken(id=" );
        builder.append( id );
        builder.append( ",start=" );
        builder.append( start );
        builder.append( ",end=" );
        builder.append( end );
        builder.append( ")" );
        return builder.toString();
    }
}
