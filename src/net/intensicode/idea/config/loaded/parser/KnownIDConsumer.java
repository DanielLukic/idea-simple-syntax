package net.intensicode.idea.config.loaded.parser;

import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public final class KnownIDConsumer implements LineConsumer
{
    public KnownIDConsumer( final ArrayList<String> aOutputList, final String aIdentifier )
    {
        myOutputList = aOutputList;
        myIdentifier = aIdentifier;
    }

    // From LineConsumer

    public final void consume( final int aLineType, final MatchedLine aMatchedLine )
    {
        if ( aLineType != ConfigurationParser.ASSIGNMENT ) return;

        final String name = aMatchedLine.getValue( 1 );
        if ( name.equalsIgnoreCase( myIdentifier ) == false ) return;

        final String key = aMatchedLine.getValue( 2 );
        myOutputList.add( key );
    }



    private final String myIdentifier;

    private final ArrayList<String> myOutputList;
}
