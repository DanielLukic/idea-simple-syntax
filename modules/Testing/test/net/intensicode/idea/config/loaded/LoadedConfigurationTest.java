package net.intensicode.idea.config.loaded;

import junit.framework.TestCase;
import net.intensicode.idea.config.CommentConfiguration;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.core.FakeSystemContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public final class LoadedConfigurationTest extends TestCase
{
    public final void testCreate() throws IOException
    {
        final FakeSystemContext systemContext = new FakeSystemContext( this );

        final InputStream input = getClass().getResourceAsStream( "TestConfig_Ruby.config" );
        final LoadedConfiguration configuration = new LoadedConfiguration( systemContext, new InputStreamReader( input ) );
        assertEquals( "Ruby", configuration.getName() );
        assertEquals( "Ruby Script File", configuration.getDescription() );
        assertEquals( 275, configuration.getExampleCode().length() );
        assertNotNull( configuration.getIcon() );
        assertEquals( 18, configuration.getIcon().getIconWidth() );
        assertEquals( 18, configuration.getIcon().getIconHeight() );

        final CommentConfiguration commentConfiguration = configuration.getCommentConfiguration();
        assertEquals( "#", commentConfiguration.getLineCommentPrefix() );
        assertEquals( "", commentConfiguration.getBlockCommentPrefix() );
        assertEquals( "", commentConfiguration.getBlockCommentSuffix() );

        final FileTypeConfiguration fileTypeConfiguration = configuration.getFileTypeConfiguration();
        assertEquals( 2, fileTypeConfiguration.getExtensions().size() );
        assertEquals( "*.rb", fileTypeConfiguration.getExtensions().get( 0 ).getPresentableString() );
        assertEquals( "*.ruby", fileTypeConfiguration.getExtensions().get( 1 ).getPresentableString() );
        assertEquals( "rb", fileTypeConfiguration.getDefaultExtension() );
        assertNotNull( fileTypeConfiguration.getIcon() );
        assertEquals( 9, fileTypeConfiguration.getIcon().getIconWidth() );
        assertEquals( 9, fileTypeConfiguration.getIcon().getIconHeight() );

        assertEquals( "Block comment", configuration.getTokenDescription( "BLOCK_COMMENT" ) );
    }
}
