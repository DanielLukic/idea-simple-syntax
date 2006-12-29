package net.intensicode.idea.util;

import junit.framework.TestCase;

/**
 * TODO: Describe this!
 */
public final class MutableCharSequenceTest extends TestCase
{
    public final void testBasics()
    {
        final String data = "this is a test";
        final MutableCharSequence sequence = new MutableCharSequence( data.toCharArray(), 2, data.length() );
        assertEquals( "is is a test", sequence.toString() );
        assertEquals( "is", sequence.subSequence( 0, 2 ).toString() );
        assertEquals( "a ", sequence.subSequence( 6, 8 ).toString() );
    }
}
