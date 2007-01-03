package net.intensicode.idea;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public final class ActionInstallTest extends TestCase
{
    public final void testInstall()
    {
        final VirtualSystemContext context = new VirtualSystemContext();
        final ActionInstall install = new ActionInstall( context );
        install.actionPerformed( null );
        validateInstallation( context );
    }

    public static final void validateInstallation( final VirtualSystemContext aContext )
    {
        final ArrayList<String> expected = new ArrayList<String>()
        {
            {
                add( "Ruby.config" );
                add( "Ruby/Icon.png" );
                add( "Ruby/ExampleCode.rb" );
                add( "Ruby/Keywords.txt" );
                add( "Ruby/NamesValidator.rb" );
                add( "Ruby/NamesValidator.groovy" );
                add( "Ruby/Syntax.rb" );
                add( "Ruby/Syntax.groovy" );

                add( "Python.config" );
                add( "Python/ExampleCode.py" );
                add( "Python/Icon.png" );
                add( "Python/Syntax.flex" );
                add( "Python/Syntax.groovy" );
                add( "Python/Syntax.skeleton" );

                add( "lib-groovy/jparsec/LexerAdapter.groovy" );
                add( "lib-groovy/jparsec/LexerBase.groovy" );
                add( "lib-ruby/jparsec/LexerAdapter.rb" );
                add( "lib-ruby/jparsec/LexerBase.rb" );
            }
        };

        assertEquals( "", aContext.confirmations.toString() );
        assertEquals( aContext.writtenFiles.toString(), expected.size(), aContext.writtenFiles.size() );

        for ( final String check : expected )
        {
            assertTrue( check, aContext.writtenFiles.contains( check ) );
        }
        for ( final String check : aContext.writtenFiles )
        {
            assertTrue( check, expected.contains( check ) );
        }
    }
}
