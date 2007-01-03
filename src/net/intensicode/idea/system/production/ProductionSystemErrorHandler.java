package net.intensicode.idea.system.production;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.ui.Messages;
import net.intensicode.idea.system.Confirmation;
import net.intensicode.idea.system.SystemErrorHandler;
import net.intensicode.idea.util.LoggerFactory;

import java.util.List;



/**
 * TODO: Describe this!
 */
final class ProductionSystemErrorHandler implements SystemErrorHandler
{
    ProductionSystemErrorHandler()
    {
    }

    // From SystemErrorHandler

    public final void onScriptingError( final Throwable aThrowable )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Script execution failed.\n" );
        messageBuilder.append( "Error message is: " );
        messageBuilder.append( aThrowable.getMessage() );
        displayError( messageBuilder.toString(), aThrowable );
    }

    public final void onConfigurationError( final Throwable aThrowable )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Configuration failed.\n" );
        messageBuilder.append( "Error message is: " );
        messageBuilder.append( aThrowable.getMessage() );
        displayError( messageBuilder.toString(), aThrowable );
    }

    public final void onSimpleSyntaxInstallFailed( final Throwable aThrowable )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Installation failed.\n" );
        messageBuilder.append( "Error message is: " );
        messageBuilder.append( aThrowable.getMessage() );
        displayError( messageBuilder.toString(), aThrowable );
    }

    public final Confirmation onFileReplaceConfirmation( final String aFileName )
    {
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append( "Target file " + aFileName + " already exists!\n" );
        messageBuilder.append( "YES: Replace this file?\n" );
        messageBuilder.append( "ALL: Replace all files?\n" );
        messageBuilder.append( "NO: Don't replace this file?\n" );
        messageBuilder.append( "NONE: Don't replace any files?\n" );
        messageBuilder.append( "CANCEL: Or cancel the installation?" );

        final String message = messageBuilder.toString();
        final int result = Messages.showDialog( message, CONFIRMATION_TITLE, CONFIRMATION_OPTIONS, 0, null );
        if ( result == 0 ) return Confirmation.YES;
        if ( result == 1 ) return Confirmation.ALL;
        if ( result == 2 ) return Confirmation.NO;
        if ( result == 3 ) return Confirmation.NONE;
        if ( result == 4 ) return Confirmation.CANCEL;
        throw new RuntimeException( "NYI" );
    }

    public final Confirmation onFileTypeInUseConfirmation( final List<FileNameMatcher> aExtensions )
    {
        LOG.warn( "NYI onFileTypeInUseConfirmation" );
        return Confirmation.YES;
    }

    public final Confirmation onFileTypeReplaceConfirmation( final String aFileType )
    {
        LOG.warn( "NYI onFileTypeReplaceConfirmation" );
        return Confirmation.YES;
    }

    public final void forgetConfirmationAnswers()
    {
        LOG.warn( "NYI forgetConfirmationAnswers" );
    }

    // Implementation

    private final void displayError( final String aMessage, final Throwable aThrowable )
    {
        ApplicationManager.getApplication().runReadAction( new Runnable()
        {
            public final void run()
            {
                Messages.showErrorDialog( aMessage, ERROR_TITLE );
                LOG.error( aMessage );
                if ( aThrowable != null ) LOG.error( aThrowable );
            }
        } );
    }



    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String ERROR_TITLE = "Simple Syntax Plugin Error";

    private static final String CONFIRMATION_TITLE = "Simple Syntax Plugin Confirmation";

    private static final String[] CONFIRMATION_OPTIONS = new String[]{ "YES", "ALL", "NO", "NONE", "CANCEL" };
}
