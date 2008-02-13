/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import junit.framework.TestCase;
import net.intensicode.idea.VirtualSystemContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

/**
 * TODO: Describe this!
 */
public final class ZipStreamInstallerTest extends TestCase
{
    public final void testInstall() throws IOException
    {
        final VirtualSystemContext context = new VirtualSystemContext();
        final CopyHandler copyHandler = new CopyHandler( context );
        final ZipStreamInstaller installer = new ZipStreamInstaller( copyHandler );
        installer.install( new ZipInputStream( getClass().getResourceAsStream( "ConfigTest.zip" ) ) );
        validateInstallation( context );
    }

    private static final void validateInstallation( final VirtualSystemContext aContext )
    {
        final ArrayList<String> expected = new ArrayList<String>()
        {
            {
                add( "lib-groovy/flux/FluxBuilder.groovy" );
                add( "lib-groovy/flux/TagReplacer.groovy" );
                add( "lib-groovy/jparsec/LexerAdapter.groovy" );
                add( "lib-groovy/jparsec/LexerBase.groovy" );
                add( "lib-ruby/jparsec/LexerAdapter.rb" );
                add( "lib-ruby/jparsec/LexerBase.rb" );

                add( "ParsecDemo/Keywords.txt" );
                add( "ParsecDemo/Syntax.groovy" );
                add( "ParsecDemo/Syntax.rb" );

                add( "Ruby/ExampleCode.rb" );
                add( "Ruby/Icon.png" );
                add( "Ruby/Syntax.flux" );
                add( "Ruby/Syntax.groovy" );

                add( "SimpleSyntax/Example.config" );
                add( "SimpleSyntax/Icon.png" );
                add( "SimpleSyntax/Syntax.flux" );
                add( "SimpleSyntax/Syntax.groovy" );

                add( "FluxSyntax.header" );
                add( "FluxSyntax.skeleton" );
                add( "Ruby.offline" );
                add( "SimpleSyntax.config" );
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
