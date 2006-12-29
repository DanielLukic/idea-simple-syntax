package net.intensicode.idea.config.loaded;

import com.intellij.lang.refactoring.NamesValidator;
import junit.framework.TestCase;
import net.intensicode.idea.config.FakeConfigurationProperties;
import net.intensicode.idea.core.FakeSystemContext;

import java.io.FileNotFoundException;

public final class LoadedNamesValidatorConfigurationTest extends TestCase
{
    public final void testSomething()
    {
        final FakeConfigurationProperties properties = new FakeConfigurationProperties();
        properties.entries.put( "NamesValidator", "RubyNamesValidator.rb" );
        properties.entries.put( "IdentifierRegex", "abc" );

        final FakeSystemContext context = new FakeSystemContext( this, "config" );
        final LoadedNamesValidatorConfiguration configuration = new LoadedNamesValidatorConfiguration( context, properties );

        assertTrue( configuration.hasExternalImplementation() );
        assertEquals( "abc", configuration.getIdentifierRegex().pattern() );

        properties.entries.put( "KeywordList", "abc,def" );
        assertEquals( 2, configuration.getKeywordList().size() );
        assertEquals( "abc", configuration.getKeywordList().get( 0 ) );
        assertEquals( "def", configuration.getKeywordList().get( 1 ) );

        properties.entries.put( "KeywordFile", "NOT FOUND" );
        try
        {
            configuration.getKeywordList();
            fail( "FileNotFound expected" );
        }
        catch ( final RuntimeException e )
        {
            assertTrue( e.getCause() instanceof FileNotFoundException );
        }

        properties.entries.put( "KeywordFile", "Ruby/Keywords.txt" );
        assertEquals( 40, configuration.getKeywordList().size() );
        assertEquals( "abc", configuration.getKeywordList().get( 0 ) );
        assertEquals( "def", configuration.getKeywordList().get( 1 ) );
        assertEquals( "alias", configuration.getKeywordList().get( 2 ) );
        assertEquals( "and", configuration.getKeywordList().get( 3 ) );
        assertEquals( "yield", configuration.getKeywordList().get( 39 ) );
    }

    public final void testExternalGroovy()
    {
        final FakeConfigurationProperties properties = new FakeConfigurationProperties();
        properties.entries.put( "NamesValidator", "Ruby/NamesValidator.groovy" );

        final FakeSystemContext context = new FakeSystemContext( this, "config" );
        final LoadedNamesValidatorConfiguration configuration = new LoadedNamesValidatorConfiguration( context, properties );

        validate( configuration );
    }

    public final void testExternalRuby()
    {
        final FakeConfigurationProperties properties = new FakeConfigurationProperties();
        properties.entries.put( "NamesValidator", "Ruby/NamesValidator.rb" );

        final FakeSystemContext context = new FakeSystemContext( this, "config" );
        final LoadedNamesValidatorConfiguration configuration = new LoadedNamesValidatorConfiguration( context, properties );

        validate( configuration );
    }

    // Implementation

    private final void validate( final LoadedNamesValidatorConfiguration aConfiguration )
    {
        assertTrue( aConfiguration.hasExternalImplementation() );

        final NamesValidator validator = aConfiguration.createExternalImplementation();
        assertNotNull( validator );

        assertEquals( true, validator.isIdentifier( "abc", null ) );
        assertEquals( false, validator.isIdentifier( "123", null ) );
        assertEquals( false, validator.isIdentifier( ":abc", null ) );

        assertEquals( true, validator.isIdentifier( "classnost", null ) );

        assertEquals( true, validator.isKeyword( "class", null ) );
        assertEquals( false, validator.isKeyword( "classnost", null ) );
        assertEquals( false, validator.isKeyword( "no class", null ) );
        assertEquals( false, validator.isKeyword( " class ", null ) );
    }
}
