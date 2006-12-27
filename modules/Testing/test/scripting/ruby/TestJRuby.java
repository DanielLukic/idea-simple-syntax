package scripting.ruby;

import junit.framework.TestCase;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.ast.Node;

import java.io.InputStreamReader;
import java.io.Reader;

public final class TestJRuby extends TestCase
{
    public final void testEval()
    {
        final IRuby runtime = Ruby.getDefaultInstance();
        final String scriptName = "Script1.rb";
        final Node node = runtime.parse( readScript( scriptName ), scriptName, null );
        final Object result = runtime.eval( node );
        assertNotNull( result );
        assertEquals( "BUSTED", result.toString() );
    }

    private final Reader readScript( final String aResourceName )
    {
        return new InputStreamReader( getClass().getResourceAsStream( aResourceName ) );
    }
}
