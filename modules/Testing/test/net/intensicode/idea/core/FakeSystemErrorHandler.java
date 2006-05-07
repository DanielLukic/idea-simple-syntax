package net.intensicode.idea.core;

import net.intensicode.idea.syntax.RecognizedToken;



/**
 * TODO: Describe this!
 */
public final class FakeSystemErrorHandler implements SystemErrorHandler
{
    public void onTokenRecognizerFailed( RecognizedToken aToken, Throwable aThrowable )
    {
        throw new RuntimeException( aToken.getTokenID(), aThrowable );
    }

    public void onSimpleSyntaxInstallFailed( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public Confirmation onFileReplaceConfirmation( String aFileName )
    {
        throw new RuntimeException( "NYI" );
    }
}
