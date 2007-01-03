package net.intensicode.idea.system.production;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.scripting.GroovySupport;
import net.intensicode.idea.scripting.RubySupport;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: Describe this!
 */
public final class ProductionScriptSupport implements ScriptSupport
{
    public ProductionScriptSupport( final SystemContext aSystemContext, final List<String> aClassPathEntries )
    {
        mySystemContext = aSystemContext;
        myClassPathEntries = aClassPathEntries;
    }

    // From ScriptSupport

    public final Object createObject( final String aScriptFileName, final HashMap<String, Object> aVariables, final Class aTargetClass ) throws IOException
    {
        if ( aScriptFileName.endsWith( ".groovy" ) )
        {
            LOG.info( "Executing Groovy script" );
            return new GroovySupport( mySystemContext, myClassPathEntries ).createObject( aScriptFileName, aVariables, aTargetClass );
        }
        else if ( aScriptFileName.endsWith( ".ruby" ) || aScriptFileName.endsWith( ".rb" ) )
        {
            LOG.info( "Executing JRuby script" );
            return new RubySupport( mySystemContext, myClassPathEntries ).createObject( aScriptFileName, aVariables, aTargetClass );
        }
        else
        {
            throw new RuntimeException( "No scripting engine available for " + aScriptFileName );
        }
    }

    private final SystemContext mySystemContext;

    private final List<String> myClassPathEntries;

    private static final Logger LOG = LoggerFactory.getLogger();
}
