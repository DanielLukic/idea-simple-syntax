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

    public final void copy( final InputStream aStream, final String aResourceName ) throws IOException
    {
        if ( myCopiedFiles.contains( aResourceName ) ) return;

        LOG.debug( "Installing " + aResourceName );
        final boolean writeConfirmed = confirmWrite( aResourceName );
        if ( writeConfirmed )
        {
            myOptionsFolder.writeFileFromStream( aResourceName, aStream );
            myCopiedFiles.add( aResourceName );
        }
    }

    // Implementation

    private final boolean confirmWrite( final String aResourceName ) throws IOException
    {
        if ( myAllConfirmedFlag ) return true;
        if ( myAllDeclinedFlag ) return false;
        if ( myOptionsFolder.fileExists( aResourceName ) == false ) return true;

        final Confirmation confirmation = myErrorHandler.onFileReplaceConfirmation( aResourceName );
        if ( confirmation == Confirmation.ALL ) return myAllConfirmedFlag = true;
        if ( confirmation == Confirmation.YES ) return true;
        if ( confirmation == Confirmation.NO ) return false;
        if ( confirmation == Confirmation.CANCEL ) throw new IOException( "Installation cancelled" );

        if ( confirmation == Confirmation.NONE )
        {
            myAllDeclinedFlag = true;
            return false;
        }
        
        throw new RuntimeException( "NYI" );
    }



    private boolean myAllDeclinedFlag = false;

    private boolean myAllConfirmedFlag = false;

    private final OptionsFolder myOptionsFolder;

    private final SystemErrorHandler myErrorHandler;

    private final ArrayList<String> myCopiedFiles = new ArrayList<String>();

    private static final Logger LOG = LoggerFactory.getLogger();
}
