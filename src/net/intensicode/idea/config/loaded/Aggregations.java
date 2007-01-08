package net.intensicode.idea.config.loaded;

import net.intensicode.idea.util.SimplePattern;

import java.util.ArrayList;
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
        final String[] patterns = getSubTokenPatterns( aTokenID );
        final ArrayList<String> subTokens = new ArrayList<String>();
        for ( final String token : patterns )
        {
            if ( SimplePattern.isPattern( token ) ) continue;
            subTokens.add( token );
        }
        return subTokens.toArray( new String[subTokens.size()] );
    }

    public final String findMatch( final String aTokenID )
    {
        for ( final String superToken : myAggregations.keySet() )
        {
            final String[] subTokens = getSubTokenPatterns( superToken );
            for ( final String token : subTokens )
            {
                if ( new SimplePattern( token ).matches( aTokenID ) ) return superToken;
            }
        }
        return null;
    }

    // Implementation

    private Aggregations()
    {
        myAggregations = new HashMap<String, String>();
    }

    private final String[] getSubTokenPatterns( final String aTokenID )
    {
        final String subTokens = myAggregations.get( aTokenID );
        if ( subTokens == null ) return new String[]{ aTokenID };
        return subTokens.split( "," );
    }



    private final HashMap<String, String> myAggregations;
}
