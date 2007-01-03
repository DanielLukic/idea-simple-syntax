package net.intensicode.idea.scripting;

import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import org.jruby.exceptions.RaiseException;

import java.io.IOException;
import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public final class RubySupportTest extends TestCase
{
    public final void testSomething() throws IOException
    {
        try
        {
            final FakeSystemContext context = new FakeSystemContext( this );
            final RubySupport support = new RubySupport( context );

            final HashMap<String, Object> variables = new HashMap<String, Object>();
            variables.put( "toasted", new ToastedBread() );

            final Object result = support.createObject( "ToastIt.rb", variables, ToastedBread.class );
            assertNotNull( result );

            final ToastedBread bread = ( ToastedBread ) result;
            assertEquals( 8, bread.callMe( 4 ) );
        }
        catch ( final RaiseException e )
        {
            e.printStackTrace();
            System.err.println( e.getException() );
            e.getException().printBacktrace( System.err );

            throw new RuntimeException( e.getException().toString() );
        }
    }
}
