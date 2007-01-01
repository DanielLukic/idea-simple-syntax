package net.intensicode.idea.scripting;

import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.Arity;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: Describe this!
 */
public final class RubyContext implements Callback, ScriptSupport
{
    public RubyContext( final SystemContext aSystemContext, final List<String> aClassPathEntries )
    {
        mySystemContext = aSystemContext;
        myClassPathEntries = aClassPathEntries;
    }

    // ScriptSupport

    public final Object createObject( final String aScriptFileName, final Class aTargetClass, final HashMap<String, Object> aVariables ) throws IOException
    {
        final OptionsFolder folder = mySystemContext.getOptionsFolder();
        final String script = folder.readFileIntoString( aScriptFileName );

        final IRuby runtime = Ruby.getDefaultInstance();

        final File configFolder = new File( folder.getConfigurationFolder().getPath() );
        runtime.setCurrentDirectory( configFolder.getAbsolutePath() );

        final List loadPath = runtime.getLoadService().getLoadPath();
        for ( final String entry : myClassPathEntries )
        {
            final File file = new File( folder.getConfigurationFolder(), entry );
            loadPath.add( file.getAbsolutePath() );
        }

        final IRubyObject topSelf = runtime.getTopSelf();
        topSelf.defineSingletonMethod( "source", this );

        addVariable( runtime, "currentDir", configFolder );
        addVariable( runtime, "systemContext", mySystemContext );
        for ( final String name : aVariables.keySet() )
        {
            addVariable( runtime, name, aVariables.get( name ) );
        }

        final IRubyObject result = runtime.evalScript( script );
        if ( result == null ) throw new ScriptingException( "Script did not create result object" );

        try
        {
            return JavaUtil.convertRubyToJava( result, aTargetClass );
        }
        catch ( final Throwable t )
        {
            final ClassLoader loader = getClass().getClassLoader();
            final Class[] interfaces = new Class[]{ aTargetClass };
            final InvocationHandler handler = new RubyInvocationHandler( runtime, result );
            return Proxy.newProxyInstance( loader, interfaces, handler );
        }
    }

    // From Callback

    public final IRubyObject execute( final IRubyObject aReceiver, final IRubyObject[] aArguments )
    {
        final IRuby ruby = aReceiver.getRuntime();
        try
        {
            final String fileName = aArguments[ 0 ].convertToString().toString();
            final OptionsFolder folder = mySystemContext.getOptionsFolder();
            final String script = folder.readFileIntoString( fileName );
            return ruby.eval( ruby.parse( script, folder.makeFileName( fileName ), null ) );
        }
        catch ( final IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public final Arity getArity()
    {
        return Arity.singleArgument();
    }

    // Implementation

    private final void addVariable( final IRuby aRuntime, final String aName, final Object aJavaObject )
    {
        final IRubyObject rubyObject = JavaUtil.convertJavaToRuby( aRuntime, aJavaObject );
        final IRubyObject topSelf = aRuntime.getTopSelf();
        topSelf.defineSingletonMethod( aName, new Callback()
        {
            public final IRubyObject execute( final IRubyObject aReceiver, final IRubyObject[] aArguments )
            {
                return rubyObject;
            }

            public final Arity getArity()
            {
                return Arity.noArguments();
            }
        } );
    }



    private final SystemContext mySystemContext;

    private final List<String> myClassPathEntries;
}
