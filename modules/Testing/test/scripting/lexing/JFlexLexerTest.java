package scripting.lexing;

import com.intellij.lexer.Lexer;
import junit.framework.TestCase;
import net.intensicode.idea.FakeLanguageConfiguration;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.scripting.GroovySupport;
import net.intensicode.idea.syntax.SimpleToken;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.util.StreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class JFlexLexerTest extends TestCase
{
    public final void testGroovyLexer() throws IOException
    {
        final GroovySupport support = new GroovySupport( createContext(), createClassPath( "lib-groovy" ) );
        final Lexer lexer = createLexer( support, "Ruby/Syntax.groovy" );
        final ArrayList<SimpleToken> tokens = getLexerOutput( lexer, "SimpleString.rb" );
        assertNotNull( tokens );

        check( "LINE_BREAK", 2, tokens );
        check( "LINE_COMMENT", 9, tokens );
        check( "LINE_BREAK", 4, tokens );
        check( "KEYWORD_RETURN", 6, tokens );
        check( "SPACE", 1, tokens );
        check( "STRING_DQ_BEGIN", 1, tokens );
        check( "STRING_DQ_DATA", 4, tokens );
        check( "STRING_DQ_END", 1, tokens );
        check( "LINE_BREAK", 2, tokens );

        assertEquals( 0, tokens.size() );
    }

    public final void testSpecialString() throws IOException
    {
        final GroovySupport support = new GroovySupport( createContext(), createClassPath( "lib-groovy" ) );
        final Lexer lexer = createLexer( support, "Ruby/Syntax.groovy" );
        final ArrayList<SimpleToken> tokens = getLexerOutput( lexer, "SpecialString.rb" );
        assertNotNull( tokens );

        check( "IDENTIFIER", 4, tokens );
        check( "SPACE", 1, tokens );
        check( "OP_ASSIGNMENT", 1, tokens );
        check( "SPACE", 1, tokens );
        check( "STRING_Q_BEGIN", 3, tokens );
        check( "STRING_Q_DATA", 6, tokens );
        check( "STRING_Q_END", 1, tokens );
        check( "LINE_BREAK", 2, tokens );
        check( "IDENTIFIER", 5, tokens );
        check( "SPACE", 1, tokens );
        check( "OP_ASSIGNMENT", 1, tokens );
        check( "SPACE", 1, tokens );
        check( "STRING_Q_BEGIN", 3, tokens );
        check( "STRING_Q_DATA", 6, tokens );
        check( "STRING_Q_END", 2, tokens );
        check( "LINE_COMMENT", 9, tokens );

        assertEquals( 0, tokens.size() );
    }

    // Implemention

    private final void check( final String aID, final int aLength, final ArrayList<SimpleToken> aTokens )
    {
        final SimpleToken token = aTokens.remove( 0 );
        assertEquals( aID + "," + aLength, token.id.toString() + "," + ( token.end - token.start ) );
    }

    private static final ArrayList<String> createClassPath( final String aEntry )
    {
        final ArrayList<String> classPath = new ArrayList<String>();
        classPath.add( aEntry );
        return classPath;
    }

    private final FakeSystemContext createContext()
    {
        return new FakeSystemContext( this, "config" );
    }

    private final Lexer createLexer( final ScriptSupport aContext, final String aScriptName ) throws IOException
    {
        final HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put( "configuration", new FakeLanguageConfiguration() );
        return ( Lexer ) aContext.createObject( aScriptName, variables, Lexer.class );
    }

    private final ArrayList<SimpleToken> getLexerOutput( final Lexer aLexer, final String aTestScript ) throws IOException
    {
        final String testData = StreamUtils.loadIntoString( getClass().getResourceAsStream( aTestScript ) );
        aLexer.start( testData.toCharArray(), 0, testData.length() );

        final ArrayList<SimpleToken> tokens = new ArrayList<SimpleToken>();
        storeTokens( tokens, aLexer );
        return tokens;
    }

    private static final void storeTokens( final ArrayList<SimpleToken> aTokens, final Lexer aLexer )
    {
        final SimpleToken token = new SimpleToken();
        token.start = aLexer.getTokenStart();
        token.end = aLexer.getTokenEnd();
        token.id = aLexer.getTokenType();
        aTokens.add( token );

        if ( aLexer.getTokenEnd() < aLexer.getBufferEnd() )
        {
            aLexer.advance();
            storeTokens( aTokens, aLexer );
        }
    }
}
