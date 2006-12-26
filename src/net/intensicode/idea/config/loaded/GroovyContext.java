package net.intensicode.idea.config.loaded;

import groovy.lang.GroovyShell;
import net.intensicode.idea.system.SystemContext;

import java.io.FileNotFoundException;

/**
 * TODO: Describe this!
 */
final class GroovyContext
{
    GroovyContext( final GroovyShell aShell, final SystemContext aSystemContext )
    {
        myShell = aShell;
        mySystemContext = aSystemContext;
    }

    public final void source( final String aScriptName ) throws FileNotFoundException
    {
        myShell.evaluate( mySystemContext.getOptionsFolder().streamFile( aScriptName ) );
    }

    private final GroovyShell myShell;

    private final SystemContext mySystemContext;
}
