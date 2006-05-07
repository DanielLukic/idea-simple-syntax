package net.intensicode.idea.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.util.LoggerFactory;



/**
 * TODO: Describe this!
 */
final class ProductionSystemErrorHandler implements SystemErrorHandler
{
    ProductionSystemErrorHandler()
    {
    }

    // From SystemErrorHandler

    public final void onTokenRecognizerFailed( final RecognizedToken aToken, final Throwable aThrowable )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Token recognizer failed.\n" );
        messageBuilder.append( "Error message is: " );
        messageBuilder.append( aThrowable.getMessage() );
        messageBuilder.append( '\n' );
        messageBuilder.append( "Token recognizer ID: " );
        messageBuilder.append( aToken.getTokenID() );
        messageBuilder.append( '\n' );
        messageBuilder.append( "Removing recognizer and continuing.." );
        displayError( messageBuilder.toString() );
    }

    public final void onSimpleSyntaxInstallFailed( final Throwable aThrowable )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Installation failed.\n" );
        messageBuilder.append( "Error message is: " );
        messageBuilder.append( aThrowable.getMessage() );
        displayError( messageBuilder.toString() );
    }

    public final Confirmation onFileReplaceConfirmation( final String aFileName )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Target file " + aFileName + " already exists!\n" );
        messageBuilder.append( "Replace the file? Yes or no?\n" );
        messageBuilder.append( "Replace all following files too?\n" );
        messageBuilder.append( "Or cancel the installation?" );

        final String message = messageBuilder.toString();
        final int result = Messages.showDialog( message, CONFIRMATION_TITLE, CONFIRMATION_OPTIONS, 0, null );
        if ( result == 0 ) return Confirmation.YES;
        if ( result == 1 ) return Confirmation.NO;
        if ( result == 2 ) return Confirmation.ALL;
        if ( result == 3 ) return Confirmation.CANCEL;
        throw new RuntimeException( "NYI" );
    }

    // Implementation

    private final void displayError( final String aMessage )
    {
        ApplicationManager.getApplication().runReadAction( new Runnable()
        {
            public final void run()
            {
                Messages.showErrorDialog( aMessage, ERROR_TITLE );
                ProductionSystemErrorHandler.LOG.error( aMessage );
            }
        } );
    }



    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ERROR_TITLE = "Simple Syntax Plugin Error";

    private static final String CONFIRMATION_TITLE = "Simple Syntax Plugin Confirmation";

    private static final String[] CONFIRMATION_OPTIONS = new String[]{"YES", "NO", "ALL", "CANCEL"};
}
