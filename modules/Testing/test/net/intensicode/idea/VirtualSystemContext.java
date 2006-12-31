/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Describe this!
 */
public final class VirtualSystemContext implements SystemContext, SystemErrorHandler, ResourceLoader, OptionsFolder
{
    public final StringBuilder confirmations = new StringBuilder();

    public final ArrayList<String> writtenFiles = new ArrayList<String>();



    public File getPluginFolder()
    {
        return new File( "config" );
    }

    public TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes )
    {
        throw new RuntimeException( "NYI" );
    }

    public SystemErrorHandler getErrorHandler()
    {
        return this;
    }

    public FileTypeManager getFileTypeManager()
    {
        throw new RuntimeException( "NYI" );
    }

    public OptionsFolder getOptionsFolder()
    {
        return this;
    }

    public ResourceLoader getResourceLoader()
    {
        return this;
    }

    public ScriptSupport createScriptSupport( List<String> aClassPathEntries )
    {
        throw new RuntimeException( "NYI" );
    }

    // From SystemErrorHandler

    public void forgetConfirmationAnswers()
    {
    }

    public void onConfigurationError( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public Confirmation onFileReplaceConfirmation( String aFileName )
    {
        confirmations.append( "[frc]" );
        return Confirmation.YES;
    }

    public Confirmation onFileTypeInUseConfirmation( List<FileNameMatcher> aExtensions )
    {
        confirmations.append( "[ftiuc]" );
        return Confirmation.YES;
    }

    public Confirmation onFileTypeReplaceConfirmation( String aFileType )
    {
        confirmations.append( "[ftrc]" );
        return Confirmation.YES;
    }

    public void onScriptingError( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public void onSimpleSyntaxInstallFailed( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    // From ResourceLoader

    public boolean isAvailable( String aResourcePath )
    {
        return stream( aResourcePath ) != null;
    }

    public Reader read( String aResourcePath )
    {
        return new InputStreamReader( stream( aResourcePath ) );
    }

    public InputStream stream( String aResourcePath )
    {
        return getClass().getResourceAsStream( "/" + aResourcePath );
    }

    // From OptionsFolder

    public boolean fileExists( String aRelativeFileName )
    {
        return writtenFiles.contains( aRelativeFileName );
    }

    public String[] findConfigurations()
    {
        throw new RuntimeException( "NYI" );
    }

    public File getConfigurationFolder()
    {
        throw new RuntimeException( "NYI" );
    }

    public Icon loadIcon( String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public String makeFileName( String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public Reader readFile( String aRelativeFileName ) throws IOException
    {
        throw new RuntimeException( "NYI" );
    }

    public String readFileIntoString( String aRelativeFileName ) throws IOException
    {
        throw new RuntimeException( "NYI" );
    }

    public InputStream streamFile( String aRelativeFileName ) throws FileNotFoundException
    {
        throw new RuntimeException( "NYI" );
    }

    public void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException
    {
        writtenFiles.add( aRelativeFileName.replace( '\\', '/' ) );
    }
}
