package net.intensicode.idea.core;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;



/**
 * TODO: Describe this!
 */
public final class ProductionSystemContext implements SystemContext
{
    public ProductionSystemContext()
    {
    }

    // From SystemContext

    public final OptionsFolder getOptionsFolder()
    {
        return new ProductionOptionsFolder();
    }

    public final ResourceLoader getResourceLoader()
    {
        return new ProductionResourceLoader();
    }

    public final SystemErrorHandler getErrorHandler()
    {
        return new ProductionSystemErrorHandler();
    }

    public final FileTypeManager getFileTypeManager()
    {
        return FileTypeManager.getInstance();
    }

    public final TextAttributesKey createTextAttributesKey( final String aTokenID, final TextAttributes aAttributes )
    {
        return TextAttributesKey.createTextAttributesKey( aTokenID, aAttributes );
    }
}
