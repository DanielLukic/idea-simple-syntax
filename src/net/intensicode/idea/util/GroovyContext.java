package net.intensicode.idea.util;

import groovy.lang.GroovyShell;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;

import java.io.FileNotFoundException;

/**
 * TODO: Describe this!
 */
public final class GroovyContext
{
    public GroovyContext( final GroovyShell aShell, final SystemContext aSystemContext )
    {
        myShell = aShell;
        myFolder = aSystemContext.getOptionsFolder();
    }

    public final Object source( final String aScriptName ) throws FileNotFoundException
    {
        return myShell.evaluate( myFolder.streamFile( aScriptName ) );
    }

    private final GroovyShell myShell;

    private final OptionsFolder myFolder;
}
