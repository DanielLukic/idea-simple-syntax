package net.intensicode.idea.util;

import net.intensicode.idea.system.SystemContext;
import org.jruby.IRuby;
import org.jruby.runtime.Arity;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.callback.Callback;

import java.io.IOException;

/**
 * TODO: Describe this!
 */
public final class RubyContext implements Callback
{
    public RubyContext( final SystemContext aSystemContext )
    {
        mySystemContext = aSystemContext;
    }

    // From Callback

    public final IRubyObject execute( final IRubyObject aReceiver, final IRubyObject[] aArguments )
    {
        final IRuby ruby = aReceiver.getRuntime();
        try
        {
            final String fileName = aArguments[ 0 ].convertToString().toString();
            final String script = mySystemContext.getOptionsFolder().readFileIntoString( fileName );
            return ruby.evalScript( script );
        }
        catch ( final IOException e )
        {
            // TODO: How to throw a (J)Ruby exception here?
            throw new RuntimeException( "NYI - " + e );
        }
    }

    public final Arity getArity()
    {
        return Arity.singleArgument();
    }

    private final SystemContext mySystemContext;
}
