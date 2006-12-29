package net.intensicode.idea.core;

import junit.framework.TestCase;
import net.intensicode.idea.FakeConfiguration;



public final class ConfigurableLanguageTest extends TestCase
{
    public final void testCreate()
    {
        final FakeConfiguration configuration = new FakeConfiguration();
        final ConfigurableLanguage language = ConfigurableLanguage.getOrCreate( configuration );
        assertEquals( "ConfigurableLanguageFake", language.getClass().getName() );
    }
}
