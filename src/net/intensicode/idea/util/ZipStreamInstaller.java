/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * TODO: Describe this!
 */
public final class ZipStreamInstaller
{
    public ZipStreamInstaller( final CopyHandler aCopyHandler )
    {
        myCopyHandler = aCopyHandler;
    }

    public final void install( final ZipInputStream aStream ) throws IOException
    {
        final ZipEntry entry = aStream.getNextEntry();
        if ( entry == null ) return;

        if ( entry.isDirectory() == false && entry.getName().startsWith( "META-INF" ) == false )
        {
            LOG.debug( "installing " + entry + " -> " + entry.getName() );
            myCopyHandler.copy( aStream, entry.getName() );
        }
        aStream.closeEntry();

        install( aStream );
    }



    private final CopyHandler myCopyHandler;

    private static final Logger LOG = LoggerFactory.getLogger();
}
