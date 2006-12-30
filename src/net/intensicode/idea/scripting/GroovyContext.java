package net.intensicode.idea.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * TODO: Describe this!
 */
public final class GroovyContext implements ScriptSupport
{
    public GroovyContext( final SystemContext aSystemContext )
    {
        myFolder = aSystemContext.getOptionsFolder();

        final Binding binding = new Binding();
        binding.setVariable( "context", this );
        binding.setVariable( "currentDir", myFolder.getConfigurationFolder() );

        try
        {
            final URL url1 = myFolder.getConfigurationFolder().toURL();
            final URL url2 = new File( myFolder.getConfigurationFolder(), "lib-groovy" ).toURL();
            final ClassLoader fuckedLoader = new URLClassLoader( new URL[]{ url2, url1 }, getClass().getClassLoader() );

            final GroovyClassLoader loader = new GroovyClassLoader( fuckedLoader );
            loader.addClasspath( myFolder.getConfigurationFolder().getPath() );

            myShell = new GroovyShell( loader, binding );
        }
        catch ( final MalformedURLException e )
        {
            throw new ScriptingException( e );
        }
    }

    public final Object source( final String aScriptName ) throws FileNotFoundException
    {
        return myShell.evaluate( myFolder.streamFile( aScriptName ) );
    }

    public final File getCurrentDir()
    {
        return myFolder.getConfigurationFolder();
    }

    // ScriptSupport

    public final Object createObject( final String aScriptFileName, final Class aTargetClass ) throws IOException
    {
        final Object result = source( aScriptFileName );
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



    private final GroovyShell myShell;

    private final OptionsFolder myFolder;
}
