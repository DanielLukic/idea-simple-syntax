/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.system.Confirmation;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.SystemErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * TODO: Describe this!
 */
public final class CopyHandler
{
    public CopyHandler( final SystemContext aSystemContext )
    {
        final SystemContext context = aSystemContext;
        myErrorHandler = context.getErrorHandler();
        myOptionsFolder = context.getOptionsFolder();
    }

    public final void copy( final InputStream aStream, final String aResourceName )
    {
        if ( myCopiedFiles.contains( aResourceName ) ) return;

        LOG.debug( "Installing " + aResourceName );
        try
        {
            final boolean writeConfirmed = confirmWrite( aResourceName );
            if ( writeConfirmed )
            {
                myOptionsFolder.writeFileFromStream( aResourceName, aStream );
                myCopiedFiles.add( aResourceName );
            }
        }
        catch ( final Throwable t )
        {
            myErrorHandler.onSimpleSyntaxInstallFailed( t );
        }
    }

    // Implementation

    private final boolean confirmWrite( final String aResourceName ) throws IOException
    {
        if ( myAllConfirmedFlag ) return true;
        if ( myOptionsFolder.fileExists( aResourceName ) == false ) return true;

        final Confirmation confirmation = myErrorHandler.onFileReplaceConfirmation( aResourceName );
        if ( confirmation == Confirmation.ALL ) return myAllConfirmedFlag = true;
        if ( confirmation == Confirmation.YES ) return true;
        if ( confirmation == Confirmation.NO ) return false;
        if ( confirmation == Confirmation.CANCEL ) throw new IOException( "Installation cancelled" );

        throw new RuntimeException( "NYI" );
    }



    private boolean myAllConfirmedFlag = false;

    private final OptionsFolder myOptionsFolder;

    private final SystemErrorHandler myErrorHandler;

    private final ArrayList<String> myCopiedFiles = new ArrayList<String>();

    private static final Logger LOG = LoggerFactory.getLogger();
}
