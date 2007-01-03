package scripting.jflex;

import com.intellij.lexer.FlexLexer;
import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;
import net.intensicode.idea.scripting.GroovySupport;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.StreamUtils;
import net.intensicode.idea.FakeLanguageConfiguration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public final class GroovyJFlexTest extends TestCase
{
    public final void testSomething() throws Exception
    {
        final HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put( "reference", this );
        variables.put( "configuration", new FakeLanguageConfiguration() );

        final SystemContext context = new FakeSystemContext( this );
        final GroovySupport support = new GroovySupport( context, new ArrayList<String>() );

        final Object result = support.createObject( "CreateLexer.groovy", variables, FlexLexer.class );
        assertTrue( FlexLexer.class.isInstance( result ) );

        final FlexLexer lexer = ( FlexLexer ) result;

        final String testData = StreamUtils.loadIntoString( getClass().getResourceAsStream( "SomePython.py" ) );
        lexer.reset( testData, 0 );

        assertEquals( "IMPORT_KEYWORD", lexer.advance().toString() );
        assertEquals( "SPACE", lexer.advance().toString() );
    }

    public final void testGeneratedLexer() throws Exception
    {
        final SystemContext context = new FakeSystemContext( this );
        final GroovySupport support = new GroovySupport( context, new ArrayList<String>() );
        final Object result = support.source( "GeneratedLexer.txt" );
        assertTrue( Class.class.isInstance( result ) );

        final Class lexerClazz = ( Class ) result;
        final Object instance = lexerClazz.newInstance();
        assertTrue( instance instanceof FlexLexer );

        final FlexLexer lexer = ( FlexLexer ) instance;
        assertNotNull( lexer );

        final Method setMethod = lexerClazz.getMethod( "setLanguage", Object.class );
        setMethod.invoke( lexer, new FakePythonLanguage() );

        final String testData = StreamUtils.loadIntoString( getClass().getResourceAsStream( "SomePython.py" ) );
        lexer.reset( testData, 0 );
        assertEquals( "IMPORT_KEYWORD", lexer.advance().toString() );
        assertEquals( "SPACE", lexer.advance().toString() );
    }
}
