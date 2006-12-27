package net.intensicode.idea.util;

/**
 * TODO: Describe this!
 */
public final class MutableCharSequence implements CharSequence
{
    public MutableCharSequence()
    {
    }

    public MutableCharSequence( final char[] aBuffer, final int aStart, final int aEnd )
    {
        reset( aBuffer, aStart, aEnd );
    }

    public final void reset( final char[] aBuffer, final int aStart, final int aEnd )
    {
        myBuffer = aBuffer;
        myStart = aStart;
        myEnd = aEnd;
    }

    // From CharSequence

    public final char charAt( final int aIndex )
    {
        return myBuffer[ myStart + aIndex ];
    }

    public final int length()
    {
        return myEnd - myStart;
    }

    public final CharSequence subSequence( final int aStart, final int aEnd )
    {
        return new MutableCharSequence( myBuffer, myStart + aStart, myStart + aEnd );
    }



    private int myEnd;

    private int myStart;

    private char[] myBuffer;
}
