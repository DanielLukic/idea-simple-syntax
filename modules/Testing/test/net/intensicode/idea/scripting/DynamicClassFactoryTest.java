package net.intensicode.idea.scripting;

import junit.framework.TestCase;



public final class DynamicClassFactoryTest extends TestCase
{
    public final void testNewInstance()
    {
        final Object result = DynamicClassFactory.newInstance( "ToastedCheese", FakeBaseClass.class, "abc", "g2g" );
        assertEquals( "ToastedCheese", result.getClass().getName() );
        assertTrue( result instanceof FakeBaseClass );

        final FakeBaseClass baseClass = ( FakeBaseClass ) result;
        assertEquals( "abcg2g", baseClass.result.toString() );
    }
}
