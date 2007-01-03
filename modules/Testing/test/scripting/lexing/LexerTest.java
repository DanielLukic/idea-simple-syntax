package scripting.lexing;

import com.intellij.lexer.Lexer;
import junit.framework.TestCase;
import net.intensicode.idea.FakeLanguageConfiguration;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.scripting.GroovySupport;
import net.intensicode.idea.scripting.RubySupport;
import net.intensicode.idea.syntax.SimpleToken;
import net.intensicode.idea.system.ScriptSupport;
import net.intensicode.idea.util.StreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class LexerTest extends TestCase
{
    public final void testGroovyLexer() throws IOException
    {
        final GroovySupport support = new GroovySupport( createContext(), createClassPath( "lib-groovy" ) );
        validateRuby( support, "Ruby/Syntax.groovy", "TestScript.rb" );
    }

    public final void testRubyLexer() throws IOException
    {
        final RubySupport support = new RubySupport( createContext(), createClassPath( "lib-ruby" ) );
        validateRuby( support, "Ruby/Syntax.rb", "TestScript.rb" );
    }

    public final void testGroovyPythonLexer() throws IOException
    {
        final GroovySupport support = new GroovySupport( createContext(), createClassPath( "lib-groovy" ) );
        validatePython( support, "Python/Syntax.groovy", "TestScript.py" );
    }

    // Implemention

    private final void validateRuby( final ScriptSupport aScriptSupport, final String aScriptName, final String aTestScript ) throws IOException
    {
        final Lexer lexer = createLexer( aScriptSupport, aScriptName );
        final ArrayList<SimpleToken> tokens = getLexerOutput( lexer, aTestScript );

        assertNotNull( tokens );
        assertEquals( 7, tokens.size() );

        check( "LINE_COMMENT", 2, 11, tokens.get( 0 ) );
        check( "WHITE_SPACE", 11, 15, tokens.get( 1 ) );
        check( "KEYWORD", 15, 21, tokens.get( 2 ) );
        check( "WHITE_SPACE", 21, 22, tokens.get( 3 ) );
        check( "DOUBLE_QUOTED_STRING", 22, 28, tokens.get( 4 ) );
        check( "BAD_CHARACTER", 28, 29, tokens.get( 5 ) );
        check( "BAD_CHARACTER", 29, 30, tokens.get( 6 ) );
    }

    private final void validatePython( final ScriptSupport aScriptSupport, final String aScriptName, final String aTestScript ) throws IOException
    {
        final Lexer lexer = createLexer( aScriptSupport, aScriptName );
        final ArrayList<SimpleToken> tokens = getLexerOutput( lexer, aTestScript );

        assertNotNull( tokens );
        assertEquals( 6, tokens.size() );

        check( "END_OF_LINE_COMMENT", 2, 11, tokens.get( 0 ) );
        check( "LINE_BREAK", 11, 15, tokens.get( 1 ) );
        check( "RETURN_KEYWORD", 15, 21, tokens.get( 2 ) );
        check( "SPACE", 21, 22, tokens.get( 3 ) );
        check( "STRING_LITERAL", 22, 28, tokens.get( 4 ) );
        check( "LINE_BREAK", 28, 30, tokens.get( 5 ) );
    }

    private final void check( final String aID, final int aStart, final int aEnd, final SimpleToken aToken )
    {
        assertEquals( aID, aToken.id.toString() );
        assertEquals( aStart, aToken.start );
        assertEquals( aEnd, aToken.end );
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
        aLexer.advance();

        final SimpleToken token = new SimpleToken();
        token.start = aLexer.getTokenStart();
        token.end = aLexer.getTokenEnd();
        token.id = aLexer.getTokenType();
        aTokens.add( token );

        if ( aLexer.getTokenEnd() < aLexer.getBufferEnd() ) storeTokens( aTokens, aLexer );
    }
}
