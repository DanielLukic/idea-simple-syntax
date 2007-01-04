package net.intensicode.idea.system;

import com.intellij.openapi.fileTypes.FileNameMatcher;

import java.util.List;



/**
 * TODO: Describe this!
 */
public interface ErrorHandler
{
    void onScriptingError( Throwable aThrowable );

    void onConfigurationError( Throwable aThrowable );

    void onSimpleSyntaxInstallFailed( Throwable aThrowable );

    Confirmation onFileReplaceConfirmation( String aFileName );

    Confirmation onFileTypeReplaceConfirmation( String aFileType );

    Confirmation onFileTypeInUseConfirmation( List<FileNameMatcher> aExtensions );

    /**
     * Use this to make the error handler forget 'All' and 'None' answers to confirmation questions.
     */
    void forgetConfirmationAnswers();
}
