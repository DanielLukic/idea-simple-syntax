package net.intensicode.idea.system;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;

import java.util.List;



/**
 * TODO: Describe this!
 */
public interface SystemContext
{
    OptionsFolder getOptionsFolder();

    ResourceLoader getResourceLoader();

    SystemErrorHandler getErrorHandler();

    FileTypeManager getFileTypeManager();

    ScriptSupport createScriptSupport( List<String> aClassPathEntries );

    TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes );
}
