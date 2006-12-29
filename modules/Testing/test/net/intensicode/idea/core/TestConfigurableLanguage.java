package net.intensicode.idea.core;

import junit.framework.TestCase;
import net.intensicode.idea.FakeConfiguration;



public final class TestConfigurableLanguage extends TestCase
{
    public final void testCreate()
    {
        final FakeConfiguration configuration = new FakeConfiguration();
        final SimpleLanguage language = SimpleLanguage.getOrCreate( configuration );
        assertEquals( "SimpleLanguageFake", language.getClass().getName() );
    }
}
