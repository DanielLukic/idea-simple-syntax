package net.intensicode.idea.config.loaded;

import com.intellij.openapi.diagnostic.Logger;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import jfun.parsec.Parser;
import jfun.parsec.Tok;
import net.intensicode.idea.config.*;
import net.intensicode.idea.config.loaded.parser.AssignmentConsumer;
import net.intensicode.idea.config.loaded.parser.ConfigurationParser;
import net.intensicode.idea.config.loaded.parser.KnownIDConsumer;
import net.intensicode.idea.config.loaded.parser.PropertyConsumer;
import net.intensicode.idea.core.SimpleAttributes;
import net.intensicode.idea.core.SimpleLanguage;
import net.intensicode.idea.core.SimpleLexer;
import net.intensicode.idea.syntax.JParsecLexer;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.GroovyContext;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.util.ReaderUtils;
import net.intensicode.idea.util.RubyContext;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.ast.Node;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;

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
public class LoadedConfiguration implements InstanceConfiguration, ConfigurationProperties
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

    public synchronized final String getExampleCode()
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

    public synchronized final BracesConfiguration getBracesConfiguration()
    {
        if ( myBracesConfiguration == null )
        {
            myBracesConfiguration = new LoadedBracesConfiguration( this );
        }
        return myBracesConfiguration;
    }

    public synchronized final CommentConfiguration getCommentConfiguration()
    {
        if ( myCommentConfiguration == null )
        {
            myCommentConfiguration = new LoadedCommentConfiguration( this );
        }
        return myCommentConfiguration;
    }

    public synchronized final FileTypeConfiguration getFileTypeConfiguration()
    {
        if ( myFileTypeConfiguration == null )
        {
            myFileTypeConfiguration = new LoadedFileTypeConfiguration( this, mySystemContext.getOptionsFolder() );
        }
        return myFileTypeConfiguration;
    }

    public synchronized final LanguageConfiguration getLanguageConfiguration()
    {
        if ( myLanguage == null )
        {
            myLanguage = SimpleLanguage.getOrCreate( mySystemContext, this );
        }
        return myLanguage;
    }

    public synchronized final SimpleAttributes getAttributes()
    {
        if ( mySimpleAttributes == null )
        {
            mySimpleAttributes = new SimpleAttributes( mySystemContext, this );
        }
        return mySimpleAttributes;
    }

    public synchronized final SimpleLexer getLexer()
    {
        if ( mySyntaxLexer == null )
        {
            try
            {
                final OptionsFolder folder = mySystemContext.getOptionsFolder();

                final String fileName = getProperty( SYNTAX_DEFINITION );
                final String fullFileName = folder.makeFileName( fileName );

                if ( fileName.endsWith( ".groovy" ) )
                {
                    LOG.info( "Creating groovy lexer" );

                    final GroovyShell shell = new GroovyShell();
                    shell.getContext().setVariable( "context", new GroovyContext( shell, mySystemContext ) );

                    final Script script = shell.parse( folder.streamFile( fileName ), fullFileName );
                    final Parser<Tok[]> lexer = ( Parser<Tok[]> ) script.run();

                    mySyntaxLexer = new JParsecLexer( lexer );
                }
                else if ( fileName.endsWith( ".ruby" ) || fileName.endsWith( ".rb" ) )
                {
                    LOG.info( "Creating ruby lexer" );

                    final IRuby runtime = Ruby.getDefaultInstance();
                    runtime.getTopSelf().defineSingletonMethod( "source", new RubyContext( mySystemContext ) );

                    final Node node = runtime.parse( folder.readFile( fileName ), fullFileName, null );

                    final IRubyObject result = runtime.eval( node );
                    final Parser<Tok[]> lexerImpl = ( Parser<Tok[]> ) JavaUtil.convertRubyToJava( result, Parser.class );
                    mySyntaxLexer = new JParsecLexer( lexerImpl );
                }
                else
                {
                    throw new RuntimeException( "No scripting engine available for " + fileName );
                }
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
        parser.addConsumer( new AssignmentConsumer( myAttributes, "attributes" ) );
        parser.addConsumer( new AssignmentConsumer( myDescriptions, "descriptions" ) );
        parser.addConsumer( new KnownIDConsumer( myKnownTokenIDs, "descriptions" ) );
        parser.consume( lines );
    }

    private final void validateConfiguration() throws IOException
    {
        for ( final String key : REQUIRED_ENTRIES )
        {
            if ( isValidProperty( key ) == false )
            {
                throw new IOException( "Missing '" + key + "' entry" );
            }
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



    private String myExampleCode;

    private SimpleLexer mySyntaxLexer;

    private SimpleLanguage myLanguage;

    private SimpleAttributes mySimpleAttributes;

    private LoadedBracesConfiguration myBracesConfiguration;

    private LoadedCommentConfiguration myCommentConfiguration;

    private LoadedFileTypeConfiguration myFileTypeConfiguration;


    private final SystemContext mySystemContext;

    private final ArrayList<String> myKnownTokenIDs = new ArrayList<String>();

    private final HashMap<String, String> myProperties = new HashMap<String, String>();

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
}
