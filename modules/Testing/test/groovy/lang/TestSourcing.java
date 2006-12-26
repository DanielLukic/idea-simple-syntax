package groovy.lang;

import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import org.codehaus.groovy.control.CompilationFailedException;

public final class TestSourcing extends TestCase
{
    public final void testRecognizer() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();

        final FakeSystemContext context = new FakeSystemContext( this );
        shell.getContext().setVariable( "context", context );

        final Object result = shell.evaluate( getClass().getResourceAsStream( "TestCode_SCRIPT-1.groovy" ) );

        assertEquals( "SCRIPT2/SCRIPT1", result.toString() );
    }
}
