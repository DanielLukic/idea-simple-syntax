package net.intensicode.idea.util;

import junit.framework.TestCase;

/**
 * TODO: Describe this!
 */
public final class SimplePatternTest extends TestCase
{
    public final void testMakeMatcher()
    {
        assertFalse( match( "OP_*", "DUMMY" ) );
        assertTrue( match( "OP_*", "OP_DUMMY" ) );

        assertTrue( match( "test?me", "test-me" ) );
        assertTrue( match( "test*me", "test-me" ) );
        assertTrue( match( "test*me", "test-it-me" ) );
        assertTrue( match( "test*me", "testme" ) );
        assertFalse( match( "test*me", "test" ) );
        assertFalse( match( "test*me", "X" ) );
    }

    private static final boolean match( final String aPattern, final String aTarget )
    {
        return new SimplePattern( aPattern ).matches( aTarget );
    }
}
