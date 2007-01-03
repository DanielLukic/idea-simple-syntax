package net.intensicode.idea.scripting;

import junit.framework.TestCase;
import net.intensicode.idea.core.FakeSystemContext;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public final class GroovySupportTest extends TestCase
{
    public final void testSomething() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this );
        final GroovySupport groovySupport = new GroovySupport( systemContext, new ArrayList<String>() );
        final Object result = groovySupport.source( "ClassPathTest.groovy" );
        assertNotNull( result );
        assertEquals( "1A", result.toString() );
    }

    public final void testAddedPath1() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovySupport groovySupport = new GroovySupport( systemContext, new ArrayList<String>() );
        final Object result = groovySupport.source( "FilePathTest1.groovy" );
        assertNotNull( result );
        assertEquals( "1A", result.toString() );
    }

    public final void testAddedPath2() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovySupport groovySupport = new GroovySupport( systemContext, new ArrayList<String>() );
        final Object result = groovySupport.source( "FilePathTest2.groovy" );
        assertNotNull( result );
        assertEquals( "MyFilePathTest2", result.toString() );
    }

    public final void testAddedPath3() throws FileNotFoundException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovySupport groovySupport = new GroovySupport( systemContext, new ArrayList<String>() );
        final Object result = groovySupport.source( "FilePathTest3.groovy" );
        assertNotNull( result );
        assertEquals( "MyFilePathTest3", result.toString() );
    }

    public final void testCreateObject() throws IOException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this, "modules/Testing/test-groovy" );
        final GroovySupport groovySupport = new GroovySupport( systemContext, new ArrayList<String>() );
        final HashMap<String, Object> variables = new HashMap<String, Object>();
        final Object result = groovySupport.createObject( "FilePathTest4.groovy", variables, Serializable.class );
        assertNotNull( result );
        assertEquals( "MyFilePathTest4", result.toString() );
    }
}
