package net.intensicode.idea.system.production;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.*;

import java.io.File;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class ProductionSystemContext implements SystemContext
{
    public ProductionSystemContext()
    {
    }

    // From SystemContext

    public File getPluginFolder()
    {
        final File pluginFolder = new File( PathManager.getPluginsPath(), "SimpleSyntax" );
        return new File( pluginFolder, "classes" );
    }

    public final OptionsFolder getOptionsFolder()
    {
        return myOptionsFolder;
    }

    public final ResourceLoader getResourceLoader()
    {
        return myResourceLoader;
    }

    public final SystemErrorHandler getErrorHandler()
    {
        return mySystemErrorHandler;
    }

    public final FileTypeManager getFileTypeManager()
    {
        return FileTypeManager.getInstance();
    }

    public final ScriptSupport createScriptSupport( final List<String> aClassPathEntries )
    {
        return new ProductionScriptSupport( this, aClassPathEntries );
    }

    public final TextAttributesKey createTextAttributesKey( final String aTokenID, final TextAttributes aAttributes )
    {
        return TextAttributesKey.createTextAttributesKey( aTokenID, aAttributes );
    }



    private final OptionsFolder myOptionsFolder = new ProductionOptionsFolder();

    private final ProductionResourceLoader myResourceLoader = new ProductionResourceLoader();

    private final ProductionSystemErrorHandler mySystemErrorHandler = new ProductionSystemErrorHandler();
}
