package net.intensicode.idea.config.loaded;

import com.intellij.lang.refactoring.JavaNamesValidator;
import com.intellij.lang.refactoring.NamesValidator;
import net.intensicode.idea.config.ConfigurationProperties;
import net.intensicode.idea.config.NamesValidatorConfiguration;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * TODO: Describe this!
 */
final class LoadedNamesValidatorConfiguration implements NamesValidatorConfiguration
{
    public LoadedNamesValidatorConfiguration( final SystemContext aSystemContext, final ConfigurationProperties aProperties )
    {
        mySystemContext = aSystemContext;
        myProperties = aProperties;
    }

    // From NamesValidatorConfiguration

    public final boolean hasExternalImplementation()
    {
        return myProperties.isValidProperty( NAMES_VALIDATOR_ID );
    }

    public final NamesValidator createExternalImplementation()
    {
        final String scriptFileName = myProperties.getProperty( NAMES_VALIDATOR_ID );
        final ScriptSupport scriptSupport = mySystemContext.createScriptSupport( new ArrayList<String>() );
        try
        {
            final HashMap<String, Object> variables = new HashMap<String, Object>();
            return ( NamesValidator ) scriptSupport.createObject( scriptFileName, NamesValidator.class, variables );
        }
        catch ( final Throwable t )
        {
            mySystemContext.getErrorHandler().onConfigurationError( t );
            return new JavaNamesValidator();
        }
    }

    public final Pattern getIdentifierRegex()
    {
        final String regex = myProperties.getProperty( IDENTIFIER_REGEX_ID );
        if ( regex == null ) return DEFAULT_IDENTIFIER_PATTERN;
        return Pattern.compile( regex );
    }

    public final List<String> getKeywordList()
    {
        final ArrayList<String> keywords = new ArrayList<String>();

        final String list = myProperties.getProperty( "KeywordList" );
        if ( list != null )
        {
            for ( final String keyword : list.split( "," ) ) keywords.add( keyword );
        }

        final String file = myProperties.getProperty( "KeywordFile" );
        if ( file != null )
        {
            for ( final String keyword : loadKeywordsFrom( file ) ) keywords.add( keyword );
        }

        return keywords;
    }

    // Implementation

    private final String[] loadKeywordsFrom( final String aFileName )
    {
        try
        {
            final OptionsFolder folder = mySystemContext.getOptionsFolder();
            final String readEntries = folder.readFileIntoString( aFileName );
            return readEntries.split( "[\n\r]" );
        }
        catch ( final IOException e )
        {
            mySystemContext.getErrorHandler().onConfigurationError( e );
            return new String[0];
        }
    }



    private final SystemContext mySystemContext;

    private final ConfigurationProperties myProperties;


    private static final String NAMES_VALIDATOR_ID = "NamesValidator";

    private static final String IDENTIFIER_REGEX_ID = "IdentifierRegex";

    private static final Pattern DEFAULT_IDENTIFIER_PATTERN = Pattern.compile( "\\b[a-z]\\w*\\b" );
}
