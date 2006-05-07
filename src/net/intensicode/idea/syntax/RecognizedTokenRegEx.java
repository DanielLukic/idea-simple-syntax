package net.intensicode.idea.syntax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * TODO: Describe this!
 */
public final class RecognizedTokenRegEx implements RecognizedToken
{
    public RecognizedTokenRegEx( final String aTokenID, final String aRegExPattern )
    {
        myTokenID = aTokenID;
        myMatcher = Pattern.compile( aRegExPattern ).matcher( RecognizedTokenRegEx.EMPTY_INPUT );
    }

    // RecognizedToken

    public final boolean isFoundAt( final CharSequence aCharSequence, final int aStartOffset )
    {
        if ( isFoundIn( aCharSequence, aStartOffset, aCharSequence.length() ) )
        {
            return getTokenStart() == aStartOffset;
        }
        return false;
    }

    public final boolean isFoundIn( final CharSequence aCharSequence, final int aStartOffset, final int aEndOffset )
    {
        myMatcher.reset( aCharSequence );
        if ( !myMatcher.find( aStartOffset ) ) return false;
        if ( myMatcher.start() < aStartOffset ) return false;
        if ( myMatcher.start() > aEndOffset ) return false;
        if ( myMatcher.end() < aStartOffset ) return false;
        if ( myMatcher.end() > aEndOffset ) return false;
        return true;
    }

    public final int getTokenStart()
    {
        return myMatcher.start();
    }

    public final int getTokenEnd()
    {
        return myMatcher.end();
    }

    public final String getTokenID()
    {
        return myTokenID;
    }



    private final Matcher myMatcher;

    private final String myTokenID;

    private static final String EMPTY_INPUT = "";
}
