package net.intensicode.idea.core;

import junit.framework.TestCase;
import net.intensicode.idea.config.NamesValidatorConfiguration;

public final class ConfigurableNamesValidatorTest extends TestCase
{
    public final void testStuff()
    {
        final NamesValidatorConfiguration configuration = new FakeNamesValidatorConfiguration();
        final ConfigurableNamesValidator validator = new ConfigurableNamesValidator( configuration );
        assertTrue( validator.isIdentifier( "onlyme", null ) );
        assertFalse( validator.isIdentifier( "notme", null ) );
        assertTrue( validator.isKeyword( "abc", null ) );
        assertTrue( validator.isKeyword( "def", null ) );
        assertFalse( validator.isKeyword( "abcabc", null ) );
    }
}
