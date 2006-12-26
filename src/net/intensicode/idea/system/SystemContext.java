package net.intensicode.idea.system;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;



/**
 * TODO: Describe this!
 */
public interface SystemContext
{
    OptionsFolder getOptionsFolder();

    ResourceLoader getResourceLoader();

    SystemErrorHandler getErrorHandler();

    FileTypeManager getFileTypeManager();

    TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes );
}
