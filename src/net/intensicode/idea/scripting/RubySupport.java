package net.intensicode.idea.scripting;

import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.system.SystemContext;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.javasupport.Java;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.Arity;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: Describe this!
 */
public final class RubySupport implements Callback, ScriptSupport
{
    public RubySupport( final SystemContext aSystemContext )
    {
        this( aSystemContext, new ArrayList<String>() );
    }

    public RubySupport( final SystemContext aSystemContext, final List<String> aClassPathEntries )
    {
        mySystemContext = aSystemContext;
        myClassPathEntries = aClassPathEntries;

        myRuntime = Ruby.newInstance( System.in, System.out, System.err );
    }

    // ScriptSupport

    public final Object createObject( final String aScriptFileName, final HashMap<String, Object> aVariables, final Class aTargetClass ) throws IOException
    {
        final OptionsFolder folder = mySystemContext.getOptionsFolder();
        final String script = folder.readFileIntoString( aScriptFileName );

        final File configFolder = new File( folder.getConfigurationFolder().getPath() );
        myRuntime.setCurrentDirectory( configFolder.getAbsolutePath() );

        final List loadPath = myRuntime.getLoadService().getLoadPath();
        for ( final String entry : myClassPathEntries )
        {
            final File file = new File( folder.getConfigurationFolder(), entry );
            loadPath.add( file.getAbsolutePath() );
        }

        final IRubyObject topSelf = myRuntime.getTopSelf();
        topSelf.defineSingletonMethod( "source", this );

        aVariables.put( "currentDir", configFolder );
        aVariables.put( "systemContext", mySystemContext );
        for ( final String name : aVariables.keySet() )
        {
            topSelf.defineSingletonMethod( name, new Callback()
            {
                public IRubyObject execute( IRubyObject aReceiver, IRubyObject[] aArguments )
                {
                    final IRubyObject rubyObject = JavaUtil.convertJavaToRuby( myRuntime, aVariables.get( name ) );
                    return Java.java_to_ruby( aReceiver, rubyObject );
                }

                public Arity getArity()
                {
                    return Arity.noArguments();
                }
            } );
        }

        final IRubyObject result = myRuntime.evalScript( script );
        if ( result == null ) throw new ScriptingException( "Script did not create result object" );

        try
        {
            return JavaUtil.convertRubyToJava( result, aTargetClass );
        }
        catch ( final Throwable t )
        {
            final ClassLoader loader = getClass().getClassLoader();
            final Class[] interfaces = new Class[]{ aTargetClass };
            final InvocationHandler handler = new RubyInvocationHandler( myRuntime, result );
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



    private final IRuby myRuntime;

    private final SystemContext mySystemContext;

    private final List<String> myClassPathEntries;
}
