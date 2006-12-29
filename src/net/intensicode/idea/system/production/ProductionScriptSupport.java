package net.intensicode.idea.system.production;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.scripting.GroovyContext;
import net.intensicode.idea.scripting.RubyContext;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;

import java.io.IOException;

/**
 * TODO: Describe this!
 */
public final class ProductionScriptSupport implements ScriptSupport
{
    public ProductionScriptSupport( final SystemContext aSystemContext )
    {
        mySystemContext = aSystemContext;
    }

    // From ScriptSupport

    public final Object createObject( final String aScriptFileName, final Class aTargetClass ) throws IOException
    {
        if ( aScriptFileName.endsWith( ".groovy" ) )
        {
            LOG.info( "Executing Groovy script" );
            return new GroovyContext( mySystemContext ).createObject( aScriptFileName, aTargetClass );
        }
        else if ( aScriptFileName.endsWith( ".ruby" ) || aScriptFileName.endsWith( ".rb" ) )
        {
            LOG.info( "Executing JRuby script" );
            return new RubyContext( mySystemContext ).createObject( aScriptFileName, aTargetClass );
        }
        else
        {
            throw new RuntimeException( "No scripting engine available for " + aScriptFileName );
        }
    }



    private final SystemContext mySystemContext;

    private static final Logger LOG = LoggerFactory.getLogger();
}
