package net.intensicode.idea.config.loaded.parser;

import com.intellij.openapi.diagnostic.Logger;

import java.util.HashMap;

import net.intensicode.idea.util.LoggerFactory;

/**
 * TODO: Describe this!
 */
public final class AggregationConsumer implements LineConsumer
{
    public AggregationConsumer( final HashMap<String, String> aHashMap, final String aIdentifier )
    {
        myHashMap = aHashMap;
        myIdentifier = aIdentifier;
    }

    // From LineConsumer

    public final void consume( final int aLineType, final MatchedLine aMatchedLine )
    {
        if ( aLineType != ConfigurationParser.ASSIGNMENT ) return;

        final String name = aMatchedLine.getValue( 1 );
        if ( name.equalsIgnoreCase( myIdentifier ) == false ) return;

        final String key = aMatchedLine.getValue( 2 );
        final String value = aMatchedLine.getValue( 3 );
        myHashMap.put( key, value );

        LOG.debug( "Aggregated " + name + ": " + key + " = " + value );
    }



    private final String myIdentifier;

    private final HashMap<String, String> myHashMap;

    private static final Logger LOG = LoggerFactory.getLogger();
}
