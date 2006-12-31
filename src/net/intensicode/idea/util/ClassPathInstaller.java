/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO: Describe this!
 */
public final class ClassPathInstaller
{
    public ClassPathInstaller( final CopyHandler aCopyHandler )
    {
        myCopyHandler = aCopyHandler;
    }

    public final void install( final File aFolder ) throws IOException
    {
        copyFolder( aFolder, null );
    }

    // Implementation

    private void copyFolder( final File aFolder, final String aTargetPrefix ) throws IOException
    {
        for ( final String fileName : aFolder.list() )
        {
            final File file = new File( aFolder, fileName );
            final File target = getTargetFile( aTargetPrefix, file.getName() );
            if ( file.isFile() )
            {
                LOG.debug( "installing " + file + " -> " + target );
                myCopyHandler.copy( new FileInputStream( file ), target.getPath() );
            }
            else if ( file.isDirectory() && fileName.equals( ".svn" ) == false )
            {
                copyFolder( file, target.getPath() );
            }
        }
    }

    private final File getTargetFile( final String aTargetPrefix, final String aFileName )
    {
        if ( aTargetPrefix == null ) return new File( aFileName );
        return new File( aTargetPrefix, aFileName );
    }



    private final CopyHandler myCopyHandler;

    private static final Logger LOG = LoggerFactory.getLogger();
}
