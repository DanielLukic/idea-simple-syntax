package scripting.parser;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import jfun.parsec.Tok;
import junit.framework.TestCase;
import org.codehaus.groovy.control.CompilationFailedException;

public final class TestGroovyCalculator extends TestCase
{
    public final void testParser() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();

        final Object result = shell.evaluate( getClass().getResourceAsStream( "Calculator.groovy" ) );
        assertNotNull( result );
        assertTrue( result instanceof GroovyObject );

        final GroovyObject object = ( GroovyObject ) result;
        final Tok[] tokens = ( Tok[] ) object.invokeMethod( "parse", "1+2" );
        assertEquals( 3, tokens.length );
    }
}
