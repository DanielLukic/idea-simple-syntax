package net.intensicode.idea.core;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import net.intensicode.idea.system.Confirmation;
import net.intensicode.idea.system.ErrorHandler;

import java.util.List;



/**
 * TODO: Describe this!
 */
public final class FakeErrorHandler implements ErrorHandler
{
    public final void onConfigurationError( final Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public final void onScriptingError( final Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public final void onSimpleSyntaxInstallFailed( final Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public final Confirmation onFileReplaceConfirmation( final String aFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public final Confirmation onFileTypeReplaceConfirmation( final String aFileType )
    {
        throw new RuntimeException( "NYI" );
    }

    public final Confirmation onFileTypeInUseConfirmation( final List<FileNameMatcher> aExtensions )
    {
        throw new RuntimeException( "NYI" );
    }

    public final void forgetConfirmationAnswers()
    {
        throw new RuntimeException( "NYI" );
    }
}
