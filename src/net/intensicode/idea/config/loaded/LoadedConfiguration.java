package net.intensicode.idea.config.loaded;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import jfun.parsec.Lexers;
import jfun.parsec.Parser;
import jfun.parsec.Scanners;
import jfun.parsec.Tok;
import net.intensicode.idea.config.*;
import net.intensicode.idea.config.loaded.parser.*;
import net.intensicode.idea.core.ConfigurableAttributes;
import net.intensicode.idea.syntax.JParsecLexerAdapter;
import net.intensicode.idea.syntax.SimpleLexerAdapter;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.util.ReaderUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class LoadedConfiguration implements InstanceConfiguration, ConfigurationProperties
{
    public static final LoadedConfiguration tryLoading( final SystemContext aSystemContext, final String aFileName )
    {
        try
        {
            final OptionsFolder optionsFolder = aSystemContext.getOptionsFolder();
            final String config = optionsFolder.readFileIntoString( aFileName );
            final Reader reader = new StringReader( config );
            return new LoadedConfiguration( aSystemContext, reader );
        }
        catch ( final Throwable t )
        {
            LOG.info( "Failed loading configuration: ", t );
            return null;
        }
    }

    public LoadedConfiguration( final SystemContext aSystemContext, final Reader aReader ) throws IOException
    {
        mySystemContext = aSystemContext;

        loadConfiguration( aReader );
        validateConfiguration();

        myBracesConfiguration = new LoadedBracesConfiguration( this );
        myCommentConfiguration = new LoadedCommentConfiguration( this );
        myFileTypeConfiguration = new LoadedFileTypeConfiguration( this, aSystemContext.getOptionsFolder() );
        myNamesValidatorConfiguration = new LoadedNamesValidatorConfiguration( aSystemContext, this );
    }

    // From InstanceConfiguration

    public final Icon getIcon()
    {
        if ( isValidProperty( ICON ) == false ) return null;
        return mySystemContext.getOptionsFolder().loadIcon( getProperty( ICON ) );
    }

    public final String getName()
    {
        return getProperty( NAME );
    }

    public final String getDescription()
    {
        return getProperty( DESCRIPTION );
    }

    public final String getExampleCode()
    {
        if ( myExampleCode == null )
        {
            try
            {
                myExampleCode = readExampleCode();
            }
            catch ( final Throwable t )
            {
                LOG.info( t.toString() );
                LOG.error( t );
                return t.toString();
            }
        }
        return myExampleCode;
    }

    public final List<String> getKnownTokenIDs()
    {
        return myKnownTokenIDs;
    }

    public final boolean isVisibleToken( final String aID )
    {
        // TODO: Read this from some property?
        return true;
    }

    public final String getTokenAttributes( final String aTokenID )
    {
        final String attributes = myAttributes.get( aTokenID );
        if ( attributes == null ) return NO_ATTRIBUTES;
        return attributes;
    }

    public final String getTokenDescription( final String aTokenID )
    {
        final String description = myDescriptions.get( aTokenID );
        if ( description == null ) return aTokenID;
        return description;
    }

    public final BracesConfiguration getBracesConfiguration()
    {
        return myBracesConfiguration;
    }

    public final CommentConfiguration getCommentConfiguration()
    {
        return myCommentConfiguration;
    }

    public final FileTypeConfiguration getFileTypeConfiguration()
    {
        return myFileTypeConfiguration;
    }

    public final NamesValidatorConfiguration getNamesValidatorConfiguration()
    {
        return myNamesValidatorConfiguration;
    }

    public final LanguageConfiguration getLanguageConfiguration()
    {
        if ( myLanguageConfiguration == null )
        {
            myLanguageConfiguration = new LoadedLanguageConfiguration( this );
        }
        return myLanguageConfiguration;
    }

    public final ConfigurableAttributes getAttributes()
    {
        if ( myConfigurableAttributes == null )
        {
            final Aggregations aggregations = new Aggregations( myAggregations );
            myConfigurableAttributes = new ConfigurableAttributes( mySystemContext, this, aggregations );
        }
        return myConfigurableAttributes;
    }

    public final ScriptSupport getScriptSupport()
    {
        if ( myScriptSupport == null )
        {
            myScriptSupport = mySystemContext.createScriptSupport( getClassPathEntries() );
        }
        return myScriptSupport;
    }

    public final Lexer getLexer()
    {
        if ( mySyntaxLexer == null )
        {
            try
            {
                mySyntaxLexer = createLexer();
            }
            catch ( final Exception t )
            {
                LOG.info( t.toString() );
                LOG.error( t );
            }
        }
        return mySyntaxLexer;
    }

    // From ConfigurationProperties

    public final String getProperty( final String aKey )
    {
        return myProperties.get( aKey );
    }

    public final boolean isValidProperty( final String aKey )
    {
        final String value = getProperty( aKey );
        return value != null && value.length() > 0;
    }

    // Implementation

    private final void loadConfiguration( final Reader aReader ) throws IOException
    {
        final List<String> lines = ReaderUtils.readLines( aReader );
        if ( lines.size() < 3 ) throw new IOException( "Short configuration file" );

        final String configHeader = lines.get( 0 );
        if ( isValidVersion( configHeader ) == false )
        {
            throw new IOException( "Invalid configuration version: " + configHeader );
        }

        final ConfigurationParser parser = new ConfigurationParser();
        parser.addConsumer( new PropertyConsumer( myProperties ) );
        parser.addConsumer( new AggregationConsumer( myAggregations, "aggregations" ) );
        parser.addConsumer( new AssignmentConsumer( myAttributes, "attributes" ) );
        parser.addConsumer( new AssignmentConsumer( myDescriptions, "descriptions" ) );
        parser.addConsumer( new KnownIDConsumer( myKnownTokenIDs, "descriptions" ) );
        parser.consume( lines );
    }

    private final void validateConfiguration() throws IOException
    {
        for ( final String key : REQUIRED_ENTRIES )
        {
            if ( isValidProperty( key ) ) continue;
            throw new IOException( "Missing '" + key + "' entry" );
        }
    }

    private static final boolean isValidVersion( final String aConfigHeader )
    {
        return aConfigHeader.equals( "[SimpleSyntax:V1.1]" );
    }

    private final String readExampleCode() throws Throwable
    {
        if ( isValidProperty( EXAMPLE_CODE ) == false ) return "No example code!";

        final String exampleCodeFileName = getProperty( EXAMPLE_CODE );
        return mySystemContext.getOptionsFolder().readFileIntoString( exampleCodeFileName );
    }

    private final List<String> getClassPathEntries()
    {
        final ArrayList<String> classPath = new ArrayList<String>();
        if ( isValidProperty( SCRIPT_CLASS_PATH ) )
        {
            final String[] entries = getProperty( SCRIPT_CLASS_PATH ).split( "," );
            for ( final String entry : entries ) classPath.add( entry );
        }
        else
        {
            classPath.add( "lib-ruby" );
            classPath.add( "lib-groovy" );
        }
        return classPath;
    }

    private final Lexer createLexer()
    {
        try
        {
            final HashMap<String, Object> variables = new HashMap<String, Object>();
            variables.put( "configuration", getLanguageConfiguration() );

            final String fileName = getProperty( SYNTAX_DEFINITION );
            final ScriptSupport scriptSupport = getScriptSupport();
            return ( Lexer ) scriptSupport.createObject( fileName, variables, Lexer.class );
        }
        catch ( final Throwable t )
        {
            mySystemContext.getErrorHandler().onConfigurationError( t );

            final LanguageConfiguration configuration = getLanguageConfiguration();
            final Parser<Tok[]> lexer = Lexers.lexeme( Scanners.isWhitespaces(), Lexers.word() );
            final JParsecLexerAdapter parsecLexer = new JParsecLexerAdapter( lexer );
            return new SimpleLexerAdapter( configuration, parsecLexer );
        }
    }



    private Lexer mySyntaxLexer;

    private String myExampleCode;

    private ScriptSupport myScriptSupport;

    private LanguageConfiguration myLanguageConfiguration;

    private ConfigurableAttributes myConfigurableAttributes;

    private final BracesConfiguration myBracesConfiguration;

    private final CommentConfiguration myCommentConfiguration;

    private final FileTypeConfiguration myFileTypeConfiguration;

    private final NamesValidatorConfiguration myNamesValidatorConfiguration;


    private final SystemContext mySystemContext;

    private final ArrayList<String> myKnownTokenIDs = new ArrayList<String>();

    private final HashMap<String, String> myProperties = new HashMap<String, String>();

    private final HashMap<String, String> myAggregations = new HashMap<String, String>();

    private final HashMap<String, String> myAttributes = new HashMap<String, String>();

    private final HashMap<String, String> myDescriptions = new HashMap<String, String>();


    private static final String NAME = "Name";

    private static final String ICON = "Icon";

    private static final String NO_ATTRIBUTES = "";

    private static final String DESCRIPTION = "Description";

    private static final String EXAMPLE_CODE = "ExampleCode";

    private static final String SYNTAX_DEFINITION = "SyntaxDefinition";

    private static final String[] REQUIRED_ENTRIES = { NAME, DESCRIPTION, SYNTAX_DEFINITION };

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String SCRIPT_CLASS_PATH = "ScriptClassPath";
}
