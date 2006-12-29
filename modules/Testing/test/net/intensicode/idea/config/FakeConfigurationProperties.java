package net.intensicode.idea.config;

import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public final class FakeConfigurationProperties implements ConfigurationProperties
{
    public final HashMap<String, String> entries = new HashMap<String, String>();

    public final String getProperty( final String aKey )
    {
        return entries.get( aKey );
    }

    public final boolean isValidProperty( final String aKey )
    {
        return entries.get( aKey ) != null;
    }
}
