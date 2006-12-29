package net.intensicode.idea.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * TODO: Describe this!
 */
public final class GroovyContext implements ScriptSupport
{
    public GroovyContext( final SystemContext aSystemContext )
    {
        mySystemContext = aSystemContext;
        myFolder = aSystemContext.getOptionsFolder();
    }

    public final Object source( final String aScriptName ) throws FileNotFoundException
    {
        return myShell.evaluate( myFolder.streamFile( aScriptName ) );
    }

    public final File currentDir()
    {
        return myFolder.getConfigurationFolder();
    }

    // ScriptSupport

    public final Object createObject( final String aScriptFileName, final Class aTargetClass ) throws IOException
    {
        final OptionsFolder folder = mySystemContext.getOptionsFolder();
        final String script = folder.readFileIntoString( aScriptFileName );

        final Binding binding = myShell.getContext();
        binding.setVariable( "context", this );
        binding.setVariable( "currendDir", myFolder.getConfigurationFolder() );
        final Object result = myShell.evaluate( script );

        try
        {
            return aTargetClass.cast( result );
        }
        catch ( final Throwable t )
        {
            final ClassLoader loader = getClass().getClassLoader();
            final Class[] interfaces = new Class[]{ aTargetClass };
            final InvocationHandler handler = new GroovyInvocationHandler( result );
            return Proxy.newProxyInstance( loader, interfaces, handler );
        }
    }



    private final OptionsFolder myFolder;

    private final SystemContext mySystemContext;

    private final GroovyShell myShell = new GroovyShell();
}
