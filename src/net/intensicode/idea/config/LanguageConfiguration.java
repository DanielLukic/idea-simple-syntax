package net.intensicode.idea.config;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;



/**
 * TODO: Describe this!
 */
public interface LanguageConfiguration
{
    Language getLanguage();

    IElementType getToken( Object aTokenId );

    SyntaxHighlighter getSyntaxHighlighter();
}
