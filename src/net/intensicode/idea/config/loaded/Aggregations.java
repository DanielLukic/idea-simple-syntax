package net.intensicode.idea.config.loaded;

import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public final class Aggregations
{
    public static final Aggregations NO_AGGREGATIONS = new Aggregations();

    public Aggregations( final HashMap<String, String> aAggregations )
    {
        myAggregations = aAggregations;
    }

    public final String[] getSubTokens( final String aTokenID )
    {
        final String subTokens = myAggregations.get( aTokenID );
        if ( subTokens == null ) return new String[]{ aTokenID };
        return subTokens.split( "," );
    }

    // Implementation

    private Aggregations()
    {
        myAggregations = new HashMap<String, String>();
    }

    private final HashMap<String, String> myAggregations;
}
