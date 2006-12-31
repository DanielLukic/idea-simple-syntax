package net.intensicode.idea.core;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.*;
import net.intensicode.idea.system.production.ProductionScriptSupport;

import java.io.File;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class FakeSystemContext implements SystemContext
{
    public FakeSystemContext( final Object aReferenceObject )
    {
        myReferenceObject = aReferenceObject;
        myOptionsFolder = new FakeOptionsFolder( myReferenceObject );
    }

    public FakeSystemContext( final Object aReferenceObject, final String aOptionsFolder )
    {
        myReferenceObject = aReferenceObject;
        myOptionsFolder = new FakeFileOptionsFolder( aOptionsFolder );
    }

    // From SystemContext

    public File getPluginFolder()
    {
        return new File( "config" );
    }

    public OptionsFolder getOptionsFolder()
    {
        return myOptionsFolder;
    }

    public ResourceLoader getResourceLoader()
    {
        return new FakeResourceLoader( myReferenceObject );
    }

    public SystemErrorHandler getErrorHandler()
    {
        return new FakeSystemErrorHandler();
    }

    public FileTypeManager getFileTypeManager()
    {
        throw new RuntimeException( "NYI" );
    }

    public final ScriptSupport createScriptSupport( final List<String> aClassPathEntries )
    {
        return new ProductionScriptSupport( this, aClassPathEntries );
    }

    public TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes )
    {
        return TextAttributesKey.find( aTokenID );
    }

    private final Object myReferenceObject;

    private final OptionsFolder myOptionsFolder;
}
