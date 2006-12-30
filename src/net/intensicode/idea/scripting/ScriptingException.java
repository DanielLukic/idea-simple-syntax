package net.intensicode.idea.scripting;

/**
 * TODO: Describe this!
 */
public class ScriptingException extends RuntimeException
{
    public ScriptingException( final String aMessage )
    {
        super( aMessage );
    }

    public ScriptingException( final Throwable aThrowable )
    {
        super( aThrowable );
    }
}
