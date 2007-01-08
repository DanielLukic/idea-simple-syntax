package net.intensicode.idea.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Describe this!
 */
public final class SimplePattern
{
    public SimplePattern( final String aPattern )
    {
        myPattern = makePattern( aPattern );
    }

    public final boolean matches( final String aTarget )
    {
        final Matcher matcher = myPattern.matcher( aTarget );
        if ( matcher.matches() == false ) return false;
        return matcher.group().equals( aTarget );
    }

    public static final boolean isPattern( final String aPattern )
    {
        if ( aPattern.contains( "?" ) ) return true;
        if ( aPattern.contains( "*" ) ) return true;
        return false;
    }

    // Implementation

    private static final Pattern makePattern( final String aPattern )
    {
        final String pattern = aPattern.replaceAll( "\\*", ".*" ).replaceAll( "\\?", "." );
        return Pattern.compile( pattern );
    }

    private final Pattern myPattern;
}
