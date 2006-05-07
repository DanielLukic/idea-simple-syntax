package net.intensicode.idea.core;

import net.intensicode.idea.syntax.RecognizedToken;



/**
 * TODO: Describe this!
 */
public interface SystemErrorHandler
{
    void onTokenRecognizerFailed( RecognizedToken aToken, Throwable aThrowable );

    void onSimpleSyntaxInstallFailed( Throwable aThrowable );

    Confirmation onFileReplaceConfirmation( String aFileName );
}
