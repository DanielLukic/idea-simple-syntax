package net.intensicode.idea.core;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import net.intensicode.idea.system.Confirmation;
import net.intensicode.idea.system.SystemErrorHandler;

import java.util.List;



/**
 * TODO: Describe this!
 */
public final class FakeSystemErrorHandler implements SystemErrorHandler
{
    public void onSimpleSyntaxInstallFailed( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public Confirmation onFileReplaceConfirmation( String aFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public Confirmation onFileTypeReplaceConfirmation( String aFileType )
    {
        throw new RuntimeException( "NYI" );
    }

    public Confirmation onFileTypeInUseConfirmation( List<FileNameMatcher> aExtensions )
    {
        throw new RuntimeException( "NYI" );
    }

    public void forgetConfirmationAnswers()
    {
        throw new RuntimeException( "NYI" );
    }
}
