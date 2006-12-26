package net.intensicode.idea.core;

import junit.framework.TestCase;
import net.intensicode.idea.FakeConfiguration;



public final class TestConfigurableLanguage extends TestCase
{
    public final void testCreate()
    {
        final FakeSystemContext context = new FakeSystemContext( this );
        final FakeConfiguration configuration = new FakeConfiguration();
        final SimpleLanguage language = SimpleLanguage.getOrCreate( context, configuration );
        assertEquals( "SimpleLanguageFake", language.getClass().getName() );
    }
}
