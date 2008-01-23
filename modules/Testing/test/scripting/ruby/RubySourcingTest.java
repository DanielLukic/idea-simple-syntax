package scripting.ruby;

import jfun.parsec.Parser;
import jfun.parsec.Tok;
import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.scripting.RubySupport;
import net.intensicode.idea.syntax.JParsecLexerAdapter;
import net.intensicode.idea.syntax.SimpleToken;
import org.jruby.Ruby;
import org.jruby.ast.Node;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public final class RubySourcingTest extends TestCase
{
    public final void testSource()
    {
        final Ruby runtime = createRuby();

        final String scriptName = "Script2.rb";
        final Node node = runtime.parse( readScript( scriptName ), scriptName, null, 0 );

        final Object result = runtime.eval( node );
        assertNotNull( result );
        assertEquals( "BUSTED STUFF", result.toString() );
    }

    public final void testLexerStuff()
    {
        final Ruby runtime = createRuby();

        final String scriptName = "Script3.rb";
        final Node node = runtime.parse( readScript( scriptName ), scriptName, null, 0 );

        final IRubyObject result = runtime.eval( node );
        final Parser<Tok[]> lexerImpl = ( Parser<Tok[]> ) JavaUtil.convertRubyToJava( result, Parser.class );

        final JParsecLexerAdapter lexer = new JParsecLexerAdapter( lexerImpl );
        lexer.start( "test".toCharArray(), 0, 4 );

        final SimpleToken token = lexer.findToken( 0 );
        assertNotNull( token );
    }

    private final Ruby createRuby()
    {
        final Ruby runtime = Ruby.getDefaultInstance();
        final FakeSystemContext systemContext = new FakeSystemContext( this );
        runtime.getKernel().defineMethod( "source", new RubySupport( systemContext, new ArrayList<String>() ) );
        return runtime;
    }

    private final Reader readScript( final String aResourceName )
    {
        return new InputStreamReader( getClass().getResourceAsStream( aResourceName ) );
    }
}
