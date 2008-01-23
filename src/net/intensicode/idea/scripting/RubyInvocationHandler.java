package net.intensicode.idea.scripting;

import org.jruby.Ruby;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TODO: Describe this!
 */
public class RubyInvocationHandler implements InvocationHandler
{
    public RubyInvocationHandler( final Ruby aRuntime, final IRubyObject aObject )
    {
        myRuntime = aRuntime;
        myObject = aObject;
    }

    // From InvocationHandler

    public final Object invoke( final Object aProxy, final Method aMethod, final Object[] aArguments ) throws Throwable
    {
        final IRubyObject[] args;
        if ( aArguments == null || aArguments.length == 0 ) args = new IRubyObject[0];
        else args = JavaUtil.convertJavaArrayToRuby( myRuntime, aArguments );

        final IRubyObject result = myObject.callMethod( myRuntime.getCurrentContext(), aMethod.getName(), args );
        return JavaUtil.convertRubyToJava( result );
    }

    private final Ruby myRuntime;

    private final IRubyObject myObject;
}
