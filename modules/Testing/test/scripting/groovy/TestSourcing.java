package scripting.groovy;

import groovy.lang.GroovyShell;
import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.util.GroovyContext;

import java.io.InputStream;

public final class TestSourcing extends TestCase
{
    public final void testRecognizer() throws Exception
    {
        final GroovyShell shell = new GroovyShell();

        final FakeSystemContext systemContext = new FakeSystemContext( this );
        shell.getContext().setVariable( "context", new GroovyContext( systemContext ) );

        final Object result = shell.evaluate( readScript( "Script1.groovy" ), "Script1" );
        assertEquals( "BUSTED STUFF", result.toString() );
    }

    private final InputStream readScript( final String aResourceName )
    {
        return getClass().getResourceAsStream( aResourceName );
    }
}
