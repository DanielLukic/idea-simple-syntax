package net.intensicode.idea.core;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.*;
import net.intensicode.idea.system.production.ProductionScriptSupport;
import net.intensicode.idea.scripting.RubyContext;



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

    public final ScriptSupport getScriptSupport()
    {
        return new ProductionScriptSupport( this );
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

    public TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes )
    {
        return TextAttributesKey.find( aTokenID );
    }

    public FileTypeManager getFileTypeManager()
    {
        throw new RuntimeException( "NYI" );
    }



    private final Object myReferenceObject;

    private final OptionsFolder myOptionsFolder;
}
