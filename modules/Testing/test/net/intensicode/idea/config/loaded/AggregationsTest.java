package net.intensicode.idea.config.loaded;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

public final class AggregationsTest extends TestCase
{
    public final void testGetSubTokens()
    {
        final HashMap<String, String> input = new HashMap<String, String>();
        input.put( "OPERATOR", "OP_*" );
        input.put( "KEYWORD", "KEYWORD_*,MORE,THAN,THIS" );

        final Aggregations aggregations = new Aggregations( input );
        validate( makeList(), aggregations.getSubTokens( "OPERATOR" ) );
        validate( makeList( "MORE", "THAN", "THIS" ), aggregations.getSubTokens( "KEYWORD" ) );
    }

    public final void testFindMatch()
    {
        final HashMap<String, String> input = new HashMap<String, String>();
        input.put( "OPERATOR", "OP_*" );
        input.put( "KEYWORD", "KEYWORD_*,MORE,THAN,THIS" );

        final Aggregations aggregations = new Aggregations( input );
        assertEquals( "OPERATOR", aggregations.findMatch( "OP_DUMMY" ) );
        assertNull( aggregations.findMatch( "DUMMY" ) );
    }

    private static final void validate( final ArrayList<String> a, final String[] b )
    {
        assertEquals( "size", a.size(), b.length );
        for ( final String v : b ) assertTrue( "found", a.contains( v ) );
    }

    private static final ArrayList<String> makeList( final String... aArguments )
    {
        final ArrayList<String> list = new ArrayList<String>();
        for ( final String arg : aArguments ) list.add( arg );
        return list;
    }
}
