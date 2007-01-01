package scripting;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import junit.framework.TestCase;
import org.codehaus.groovy.control.CompilationFailedException;



public final class GroovyTest extends TestCase
{
    public final void testCreate() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "" );
        assertNull( result );
    }

    public final void testValue() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "return \"toast\"" );
        assertEquals( "toast", result );
    }

    public final void testClosure() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "find_in = { return 17 }" );
        final Closure closure = ( Closure ) result;
        assertEquals( 17, closure.call() );
    }

    public final void testClass() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        assertNull( shell.evaluate( "class Queso { \n def toast() { \n return 17; \n } } \n return null" ) );
        assertEquals( 17, shell.evaluate( "new Queso().toast()" ) );
    }
}
