package scripting.ruby;

import junit.framework.TestCase;
import org.jruby.Ruby;

import java.io.InputStreamReader;
import java.io.Reader;

public final class RubyTest extends TestCase
{
    public final void testEval()
    {
        final Ruby runtime = Ruby.getDefaultInstance();
        final String scriptName = "Script1.rb";
        final Object result = runtime.evalScript( readScript( scriptName ), scriptName );
        assertNotNull( result );
        assertEquals( "BUSTED", result.toString() );
    }

    private final Reader readScript( final String aResourceName )
    {
        return new InputStreamReader( getClass().getResourceAsStream( aResourceName ) );
    }
}
