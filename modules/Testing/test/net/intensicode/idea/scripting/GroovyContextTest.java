package net.intensicode.idea.scripting;

import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public final class GroovyContextTest extends TestCase
{
    public final void testSomething() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this );
        final GroovyContext groovyContext = new GroovyContext( systemContext, new ArrayList<String>() );
        final Object result = groovyContext.source( "ClassPathTest.groovy" );
        assertNotNull( result );
        assertEquals( "1A", result.toString() );
    }

    public final void testAddedPath1() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovyContext groovyContext = new GroovyContext( systemContext, new ArrayList<String>() );
        final Object result = groovyContext.source( "FilePathTest1.groovy" );
        assertNotNull( result );
        assertEquals( "1A", result.toString() );
    }

    public final void testAddedPath2() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovyContext groovyContext = new GroovyContext( systemContext, new ArrayList<String>() );
        final Object result = groovyContext.source( "FilePathTest2.groovy" );
        assertNotNull( result );
        assertEquals( "MyFilePathTest2", result.toString() );
    }

    public final void testAddedPath3() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovyContext groovyContext = new GroovyContext( systemContext, new ArrayList<String>() );
        final Object result = groovyContext.source( "FilePathTest3.groovy" );
        assertNotNull( result );
        assertEquals( "MyFilePathTest3", result.toString() );
    }

    public final void testCreateObject() throws IOException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovyContext groovyContext = new GroovyContext( systemContext, new ArrayList<String>() );
        final Object result = groovyContext.createObject( "FilePathTest4.groovy", Serializable.class );
        assertNotNull( result );
        assertEquals( "MyFilePathTest4", result.toString() );
    }
}
