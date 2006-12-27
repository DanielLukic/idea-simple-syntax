package scripting.ruby;

import jfun.parsec.Parser;
import jfun.parsec.Parsers;
import jfun.parsec.Tok;
import jfun.parsec.tokens.TypedToken;
import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.util.RubyContext;
import net.intensicode.idea.util.StreamUtils;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.IOException;

public final class TestRubySyntax extends TestCase
{
    public final void testLexer() throws IOException
    {
        final Tok[] tokens = getLexerOutput();

        assertNotNull( tokens );

        assertEquals( 3, tokens.length );

        assertEquals( "# comment", tokens[ 0 ].toString() );
        assertEquals( "LINE_COMMENT", getType( tokens[ 0 ] ) );

        assertEquals( "return", tokens[ 1 ].toString() );
        assertEquals( "KEYWORD", getType( tokens[ 1 ] ) );

        assertEquals( "\"this\"", tokens[ 2 ].toString() );
        assertEquals( "DOUBLE_QUOTED_STRING", getType( tokens[ 2 ] ) );
    }

    // Implemention

    private final String getType( final Tok aToken )
    {
        final TypedToken token = ( TypedToken ) aToken.getToken();
        return token.getType().toString();
    }

    private final Tok[] getLexerOutput() throws IOException
    {
        final IRuby runtime = Ruby.getDefaultInstance();

        final FakeSystemContext systemContext = new FakeSystemContext( this, "config" );
        runtime.getTopSelf().defineSingletonMethod( "source", new RubyContext( systemContext ) );

        final IRubyObject rubyObject = runtime.evalScript( "source 'RubySyntax.rb'" );

        final Object javaObject = JavaUtil.convertRubyToJava( rubyObject, Parser.class );
        final Parser<Tok[]> lexerObject = ( Parser<Tok[]> ) javaObject;

        final String testData = StreamUtils.loadIntoString( getClass().getResourceAsStream( "Script4.rb" ) );
        return Parsers.runParser( testData, lexerObject, null );
    }
}
