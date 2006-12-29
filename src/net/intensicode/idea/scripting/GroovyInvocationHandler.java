package net.intensicode.idea.scripting;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public class GroovyInvocationHandler implements InvocationHandler
{
    public GroovyInvocationHandler( final Object aObject )
    {
        myObject = aObject;
    }

    // From InvocationHandler

    public final Object invoke( final Object aProxy, final Method aMethod, final Object[] aArguments ) throws Throwable
    {
        if ( myCachedMethods.containsKey( aMethod ) )
        {
            return myCachedMethods.get( aMethod ).invoke( myObject, aArguments );
        }

        final String methodName = aMethod.getName();
        for ( final Method method : myObject.getClass().getMethods() )
        {
            if ( method.getName().equals( methodName ) == false ) continue;
            if ( method.getParameterTypes().length != aArguments.length ) continue;
            final Object result = method.invoke( myObject, aArguments );
            myCachedMethods.put( aMethod, method );
            return result;
        }

        throw new ScriptingException( "Method not implemented: " + aMethod );
    }


    
    private final Object myObject;

    private final HashMap<Method, Method> myCachedMethods = new HashMap<Method, Method>();
}
